import org.apache.commons.lang3.StringEscapeUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import org.mongodb.scala.bson.collection.immutable
import org.mongodb.scala.{Completed, MongoClient, Observer}

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

object Converter extends App {
  val client = MongoClient() // you can put the database url between parenthesis
  val db = client.getDatabase("docs")
  val colSyntax = db.getCollection("syntax")
  val colAddons = db.getCollection("addons")

  println("Dropping collection..")
  List(colSyntax, colAddons).foreach { col =>
    col.drop().subscribe(new Observer[Completed] {
      override def onError(e: Throwable): Unit = e.printStackTrace()

      override def onComplete(): Unit = println(
        "Dropped collection: " + col.namespace.getCollectionName + "; in database: " + col.namespace.getDatabaseName)

      override def onNext(result: Completed): Unit = {}
    })
  }

  val urlEvents = ("event", "https://docs.skunity.com/get/content.php?v=events")
  val urlConditions = ("condition", "https://docs.skunity.com/get/content.php?v=conditions")
  val urlEffects = ("effect", "https://docs.skunity.com/get/content.php?v=effects")
  val urlExpressions = ("expression", "https://docs.skunity.com/get/content.php?v=expressions")
  val urlTypes = ("type", "https://docs.skunity.com/get/content.php?v=types")

  case class AddonInfo(name: String, color: String)

  val elements = new ArrayBuffer[immutable.Document]()
  val addons = new ArrayBuffer[AddonInfo]()

  def normalize(text: String): String = {
    StringEscapeUtils.unescapeHtml4(
      Jsoup.clean(text, "", Whitelist.none(), new Document.OutputSettings().prettyPrint(false)))
      // Does these methods above even do anything.. I have to add a ton of additional escapes
      .replaceAll("\\\\\\\\", "xxescapedslashxx")
      .replaceAll("&lt;", "<")
      .replaceAll("&gt;", ">")
      .replaceAll("\\\\'", "'")
      .replaceAll("\\\\n", "\n")
      .replaceAll("xxescapedslashxx", "\\\\")
  }

  List(urlEvents, urlConditions, urlEffects, urlExpressions, urlTypes) foreach { case (syntaxType, url) =>
    print(s"Downloading from $url...\r")
    val doc = Jsoup.connect(url).get()

    doc.outputSettings(new Document.OutputSettings().prettyPrint(false))

    // <br>s (new lines in html) are ignored, only the text inside tags is read,
    // so we add an escaped new line to inside of each br tag, so it's <br>\n</br>, and new lines work properly
    doc.select("br").append("\\n")

    val loopElements = doc.select("div.doc-item").asScala
    loopElements.zipWithIndex.foreach { case (e, index) =>
      val title = e.select("span.card-title").first().text()
      val pattern = e.select("div.pattern").first().text()
      val addon = e.select("a.addon[href^=\"/syntax/search/addon:\"]").first().text()
      val desc = normalize(e.select("div.card-content.black-text").first().text())
      val exampleDivs = e.select("div.example").asScala
      val examples = exampleDivs.map(div => {
        val content = normalize(div.select("div.examples").first().html())
        val votes = div.select("span[style=\"display:block; line-height: 20px; font-size: 20px\"]").first().text().toInt
        immutable.Document("content" -> content, "votes" -> votes)
        // It's not possible to check who voted and what their vote was without database access,
        // but I think it would be nice to have an empty list with such data where we can append to eventually,
        // and perhaps don't copy votes from the docs at all, just start with zero score and an empty list,
        // it really depends on what you'd like to use this data for
      })
      val doc = immutable.Document(
        "type" -> syntaxType,
        "title" -> title,
        "pattern" -> pattern,
        "desc" -> desc,
        "addon" -> addon,
        "examples" -> examples
      )
      elements += doc
      if (!addons.exists(_.name == addon)) {
        val style = e.select("div.row.card-content.white-text").first().attr("style")
        val rgbIndex = style.indexOf("rgb")
        // if you want you can replace "" with "rgb(0, 148, 255)", as it's the default color if addon has no custom one
        addons += AddonInfo(addon, if (rgbIndex == -1) "" else style.substring(rgbIndex))
      }
      print(s"Downloading from $url... parsing ${index + 1}/${loopElements.length}...\r")
    }
    println(s"Downloading from $url... parsing ${loopElements.length}/${loopElements.length}... done")
  }
  print(s"Inserting ${elements.size} elements to database... ")

  val t = Thread.currentThread()
  colSyntax.insertMany(elements).subscribe(new Observer[Completed] {
    override def onError(e: Throwable): Unit = e.printStackTrace()

    override def onComplete(): Unit = {
      print(s"done\nInserting ${addons.size} addons to database... ")
      colAddons.insertMany(addons.map(a => immutable.Document("name" -> a.name, "color" -> a.color)))
        .subscribe(new Observer[Completed] {
          override def onError(e: Throwable): Unit = e.printStackTrace()

          override def onComplete(): Unit = {
            println("done")
            t.interrupt()
          }

          override def onNext(result: Completed): Unit = {}
        })
    }

    override def onNext(result: Completed): Unit = {}
  })
  try {
    Thread.sleep(10000) // Just so SBT with default configuration doesn't show 'thread exited' in the middle..
  } catch { // It simply looked too ugly to me lol
    case _: InterruptedException =>
  }
  //  colSyntax.find().subscribe(new Observer[scala.Document] {
  //    override def onError(e: Throwable): Unit = {}
  //
  //    override def onComplete(): Unit = {}
  //
  //    override def onNext(result: scala.Document): Unit =
  //      result.get[BsonArray]("examples").get.forEach(el =>
  //        println(el.asDocument().getString("content").getValue))
  //  })
}
