package com.example.scf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.cloudevent.CloudEventMessageBuilder;
import org.springframework.cloud.function.cloudevent.CloudEventMessageUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.net.URI;
import java.util.function.Function;

@SpringBootApplication
public class SpringCloudFunctionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudFunctionDemoApplication.class, args);
	}

	private final Logger log = LoggerFactory.getLogger(SpringCloudFunctionDemoApplication.class.getName());

	@Bean
	public Function<Message<Person>, Message<Employee>> hire() {
		return personMessage -> {
			Person person = personMessage.getPayload();
			log.info("Received Person to hire: {}", person);

			if (CloudEventMessageUtils.isCloudEvent(personMessage)) {
				String specVersion = CloudEventMessageUtils.getSpecVersion(personMessage);
				String type = CloudEventMessageUtils.getType(personMessage);
				String id = CloudEventMessageUtils.getId(personMessage);
				URI source = CloudEventMessageUtils.getSource(personMessage);

				log.info("Received Person in Cloud Event: specVersion={}, type={}, id={}, source={}", specVersion, type, id, source);
			} else {
				log.info("Received Person in Generic Message");
			}

			Employee employee = new Employee(person);
			log.info("New Employee: {}", employee);
			return CloudEventMessageBuilder
					.withData(employee)
					.setId("56789")
					.setSource("https://example.dev/jcm/scf")
					.build();
		};
	}

}
