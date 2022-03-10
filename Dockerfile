# Stage 1 Build
FROM openjdk:11 AS build

# Change work directory inside container
WORKDIR /app

# Copy repository to the container
COPY . .

# RUN mvn -f ./pom.xml clean package
RUN ./mvnw package -Dmaven.test.skip


# Stage 2 Package
FROM openjdk:11

# Copy builded app from previous "build" container to a new openjdk container
COPY --from=build /app/target/meteo-0.0.1-SNAPSHOT.jar /app/meteo.jar

# Define entripoint for running container
ENTRYPOINT ["java","-jar","/app/meteo.jar"]
