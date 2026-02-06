# Viikkotehtava4Kotlin
---
## Navigointi Jetpack Composessa.

Navigointi Composessa perustuu deklaratiiviseen malliin, jossa käyttöliittymä reagoi reitityksen (routing) muutoksiin. Sen sijaan, että vaihtaisimme kokonaisia Activityja tai Fragmenteja, sovellus vaihtaa näytettäviä Composable-funktioita määritetyn reitin perusteella.

---

##Mitä ovat NavHost ja NavController.

- NavHost on Composable-funktio, joka toimii näyttämönä kaikille niille näkymille, joiden välillä navigoidaan.
- NavController on keskeinen API Jetpack Compose Navigationissa. Se pitää kirjaa sovelluksen tilasta ja ohjaa siirtymisiä.

---

## Sovelluksen navigaatiorakene.

- Routes: Sovelluksessa on kaksi päänäkymää, (ROUTE_HOME) ja (ROUTE_CALENDAR).
- Navigoiminen näkymien välillä: HomeScreen saa parametrinä onNavigateCalendar-funktion. Kun tätä funktiota kutsutaan, se suorittaa komennon navController.navigate(ROUTE_CALENDAR), joka käskee NavHostia siirtymään kalenterinäkymään. Kalenterista takaisin kotiin tapahtuu kun CalendarScreen saa onNavigateHome-funktion, joka suorittaa navController.popBackStack(). Tämä poistaa nykyisen näkymän (kalenterin) pinosta ja palaa edelliseen, joka on kotinäkymä.

---


