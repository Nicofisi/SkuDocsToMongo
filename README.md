Some Scala code for scraping skUnity docs into a Mongo database

Tested using:
* Java 8
* Scala 2.12.3
* SBT 1.0.2

An example console result:
```
Dropped collection: addons; in database: docs
Dropped collection: syntax; in database: docs
Downloading from https://docs.skunity.com/get/content.php?v=events... parsing 312/312... done
Downloading from https://docs.skunity.com/get/content.php?v=conditions... parsing 143/143... done
Downloading from https://docs.skunity.com/get/content.php?v=effects... parsing 355/355... done
Downloading from https://docs.skunity.com/get/content.php?v=expressions... parsing 389/389... done
Downloading from https://docs.skunity.com/get/content.php?v=types... parsing 111/111... done
Inserting 1310 elements to database... done
Inserting 41 addons to database... done
```

An example of the end effect using command `mongo` on Linux:
```
> use docs
switched to db docs
> db.addons.find()
{ "_id" : ObjectId("59e3c3740a975a768bcef37e"), "name" : "MundoSK", "color" : "rgb(70, 132, 153);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef37f"), "name" : "Skellett", "color" : "rgb(100, 100, 100);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef380"), "name" : "Skript", "color" : "rgb(212, 78, 88);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef381"), "name" : "ExtrasSK", "color" : "rgb(111,84,153);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef382"), "name" : "SkDragon", "color" : "rgb(70, 132, 153);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef383"), "name" : "RandomSK", "color" : "rgb(136,89,23);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef384"), "name" : "Vixio", "color" : "rgb(100, 100, 100);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef385"), "name" : "TuSKe", "color" : "rgb(100, 100, 100);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef386"), "name" : "MineLuaSK", "color" : "rgb(21, 162, 214);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef387"), "name" : "SharpSK", "color" : "rgb(100, 100, 100);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef388"), "name" : "ExertSK", "color" : "rgb(76, 86, 175);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef389"), "name" : "sKeeland", "color" : "rgb(100, 100, 100);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef38a"), "name" : "Umbaska", "color" : "rgb(76, 86, 175);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef38b"), "name" : "SkQuery WildSkript", "color" : "rgb(34, 34, 34);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef38c"), "name" : "SkMorkaz", "color" : "rgb(0, 148, 255);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef38d"), "name" : "SkQuery", "color" : "rgb(34, 34, 34);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef38e"), "name" : "SkRayFall", "color" : "rgb(21, 162, 214);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef38f"), "name" : "skUtilities", "color" : "rgb(70, 132, 153);" }
{ "_id" : ObjectId("59e3c3740a975a768bcef390"), "name" : "Umbaska2", "color" : "" }
{ "_id" : ObjectId("59e3c3740a975a768bcef391"), "name" : "WildSkript", "color" : "rgb(76, 86, 175);" }
Type "it" for more
> db.syntax.find()
{ "_id" : ObjectId("59ef435e0a975a603f352be5"), "type" : "event", "title" : "Armor Stand Interact", "pattern" : "armor stand (interact|manipulate)", "desc" : "armor stand (interact|manipulate)", "addon" : "MundoSK", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352be6"), "type" : "event", "title" : "Armor Stand Place", "pattern" : "armor stand place", "desc" : "armor stand place", "addon" : "MundoSK", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352be7"), "type" : "event", "title" : "Arrow pickup", "pattern" : "[on] [skellett] arrow pickup", "desc" : "[on] [skellett] arrow pickup", "addon" : "Skellett", "examples" : [ { "content" : "on arrow pickup:\n\tcancel event\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352be8"), "type" : "event", "title" : "At Time", "pattern" : "at time", "desc" : "at time", "addon" : "Skript", "examples" : [ { "content" : "at 18:00\nat 7am in \"world\"\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 2 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352be9"), "type" : "event", "title" : "block break xp drop", "pattern" : "[on] block [break] (xp|exp|experience) [drop]", "desc" : "[on] block [break] (xp|exp|experience) [drop]", "addon" : "Skellett", "examples" : [ { "content" : "on block experience drop:\n    broadcast \"&6%dropped block xp%\"\n    set block xp to 10\n    broadcast \"&a%dropped block xp%\"\n    add 1 to block xp\n    broadcast \"&b%dropped block xp%\"\n    subtract 1 from block xp\n    broadcast \"&a%dropped block xp%\"\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bea"), "type" : "event", "title" : "Border Stabilize", "pattern" : "border stabilize [in %world%]", "desc" : "border stabilize [in %world%]", "addon" : "MundoSK", "examples" : [ { "content" : "on border stabilize in \"world\":\n    broadcast \"The Border is N0 longer moving!\"\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352beb"), "type" : "event", "title" : "Breeding", "pattern" : "[on] [skellett] bre[e]d[ing]", "desc" : "[on] [skellett] bre[e]d[ing]", "addon" : "Skellett", "examples" : [ { "content" : "on breeding:\n    broadcast \"%breed item used%\"\n    broadcast \"%breeder%\" #The entity that caused the breed\n    broadcast \"%final breeded entity%\" #The outcome of the breed\n    broadcast \"%breeding experience%\" #XP earned\n    add 100 to breeding experience\n    reset breeding experience #Reseting it, resets it to the original XP earned.\n    set breeding experience to 20\n    subtract 1 from breeding experience\n    add 2 to breeding experience\n    broadcast \"%breeding experience%\" #XP earned should be 21\n    broadcast \"%bred father%\"\n    broadcast \"%bred mother%\"\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 1 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bec"), "type" : "event", "title" : "Brewing stand fuel increase", "pattern" : "[on] brew[ing] [stand] fuel [increase]", "desc" : "[on] brew[ing] [stand] fuel [increase]", "addon" : "Skellett", "examples" : [ { "content" : "on brewing stand fuel increase:\n    broadcast \"%the brewing fuel power%\"\n    set brewing fuel power to 10\n    broadcast \"%the brewing fuel power%\"\n    broadcast \"%brewing stands consuming state%\"\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bed"), "type" : "event", "title" : "Chat Tab Complete", "pattern" : "on chat tab complete event-string last token completions", "desc" : "on chat tab complete event-string last token completions", "addon" : "MundoSK", "examples" : [ { "content" : "on chat tab complete: \n\tif the first character of event-string is \".\":\n\t\tloop all players:\n\t\t\tif loop-player has permission \"tabcomplete.notify\":\n\t\t\t\tsend \"%player% is attempting to do %event-string%\" to loop-player \n\n#Skript made by @SaudiArabian\n#Please do not claim this as yours however please feel free to use or edit the code\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bee"), "type" : "event", "title" : "Citizens - Click", "pattern" : "[on] (npc|citizen) click", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ { "content" : "on npc click:\n    cancel event\n    open \"CHEST\" with 2 rows named \"            &4&lMenu\" to player\n    set slot 4 of player's current inventory to stone named \"Example\"\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bef"), "type" : "event", "title" : "Citizens - Combust", "pattern" : "[on] (npc|citizen) (combust[ion]|ignition) (by|from) [a] block", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf0"), "type" : "event", "title" : "Citizens - Combust by entity", "pattern" : "[on] (npc|citizen) (combust[ion]|ignition) (by|from) [a[n]] entity", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ { "content" : "on npc combust from entity:\n    cancel event\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf1"), "type" : "event", "title" : "Citizens - Create", "pattern" : "[on] (npc|citizen) create", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf2"), "type" : "event", "title" : "Citizens - Damage by block", "pattern" : "[on] (npc|citizen) damage (by|from) [a] block", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf3"), "type" : "event", "title" : "Citizens - Damage by entity", "pattern" : "[on] (npc|citizen) damage (by|from) [a[n]] entity", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ { "content" : "on npc damage by entity:\n    cancel event\n    make npc event-npc look at event-entity\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf4"), "type" : "event", "title" : "Citizens - Death event", "pattern" : "[on] (npc|citizen) death", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf5"), "type" : "event", "title" : "Citizens - Despawn event", "pattern" : "[on] (npc|citizen) despawn", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf6"), "type" : "event", "title" : "Citizens - Ender pearl", "pattern" : "[on] (npc|citizen) ender[[ ]pearl] [teleport]", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf7"), "type" : "event", "title" : "Citizens - Entity collide", "pattern" : "[on] (npc|citizen) [entity] colli(sion|de)", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ ] }
{ "_id" : ObjectId("59ef435e0a975a603f352bf8"), "type" : "event", "title" : "Citizens - Entity target", "pattern" : "[on] entity target (npc|citizen)", "desc" : "Plugin required: Citizens", "addon" : "Skellett", "examples" : [ { "content" : "on entity target npc:\n    kill event-entity\n    broadcast \"Nobody targets bae!\" #:P\t\t\t\t\t\t\t\t\t\t\t\t", "votes" : 0 } ] }
Type "it" for more
```

**Note:** everywhere where there are escapes like `\n` it is just because Mongo shows it this way in console, in reality these are all actual new line characters, `&` characters, `<` characters etc.
