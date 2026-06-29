package payment_processing_engine;

import org.springframework.boot.SpringApplication;

public class TestPaymentProcessingEngineApplication {

	public static void main(String[] args) {
		SpringApplication.from(PaymentProcessingEngineApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
