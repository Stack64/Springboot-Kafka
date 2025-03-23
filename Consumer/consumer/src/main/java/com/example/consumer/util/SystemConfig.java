package com.example.consumer.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	public String kafkaServer;
	@Value("${group.id.topic1}")
	public String topicId1;
	@Value("${topic1.partitions}")
	public String topic1Partitions;
	@Value("${enable.auto.commit}")
	public String kafkaAutoCommit;
	@Value("${auto.offset.reset}")
	public String kafkaOffSet;
	@Value("${max.poll.records}")
	public String kafkaMaxPollRecords;
	@Value("${session.timeout.ms}")
	public String kafkaSessionTimeoutms;
	@Value("${max.poll.interval.ms}")
	public String kafkaMaxPollInterval;
	@Value("${poll.timeout.ms}")
	public String pollTimeOut;
	
	public String getTopicId1() {
		return topicId1;
	}
	public String getKafkaServer() {
		return kafkaServer;
	}
	public String getTopic1Partitions() {
		return topic1Partitions;
	}
	public String getKafkaAutoCommit() {
		return kafkaAutoCommit;
	}
	public String getKafkaOffSet() {
		return kafkaOffSet;
	}
	public String getKafkaMaxPollRecords() {
		return kafkaMaxPollRecords;
	}
	public String getKafkaSessionTimeoutms() {
		return kafkaSessionTimeoutms;
	}
	public String getKafkaMaxPollInterval() {
		return kafkaMaxPollInterval;
	}
	public String getPollTimeOut() {
		return pollTimeOut;
	}
}
