package com.diosaraiva.query.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.diosaraiva.query.demo.entity.DemoEntity;

public interface DemoEntityRepository extends MongoRepository<DemoEntity, Long> 
{
	//
}