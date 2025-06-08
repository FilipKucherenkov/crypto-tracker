package com.fnk.data.enums;

import com.fnk.data.model.HeartbeatMessageAvro;
import com.fnk.data.model.SubscriptionsMessageAvro;
import com.fnk.data.model.TickerMessageAvro;
import org.apache.avro.specific.SpecificRecordBase;

public enum InputDataset {

    TICKER("ticker", TickerMessageAvro.class),
    SUBSCRIPTIONS("subscriptions", SubscriptionsMessageAvro.class),
    HEARTBEAT("heartbeat", HeartbeatMessageAvro.class);

    public final String key;
    public final Class<? extends SpecificRecordBase> avroClazz;

    InputDataset(String key, Class<? extends SpecificRecordBase> avroClazz) {
        this.key = key;
        this.avroClazz = avroClazz;
    }
}
