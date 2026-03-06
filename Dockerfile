# 1. ビルド用の環境（MavenとJava17）
FROM maven:3.8.4-openjdk-17 AS build
COPY . .

# 【重要】フォルダ階層がずれていても pom.xml を自動で探し出して一番上に持ってくる設定
RUN find . -name "pom.xml" -exec dirname {} + | xargs -I {} sh -c 'cp -r {}/* . 2>/dev/null || true'

# ビルド実行（設計図が見つかった状態で実行されます）
RUN mvn clean package -DskipTests

# 2. 実行用の環境（Tomcat）
FROM tomcat:10-jdk17-openjdk-slim
# ビルドされた .war ファイルを Tomcat の公開フォルダに ROOT.war として配置
COPY --from=build /target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]