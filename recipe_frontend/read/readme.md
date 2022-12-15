# Student Recipe App

This repository contains the code for our student recipe app that has a Next.js frontend and a Spring Boot/Kotlin backend.

[Live Demo](https://student-recipes.vercel.app)

## Notice
Ausgehend von unserem Gespräch am Freitag den 9.12. wo wir sie nach einem Server gefragt hatten. Konnten sie uns diesen leider nicht anbieten da dieser gerade nicht funktioniert hatte. Draufhin hatten wir uns darauf geeignet das wir auch von einem Github Repository deployen dürfen. Im Github worklfow werden nun für jeden Push die Tests ausgeführt und im anschluss das Backend auf Digital Ocean hochgeladen und das Frontend auf Vercel.

## Overview

The frontend of the app is built using Next.js and React. It connects to the backend API to fetch and display data. The backend is a Spring Boot/Kotlin REST API that uses a PostgreSQL database to store data.

To run the tests for the backend, you will need Docker installed and running on your machine. The tests will create a temporary PostgreSQL database for testing, which will be deleted after the tests are finished. Images are stored on Google Cloud Storage.

You can view a live demo of the app at https://sea-turtle-app-hqisk.ondigitalocean.app (backend API) and https://student-recipes.vercel.app (frontend).

### Frontend
- NextJs Version 13 (13.0.6)
- NodeJs Version 18.9.1

### Backend
- Spring Boot with Kotlin
- Java Version 17.0.5.jdk

## Quick Start

To run the project on your local machine, follow these steps:

#### Frontend
1. Install the latest version of Node.js (Version 18.9.1).
2. cd into recipe_frontend: `cd recipe_frontend`
2. Install the dependencies: `npm install`
3. To run: `npm run dev`
4. Open http://localhost:3000 in your web browser to access the frontend app.


### Backend
1. Import into Intellij
2. To run: `./gradlew bootRun`
3. To run the tests: `./gradlew test`
- Docker has to be installed and running (A temporary PG Database gets created for each test run)
4. The backend API will be available at http://localhost:8080
