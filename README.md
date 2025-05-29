# crypto-tracker

### 🎯 Project Goals
- Ingest real-time market data (e.g., ETH-USD) from Coinbase WebSocket.
- Stream the data into Apache Kafka using a Java-based WebSocket client.
- Process the data with Apache Spark Structured Streaming (Java API).
- Calculate and monitor key market metrics in real time:
  - Price
  - Volume
  - Spread
  - Price volatility
- Trigger alerts when certain thresholds are exceeded (e.g., price drops 5% in 10s).
- Output results to a dashboard (Grafana via InfluxDB or REST API).

### 🏗️ Architecture Diagram

### 🛠 Extra info
#### Coinbase websocket
- To manually test websocket I used https://github.com/vi/websocat
- Websocket docs: https://docs.cdp.coinbase.com/exchange/docs/websocket-overview

Example:
```
 wss://ws-feed.exchange.coinbase.com
{"type":"subscribe","product_ids":["ETH-USD","ETH-EUR"],"channels":["level2","heartbeat",{"name":"ticker","product_ids":["ETH-BTC","ETH-USD"]}]}
```


