# 1. ビルド環境 (Java 21 対応の Maven を使用)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

# pom.xml を使ってビルド
RUN mvn clean package -DskipTests

# 2. 実行環境 (Java 21 対応の Tomcat 10)
FROM tomcat:10.1-jdk21-temurin-jammy
# ビルドされた .war ファイルを Tomcat に配置
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]