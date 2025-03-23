package com.example.producer.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.producer.ApplicationProperties;
import com.example.producer.interceptor.CustomInterceptor;
import com.example.producer.kafka.KafkaProducerSetup;
import com.example.producer.model.EmployeeMasterVO;
import com.example.producer.model.PostResponse;
import com.example.producer.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	ApplicationProperties applicationProperties;
	
	@Autowired
    EmployeeRepository employeeRespository;

	@Autowired
    KafkaProducerSetup kafkaProducerSetup;

	@PostMapping("/addProfile")
	public ResponseEntity<PostResponse> addProfile(HttpServletRequest request, @RequestBody
			Map<String,Object> requestBody) throws Exception{
		EmployeeMasterVO emp=new EmployeeMasterVO();
		ObjectMapper objectmapper=new ObjectMapper();
		String employeeJson= objectmapper.writeValueAsString(requestBody);
		
		logger.info("employeeJson: "+employeeJson.toString());
		try {
			
			Map<String,Object> trimedRequestBody= trimedRequestBody(requestBody);
			
			emp.setPernr((String) trimedRequestBody.get(applicationProperties.getUserId()));
			emp.setEname((String) trimedRequestBody.get(applicationProperties.getEmployeeName()));
			emp.setDomainid((String) trimedRequestBody.get(applicationProperties.getDomainid()));
			emp.setPernrmgr((String) trimedRequestBody.get(applicationProperties.getManagerUserId()));
			emp.setEmailid((String) trimedRequestBody.get(applicationProperties.getEmailId()));
			emp.setTransactionid(CustomInterceptor.getThreadId());
			Optional<EmployeeMasterVO> existEmployee= employeeRespository.findByPernr(emp.getPernr());
			Boolean empActive=false;
			if(existEmployee.isPresent()) {
				EmployeeMasterVO existingEmployee=existEmployee.get();
				emp.setRequest_time(existingEmployee.getRequest_time());
				if(existingEmployee.isActive()==true) {
					empActive=true;
				}
			}else {
				emp.setRequest_time(Timestamp.from(Instant.now()));
			}
			String checkYesOrNo= (String) trimedRequestBody.get(applicationProperties.getActiveField());
			
			if(checkYesOrNo ==null || checkYesOrNo.isEmpty()) {
				emp.setActive(true);
			}else if("Yes".equalsIgnoreCase(checkYesOrNo)) {
				emp.setActive(true);	
			}else if("No".equalsIgnoreCase(checkYesOrNo)) {
//				emp.setActive(false);	
			}else {
				emp.setActive(empActive);
			}
			
			ObjectMapper objectMapperKafka=new ObjectMapper();
			String employeeJsonKafka=objectMapperKafka.writeValueAsString(emp);
			logger.info("employee details: "+employeeJsonKafka.toString());
			sendMessageToKafkaEmployeeProfileTopic(employeeJsonKafka);
			logger.info("Employee Inserted");
			return ResponseEntity.ok(new PostResponse(HttpStatus.OK, "Employee added successfully"));
		}catch (Exception e) {
			logger.error("Error in addProfile method: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PostResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add employee"));
		}		
	}

	private void sendMessageToKafkaEmployeeProfileTopic(String employeeJsonKafka) {
        KafkaProducer<String, String> producer = kafkaProducerSetup.getProducer();
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(applicationProperties.getEmployeeProfileTopic(), employeeJsonKafka);

            // Send message asynchronously
            Future<RecordMetadata> future = producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                	logger.info("Error sending message to Kafka: " + exception.getMessage());
                } else {
                    logger.info("Message sent to topic: " + metadata.topic() +
                                       ", partition: " + metadata.partition() +
                                       ", offset: " + metadata.offset());
                }
            });
            future.get();
        } catch (Exception e) {
            logger.info("Kafka message sending failed: " + e.getMessage());
        } finally {
            producer.flush();
            producer.close();
        }
	}

	private Map<String, Object> trimedRequestBody(Map<String, Object> requestBody) {
        Map<String,Object> trimedMap=new HashMap<>();
        for(Map.Entry<String, Object> entry :requestBody.entrySet()) {
        	String trimedKey=entry.getKey().trim();
        	Object value =entry.getValue();
        	
            if(value instanceof String) {
            	String trimedValue = ((String) value).trim();
            	trimedMap.put(trimedKey, value);
            }
        }
		return trimedMap;
	}
}
