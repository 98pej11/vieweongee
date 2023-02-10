FROM openjdk:11-jdk
# COPY gradlew .
# COPY gradle gradle
# COPY build.gradle .
# COPY settings.gradle .
# COPY src src
# RUN chmod +x ./gradlew
# RUN ./gradlew clean build
ARG JAR_FILE=build/libs/vieweongee.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar","app.jar"]
