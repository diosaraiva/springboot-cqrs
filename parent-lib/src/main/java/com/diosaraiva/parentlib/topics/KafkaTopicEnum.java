package com.diosaraiva.parentlib.topics;

public enum KafkaTopicEnum {
	DEFAULT		("nxtlvl-default-topic");

	private final String enumOption;

	private KafkaTopicEnum(String enumOption){
		this.enumOption = enumOption;
	}

	private static KafkaTopicEnum getEnum(String enumOption) {
		for (KafkaTopicEnum e : values()) {
			if (e.enumOption.equals(enumOption)) return e;
		}
		return null;
	}
}