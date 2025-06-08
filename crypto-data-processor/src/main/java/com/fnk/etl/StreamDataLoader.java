package com.fnk.etl;

import com.fnk.data.enums.InputDataset;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.avro.functions.from_avro;
import static org.apache.spark.sql.functions.col;

public class StreamDataLoader {

    private final SparkSession sparkSession;
    private final SparkContext sparkContext;

    public StreamDataLoader(SparkSession sparkSession, SparkContext sparkContext) {
        this.sparkSession = sparkSession;
        this.sparkContext = sparkContext;
    }

    public void loadInputs(InputDataset... inputDatasets){
        Dataset<Row> kafkaRaw = load();

        for(InputDataset inputDataset: inputDatasets){
            Dataset<Row> dataset = kafkaRaw.filter(col("key").startsWith(inputDataset.key));
            Dataset<Row> decoded = decodeAvroDs(dataset, inputDataset.avroClazz);
            sparkContext.setDataset(inputDataset, decoded);
        }
    }


    private Dataset<Row> load(){
        Dataset<Row> kafkaRaw = sparkSession.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "coinbase-messages")
                .load();

        return kafkaRaw.withColumn("key", col("key").cast("string"));
    }


    private <T> Dataset<Row> decodeAvroDs(Dataset<Row> dataset, Class<T> clazz){
        try {
            String avroSchema = clazz.getMethod("getClassSchema")
                    .invoke(null)
                    .toString();

            return dataset
                    .select(from_avro(col("value"), avroSchema).as("value"))
                    .select("value.*");

        } catch (Exception e) {
            throw new RuntimeException("Failed to get Avro schema from class: " + clazz.getName(), e);
        }
    }

}
