## Coinbase client
Simple Micronaut app with a single endpoint which establishes connection to Coinbase's websocket and then brodcasts messages to a Kafka topic.

### Socket endpoint

#### Opening a connection

To open a connection send an HTTP POST with optionally providing productIds 
```
curl -X POST http://localhost:8080/api/v1/exchange-connection \
     -H "Content-Type: application/json" \
     -d '{"connectionAction":"CONNECT","productIds":["ETH-USD","BTC-USD"]}
```


#### Closing a connection

```
curl -X POST http://localhost:8080/api/v1/exchange-connection \
     -H "Content-Type: application/json" \
     -d '{"connectionAction":"CLOSE_CONNECTION"}'           
```
