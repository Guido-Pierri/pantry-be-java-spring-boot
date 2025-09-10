# Getting Started

## Install dependencies


```shell
mvn dependency:resolve
```

Create a local database:
```shell
cd docker/local
docker build . --tag pantry-local-db-postgres
```
## Run the application

Start the db:
```shell
cd docker/local
docker run -p 5432:5432 pantry-local-db-postgres
```


Start the application:
```shell
mvn -Dspring-boot.run.profiles=local spring-boot:run
```

Or use the corresponding IntelliJ features.