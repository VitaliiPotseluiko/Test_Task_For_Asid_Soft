# Book Manager

RESTful API based Spring Boot that handles book list and includes CRUD operations(creating, reading, updating, deleting) for Book entity.
## 📁 Architecture
The project follows the following package structure:
* `config:` Contains configuration files.
* `controller:` Contains the controllers.
* `dto:` Contains data transfer objects used for data encapsulation and transfer between different layers of the 
application.
* `exception:` Contains custom exception classes.
* `model:` Contains the database model used to represent data entities and map database records to Java objects.
* `repository:` Contains the data access layer responsible for accessing and manipulating data in the database.
* `service:` Contains the services responsible for business logic and interaction between controllers and repositories.
## 🛠 Used technologies:

- Java 17 version
- Spring Boot 3.3.3 version
- Spring Data JPA
- MapStruct tool
- H2 database
- Junit-5
- Mockito
