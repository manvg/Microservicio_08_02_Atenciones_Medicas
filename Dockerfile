FROM openjdk:21-ea-24-oracle

WORKDIR /app
COPY target/atenciones-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet_D37JY763WIFJOCGZ /app/oracle_wallet
EXPOSE 8081

CMD [ "java", "-jar", "app.jar" ]