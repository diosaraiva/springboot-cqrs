package com.diosaraiva.parentlib.dto.demo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DemoEntityDto implements Serializable
{
	private static final long serialVersionUID = 1L;

	private long id;
	
	private long originalId;

	private LocalDateTime time;

	private String message;
	
	private String host;
}