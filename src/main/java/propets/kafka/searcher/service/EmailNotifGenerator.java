package propets.kafka.searcher.service;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import propets.kafka.searcher.dto.EmailListAndPostDto;

@Service
public class EmailNotifGenerator {
	
	@Autowired
	private KafkaTemplate<String, EmailListAndPostDto> kafkaTemplate;
	
	//@Value("${spring.cloud.stream.bindings.output.destination}")
	//@Value("${cloudkarafka.topic}")
	@Value("${topicName}")
	private String topic;// = "usy6o5vk-emailnotif";
	
	//@Value("${spring.cloud.stream.bindings.output.group}")
	//@Value("${kafkaf-group}")
	//private String groupId;
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	//@SendTo(Source.OUTPUT)
	public void sendToEmailNotification(EmailListAndPostDto emailListAndPostDto) throws JsonProcessingException {
		/*
		Properties settings = new Properties();
		settings.put("client.id", "emailnotif-producer");
		settings.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		settings.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		settings.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		final KafkaProducer<String, EmailListAndPostDto> producer = new KafkaProducer<>(settings);
		
		final ProducerRecord<String, EmailListAndPostDto> record = 
				new ProducerRecord<>(topic, emailListAndPostDto);
		 
		
		System.out.println(record.key() + " " + record.topic() + " " + record.value());
		
		producer.send(record);
		//producer.flush();
		producer.close();
		*/
				
		Message<EmailListAndPostDto> message = MessageBuilder
				.withPayload(emailListAndPostDto)
				.setHeader(KafkaHeaders.TOPIC, topic)
				.build();
		
		System.out.println("***" + topic + " " + emailListAndPostDto);
		ListenableFuture<SendResult<String, EmailListAndPostDto>> future = 
				kafkaTemplate.send(message);
				//kafkaTemplate.send(topic, emailListAndPostDto);
		
		System.out.println("===== " + future.isDone());
		
		future.addCallback(new ListenableFutureCallback<SendResult<String, EmailListAndPostDto>>() {
			@Override
	        public void onSuccess(SendResult<String, EmailListAndPostDto> result) {
	            System.out.println("Sent message=[" + emailListAndPostDto.getPostId() + 
	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	            System.out.println("Unable to send message=[" 
	              + emailListAndPostDto.getPostId() + "] due to : " + ex.getMessage());
	        }
		});
		
	}

}
