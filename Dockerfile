# 1. ビルド環境
FROM maven:3.8.4-openjdk-17 AS build
COPY . .

# pom.xmlがある場所を自動で探し、そのフォルダに移動してビルドする
RUN export POM_PATH=$(find . -name "pom.xml" | head -n 1) && \
    export PROJECT_DIR=$(dirname $POM_PATH) && \
    cd $PROJECT_DIR && \
    mvn clean package -DskipTests && \
    mkdir -p /target_out && \
    cp target/*.war /target_out/

# 2. 実行環境 (Tomcat)
FROM tomcat:10-jdk17-openjdk-slim
# ビルドした war ファイルをコピー
COPY --from=build /target_out/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]