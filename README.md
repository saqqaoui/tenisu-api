# Tenisu API

Une API Spring-Boot qui expose des informations sur des joueurs de tennis et plusieurs statistiques : tailles, 
IMC et ratios de victoires.

---

## Sommaire

1. **[Prérequis](#prérequis)**
2. **[Installation & lancement](#installation--lancement)**
3. **[Exécution des tests](#exécution-des-tests)**
4. **[Documentation Swagger](#documentation-swagger)**
5. **[Fonctionnalités exposées](#fonctionnalités-exposées)**
6. **[Bonus](#bonus)**

---

## Prérequis

| Outil        | Version minimale | Commande de vérification |
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

# Démarrage
./mvnw spring-boot:run
# ou
java -jar target/tenisu-api-*.jar
```

L'API est alors disponible sur **[http://localhost:5000](http://localhost:5000)**.

---

## Exécution des tests

```bash
./mvnw test
```

* **Unitaires**: couvrent `StatsHeightService`, `StatsBmiService`, `StatsWinRatioService` =>
  `StatsHeightServiceTest`, `StatsBmiServiceTest`, `StatsWinRatioServiceTest`.
* **Intégration**: démarre un vrai contexte Spring avec un dépot stub en mémoire pour tester `TennisPlayerController`=>
`TennisPlayerControllerIT`.

---

## Documentation Swagger

| URL                                           | Description             |
| --------------------------------------------- |-------------------------|
| `http://localhost:5000/swagger-ui/index.html` | Interface interactive   |
| `http://localhost:5000/v3/api-docs`           | Spécification OpenAPI 3 |

---

## Fonctionnalités exposées

| Endpoint                                     | Verbe | Description                                             |
|----------------------------------------------|-------|---------------------------------------------------------|
| `/api/tennis-player/all`                     | GET   | Liste de tous les joueurs triés par rang (puis points). |
| `/api/tennis-player/{id}`                    | GET   | Détail d'un joueur. 404 si inexistant.                  |
| `/api/tennis-player/all-by-gender?gender={M\F}`| GET   | *Bonus* Filtre par sexe.                                |
| `/api/stats`                                 | GET   | Médiane des tailles (tous pays).                        |
|                                              | +     | IMC moyen de l'ensemble des joueurs.                    |
|                                              | +     | Pays avec le meilleur ratio victoires/défaites.         |
| `/api/stats/height/median/{countryCode}`     | GET   | *Bonus* Mdiane des tailles pour un pays.                |

### Services internes

| Service                | Règle                                   |
| ---------------------- |-----------------------------------------|
| `StatsBmiService`      | L'IMC moyen est arrondi à 3 décimales). |


---

## Bonus

1. **Filtre par sexe**`/api/tennis-player/all-by-gender` : interroger rapidement l'effectif masculin ou féminin.
2. **Médiane par pays** `/api/stats/median-height/{countryCode}` : connaitre la taille médiane des joueurs d'un pays donné.

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

| Tool         | Minimum Version | Check command   |
| ------------ | --------------- | --------------- |
| **Java JDK** | 17              | `java -version` |
| **Maven**    | 3.9             | `mvn -version`  |


---

## Installation & Run

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

* **Unit tests** – cover `StatsHeightService`, `StatsBmiService`, `StatsWinRatioService` (see `StatsHeightServiceTest`, `StatsBmiServiceTest`, `StatsWinRatioServiceTest`).
* **Integration tests** – start a full Spring context with an in?memory stub repository to test the controller (`TennisPlayerControllerIT`).

---

## Swagger Documentation

| URL                                           | Description        |
| --------------------------------------------- | ------------------ |
| `http://localhost:5000/swagger-ui/index.html` | Interactive UI     |
| `http://localhost:5000/v3/api-docs`           | Raw OpenAPI 3 spec |

---

## Available End?points

| End?point                                        | Verb | Description                                                |
| ------------------------------------------------ | ---- | ---------------------------------------------------------- |
| `/api/tennis-player/all`                         | GET  | List all players ordered by ranking (then points).         |
| `/api/tennis-player/{id}`                        | GET  | Single player details – 404 if the id does not exist.      |
| `/api/tennis-player/all-by-gender?gender={M\|F}` | GET  | *Bonus* – filter players by gender.                        |
| `/api/stats`                                     | GET  | Statistics root:                                           |
|                                                  |      | • height median (all countries)                            |
|                                                  |      | • average BMI of all players                               |
|                                                  |      | • country with best win/loss ratio                         |
| `/api/stats/height/median/{countryCode}`         | GET  | *Bonus* – height median for a specific ISO?3 country code. |

### Internal Services

| Service                | Key rule / processing                                          |
| ---------------------- | -------------------------------------------------------------- |
| `PlayerService`        | Fetch & basic rules (sort, gender filter, id lookup, grouping) |
| `StatsHeightService`   | Height median (global & by country)                            |
| `StatsBmiService`      | Average BMI (rounded to 3 decimals)                            |
| `StatsWinRatioService` | Win ratio and best country                                     |

---

## Bonus Features

1. **Filter by gender** – `/api/tennis-player/all-by-gender?gender=M|F` lets you quickly retrieve only male or female rosters.
2. **Median by country** – `/api/stats/height/median/{countryCode}` returns the median height for the players of a given country.

---
