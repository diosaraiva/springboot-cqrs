package com.diosaraiva.command.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diosaraiva.command.demo.entity.DemoEntity;

public interface DemoEntityRepository extends JpaRepository<DemoEntity, Long> 
{
	//
}