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
		//初始化方法。
		Method[] methods = clazz.getDeclaredMethods();
		for (final Method method : methods) {
			if (method.isAnnotationPresent(Click.class)) {
				initListener(method);
			}
		}
		//依赖注入。
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			//如果需要依赖注入的话...
			if(field.isAnnotationPresent(FieldInjury.class)){
				initInjury(field);//依赖注入
			}
		}
		
	}

	private void initInjury(Field field) {
		field.setAccessible(true);//暴力反射
		FieldInjury injury = field.getAnnotation(FieldInjury.class);
		int id = injury.value();
		//获取id
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
		// 如果声明了了，就拿出来值。
		Click click = method.getAnnotation(Click.class);
		// 获取id值。
		int id = click.value();
		// 执行findViewById方法。
		View v = findViewById(id);
		// 设置监听器为当前方法。
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
			throw new RuntimeException("哈哈哈哈哈哈哈");
		}
	}

}
