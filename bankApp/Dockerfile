FROM adoptopenjdk/openjdk8:jre8u265-b01-ubuntu
LABEL bank-app="bank-app"
COPY ./target/bank-app-0.0.1-SNAPSHOT.jar /tmp
ADD ./wait-for-mysqldb.sh /tmp
RUN chmod 744 /tmp/bank-app-0.0.1-SNAPSHOT.jar &&\
    chmod 744 /tmp/wait-for-mysqldb.sh
WORKDIR /tmp
CMD ["java", "-jar", "./bank-app-0.0.1-SNAPSHOT.jar"]
#NOTA: el jar siempre tiene q estar actualizado