package com.diosaraiva.query;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

	private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
			.addModule(new JavaTimeModule())
			.build();

	public Object consumeKafka(ConsumerRecord<String, String> consumerRecord, Class<?> c) {

		try {

			System.out.println("Consumed message=[" + 
					consumerRecord + 
					"] with offset=[" + 
					consumerRecord.offset() + 
					"]");
			
			return OBJECT_MAPPER.readValue(consumerRecord.value(), c);

			/*
			JsonNode tree = OBJECT_MAPPER.readTree(consumerRecord.value());
			return OBJECT_MAPPER.treeToValue(tree, c);
			 */
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}

		return null;
	}
}