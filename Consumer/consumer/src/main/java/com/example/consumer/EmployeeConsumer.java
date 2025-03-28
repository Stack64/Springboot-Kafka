package com.example.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.consumer.model.EmployeeVO;
import com.example.consumer.service.EmployeeProfile;
import com.example.consumer.util.SystemConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class EmployeeConsumer extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeConsumer.class);

	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	EmployeeProfile employeeProfile;
	
	public void run() {
		logger.info("Employee Consumer is running ");
		KafkaConsumer<String,String> kafkaConsumer=getKafkaConsumer();
		kafkaConsumer.subscribe(Collections.singleton(systemConfig.getTopicId1()));
		logger.info("Starting Employee Consumer...");
		try {
			while(true) {
				try {
					ConsumerRecords<String,String> records=kafkaConsumer.poll(Duration.ofMillis(
		                    Long.parseLong(systemConfig.getPollTimeOut())
			                ));;
					if(!records.isEmpty()) {
						logger.info("polling the Employee Records with Partitions: "+records.partitions());
						
						for(TopicPartition partition: records.partitions()) {
							List<ConsumerRecord<String,String>> partitionRecords=records.records(partition);
							
							for(ConsumerRecord<String,String> record : partitionRecords) {
								ObjectMapper objectMapper=new ObjectMapper();
								EmployeeVO employee=objectMapper.readValue(record.value(), EmployeeVO.class);
								Thread.currentThread().setName(employee.getTransactionid());
								employeeProfile.saveOrUpdate(employee);
								kafkaConsumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(record.offset()+1)));
								logger.info("Employee --> Transaction ID: "+ employee.getTransactionid() +" processed successfully for partition "+ record.partition()+" and offset "+ record.offset());
							}
						}
						
					}
				}catch(Exception e) {
					logger.error("Exception occur while comsuming data",e);
				}
			}
		}catch(Exception e) {
			logger.error("Exception occur while comsuming data",e);
		}
	}

	private KafkaConsumer<String, String> getKafkaConsumer() {
		Properties props=new Properties();
		String kafkaServer= systemConfig.getKafkaServer();
		kafkaServer=kafkaServer.replaceAll("\\s", ",");
		props.put("bootstrap.servers", kafkaServer);
		props.put("group.id", systemConfig.topicId1);
		props.put("client.id", String.valueOf(System.nanoTime()));
		props.put("enable.auto.commit", systemConfig.kafkaAutoCommit);
		props.put("max.poll,records", systemConfig.kafkaMaxPollRecords);
		props.put("session.timeout.ms",systemConfig.kafkaSessionTimeoutms);
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("max.poll.interval.ms",systemConfig.kafkaMaxPollInterval);
		
		KafkaConsumer<String,String> kafkaConsumer=new KafkaConsumer<>(props);
		return kafkaConsumer;
	}
}
