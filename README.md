# Tenisu API

Une API Spring-Boot qui expose des informations sur des joueurs de tennis et plusieurs statistiques : tailles, 
IMC et ratios de victoires.

---

## Sommaire

1. **[Pr�requis](#pr�requis)**
2. **[Installation & lancement](#installation--lancement)**
3. **[Ex�cution des tests](#ex�cution-des-tests)**
4. **[Documentation Swagger](#documentation-swagger)**
5. **[Fonctionnalit�s expos�es](#fonctionnalit�s-expos�es)**
6. **[Bonus](#bonus)**

---

## Pr�requis

| Outil        | Version minimale | Commande de v�rification |
| ------------ | ---------------- |--------------------------|
| **Java JDK** | 17               | `java -version`          |
| **Maven**    | 3.9              | `mvn -version`           |


---

## Installation & lancement

```bash
# Clonage
git clone https://github.com/saqqaoui/tenisu-api.git
cd tenisu-api

# Build
./mvnw clean package

# D�marrage
./mvnw spring-boot:run
# ou
java -jar target/tenisu-api-*.jar
```

L'API est alors disponible sur **[http://localhost:5000](http://localhost:5000)**.

---

## Ex�cution des tests

```bash
./mvnw test
```

* **Unitaires**: couvrent `StatsHeightService`, `StatsBmiService`, `StatsWinRatioService` =>
  `StatsHeightServiceTest`, `StatsBmiServiceTest`, `StatsWinRatioServiceTest`.
* **Int�gration**: d�marre un vrai contexte Spring avec un d�pot stub en m�moire pour tester `TennisPlayerController`=>
`TennisPlayerControllerIT`.

---

## Documentation Swagger

| URL                                           | Description             |
| --------------------------------------------- |-------------------------|
| `http://localhost:5000/swagger-ui/index.html` | Interface interactive   |
| `http://localhost:5000/v3/api-docs`           | Sp�cification OpenAPI 3 |

---

## Fonctionnalit�s expos�es

| Endpoint                                     | Verbe | Description                                             |
|----------------------------------------------|-------|---------------------------------------------------------|
| `/api/tennis-player/all`                     | GET   | Liste de tous les joueurs tri�s par rang (puis points). |
| `/api/tennis-player/{id}`                    | GET   | D�tail d'un joueur. 404 si inexistant.                  |
| `/api/tennis-player/all-by-gender?gender={M\F}`| GET   | *Bonus* Filtre par sexe.                                |
| `/api/stats`                                 | GET   | M�diane des tailles (tous pays).                        |
|                                              | +     | IMC moyen de l'ensemble des joueurs.                    |
|                                              | +     | Pays avec le meilleur ratio victoires/d�faites.         |
| `/api/stats/height/median/{countryCode}`     | GET   | *Bonus* Mdiane des tailles pour un pays.                |

### Services internes

| Service                | R�gle                                   |
| ---------------------- |-----------------------------------------|
| `StatsBmiService`      | L'IMC moyen est arrondi � 3 d�cimales). |


---

## Bonus

1. **Filtre par sexe**`/api/tennis-player/all-by-gender` : interroger rapidement l'effectif masculin ou f�minin.
2. **M�diane par pays** `/api/stats/median-height/{countryCode}` : connaitre la taille m�diane des joueurs d'un pays donn�.

---


**EN**

# Tenisu API

A Spring?Boot REST API that exposes tennis?player information together with several statistics: height, BMI and win ratios.

---

## Table of Contents

1. **[Prerequisites](#prerequisites)**
2. **[Installation & Run](#installation--run)**
3. **[Running the Tests](#running-the-tests)**
4. **[Swagger Documentation](#swagger-documentation)**
5. **[Available End?points](#available-end-points)**
6. **[Bonus Features](#bonus-features)**

---

## Prerequisites

| Tool         | Minimum�Version | Check command   |
| ------------ | --------------- | --------------- |
| **Java JDK** | 17              | `java -version` |
| **Maven**    | 3.9             | `mvn -version`  |


---

## Installation�& Run

```bash
# Clone
git clone https://github.com/saqqaoui/tenisu-api.git
cd tenisu-api

# Build
./mvnw clean package

# Start the service
./mvnw spring-boot:run
# or
java -jar target/tenisu-api-*.jar
```

The API will be available at **[http://localhost:5000](http://localhost:5000)**.

---

## Running the Tests

```bash
./mvnw test
```

* **Unit tests** � cover `StatsHeightService`, `StatsBmiService`, `StatsWinRatioService` (see `StatsHeightServiceTest`, `StatsBmiServiceTest`, `StatsWinRatioServiceTest`).
* **Integration tests** � start a full Spring context with an in?memory stub repository to test the controller (`TennisPlayerControllerIT`).

---

## Swagger Documentation

| URL                                           | Description        |
| --------------------------------------------- | ------------------ |
| `http://localhost:5000/swagger-ui/index.html` | Interactive UI     |
| `http://localhost:5000/v3/api-docs`           | Raw OpenAPI�3 spec |

---

## Available End?points

| End?point                                        | Verb | Description                                                |
| ------------------------------------------------ | ---- | ---------------------------------------------------------- |
| `/api/tennis-player/all`                         | GET  | List all players ordered by ranking (then points).         |
| `/api/tennis-player/{id}`                        | GET  | Single player details ��404 if the id does not exist.      |
| `/api/tennis-player/all-by-gender?gender={M\|F}` | GET  | *Bonus* � filter players by gender.                        |
| `/api/stats`                                     | GET  | Statistics root:                                           |
|                                                  |      | � height median (all countries)                            |
|                                                  |      | � average BMI of all players                               |
|                                                  |      | � country with best win/loss ratio                         |
| `/api/stats/height/median/{countryCode}`         | GET  | *Bonus* � height median for a specific ISO?3 country code. |

### Internal Services

| Service                | Key rule / processing                                          |
| ---------------------- | -------------------------------------------------------------- |
| `PlayerService`        | Fetch & basic rules (sort, gender filter, id lookup, grouping) |
| `StatsHeightService`   | Height median (global & by country)                            |
| `StatsBmiService`      | Average BMI (rounded to 3 decimals)                            |
| `StatsWinRatioService` | Win ratio and best country                                     |

---

## Bonus Features

1. **Filter by gender** � `/api/tennis-player/all-by-gender?gender=M|F` lets you quickly retrieve only male or female rosters.
2. **Median by country** � `/api/stats/height/median/{countryCode}` returns the median height for the players of a given country.

---
