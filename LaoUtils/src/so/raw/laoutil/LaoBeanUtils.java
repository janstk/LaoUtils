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
	 * �򵥵����bean����.
	 * ���룬javabean��ʵ��
	 * ����map
	 * ���أ����ú�ֵ��javabean
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
				// �ҵ�set����
				String fieldName = methodName.substring(3);
				// �ҵ��ֶ�����
				if (!TextUtils.isEmpty(fieldName)) {
					// �����ȡ֮���ǿյĻ�...
					// ��properties�е�ֵ����obj��set����
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
	 * ˽�з���
	 * ��map�к��Դ�Сд����һ��key������map�е���ʵkey
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
