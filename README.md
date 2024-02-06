# GiftBuddy

## Wstęp
GiftBuddy to aplikacja przeznaczona do tworzenia zbiórek pieniędzy na prezenty dla znajomych. Wykorzystuje Spring Boot 2.9, Maven oraz Java 11 do obsługi backendu, Angular 16.2 dla frontendu oraz Docker-compose do uruchomienia bazy danych.

## Wymagania
- Java 11
- Maven
- Node.js (dla Angulara)
- Docker oraz Docker Compose

## Porty domyślne
- Spring Boot: 8080
- Angular: 4200
- Docker-compose - PostgreSQL: 5432
- Docker-compose - Keycloak: 9999

## Instalacja środowiska deweloperskiego
Projekt GiftBuddy składa się z dwóch głównych części: backendu zbudowanego z użyciem Spring Boot oraz frontendu stworzonego przy pomocy Angulara. Baza danych i keycloak są zarządzane przez docker-compose.

### Uruchomienie Docker-compose
1. Przed uruchomieniem backendu, uruchom kontenery docker-compose, które zawierają bazę danych PostgreSQL oraz narzędzie Keycloak.
2. W katalogach `database` i `keycloak`zmień nazwy plików:
 - .env.database.template -> .env.database
 - .env.keycloak.template -> .env.keycloak

3. Następnie w każdym z katalogów wywołaj komendę `docker-compose up`, aby uruchomić potrzebne usługi.

### Instalacja Backendu
1. Upewnij się, że masz zainstalowane Java 11 i Maven.
3. Przejdź do katalogu głównego.
4. Uruchom `mvn clean install`, aby zbudować projekt.
5. Uruchom `java -jar target/giftbuddy-0.0.1-SNAPSHOT.jar`

### Instalacja Frontendu
1. Przejdź do katalogu angular.
2. Uruchom `npm install`, aby zainstalować zależności.
3. Uruchom `ng serve` aby uruchomić serwer developerski.
4. Przejdź do `http://localhost:4200/`, aby wyświetlić aplikację.

## Link (12.01.2024)
- https://black-dune-0bfc4a603.4.azurestaticapps.net/
