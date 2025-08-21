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
- **API Gateway (Spring Cloud Gateway MVC)** - implemented as the main gateway and serving as the single entry point to the backend. In this project, it handles routing to the microservices and integrates with `Keycloak` to control user access.
- **MongoDB** - a NoSQL database used by the event-service to store and manage event data.
- **MySQL** - used as a relational database in the purchase-service, stock-service, and for managing users in `Keycloak`. 
- **Spring Data JPA (Hibernate)** - used for managing data persistence and providing an abstraction layer over the database. Under the hood, it uses Hibernate as the default JPA provider to handle object-relational mapping (ORM).
- **Kubernetes** - is responsible for running and orchestrating all microservices and supporting components (such as `Kafka`, `Keycloak`, `MySQL`, and `MongoDB`). Through the provided manifests, it manages their lifecycle, injects configuration and credentials via ConfigMaps and Secrets, and ensures data persistence for databases using volumes. Each component runs in its own Pod with an associated Service, enabling stable communication across the cluster through internal DNS.
- **Docker** - used to containerize the application and all auxiliary services (including frontend, backend, databases, Kafka) in order to provide a consistent and easily runnable environment regardless of the local configuration.
- **Maven** - used as the build automation tool to efficiently manage project dependencies and automate the build process.
- **Testcontainers** - runs a real `MySQL` database in a container, enabling tests in production-like conditions.
- **WireMock** - simulates responses from external services, making tests independent of other microservices.
- **Mockito** - mocks components (`Kafka`), simplifying tests and removing the need for additional infrastructure.
- **Swagger** - used for interactive documentation and testing of REST API endpoints in the application's microservices.
- **Resilience4j** - used to increase the resilience of microservices, providing support for `Circuit Breaker` and `Retry`.
- **Lombok** - used to reduce boilerplate code in Java classes, such as getters, setters and constructors. 

## Architecture
 

## Run
- Make sure you have installed: `Java 17`, `Docker`, `Kind Cluster`, `Angular CLI`, `NPM`, `Node.js`.
- Clone the repository: `https://github.com/MichalTrzaska9/Ticketing.git`.
- Go to the project directory: `cd Ticketing`.
- Paste the Docker username and password into the `pom.xml` file.
- Execute the following command to build and package the backend services inside a Docker container:
```bash
mvn spring-boot:build-image
```
- Go to the `angular-frontend` directory and run the following commands to start the frontend application `http://localhost:4200` :  
```bash
npm install
npm run start
```
- Deploy the services:
```bash
kubectl apply -f kubernetes/manifests/apps
```
- Deploy the infrastucture:
```bash
kubectl apply -f kubernetes/manifests/platform
```

## Access
- Access to the API Gateway and Swagger `http://localhost:9000/swagger-ui.html` requires forwarding its service port to your local machine:
```bash
kubectl port-forward svc/gateway 9000:9000
```
- Access to the Keycloak Admin Console `http://localhost:8080` username `administrator`, password `administrator` requires forwarding its service port to your local machine:
```bash
kubectl port-forward svc/keycloak 8080:8080
```
- Access to the Kafka UI `http://localhost:18080` requires forwarding its service port to your local machine:
```bash
kubectl port-forward svc/kafka-ui 18080:8080
```

## Tests
To verify the application, a set of tests has been implemented, which can be executed using:
```bash
mvn test
```

## Author
Michał Trzaska
`michaltrzaska18@gmail.com`


