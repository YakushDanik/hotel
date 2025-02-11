# Hotel RESTful API

Это RESTful API приложение для управления отелями, разработанное в рамках технического задания. Приложение предоставляет возможность управлять отелями, их удобствами, а также выполнять поиск и аналитику.



## Используемые технологии

- **Язык программирования**: Java 17+
- **Фреймворк**: Spring Boot
- **База данных**: H2/MySQL/PostgreSQL
- **Управление зависимостями**: Maven
- **Миграции базы данных**: Liquibase
- **Документация API**: Swagger

## Запуск приложения

### Требования

- Установленный JDK 21
- Установленный Maven

### Шаги для запуска

1. Клонируйте репозиторий:

2. Соберите проект с помощью Maven:
   
```
mvn clean install
```
3. Запустите приложение(H2/MySQL/PostgreSQL):
```
mvn spring-boot:run
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

## Документация API
После запуска приложения, документация API будет доступна через Swagger UI:
http://localhost:8092/swagger-ui.html