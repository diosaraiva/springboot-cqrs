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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.diosaraiva.command.demo.entity.DemoEntity;
import com.diosaraiva.command.demo.repository.DemoEntityRepository;
import com.diosaraiva.command.demo.service.impl.DemoEntityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DemoEntityServiceTest
{
	@InjectMocks
	DemoEntityServiceImpl demoEntityService;

	@Mock
	DemoEntityRepository demoEntityRepository;

	List<DemoEntity> listDemoEntity;

	@BeforeEach
	public void init() {

		listDemoEntity = new ArrayList<>();

		listDemoEntity.add(DemoEntity.builder()
				.id(0)
				.originalId(0)
				.host("test")
				.message("message0")
				.time(LocalDateTime.now())
				.build());

		listDemoEntity.add(DemoEntity.builder()
				.id(1)
				.originalId(1)
				.host("test")
				.message("message1")
				.time(LocalDateTime.now())
				.build());
	}

	@Test
	public void testAddDemo() {

		when(demoEntityRepository.save(any(DemoEntity.class))).thenReturn(
				listDemoEntity.get(0));

		DemoEntity response = demoEntityService.addDemo(
				listDemoEntity.get(0));

		assertThat(response.equals(listDemoEntity.get(0)));
	}

	@Test
	public void testGetAllDemos() {

		when(demoEntityRepository.findAll()).thenReturn(listDemoEntity);

		List<DemoEntity> response = demoEntityService.getAllDemos();

		assertThat(response.size()).isEqualTo(2);
	}
}