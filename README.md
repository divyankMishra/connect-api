# connect-api
Developing this API to learn and get better at Spring Boot.

Will Add details once it is done more than 70 percent.


project.properties --->

#Bcrypt Strength

password.encoder.strength=12

#Do not share this secret

jwt.secret=provide-your-secret-here

jwt.expiry.minute=5

spring.datasource.url=#database url here
spring.datasource.username=usernamehere
spring.datasource.password=passwordhere
spring.jpa.hibernate.ddl-auto=update   # read up on this if you don't know
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
spring.jackson.time-zone=UTC
