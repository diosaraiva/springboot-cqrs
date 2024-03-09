package com.diosaraiva.parentlib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

import com.diosaraiva.parentlib.utils.RegexUtils;

public class RegexUtilsTest {
	@Test
	void isNumericTest(){
		Integer i = new Random().nextInt();
		String numeric = i.toString();
		String notNumeric = "not numeric";

		assertEquals(RegexUtils.isNumeric(numeric), true);
		assertEquals(RegexUtils.isNumeric(notNumeric), false);
	}
}