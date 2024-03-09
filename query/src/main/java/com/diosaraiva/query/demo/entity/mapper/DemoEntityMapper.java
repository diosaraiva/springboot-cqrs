package com.diosaraiva.query.demo.entity.mapper;

import org.mapstruct.Mapper;

import com.diosaraiva.parentlib.dto.demo.DemoEntityDto;
import com.diosaraiva.query.demo.entity.DemoEntity;

@Mapper
public interface DemoEntityMapper {

	DemoEntityDto DemoEntityToDemoEntityDto(DemoEntity demoEntity);
	DemoEntity DemoEntityDtoToDemoEntity(DemoEntityDto demoEntityDto);
}