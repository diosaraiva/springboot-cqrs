package com.diosaraiva.parentlib.utils;

import java.util.regex.Pattern;

public class RegexUtils {
	
	public static Boolean isNumeric(String strNum) {

		if (strNum == null) return false;

		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		return pattern.matcher(strNum).matches();
	}
}