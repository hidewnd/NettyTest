# device mac mini M1
FROM meddream/jdk17:latest

COPY nettyTest.jar /app/netty-test.jar
WORKDIR /app

CMD ["java", "-jar", "netty-test.jar"]