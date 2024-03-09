package com.diosaraiva.query.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diosaraiva.parentlib.dto.demo.DemoEntityDto;
import com.diosaraiva.query.demo.entity.DemoEntity;
import com.diosaraiva.query.demo.entity.mapper.DemoEntityMapper;
import com.diosaraiva.query.demo.service.DemoEntityService;
import com.diosaraiva.query.demo.service.DemoTestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class DemoTestController 
{
	@Autowired
	DemoTestService demoTestService;

	@Autowired
	DemoEntityService demoEntityService;

	private DemoEntityMapper demoEntityMapper = Mappers.getMapper(DemoEntityMapper.class);

	@GetMapping
	public ResponseEntity<DemoEntityDto> generateDemoEntity(Optional<Integer> i) 
	{
		DemoEntityDto demoEntityDto = 
				demoEntityMapper.DemoEntityToDemoEntityDto(
						demoTestService.generateDemoEntity(i.orElse(0)));

		return ResponseEntity.ok(demoEntityDto);
	}

	@PostMapping
	public ResponseEntity<List<DemoEntityDto>> fetchDemos() {

		try {

			List<DemoEntityDto> listDemoEntityDto = new ArrayList<DemoEntityDto>();

			demoEntityService.getAllDemos().forEach(d -> listDemoEntityDto.add(
					demoEntityMapper.DemoEntityToDemoEntityDto(d)));

			for(int i=0;i<=2;i++) {

				DemoEntity demoEntity = demoTestService.generateDemoEntity(i);	
				demoEntity = demoEntityService.addDemo(demoEntity);
				System.out.println("Demo created in Query.");

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
}