package so.raw.laoutil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import android.text.TextUtils;

public class LaoBeanUtils {

	/**
	 * 简单的填充bean方法.
	 * 传入，javabean的实例
	 * 属性map
	 * 返回，设置好值的javabean
	 * @param obj
	 * @param properties
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object populate(Object obj, Map<String, Object> properties)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Class<? extends Object> clazz = obj.getClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set")) {
				// 找到set方法
				String fieldName = methodName.substring(3);
				// 找到字段名称
				if (!TextUtils.isEmpty(fieldName)) {
					// 如果截取之后不是空的话...
					// 将properties中的值赋给obj的set方法
					String realNameInMap = getStrInMapIgnoreCase(fieldName,
							properties);
					if (!TextUtils.isEmpty(realNameInMap)) {
						method.invoke(obj, properties.get(realNameInMap));
					}
				}
			}
		}
		return obj;
	}

	/**
	 * 私有方法
	 * 在map中忽略大小写查找一个key，返回map中的真实key
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	private static String getStrInMapIgnoreCase(String key,
			Map<String, Object> map) {
		Set<String> keySet = map.keySet();
		for (String keyInMap : keySet) {
			if (key.equalsIgnoreCase(keyInMap)) {
				return keyInMap;
			}
		}
		return null;
	}

}
