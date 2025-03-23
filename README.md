
---
## 🛠️ **Tech Stack**

| Technology        | Version       |
|------------------|--------------|
| Spring Boot      | 3.4.4        |
| JDK             | 21           |
| PostgreSQL      | 15           |
| Kafka           | 2.13-3.9.0   |


---
## 🚀 How It Works
1. The **Producer Service** sends messages to a Kafka topic.  
2. The **Consumer Service** listens to the topic, reads messages, and stores them in a **PostgreSQL database**.  

### 🔹 **Producer Service**
- Connects to Kafka broker
- Publishes messages to a configured topic
- Uses KafkaProducer API

### 🔹 **Consumer Service**
- Subscribes to the Kafka topic
- Reads messages using KafkaConsumer API
- Inserts messages into the database

    
---

## 🛠️ **Setup & Run Instructions**

### ✅ *1. Install & Start Kafka**
Ensure **Kafka** and **Zookeeper** are running on your system and Create an Kafka Topic.


```sh
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka Broker
bin/kafka-server-start.sh config/server.properties

# Create an Kafka Topic 
kafka-topics.bat --create --topic employee-profile-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1

