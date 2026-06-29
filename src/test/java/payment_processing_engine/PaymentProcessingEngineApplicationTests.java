package payment_processing_engine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class PaymentProcessingEngineApplicationTests {

	@Autowired(required = false)
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired(required = false)
	private StringRedisTemplate redisTemplate;

	@Test
	void contextLoads() {
	}


}
