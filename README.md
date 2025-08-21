# Ticketing 
Ticketing is a distributed microservices-based web application designed for managing and purchasing event tickets. It consists of multiple backend services, an API gateway, and an Angular frontend. The system follows a microservices architecture to separate concerns between business logic, data persistence, and user interaction.

This project was created for educational purposes as part of self-directed learning in software development. At the same time, it was designed with potential future business use in mind, making it a solid foundation for possible commercial expansion

![Gif](https://github.com/MichalTrzaska9/Watch-Store/blob/d90aaf995f035dc6c57c0711607d72ea308e26b3/Watch_Store.gif)

## Technologies Used
- **Java 17** - used as the main programming language for the backend layer. As a long-term support (LTS) release, it provides ongoing updates and bug fixes, ensuring long-term stability. Java 17 is a mature and stable version, well suited for production-grade applications. 
- **Spring Boot** - used to build backend microservices, chosen for its simplicity in setting up production-ready Java applications. It reduces boilerplate configuration and improves code readability.
- **Angular** - used to build the frontend application. In the project, the frontend handles login via Keycloak, displays the list of events and allows adding new ones, and enables ticket purchases through the purchase-service.
- **Keycloak** - configured as the central identity and access management system. Handles user registration and login.
- **Apache Kafka** - powers asynchronous communication between microservices. For example, `purchase-service` publishes ticket purchases and `alerts-service` consumes them to generate notifications.
- **MongoDB** - a NoSQL database used by the event-service to store and manage event data.
- **MySQL** - used as a relational database in the purchase-service, stock-service, and for managing users in Keycloak. 
- **Spring Data JPA (Hibernate)** - used for managing data persistence and providing an abstraction layer over the database. Under the hood, it uses Hibernate as the default JPA provider to handle object-relational mapping (ORM).
- **Kubernetes** - is responsible for running and orchestrating all microservices and supporting components (such as Kafka, Keycloak, MySQL, and MongoDB). Through the provided manifests, it manages their lifecycle, injects configuration and credentials via ConfigMaps and Secrets, and ensures data persistence for databases using volumes. Each component runs in its own Pod with an associated Service, enabling stable communication across the cluster through internal DNS.
- **Gradle** - used as the build automation tool to efficiently manage project dependencies and automate the build process. It simplifies configuration by integrating plugins for Java, Spring Boot and application packaging. In my opinion, it's more readable and understandable than Maven.
- **JUnit 5, Mockito, Spring Boot Test** - these testing tools were used to write and run unit and integration tests. JUnit 5 offers a modern testing platform, while Mockito helps with mocking dependencies, and Spring Boot Test simplifies application context testing.
- **Lombok** - used to reduce boilerplate code in Java classes, such as getters, setters and constructors. 

## System Features
The online store project defines three types of users:
- **Administrator** - has access to the admin panel, from which they manage user accounts, the watch catalog, available brands and orders.
- **Customer** - manages their own account, can change their password and personal information, add products to the cart and places orders.
- **Unregistered User** - can browse the catalog and has the ability to log in and register in the system. 

## Setup and Run
- Clone the repository: `git clone https://github.com/MichalTrzaska9/Watch-Store.git`
- Go to the project directory: `cd Watch-Store`
- The project is configured to use a PostgreSQL database by default. Update your `application.properties` with the correct database credentials.
- Run the project: `gradlew bootRun`
- Navigate to the store homepage: http://localhost:8080/watches

## Sample Data
The application generates sample data for watches, brands and users.
- Administrator (login: `kowalski@wp.pl`, password: `a`)
- Customer (login: `nowak@wp.pl`, password: `a`)

## Tests
To verify the application's correctness, both unit tests and integration tests are implemented.
- To run the tests, execute the command: `gradlew test`
- The test report is available in the file at the following path: `build/reports/tests/test/index.html`

## Author
Michał Trzaska
`michaltrzaska18@gmail.com`


