package com.diosaraiva.query.demo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.diosaraiva.parentlib.dto.demo.DemoEntityDto;
import com.diosaraiva.query.KafkaConsumer;
import com.diosaraiva.query.demo.entity.DemoEntity;
import com.diosaraiva.query.demo.entity.mapper.DemoEntityMapper;
import com.diosaraiva.query.demo.service.DemoEntityService;

@Component
public class DemoEntityConsumer
{
	@Autowired
	private KafkaConsumer kafka;

	@Autowired
	DemoEntityService demoEntityService;

	private DemoEntityMapper demoEntityMapper = Mappers.getMapper(DemoEntityMapper.class);

	@KafkaListener(topics = "nxtlvl-demo-create")
	public void createDemo(ConsumerRecord<String, String> consumerRecord) {

		try {
			DemoEntityDto demoEntityDto = (DemoEntityDto)kafka.consumeKafka(consumerRecord, DemoEntityDto.class);

			if(demoEntityDto != null) {
				DemoEntity demoEntity = demoEntityMapper.DemoEntityDtoToDemoEntity(demoEntityDto);

				/*
				DemoEntity demoEntity = DemoEntity.builder()
						.id(demoEntityDto.getId())
						.time(demoEntityDto.getTime())
						.message(demoEntityDto.getMessage())
						.host(demoEntityDto.getHost())
						.build();
				 */

				demoEntity = demoEntityService.addDemo(demoEntity);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@KafkaListener(topics = "nxtlvl-demo-update")
	public void updateDemo(ConsumerRecord<String, String> consumerRecord) {

		try {
			DemoEntityDto demoEntityDto = (DemoEntityDto)kafka.consumeKafka(consumerRecord, DemoEntityDto.class);

			if(demoEntityDto != null) {
				DemoEntity demoEntity = demoEntityMapper.DemoEntityDtoToDemoEntity(demoEntityDto);

				demoEntity = demoEntityService.updateDemo(demoEntity);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@KafkaListener(topics = "nxtlvl-demo-delete")
	public void deleteDemo(ConsumerRecord<String, String> consumerRecord) {

		try {
			DemoEntityDto demoEntityDto = (DemoEntityDto)kafka.consumeKafka(consumerRecord, DemoEntityDto.class);

			demoEntityService.deleteDemo(demoEntityDto.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@KafkaListener(topics = "nxtlvl-demo-deleteAll")
	public void deleteAllDemos(ConsumerRecord<String, String> consumerRecord) {

		try {
			demoEntityService.deleteAllDemos();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}