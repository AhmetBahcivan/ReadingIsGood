# ReadingIsGood

Started this project on the train. 
Reading carefully and catching crucial keywords made this project's requirements clear.

Second round of analysis was with an empty page and pencil. Drawing db model, use case diagram and evaluating functional requirements based on these models were the most important steps.

There was no need to seperate the services for such a small project. So I go with monolith app even though microservices sound so cool.
Tech stack is Spring boot application, data-jpa, jwt, junit, postgres, git, docker, swagger, postman.
I used relational db, postgres because of consistency requierement and familarity.

Then coding started with models, repos, services and controller.
I used git frequently by developing.
New order and update stock functions are implemented as Synchronized to avoid concurrent actions.

API endpoints can be seen from Swagger UI.
localhost:8080/swagger-ui.html

**Postgres credentials** is written in application.properties as follows
db name: readingisgod_db
spring.datasource.username=postgres
spring.datasource.password=Pstgrs
