package com.diosaraiva.command.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diosaraiva.command.KafkaProducer;
import com.diosaraiva.command.demo.entity.DemoEntity;
import com.diosaraiva.command.demo.entity.mapper.DemoEntityMapper;
import com.diosaraiva.command.demo.service.DemoEntityService;
import com.diosaraiva.command.demo.service.DemoTestService;
import com.diosaraiva.parentlib.dto.demo.DemoEntityDto;

@RestController
@RequestMapping("/test")
public class DemoTestController 
{
	@Autowired
	DemoTestService demoTestService;

	@Autowired
	DemoEntityService demoEntityService;

	private DemoEntityMapper demoEntityMapper = Mappers.getMapper(DemoEntityMapper.class);

	@Autowired
	private KafkaProducer kafka;

	@GetMapping
	public ResponseEntity<DemoEntityDto> generateDemoEntity(Optional<Integer> i) 
	{
		DemoEntityDto demoEntityDto = 
				demoEntityMapper.DemoEntityToDemoEntityDto(
						demoTestService.generateDemoEntity(i.orElse(0)));

		return ResponseEntity.ok(demoEntityDto);
	}

	@PostMapping
	public ResponseEntity<List<DemoEntityDto>> fetchDemos(@RequestBody List<DemoEntityDto> receivedListDemoEntityDto) {

		try {

			List<DemoEntityDto> listDemoEntityDto = new ArrayList<DemoEntityDto>();

			if(receivedListDemoEntityDto != null) {

				receivedListDemoEntityDto.forEach(d -> {

					DemoEntity demoEntity = demoEntityService.addDemo(demoEntityMapper.DemoEntityDtoToDemoEntity(d));
					listDemoEntityDto.add(demoEntityMapper.DemoEntityToDemoEntityDto(demoEntity));
				});
			}

			for(int i=0;i<=2;i++) {

				DemoEntity demoEntity = demoTestService.generateDemoEntity(i);	
				demoEntity = demoEntityService.addDemo(demoEntity);

				listDemoEntityDto.add(demoEntityMapper.DemoEntityToDemoEntityDto(demoEntity));
			}

			if (!listDemoEntityDto.isEmpty())
				return new ResponseEntity<>(listDemoEntityDto, HttpStatus.CREATED);
			else 
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<String> cleanDemos() {

		try {

			demoEntityService.deleteAllDemos();

			return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/kafka")
	public ResponseEntity<DemoEntityDto> generateDemoOnKafka(
			@RequestBody Optional<DemoEntityDto> demoEntityDto, @RequestBody Optional<HashMap<String,String>> headers, 
			@RequestParam(required=false) String... topics) {

		DemoEntityDto sentDemoEntityDto;

		if(demoEntityDto.isPresent()) {
			sentDemoEntityDto = demoEntityDto.get();
		} else {
			sentDemoEntityDto = demoEntityMapper.DemoEntityToDemoEntityDto(
					demoTestService.generateDemoEntity(0));
		}

		kafka.sendKafka(
				sentDemoEntityDto, 
				Long.toString(sentDemoEntityDto.getId()),
				headers.orElse(null), 
				topics);

		return ResponseEntity.ok(sentDemoEntityDto);
	}
}