# TeamTally's Server

Technologies: Spring Boot, Spring Boot JPA, Spring Boot Security(with JWT token authentication), JUnit, PostreSQL

This is the server side of the project. If you want to see the purpose of the project and the client side, you can check my other [repository](https://github.com/Djimi02/Sport-App-Client?tab=readme-ov-file).

NOTE: The current version of the application is just a demo having the main functionality.

General Purpose: This repository containts the code of the REST API that servers an Andorid app. The server is responsible for retrieving/persisting/deleting entities such as users, groups, members and games into/from the database. Furthermore, the server is responsible for authenticating users using JWT tokens.

### Entities

Follows the ER-diagram. Note that this is not the concrete ER-diagram but a generilized version of it.

<div align="center">
	<img width = "33%" src="https://github.com/Djimi02/Sport-App-Server/blob/main/images/ER-Diagram.png">
</div>

For each new sport, new tables for the sport specific member and stats are added, hence using TABLE_PER_CLASS inheritance strategy. Since group and game do not contain sport specific data (only contain references to it) all sports are in the same table, hence using SINGLE_TABLE inheritance strategy.

Stats class is used both for member statistics but also for game statistics. Hence, the statistics that are mapped from a member table are member statistics, and the statistics that are not mapped from a member table and at the same time they map to the game table are game statistics of a specific member for a specific game.

### Exception handling
Exceptions are handled via the class RestControllerExceptionHandler annotated with @ControllerAdvice. Upon exception in one of the controller classes, a ResponseEntity object is created, with the appropriate error code and message, and returned.

### Testing
Last but not least, several service and repository methods are tested using JUnit. 