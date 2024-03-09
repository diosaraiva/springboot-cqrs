package com.diosaraiva.command;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
			.addModule(new JavaTimeModule())
			.enable(SerializationFeature.INDENT_OUTPUT)
			.build();

	@Value("${spring.kafka.template.default-topic}")
	String defaultTopic;

	@Transactional
	public <E> void sendKafka(E obj, String key, HashMap<String,String> headers, String... topics) {

		this.raiseEvent(obj, key, headers, topics);
	}

	private <O> void raiseEvent(O obj, String key, Map<String,String> headers, String... topics) {

		try {

			String data = OBJECT_MAPPER.writeValueAsString(obj);
			Set<ProducerRecord<String, String>> producerRecords = new HashSet<ProducerRecord<String, String>>();

			if(topics == null) {

				ProducerRecord<String, String> producerRecord = new ProducerRecord<>(defaultTopic, key, data);

				producerRecords.add(producerRecord);
			}
			else {

				Arrays.stream(topics).forEach(topic -> {

					ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, data);

					producerRecords.add(producerRecord);
				});
			}

			producerRecords.forEach(producerRecord -> {

				if(headers != null)
					headers.entrySet().forEach(
							header -> producerRecord.headers().add(
									header.getKey(), 
									header.getValue().getBytes(StandardCharsets.UTF_8)));

				ListenableFuture<SendResult<String, String>> future = 
						this.kafkaTemplate.send(producerRecord);

				future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

					@Override
					public void onSuccess(SendResult<String, String> result) {

						System.out.println("Sent message=[" + 
								producerRecord + 
								"] with offset=[" + 
								result.getRecordMetadata().offset() + 
								"]");
					}

					@Override
					public void onFailure(Throwable ex) {

						System.out.println("Unable to send message=[" + 
								producerRecord +
								"] due to : " +
								ex.getMessage());
					}
				});
			});

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
