FROM apache/spark:3.4.1

USER root

# Install Java 8
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    update-alternatives --set java /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV SPARK_HOME /opt/spark
ENV PATH $SPARK_HOME/bin:$PATH

# Set up Ivy cache directory
ENV IVY_HOME /tmp/.ivy2
RUN mkdir -p $IVY_HOME && chmod -R 777 $IVY_HOME

# Create a specific user for running Spark
RUN useradd -ms /bin/bash sparkuser
RUN chown -R sparkuser:sparkuser $SPARK_HOME

WORKDIR /app

# Copy the assembled JAR file
COPY target/scala-2.12/sparkrddsum-assembly-1.0.jar /app/

RUN chown -R sparkuser:sparkuser /app

USER sparkuser

CMD ["/opt/spark/bin/spark-submit", "--conf", "spark.jars.ivy=/tmp/.ivy2", "--class", "SparkRDDSum", "/app/sparkrddsum-assembly-1.0.jar"]