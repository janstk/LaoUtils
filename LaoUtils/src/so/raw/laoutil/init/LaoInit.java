package so.raw.laoutil.init;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import so.raw.laoutil.injury.FieldInjury;
import so.raw.laoutil.listener.Click;

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
		//��ʼ��������
		Method[] methods = clazz.getDeclaredMethods();
		for (final Method method : methods) {
			if (method.isAnnotationPresent(Click.class)) {
				initListener(method);
			}
		}
		//����ע�롣
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			//�����Ҫ����ע��Ļ�...
			if(field.isAnnotationPresent(FieldInjury.class)){
				initInjury(field);//����ע��
			}
		}
		
	}

	private void initInjury(Field field) {
		field.setAccessible(true);//��������
		FieldInjury injury = field.getAnnotation(FieldInjury.class);
		int id = injury.value();
		//��ȡid
		View view = findViewById(id);
		Class<?> type = field.getType();
		type.cast(view);
		try {
			field.set(obj, view);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initListener(final Method method) {
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
