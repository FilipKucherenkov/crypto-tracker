package com.fnk;

import com.fnk.data.enums.InputDataset;
import com.fnk.etl.SparkContext;
import com.fnk.etl.StreamDataLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.streaming.Trigger;

import java.util.concurrent.TimeoutException;

public class App {
    public static void main(String[] args) throws TimeoutException, StreamingQueryException {
        SparkSession sparkSession = SparkSession.builder()
                .appName("TickerMetricsApp")
                .master("local[*]")
                .getOrCreate();

        SparkContext sparkContext = new SparkContext();
        StreamDataLoader streamDataLoader = new StreamDataLoader(sparkSession, sparkContext);
        streamDataLoader.loadInputs(InputDataset.TICKER);

        sparkContext.getDataset(InputDataset.TICKER).writeStream()
                .format("console")
                .trigger(Trigger.ProcessingTime("5 seconds"))
                .outputMode("append")
                .option("truncate", false)
                .start()
                .awaitTermination();
    }
}