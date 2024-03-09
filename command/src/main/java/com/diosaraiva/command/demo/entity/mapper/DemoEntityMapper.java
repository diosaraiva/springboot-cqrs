package com.diosaraiva.command.demo.entity.mapper;

import org.mapstruct.Mapper;

import com.diosaraiva.command.demo.entity.DemoEntity;
import com.diosaraiva.parentlib.dto.demo.DemoEntityDto;

@Mapper
public interface DemoEntityMapper {

	DemoEntityDto DemoEntityToDemoEntityDto(DemoEntity demoEntity);
	DemoEntity DemoEntityDtoToDemoEntity(DemoEntityDto demoEntityDto);
}