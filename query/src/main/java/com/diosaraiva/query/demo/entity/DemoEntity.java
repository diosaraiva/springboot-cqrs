package com.diosaraiva.query.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Document
public class DemoEntity {
	
	@Id
	private long id;
	
	private long originalId;

	private LocalDateTime time;

	private String message;
	
	private String host;
}