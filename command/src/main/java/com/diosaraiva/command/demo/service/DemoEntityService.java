package com.diosaraiva.command.demo.service;

import java.util.List;

import com.diosaraiva.command.demo.entity.DemoEntity;

public interface DemoEntityService {

	//Create
	DemoEntity addDemo(DemoEntity demoEntity);

	//Retrieve
	List<DemoEntity> getAllDemos();

	DemoEntity getDemo(long id);

	//Update
	DemoEntity updateDemo(DemoEntity demoEntity);

	//Delete
	void deleteDemo(long id);

	void deleteAllDemos();
}