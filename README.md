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
