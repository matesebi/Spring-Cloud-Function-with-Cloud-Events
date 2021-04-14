# Spring Cloud Function with Cloud Events

This repo showcase how to reproduce a bug in Spring Cloud Function `CloudEventMessageUtils`.

Steps to reproduce:
1 Run RabbitMQ and Kafka:
  ```shell
  docker-compose up -d
  ```
1 Run the app
  ```shell
  ./mvnw spring-boot:run
  ```
1 Go to RabbitMQ management UI: http://localhost:15672/#/exchanges/%2F/hire-in-0
  * Log in with user/bitnami
  * Publish a message:
    * Headers:
      ```
      cloudEvents:specversion=1.0
      cloudEvents:id=0001
      cloudEvents:type=com.example.scf.Person
      cloudEvents:source=localhost/testing
      ```
    * Payload:
      ```json
      {
       "firstName": "Cloud",
       "lastName": "Events"
      }
      ```
1 Look at the app logs:
  ```
  2021-04-14 10:57:13.385 DEBUG 156391 --- [qSJ_Z_SHFemXQ-1] c.f.c.c.BeanFactoryAwareFunctionRegistry : Invoking function hire<org.springframework.messaging.Message<com.example.scf.Person>, org.springframework.messaging.Message<com.example.scf.Employee>>
  2021-04-14 10:57:13.434 DEBUG 156391 --- [qSJ_Z_SHFemXQ-1] c.f.c.c.BeanFactoryAwareFunctionRegistry : Converted Message: GenericMessage [payload=byte[57], headers={amqp_receivedDeliveryMode=PERSISTENT, amqp_receivedExchange=hire-in-0, amqp_deliveryTag=1, ce-specversion=1.0, deliveryAttempt=1, amqp_consumerQueue=hire-in-0.anonymous.xyAiGfWWTqSJ_Z_SHFemXQ, amqp_redelivered=false, target-protocol=kafka, ce-source=localhost/testing, ce-i=0001, ce-type=example.scf.Person, message-type=cloudevent, id=47f23eb0-f341-5c84-5374-af34dd48ffe1, amqp_consumerTag=amq.ctag-ydEI_bjSL1uXQS2KFPkczQ, sourceData=(Body:'[B@40b11831(byte[57])' MessageProperties [headers={cloudEvents:type=example.scf.Person, cloudEvents:specversion=1.0, cloudEvents:source=localhost/testing, cloudEvents:i=0001}, contentLength=0, receivedDeliveryMode=PERSISTENT, redelivered=false, receivedExchange=hire-in-0, receivedRoutingKey=, deliveryTag=1, consumerTag=amq.ctag-ydEI_bjSL1uXQS2KFPkczQ, consumerQueue=hire-in-0.anonymous.xyAiGfWWTqSJ_Z_SHFemXQ]), contentType=application/json, timestamp=1618390633382}] to: org.springframework.cloud.function.context.catalog.SimpleFunctionRegistry$OriginalMessageHolder@311ca203
  2021-04-14 10:57:13.434  INFO 156391 --- [qSJ_Z_SHFemXQ-1] c.e.s.SpringCloudFunctionDemoApplication : Received Person to hire: Person{firstName='Cloud', lastName='Events'}
  2021-04-14 10:57:13.438  INFO 156391 --- [qSJ_Z_SHFemXQ-1] c.e.s.SpringCloudFunctionDemoApplication : Received Person in Cloud Event: specVersion=null, type=null, id=null, source=null
  2021-04-14 10:57:13.438  INFO 156391 --- [qSJ_Z_SHFemXQ-1] c.e.s.SpringCloudFunctionDemoApplication : New Employee: Employee{employeeNumber=0, firstName='Cloud', lastName='Events'}
  ```
