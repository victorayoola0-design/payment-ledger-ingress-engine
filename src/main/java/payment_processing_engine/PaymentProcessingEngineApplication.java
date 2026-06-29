package payment_processing_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "payment_processing_engine")
public class PaymentProcessingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentProcessingEngineApplication.class, args);
	}

}
