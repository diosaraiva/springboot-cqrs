package com.diosaraiva.command.demo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diosaraiva.command.demo.entity.DemoEntity;
import com.diosaraiva.command.demo.repository.DemoEntityRepository;
import com.diosaraiva.command.demo.service.DemoEntityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DemoEntityServiceImpl implements DemoEntityService
{
	@Autowired
	DemoEntityRepository demoEntityRepository;

	@Value("${spring.application.name}")
	String host;

	//Create
	@Override
	public DemoEntity addDemo(DemoEntity demoEntity) 
	{
		demoEntity.setTime(LocalDateTime.now());

		demoEntity.setHost(host);

		return demoEntityRepository.save(demoEntity);
	}

	//Retrieve
	@Override
	public List<DemoEntity> getAllDemos() 
	{
		List<DemoEntity> demos = new ArrayList<>();
		demoEntityRepository.findAll().forEach(demos::add);

		return demos;
	}

	@Override
	public DemoEntity getDemo(long id) 
	{
		return demoEntityRepository.findById(id)
				.orElseThrow();
	}

	//Update
	@Override
	public DemoEntity updateDemo(DemoEntity demoEntity) 
	{
		Optional<DemoEntity> dbDemoEntity = demoEntityRepository.findById(demoEntity.getId());

		if(dbDemoEntity.isPresent())
		{
			addDemo(demoEntity);

			dbDemoEntity = demoEntityRepository.findById(demoEntity.getId());
		}

		return dbDemoEntity.orElseThrow();
	}

	//Delete
	@Override
	public void deleteDemo(long id) 
	{
		demoEntityRepository.deleteById(id);
	}

	@Override
	public void deleteAllDemos() 
	{
		demoEntityRepository.deleteAll();
	}
}