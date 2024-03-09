package com.diosaraiva.query.demo.service;

import java.util.List;

import com.diosaraiva.query.demo.entity.DemoEntity;

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