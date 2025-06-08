package com.fnk.etl;

import com.fnk.data.enums.InputDataset;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.HashMap;
import java.util.Map;

public class SparkContext {

    public Map<InputDataset, Dataset<Row>> datasetMap;

    public SparkContext() {
        this.datasetMap = new HashMap<>();
    }

    public Dataset<Row> getDataset(InputDataset inputDataset){
        return datasetMap.get(inputDataset);
    }

    public void setDataset(InputDataset inputDataset, Dataset<Row> dataset){
        datasetMap.put(inputDataset, dataset);
    }

}
