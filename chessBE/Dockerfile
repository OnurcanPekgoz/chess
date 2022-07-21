FROM openjdk:17
EXPOSE 8080
RUN  mkdir -p /onurcan
WORKDIR /onurcan
COPY target/chess-be-1.0.jar ./chess-be-1.0.jar
ENTRYPOINT ["java","-jar","/onurcan/chess-be-1.0.jar"]