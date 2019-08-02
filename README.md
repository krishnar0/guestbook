# Guest Book

Guest book application provides users to create guest book entries and administration will approve or reject the entries.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* [H2 In Memory](https://www.h2database.com) - In memory database
* [git](https://git-scm.com/) - Free and Open-Source distributed version control system 
* [Bootstrap](https://getbootstrap.com) - User interface
* [Log4J](https://logging.apache.org/log4j/2.x/) - Logging API


## Running the application locally

To run the application follow the below steps.

- clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml
- Open Eclipse 
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
   - Select the project
- Choose the Spring Boot Application file (search for @SpringBootApplication)
- Right Click on the project and Run as Spring boot App
- After starting the server. open browser and hit the link http://localhost:9090/h2 to connect to the In memmory database
- Click on connect button and execute the below script to set up the initial tables for the user login(Admin/User).

insert into Guest_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED)
values (1, 'admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into Guest_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED)
values (2, 'user1', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into Guest_User (USER_ID, USER_NAME, ENCRYTED_PASSWORD, ENABLED)
values (3, 'user2', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1); 

 
insert into Guest_role (ROLE_ID, ROLE_NAME)
values (1,'ROLE_ADMIN');
 
insert into Guest_role (ROLE_ID, ROLE_NAME)
values (2,'ROLE_USER');
 

 
insert into user_role (USER_ID, ROLE_ID)
values (1,1, 1);
 
insert into user_role (USER_ID, ROLE_ID)
values (2,2, 2);

insert into user_role (USER_ID, ROLE_ID)
values (3,3, 2);

- open new browser tab and hit the url http://localhost:9090. login with below users.
	-- Admin : admin/123
	-- User	 : user1/123

Alternatively you can use the jar file to run the application and follow above to steps to run the application.

java -jar guestbooktest-0.0.1-SNAPSHOT.jar
```
## packages

- `models` — to hold our entities;
- `repositories` — to communicate with the database;
- `services` — to hold our business logic;
- `controllers` — to listen to the client;

- `resources/` - Contains all the static resources, templates and property files.
- `resources/css` - contains static resources such as css, js and images.
- `resources/templates` - contains server-side templates which are rendered by Spring.
- `resources/application.properties` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, database URLs etc, in this file.

- `test/` - contains unit and integration tests

- `pom.xml` - contains all the project dependencies