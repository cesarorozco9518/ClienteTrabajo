package com.bancoazteca.bdm.cliente.commons;

/**
 * @author Cesar M Orozco R
 *
 */
public class ValidacionesUtils {
	
	
	/**
	 * @param data
	 * @return
	 */
	public static boolean isNullOrEmpty(Object data) {
		boolean flag = false;
		if (isNull(data)) {
			flag = true;
		} else if (String.valueOf(data).trim().isEmpty()) {
			flag = true;
		}
		return flag;
	}
	

	/**
	 * @param data
	 * @return
	 */
	public static boolean isNull(Object data) {
		boolean flag = false;
		if (data == null) {
			flag = true;
		}
		return flag;
	}
}
