FROM openjdk:8u322-jdk

RUN apt-get update \
    && apt-get install -y supervisor --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
COPY uploads ./uploads
COPY home ./home

EXPOSE 80

COPY supervisord.conf /etc/supervisord.conf

COPY flag.txt /secretFlagTxtFileToHideBeforeXMLEntityReading.txt

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]
