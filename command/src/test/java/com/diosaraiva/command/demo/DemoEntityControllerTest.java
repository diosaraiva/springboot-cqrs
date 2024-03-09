package com.diosaraiva.command.demo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.diosaraiva.command.KafkaProducer;
import com.diosaraiva.command.demo.controller.DemoEntityController;
import com.diosaraiva.command.demo.entity.DemoEntity;
import com.diosaraiva.command.demo.entity.mapper.DemoEntityMapper;
import com.diosaraiva.command.demo.service.DemoEntityService;
import com.diosaraiva.parentlib.dto.demo.DemoEntityDto;

@ExtendWith(MockitoExtension.class)
public class DemoEntityControllerTest
{
	@InjectMocks
	DemoEntityController demoEntityController;

	@Mock
	DemoEntityService demoEntityService;
	
	@Mock
	private KafkaProducer kafka;

	DemoEntityMapper demoEntityMapper = Mappers.getMapper(DemoEntityMapper.class);

	List<DemoEntityDto> listDemoEntityDto;

	@BeforeEach
	public void init() {
		
		listDemoEntityDto = new ArrayList<>();

		listDemoEntityDto.add(DemoEntityDto.builder()
				.id(0)
				.originalId(0)
				.host("test")
				.message("message0")
				.time(LocalDateTime.now())
				.build());

		listDemoEntityDto.add(DemoEntityDto.builder()
				.id(1)
				.originalId(1)
				.host("test")
				.message("message1")
				.time(LocalDateTime.now())
				.build());
	}

	@Test
	public void testCreateDemo() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(demoEntityService.addDemo(any(DemoEntity.class))).thenReturn(
				demoEntityMapper.DemoEntityDtoToDemoEntity(listDemoEntityDto.get(0)));

		ResponseEntity<DemoEntityDto> response = demoEntityController.createDemo(
				listDemoEntityDto.get(0));

		assertThat(response.getStatusCodeValue()).isEqualTo(201);
	}

	@Test
	public void testGetAllDemos() {

		List<DemoEntity> listDemoEntity = new ArrayList<>();
		listDemoEntityDto.forEach(e -> listDemoEntity.add(
				demoEntityMapper.DemoEntityDtoToDemoEntity(e)));

		when(demoEntityService.getAllDemos()).thenReturn(listDemoEntity);

		ResponseEntity<List<DemoEntityDto>> response = demoEntityController.getAllDemos();

		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody().size()).isEqualTo(2);
		assertThat(response.getBody().get(0).getMessage()).isEqualTo(listDemoEntityDto.get(0).getMessage());
		assertThat(response.getBody().get(1).getMessage()).isEqualTo(listDemoEntityDto.get(1).getMessage());
	}
}