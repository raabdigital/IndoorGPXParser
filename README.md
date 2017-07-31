# GPX formátum áttekintés
A GPX (GPS eXchange format) egy XML alapú nyílt szabvány, amely pontok, útvonalak és érdekes helyek leírására szolgál. 
A beltéri navigáció megvalósítására ezt a szabványt vettük alapul és fejlesztettük tovább olyan egyedi kiterjesztésekkel (XML elemekkel, továbbiakban: tag).

# GPX dokumentáció 
## Helyszín adatok
A beltéri tárlatvezetés alapdatainak leírása a gpx fájl <metadata> tag között található elemek szolgálnak:

```xml
<name>
```
Tárlat elnevezése, `<language>` tag által definiált, alapértelmezett nyelven

```xml
<description>
```
Tárlat leírása, `<language>` tag által definiált, alapértelmezett nyelven

```xml
<trackType>
```
Az épületen belüli tájékozódást elősegítő megoldás típusának azonosítója. Lehetséges értékei: MULTIMEDIA_GUIDE, MULTIMEDIA_GUIDE_WITH_BEACON

```xml
<language>
```
A tárlat alapértelmezett nyelve

```xml
<regioncode>
```
A tárlat helyszínül szolgáló régió azonosítója

```xml
<fullduration>
```
A tárlatvezetés becsült hossza másodpercben, az egyes érdekes helyeknél eltölthető időt figyelembe véve

```xml
<realduration>
```
A tárlatvezetés tényleges hossza másodpercben mérve, átlagos séta tempó mellett, az érdekes helyeknél eltöltött időt figyelmen kívül hagyva

```xml
<distancelong>
```
A tárlat méterben megadott hossza

```xml
<poicount>
```
Érdekes helyek (POI) száma

```xml
<strollerfriendly>
```
Logikai érték, leírja hogy az adott tárlat teljesíthető baba kocsival vagy sem


```xml
<imagemapfileid>
```
A beltéri tájékozódást elősegítő statikus térkép fájlt azonosítója. 

```xml
<maincoverimageid>
```
A tárlat fő borítóképének azonosítója

```xml
<coverimagelist>
	<coverimage>
	</coverimage>
<coverimagelist>
```
A tárlat további borítóképeinek azonosítói, tetszőleges hosszúságú listában definiálva.

```xml
<startLatitude> 
```
Helyszínének szélességi koordinátája

```xml
<startLongitude> 
```
Helyszínének hosszúsági koordinátája 

```xml
<countryName> 
```
Tárlat helyszínéül szolgáló ország megnevezése 

```xml
<author>
```
A tárlat tulajdonosának megnevezése

```xml
<tourTrls>
	<languageCode>
	<languageName>
	<name>
	<description>
	<startPlace>
	<translatedAudioExist>
	<translatedVideoExist>
	<translatedExternalVideoExist>
</tourTrls>
```
A tárlat alapadatinak nyelvfüggő mezői. A `<languageCode>` az adott nyelv kódját, a `<languageName>` a nyelv megnevezését tartalmazza. 
A`<name>, <description>, <startplace>` tagek az adott nyelvre lefordított szöveges értékeket tartalmazzák. 
A`<translatedAudioExist>, <translateVideoExist> <translatedExternalVideoExist>` tagek logikai értékek, meghatározzák, hogy az adott nyelven tartozik-e hang- és videó anyaga tárlathoz, illetve van-e aktív internet kapcsolat mellett megtekinthető videós tartalom.

## Érdekes helyek
Az érdekes helyeket a GPX fájl szabvány szerint `<wpt>` tag jelöli, az adott hely pozícióját pedig a `<wpt>` tag lat (latitude: szélesség) és lon (longitude: hosszúság) tulajdonságok értékei határozzák meg. Nincs ez másként az általunk definiált szabványban sem, azzal a különbséggel, hogy beltéri tárlatvezetés esetében a szélesség és hosszúsági értékek nem GPS koordinátát jelölnek, hanem a térképként szolgáló képfájl pixelei által behatárolt koordináta rendszerben elfoglalt pozíciót, így vizuálisan is meghatározható a felhasználók számára, hogy az adott érdekes hely hol foglal helyet az általuk látott térképen. Tárlat érdekes helyét leíró további tulajdonságok:

### Alapadatok
```xml
<time>
```
Eredeti GPX szabvány által is definiált, érdekes hely rögzítésének időpontját tartalmazó dátum mező. A tárlaton belül elfoglalt sorrendiség meghatározására is szolgál. 

```xml
<name>
```
Adott érdekes hely nevét tartalmazó mező, a helyszín alapadatainak részét képző `<language>` tag által definiált nyelven. A `<name>` tag az eredeti GPX szabványban is értelmezett.

```xml
<description>
```
Érdekes hely leírását tartalmazó mező, a helyszín alapadatainak részét képző `<language>` tag által definiált nyelven. A `<name> `tag az eredeti GPX szabványban is értelmezett.

### Kiterjesztések
Az érdekes helyekhez tartozó `<extensions>` tag Minden olyan további POI-t leíró információt tartalmaz, ami már nem képzi részét a GPX szabványnak. 

