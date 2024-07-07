FROM bde2020/spark-submit:3.1.1-hadoop3.2

ENV SPARK_APPLICATION_JAR_LOCATION /app/customer_transaction_processor.jar
ENV SPARK_APPLICATION_MAIN_CLASS CustomerTransactionProcessor
ENV SPARK_APPLICATION_ARGS "kafka:9092 hbase:16000"

COPY customer_transaction_processor.jar /app/customer_transaction_processor.jar