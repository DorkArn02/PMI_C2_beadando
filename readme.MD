<p align="center">
    <img alt="logo" src="./src/main/resources/logo.png" height="500px">
</p>

# Egészségügyi nyilvántartó rendszer

> Páciensek tárolása és diagnózisok rögzítése egyszerűen.

## Tartalom

* [Általános leírás](#általános-leírás)
* [A program működése](#a-program-működése)
* [Objektumok felépítése](#objektumok-felépítése)
* [XML fájlstruktúrája](#xml-fájlstruktúra)
* [Használt maven dependenciák](#használt-maven-dependenciák)
* [Használt képek és ikonok](#használt-képek-és-ikonok)

##  Általános leírás

Ez egy egészségügyi nyilvántartó program, amely segítségével XML fájlban tárolhatunk adatokat páciensekről és a hozzájuk tartozó diagnózisokat.
Az applikáció Java programozási nyelven készült és ablakos alkalmazás.
A Java-ban a [Swing API](https://en.wikipedia.org/wiki/Swing_(Java)) segítségével lehetséges ablakos alkalmazásokat létrehozni, amelyek Windows illetve Linux alapú rendszereken is futnak.

## A program működése



## Objektumok felépítése

Az alábbi két táblázat bemutatja az objektumnak a felépítését és az itt felvázolt mezők rendelkeznek getter/setter függvényekkel.

### Páciens objektum (Patient.java)
| Páciens mező    | Leírás                |
|-----------------|-----------------------|
| **firstName**   | A páciens keresztneve |
| **lastName**    | A páciens vezetékneve |
| **bornDate**    | Születési dátum       |
| **zipCode**     | Páciens irányítószáma |
| **city**        | Páciens lakhelye      |
| **street**      | Utca                  |
| **houseNumber** | Házszám               |
| **motherName**  | Anyja neve            |
| **fatherName**  | Apja neve             |
| **bornPlace**   | Születési hely        |
| **phoneNumber** | Telefonszám           |
| **gender**      | Neme                  |

### Diagnózis objektum (Diagnosis.java)

| Diagnózis mező    | Leírás                  |
|-------------------|-------------------------|
| **name**          | A diagnózis megnevezése |
| **expertOpinion** | Szakértői vélemény      |

## XML fájlstruktúra

Az XML fájlok kezelésért az `xmlHandler.java` nevű osztály a felelős.

Az XML fájlban a `patient` tag tartalmazza a páciens adatait.

Az elsődleges kulcs a `tajNumber` attributúm, ami alapján lehet beazonosítani egy-egy személyt. Ez egy 9 számjegyű egyedi kód.

A páciens kórtörténetét a `medicalHistory` tag tartalmazza.

A diagnózisok ezen belűl találhatóak úgynevezett `diagnosis` tag-ek.

A diagnózisoknak az elsődleges kulcsa a `date` attributúm, formátuma `date="yyyy-MM-dd hh:mm"` ahol hh:mm órákat és perceket jelöl.

```xml
<patients>
    <patient tajNumber="xxxxxxxxx">
        <firstName>Keresztnév</firstName>
        <lastName>Vezetéknév</lastName>
        <bornDate>yyyy/MM/dd</bornDate>
        <zipCode>Irányítószám</zipCode>
        <city>Település</city>
        <street>Utca</street>
        <houseNumber>Házszám</houseNumber>
        <motherName>Anyja neve</motherName>
        <fatherName>Apja neve</fatherName>
        <bornPlace>Születési hely</bornPlace>
        <phoneNumber>Telefonszám</phoneNumber>
        <gender>Neme</gender>
        <medicalHistory>
            <diagnosis date="yyyy-MM-dd hh:mm">
                <name>Diagnózis neve</name>
                <expertOpinion>Szakértői vélemény</expertOpinion>
            </diagnosis>
        </medicalHistory>
    </patient>
</patients>
```

## Használt maven dependenciák

A Swing komponensek között található JDatePicker nem bizonyult megfelelőnek.
LGoodDatePicker komponens segítségével lehet egyszerre dátumot és időt bekérni a felhasználótól.

* **JCalendar**: https://mvnrepository.com/artifact/com.toedter/jcalendar
* **LGoodDatePicker**: https://mvnrepository.com/artifact/com.github.lgooddatepicker/LGoodDatePicker

## Használt képek és ikonok

* **Google Fonts**: *https://fonts.google.com/icons*
* **Pexels**: *https://www.pexels.com/hu-hu/*