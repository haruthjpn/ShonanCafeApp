# MavenとJava17を使ってビルドする
FROM maven:3.8.4-openjdk-17 AS build
COPY . .
# もしフォルダの中に pom.xml があるなら、それを外に出す処理を追加
RUN if [ -f Test_Project/pom.xml ]; then mv Test_Project/* . ; fi
RUN mvn clean package -DskipTests
RUN mvn clean package -DskipTests

# 実行にはTomcatを使う
FROM tomcat:10-jdk17-openjdk-slim
# ビルドされたwarファイルをTomcatの実行ディレクトリにコピー
COPY --from=build /target/*.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]