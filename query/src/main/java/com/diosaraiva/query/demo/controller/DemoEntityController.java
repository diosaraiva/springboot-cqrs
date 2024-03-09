package com.diosaraiva.query.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diosaraiva.parentlib.dto.demo.DemoEntityDto;
import com.diosaraiva.query.demo.entity.DemoEntity;
import com.diosaraiva.query.demo.entity.mapper.DemoEntityMapper;
import com.diosaraiva.query.demo.service.DemoEntityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoEntityController 
{
	@Autowired
	DemoEntityService demoEntityService;

	private DemoEntityMapper demoEntityMapper = Mappers.getMapper(DemoEntityMapper.class);

	//Create
	@PostMapping
	public ResponseEntity<DemoEntityDto> createDemo(@RequestBody DemoEntityDto demoEntityDto) {

		try {
			DemoEntity demoEntity = demoEntityService.addDemo(
					demoEntityMapper.DemoEntityDtoToDemoEntity(demoEntityDto));

			if (demoEntity != null) {
				DemoEntityDto returnedDemoEntityDto = demoEntityMapper.DemoEntityToDemoEntityDto(demoEntity);

				return new ResponseEntity<>(returnedDemoEntityDto, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(demoEntityDto, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(demoEntityDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Retrieve
	@GetMapping
	public ResponseEntity<List<DemoEntityDto>> getAllDemos() {

		try {
			List<DemoEntityDto> listDemoEntityDto = new ArrayList<DemoEntityDto>();

			demoEntityService.getAllDemos().forEach(demoEntity -> 
			listDemoEntityDto.add(
					demoEntityMapper.DemoEntityToDemoEntityDto(demoEntity)));

			if (!listDemoEntityDto.isEmpty()) {				
				return new ResponseEntity<>(listDemoEntityDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<DemoEntityDto> getDemoById(@PathVariable("id") long id) {

		try {
			DemoEntity demoEntity = demoEntityService.getDemo(id);

			if (demoEntity != null) {
				DemoEntityDto returnedDemoEntityDto = demoEntityMapper.DemoEntityToDemoEntityDto(demoEntity);

				return new ResponseEntity<>(returnedDemoEntityDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Update
	@PutMapping("/{id}")
	public ResponseEntity<DemoEntityDto> updateDemo(@PathVariable("id") long id, @RequestBody DemoEntityDto demoEntityDto) {

		try {
			DemoEntity demoEntity = demoEntityService.updateDemo(
					demoEntityMapper.DemoEntityDtoToDemoEntity(demoEntityDto));

			if (demoEntity != null) {
				DemoEntityDto returnedDemoEntityDto = demoEntityMapper.DemoEntityToDemoEntityDto(demoEntity);

				return new ResponseEntity<>(returnedDemoEntityDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(demoEntityDto, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//Delete
	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllDemos() {

		try {
			demoEntityService.deleteAllDemos();

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteDemo(@PathVariable("id") long id) {

		try {
			DemoEntity demoEntity = demoEntityService.getDemo(id);

			if (demoEntity != null) {
				demoEntityService.deleteDemo(id);

				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}