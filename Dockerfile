FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build /app/target/shop-0.0.1-SNAPSHOT.jar shop.jar
COPY --from=build /app/src/main/resources/truststore.p12 /app/truststore.p12
EXPOSE 8080
ENTRYPOINT ["java","-jar","shop.jar"]
