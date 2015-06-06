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
		}
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
