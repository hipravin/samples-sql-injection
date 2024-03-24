# samples-sql-injection

Sql injection post for blog https://hipravin.github.io/

It is not supposed to be executed as complete application, just to showcase 
different implementations.

Working with samples: 
1. start postgres

cd development/postgresql

docker-compose up -d

2. Apply db scripts

mvn flyway:migrate -Dflyway.password=admin -f pom.xml

3. Run individual tests class PostgreSqlTestsIT to check behavior against running PostgreSQL instance. Other tests are unit tests executed on embedded hsqldb instance.    
