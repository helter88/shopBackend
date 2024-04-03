# ElecElite e-commerce electronics shop Backend part <br>

> See demo live [click here](https://elecelite.onrender.com/ "click to open link") <br>
> See frontend part: [Frontend Part](https://github.com/helter88/shopForntend "click to visit Github repository with frontend part")

## Functionalities of the ElecElite
- Registration
- Authorization by token
- Viewing products
- Viewing categories
- Searching products
- Admin panel for editing and adding products with images, categories,
- Scheduler to clean up orders from cart each day at 3:00 am
- Image compression
- Order service
- Send email with order confirmation
- Request validation
- deployment in cloud <br>
and more ...


## Tech

ElecElite is developed using following technologies: <br>

![image](https://img.shields.io/badge/21-Java-orange?style=for-the-badge) &nbsp;
![image](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring) &nbsp;
![image](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=liquibase&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white) &nbsp;


## Installation and run

### Requirements:
- Docker

### To run the application:
- write
``
mvn install
``
- To add database MySQL just run following command, and wait for containers to be pulled up and started.

``
docker compose up
``
- after write 
``
mvn spring-boot:run
``

## Rest-API Endpoints

Service url: [Swagger](https://shopbackend-4.onrender.com/swagger-ui/index.html "click to see swagger")
<img src="./readme_images/swagger-1.PNG" alt="swagger-1">
<img src="./readme_images/swagger-2.PNG" alt="swagger-2">
<img src="./readme_images/swagger-3.PNG" alt="swagger-3">