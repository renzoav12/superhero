FROM openjdk:11
EXPOSE 8080
ADD /target/superhero.jar superhero.jar
ENTRYPOINT ["java", "-jar", "superhero.jar"]