```xml
<poiid>
```
Érdekes hely egyedi azonosítója.

```xml
< poiimagelist >
		< poiimage >
</ poiimagelist >
```
Az adott érdekes helyhez tartozó kép fájlok azonosítójának, tetszőleges hosszúságú listája. 

<poiaudiofileid>
Érdekes helyhez tartozó audió fájl azonosítója, a helyszín alapadatainak részét képző <language> tag által definiált nyelven. Opcionális, nem minden POI-hoz tartozik hanganyag.

<poitranslatedvideofilename>
Érdekes helyhez tartozó videó fájl azonosítója, a helyszín alapadatainak részét képző <language> tag által definiált nyelven. Opcionális, nem minden POI-hoz tartozik videó.

<poitranslatedexternalvideolink>
Érdekes helyhez tartozó aktív internet kapcsolat esetén elérhető videó URL-jét, a helyszín alapadatainak részét képző <language> tag által definiált nyelven. Opcionális, nem minden POI-hoz tartozik videó URL.

<poitype>
 Érdekes hely típusa. Lehetséges értékei
•	VIEW_POINT: tényleges látványosságot vagy attrakciót jelöl
•	ADS: helyszínen található szolgáltatókat jelöl, pl.: ajándékbolt

```xml
<poitranslationlist>
	<poitranslation>
		<poilanguagecode>
		<poilanguagename>
		<poinametranslation>
		<poidescriptiontranslation>
		< poitranslatedaudifilename>
		< poitranslatedvideofilename>
		< poitranslatedexternalvideolink>
	</poitranslation>
</poitranslationlist>
```
Az érdekes helyhez tartozó nyelvfüggő adatokat tartalmazza. A `<poilanguagecode>` és `<poilanguagename>` az adott nyelv kódját és megnevezését, a `<poinametranslation>` és `<poidescriptiontranslation>` pedig a POI nevét és leírását tartalmazza. A `<poitranslatedaudifilename>,  <poitranslatedvideofilename>`, `<poitranslatedexternalvideolink>` tagek pedig az adott nyelvű hang- és videó anyagokat jelölik.

```xml
<poiimagedescriptionlist>
<poiimagedescription>
<filename>
<description>
</poiimagedescription>
</poiimagedescriptionlist>
```
A `<poiimagedescriptionlist>` lista az érdekes helyekhez tartozó képekhez tartozó leírásokat tartalmazza. A `<poiimagedescription>` gyerek tagjeiként definiált `<filename>` és `<description>` párosokkal vannak definiálva a kép leírás és fájlnév párosok.


### Beltéri pozíció meghatározáshoz kapcsolódó kiterjesztések
```xml
<beacondevices>
		<beacondevice>
		<beacondeviceuniqueid>
		<beacondevicename>
		<beacondevicemajor>
	</beacondevice>
</beacondevices>
```
Bluetooth alapú beacon eszközökkel támogatott beltéri navigáció leírására szolgáló tag-ek összessége. A `<beacondevices>` lista tetszőleges számú `<beacon>` eszközt leíró tag-ek tartalmazhat. A `<beacondeviceuniqueid>` a jeladó egyedi azonosítóját definiálja, a `<beacondevicename>` pedig az eszköz elnevezését, mindkettő fejlesztési feladatokat elősegítő tulajdonság, a beltéri pozíció tényleges meghatározása szempontjából nem fontosak. A `<beacondevicemajor>` alapján történik az eszköz POI-hoz történő hozzárendelése, tehát azonos POI-hoz tartozó jeladók major értéke megegyezik. Nagyobb területen elhelyezkedő tárlatok és egymástól távol eső POI esetében egyetlen beacon is elegendő lehet az azonosítására, közelebb elhelyezkedő érdekes pontoknál szükség lehet 2-3 jeladóra a jel áthallás miatt.
Szolgáltatóhoz kapcsolódó kiterjesztések
A következő tagek olyan POI-k esetében értelmezhetőek, ami nem látnivalók, hanem a helyszín területén található szolgáltatókat vagy eseményeket reprezentálnak.

```xml
<poicategory>
```
POI kategória kódját határozza meg, abban az esetben. Lehetséges típusra példák: POI_CATEGORY_RESTAURANT, POI_CATEGORY_MARKET, POI_CATEGORY_EVENT 

```xml
<poiphonenumber>
```
Esemény vagy szolgáltató telefonszáma.

```xml
<poiwebpage>
```
Esemény vagy szolgáltató weboldala.

```xml
<poifacebookpage>
```
Esemény vagy szolgáltató facebook oldalának elérhetősége.

```xml
<poiopeninghours>
```
Szolgáltató nyitvatartási ideje.

```xml
<eventstarttime>
```
Esemény kategóriájú POI esetében az esemény kezdődátuma.

```xml
<eventendtime>
```
Esemény kategóriájú POI esetében az esemény végdátuma.

```xml
<poiemail>
```
Szolgáltatóhoz vagy eseményhez kapcsolódó e-mail elérhetőség.

