# 1. ビルド環境
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
# pom.xml を使ってビルド
RUN mvn clean package -DskipTests

# 2. 実行環境
FROM tomcat:10-jdk17-openjdk-slim
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]