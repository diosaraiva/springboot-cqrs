package com.diosaraiva.parentlib.utils;

public class MethodUtils {

	public static String getCallerMethodName() {		

		StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

		return walker.getCallerClass().toString();
	}
}