# connect-api
## Developing this API to learn and get better at Spring Boot.
### ![Progress](https://progress-bar.dev/45/?title=progress)



### <span style="color:green">project.properties &rarr;</span> 



|  Property                     | key   | value                    | Remark                                                                                                                                                                        |
|-------------------------------|-------|--------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Bcrypt Strength               | password.encoder.strength | 12                       | You can read up on it, if you don't know about how strength works in Bcrypt                                                                                                   |
| JWT Secret                    | jwt.secret  | provide-your-secret-here | Do not share this with anyone.                                                                                                                                                |
| JWT Expire Time(In minutes)   | jwt.expiry.minute  | 5                        | Self explanatory                                                                                                                                                              |
| DB URL                        | spring.datasource.url | provide-db-url-here      | URL to DB you need to connect                                                                                                                                                 |
| DB Username                   | spring.datasource.username | username-here            | DB Username                                                                                                                                                                   |
| DB Password                   | spring.datasource.password | password-here            | DB Password                                                                                                                                                                   |
| DB initialization on startup  | spring.jpa.hibernate.ddl-auto | update            | There are multiple ways you can provide value to this. Like  create, create-drop, validate, and update. You can explore how different values work if this is your first time. |
| To show sql queries in console | spring.jpa.show-sql  | true            | This will print different queries being run, you can avoid if you want.                                                                                                       |
| Hibernate dialect             | spring.jpa.properties.hibernate.dialect  | org.hibernate.dialect.MySQL5Dialect            | You can search for Hibernate Dialects                                                                                                                                         |
| Timezone                      | spring.jackson.time-zone  | UTC           | Timezone.                                                                                                                                                                     |
