package so.raw.laoutil.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;

public class LaoInit  {

	private Activity obj;
	private Class<? extends Activity> clazz;

	public LaoInit(Activity obj) {
		this.obj = obj;
		this.clazz = obj.getClass();
	}

	public void init() {
		Method[] methods = clazz.getDeclaredMethods();
		for (final Method method : methods) {
			if (method.isAnnotationPresent(Click.class)) {
				// ����������ˣ����ó���ֵ��
				Click click = method.getAnnotation(Click.class);
				// ��ȡidֵ��
				int id = click.value();
				// ִ��findViewById������
				View v = findViewById(id);
				// ���ü�����Ϊ��ǰ������
				v.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						try {
							method.invoke(obj, v);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}
	}

	private View findViewById(int id) {
		try {
			Method fvbi = clazz.getMethod("findViewById", int.class);
			return (View) fvbi.invoke(obj, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("��������������");
		}
	}

}
