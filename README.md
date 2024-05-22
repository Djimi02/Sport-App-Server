# TeamTally's Server

Technologies: Spring Boot, Spring Boot JPA, Spring Boot Security(with JWT token authentication), JUnit, PostreSQL

This is the server side of the project. If you want to see the purpose of the project and the client side, you can check my other [repository](https://github.com/Djimi02/Sport-App-Client?tab=readme-ov-file).

NOTE: The current version of the application is just a demo having the main functionality.

General Purpose: This repository containts the code of the REST API that servers an Andorid app. The server is responsible for retrieving/persisting/deleting entities such as users, groups, members and games into/from the database. Furthermore, the server is responsible for authenticating users using JWT tokens.

### Entities

Follows the ER-diagram. Note that this is not the concrete ER-diagram but a generilized version of it.

<div align="center">
	<img width = "60%" src="https://github.com/Djimi02/Sport-App-Server/blob/main/images/ER-Diagram.png">
</div>

For each new sport, new tables for the sport specific member and stats are added, hence using TABLE_PER_CLASS inheritance strategy. Since group and game do not contain sport specific data (only contain references to it) all sports are in the same table, hence using SINGLE_TABLE inheritance strategy.

Stats class is used both for member statistics but also for game statistics. Hence, the statistics that are mapped from a member table are member statistics, and the statistics that are not mapped from a member table and at the same time they map to the game table are game statistics of a specific member for a specific game.

### Services
Each sport has its own service. The this service provides all the necessary functionality for storing, retriving and modelling sprot specific data for groups, members and games. Each sport specific service class implements the following [interface](https://github.com/Djimi02/Sport-App-Server/blob/main/project/src/main/java/com/example/project/service/interfaces/SportService.java). Short overview of the interface:

Group Management:
* Creating and Managing Groups: Allows for the creation of new groups associated with specific sports and users. Groups can be identified and retrieved by their unique IDs or UUIDs.
* Updating Group Information: Facilitates updating the group's name and deleting groups when they are no longer needed.

Member Management:
* Adding and Removing Members.
* Member Roles and Permissions: Enables assigning roles to members, such as admin, game maker, or regular member, to control their permissions within the group.
* Joining Groups: Provides mechanisms for users to join groups either as existing members or as new members.

Game Management:
* Adding, Managing and Deleting Games.
* Retrieving Game Statistics.

The interface is generic and designed to be implemented with sport-specific details, ensuring that the app can handle various sports with unique statistics and requirements.

### Controllers and Endpoints
The sport services are exposed via sport controllers. Similar to the sport services, sport controllers implement the following [interface](https://github.com/Djimi02/Sport-App-Server/blob/main/project/src/main/java/com/example/project/controller/SportController.java). Sport controller endpoints have simple structure. Each sport specific service endpoints start with "/\<sport>/". Then follows what do you need the service for. It could be for:
* "/\<sport>/group/"
* "/\<sport>/member/"
* "/\<sport>/game/"

Then follows the operation of the service with the required path variables and parameters. Some examples are:
* "/\<sport>/group/get/{id}"
* "/\<sport>/group/save/{name}/{userid}"
* "/\<sport>/group/join/new/{userid}/{groupid}"
* "/\<sport>/group/delete/{id}"

* "/\<sport>/member/save/{groupid}/{name}"
* "/\<sport>/member/role/admin/{id}"
* "/\<sport>/member/role/gamemaker/{id}"
* "/\<sport>/member/delete/{id}"

* "/\<sport>/game/get/gamestats/{id}"
* "/\<sport>/game/save"
* "/\<sport>/game/delete/{id}"

### Exception handling
Exceptions are handled via the class RestControllerExceptionHandler annotated with @ControllerAdvice. Upon exception in one of the controller classes, a ResponseEntity object is created, with the appropriate error code and message, and returned.

### Testing
Last but not least, several service and repository methods are tested using JUnit. 