package propets.kafka.searcher.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

//@Configuration
public class KafkaTopicConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstraapAddress;
	
	//@Value("${cloudkarafka.topic}")
	@Value("${topicName}")
	private String topic;
	
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstraapAddress);
		return new KafkaAdmin(configs);
	}
	
	/*
	 * @Bean public NewTopic emailNotifTopic() { return new NewTopic(topic, 1,
	 * (short) 1); }
	 */
}
