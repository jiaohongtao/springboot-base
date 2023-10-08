package com.hong.util.common;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author jiaohongtao
 * @since 2019/10/10 14:18
 */
public class GetName {

	private static String[] getNames() {
		Class<GetName> clazz = GetName.class;
		Method method;
		try {
			method = clazz.getMethod("isNotEmptyBatch", Object.class);
			ParameterNameDiscoverer pnd = new LocalVariableTableParameterNameDiscoverer();
			String[] paramNames = pnd.getParameterNames(method);//返回的就是方法中的参数名列表了
			System.out.println(Arrays.toString(paramNames));
			return paramNames;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return new String[]{};
	}

	private static boolean isNotEmptyBatch(Object strs) {
		/*for (Object str : strs) {
			if (str == null || "".equals(str))
				return false;
		}*/
		if (strs == null || "".equals(strs)){

			return false;
		} {
			return true;
		}
	}

	public static void main(String[] args) {
		/*Object[] objects = {"123", "qqq", 23};
		for (Object object : objects) {
			boolean bl = isNotEmptyBatch(object);
			System.out.println(bl);
			System.out.println(getNames()[0]);
			System.out.println(getNames()[1]);
		}*/


	}
}
