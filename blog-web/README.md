# blog-web

>   A simple blog api service, using `Spring MVC`, `JDBC`, `MyBatis` and `Swagger2` so on.


### Spring MVC

Just see official documentation: https://spring.io/projects/spring-framework and https://docs.spring.io/spring-framework/docs/current/reference/html/web.html .

### Database migration

Using `flyway` as migration tool, its migration files located in [resources/db/migration](./src/main/resources/db/migration) directory. Official website: https://flywaydb.org/ .

### MyBatis

Half ORM framework for Java, using XML definitions to mapper one-or-more SQL statements. Its mapper files located in [resources/mappers](/src/main/resources/mappers) directory. Official website: https://mybatis.org/mybatis-3/ . 

### Swagger2 and API Documentation

A api documentation tool with beautiful ui. Official website: https://swagger.io/ .

Using the newest Java library [springfox](https://github.com/springfox/springfox) to generate api document. You can visit `http://127.0.0.1:16601/api/t-blog/swagger-ui/index.html` after running blog-web application.

### Others

Other resources such as cache, logging etc. See official guides: https://spring.io/guides .

