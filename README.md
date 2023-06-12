# Cinema Booking API Challenge

## Learning Objectives
- Use Spring Boot, Spring Data JPA and Hibernate to build a RESTful API
- Use object-oriented programming to manage source code complexity
- Use an API client to test-drive code

## Instructions

It's time to use everything you've learned up to this point! Your task is to build a complete API in Java using Spring Boot, Spring Data JPA and Hibernate. Treat this challenge like a real project, not just an exercise to complete. Take time to read documentation, experiment & discuss ideas with your peers and teachers.

1. Fork this repository
2. Clone your fork to your machine
3. Open the project in IntelliJ
4. Navigate to the `src/main/resources/` dirctory and rename the `application.yml.example` file to `application.yml`
    - Create a new PostgreSQL database for this exercise, a cloud database such as ElephantSQL is fine
    - Replace the `DATABASE_URL`, `DATABASE_USERNAME` and `DATABASE_PASSWORD` sections in the .yml file with your real database information. An example of what the file should look like is below
5. Your task is to develop the API that [satisfies this API spec](https://boolean-uk.github.io/java-api-cinema-challenge/). Create your source code in the `com.booleanuk.api.Cinema` package

Pay close attention to the details of each endpoint. How you choose to implement the solution is up to you, there are no wrong answers, but the inputs and outputs must match the provided API documentation exactly.

[You'll find an entity relationship diagram here.](./assets/entity-relationship-diagram.png) Use it to help you design your models.

Example application.yml file:

```yml
server:
  port: 4000
  error:
    include-message: always
    include-binding-errors: always
    include-stacktrace: never
    include-exception: false

spring:
  datasource:
    url: jdbc:postgresql://tfke.db.elephantsql.com/dkshfirn
    username: dkshfirn
    password: PFif40fj-eb6btfiF*k3nfEobyGaH
    max-active: 3
    max-idle: 3
  jpa:
    hibernate:
      ddl-auto: create-drop
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
    show-sql: true
```

## Extensions

[Here is an extension API spec.](https://boolean-uk.github.io/java-api-cinema-challenge/extensions)

It contains a few new routes, different approaches to data mutation and, most importantly, an entirely different response format for each request.

## Useful Resources

You'll need to do a fair amount of research in order to complete this challenge. Make liberal use of StackOverflow, search engines, YouTube and the teaching team.

Here are some reference documentation links that will be useful:

- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
- https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html#preface