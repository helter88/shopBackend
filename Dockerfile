FROM alpine:3 AS build
COPY ..
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build /target/shop-0.0.1-SNAPSHOT.jar shop.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","shop.jar"]

