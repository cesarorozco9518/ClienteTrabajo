package com.bancoazteca.bdm.commons;

public class ValidacionesUtils {
	
	public static boolean isNullOrEmpty(Object data) {
		boolean flag = false;
		if (isNull(data)) {
			flag = true;
		} else {
			if (String.valueOf(data).trim().isEmpty()) {
				flag = true;
			}
		}
		return flag;
	}
	
	public static boolean isNull(Object data) {
		boolean flag = false;
		if (data == null) {
			flag = true;
		}
		return flag;
	}

}
