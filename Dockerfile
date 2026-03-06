# MavenとJava17を使ってビルドする
FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 実行にはTomcatを使う
FROM tomcat:10-jdk17-openjdk-slim
# ビルドされたwarファイルをTomcatの実行ディレクトリにコピー
COPY --from=build /target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]