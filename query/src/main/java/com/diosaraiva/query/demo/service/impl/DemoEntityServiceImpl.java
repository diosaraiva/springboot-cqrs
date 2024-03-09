package com.diosaraiva.query.demo.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.diosaraiva.query.demo.entity.DemoEntity;
import com.diosaraiva.query.demo.repository.DemoEntityRepository;
import com.diosaraiva.query.demo.service.DemoEntityService;

@Service
public class DemoEntityServiceImpl implements DemoEntityService
{
	DemoEntityRepository demoEntityRepository;

	@Value("${spring.application.name}")
	String host;

	public DemoEntityServiceImpl(DemoEntityRepository demoEntityRepository) {
		this.demoEntityRepository = demoEntityRepository;
	}

	//Create
	@Override
	public DemoEntity addDemo(DemoEntity demoEntity) 
	{
		demoEntity.setTime(LocalDateTime.now());

		if(demoEntity.getHost() == null || demoEntity.getHost().equals(""))
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