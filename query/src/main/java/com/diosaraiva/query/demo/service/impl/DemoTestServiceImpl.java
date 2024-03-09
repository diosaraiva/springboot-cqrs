package com.diosaraiva.query.demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diosaraiva.query.demo.entity.DemoEntity;
import com.diosaraiva.query.demo.service.DemoTestService;

@Service
public class DemoTestServiceImpl implements DemoTestService
{
	@Value("${spring.application.name}")
	String host;

	//Test
	@Override
	public DemoEntity generateDemoEntity(int i) 
	{
		return DemoEntity.builder()
				.id(i)
				.time(LocalDateTime.now())
				.message("Test ok")
				.host(this.host)
				.build();
	}
}