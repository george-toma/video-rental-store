# Video Rental Store Java Example

Simple Java 9 example using SpringBoot 2.0.3.RELEASE, Spring Data and Neo4j Desktop 1.1.8
for modelling a video rental store use case.

## Prerequisites

* Java 9.0 
* Apache Maven 3.3+
* Neo4j Desktop 1.1.8
    * creates a Neo4j Desktop project with **user:neo4j** and **password:1234** 

## Building and Running

```
mvn clean package
java -jar target\video-rental-store-1.0.0-SNAPSHOT.jar
```
## Running the tests
Automated tests implemented with Spring JUnit Runner and embedded graph database (
[GraphDatabase.java](./src\test\java\com\video\rental\store\graph\GraphDatabase.java))
```
mvn test
```

## API
API URL                               | HTTP Method  | Response Type   | Description                                    |
--------------------------------------| -------------|-----------------|------------------------------------------------
/store/rent/film?titles=Matrix,Akira  | GET          | application/json| Find films                                     |
/store/rent/film                      | POST         | application/json| Rent films and calculate the renting price     |
/store/rent/film                      | PUT          | application/json| Return rented films and calculate extra charge |

## API Requests

* POST
```
{
    "filmsToRent": [
        {
            "title": "Matrix 11",
            "rentingPeriod": 1
        },
        {
            "title": "Spider Man",
            "rentingPeriod": 5
        },
         {
            "title": "Spider Man 2",
            "rentingPeriod": 2
        },
         {
            "title": "Out of Africa",
            "rentingPeriod": 7
        }
    ],
    "customerName": "Michelle Rano"
}
```

* PUT
```
{
    "filmsToRent": [
        {
            "title": "Matrix 11",
            "rentStartingDate": "2018-07-22"
        },
        {
            "title": "Spider Man",
            "rentStartingDate": "2018-07-22"
        },
         {
            "title": "Spider Man 2",
            "rentStartingDate": "2018-07-22"
        },
         {
            "title": "Out of Africa",
            "rentStartingDate": "2018-07-22"
        }
    ],
    "customerName": "Michelle Rano"
}
}
```
## Diagrams
- Find films diagram
```puml 
Client -> API: findFilms(titles)
API -> API: validateRequest(json)
alt successful validation
API -> Neo4jGraph: queryDatabase(titles)
Neo4jGraph -> API: returnResult()
API->Client:returnResult(json)
else failure validation
API -> Client: returnError(errorCode)
end
```
- Rent films diagram
```puml 
Client -> API: rentFilms(titles, rentingPeriod, customerName)
API -> API: validateRequest(json)
alt successful validation
API -> Neo4jGraph: queryDatabase(titles)
API -> Neo4jGraph: saveCustomer(customerName)
API -> Neo4jGraph: calculateTotalPrice(customerName)
Neo4jGraph -> API: returnResult()
API->Client:returnResult(json)
else failure validation
API -> Client: returnError(errorCode)
end
```

- Return rented films diagram
```puml 
Client -> API: returnRentFilms(titles, rentStartingDate, customerName)
API -> API: validateRequest(json)
alt successful validation
API -> Neo4jGraph: queryDatabase(titles,rentStartingDate,customerName)
API -> Neo4jGraph: calculateExtraCharge(customerName)
Neo4jGraph -> API: returnResult()
API->Client:returnResult(json)
else failure validation
API -> Client: returnError(errorCode)
end
```

## References
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data Neo4j](https://projects.spring.io/spring-data-neo4j/)
* [Neo4j](https://neo4j.com/)
* [Neo4j Developer](https://neo4j.com/developer/)
* [Neo4j Cypher manual](https://neo4j.com/docs/developer-manual/current/cypher)
* [Neo4j Desktop](https://neo4j.com/developer/guide-neo4j-desktop/)

## Authors
* George Toma

