package com.diosaraiva.query.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.diosaraiva.parentlib.dto.demo.DemoEntityDto;
import com.diosaraiva.query.KafkaConsumer;

@Component
public class DemoTestConsumer
{
	@Autowired
	private KafkaConsumer kafka;

	@KafkaListener(topics = "nxtlvl-default-topic")
	public void consumeKafkaDemo(ConsumerRecord<String, String> consumerRecord) {

		try {
			DemoEntityDto demoEntityDto = (DemoEntityDto)kafka.consumeKafka(consumerRecord, DemoEntityDto.class);

			if(demoEntityDto != null) {
				System.out.println("WHOA");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		//				
	}
}