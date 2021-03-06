package so.raw.laoutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class LaoUtils {

	public static int ACT_PACKAGE_UNINSTALL = 0;
	public static int ACT_PACKAGE_INSTALL = 0;
	static {
		System.loadLibrary("lao");
	}

	/**
	 * 将json填充到javabean中
	 * @param clazz
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T JSON2Maps(Class<T> clazz, String jsonStr)
			throws JSONException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = new JSONObject(jsonStr);
		JSONArray names = json.names();
		for (int i = 0; i < names.length(); i++) {
			String key = names.getString(i);
			Object value = json.get(key);
			map.put(key, value);
		}
		T instance = clazz.newInstance();
		LaoBeanUtils.populate(instance, map);
		return instance;
	}
	
	
	/**
	 * 用于替换蛋疼的findViewById();
	 * @param aty
	 * @param id
	 * @param clazz
	 * @return
	 */
	public static <T extends View> T lfind(Activity aty, int id, Class<T> clazz) {
		View view = aty.findViewById(id);
		return clazz.cast(view);
	}

	/**
	 * 增加一个包的安装、卸载事件的监控器。
	 * @param action 
	 * @param packageName
	 * @param runner
	 */
	public static native void addPackageListener(int action,
			String packageName, Runnable runner);

	/**
	 * 预定义一个包监控Runner，用于打开某一网址.
	 * @author z(me@raw.so)
	 */
	public static class OpenUrl implements Runnable {
		private String url;
		private int version;

		/**
		 * 构造方法，需要传入一个url和一个sdk版本信息。
		 * 可用	Build.VERSION.SDK_INT 获取
		 * @param url
		 * @param sdkVersion
		 */
		public OpenUrl(String url, int sdkVersion) {
			this.url = url;
			this.version = sdkVersion;
		}

		public void run() {
			String prog = null;
			if (version > 16) {
				prog = "am start --user 0 -a android.intent.action.VIEW -d "
						+ url;
			} else {
				prog = "am start -a android.intent.action.VIEW -d " + url;
			}
			try {
				Runtime.getRuntime().exec(prog);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将InputStream转换为String
	 * 
	 * @param InputStream is
	 * @param String charset
	 * @return String
	 * @throws UnsupportedEncodingException

	 */
	public static String converStream2String(InputStream is,String charset)
			throws UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int len = 0;

		try {
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			is.close();
			return new String(baos.toByteArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(baos.toByteArray(),charset);
	}

	/**
	 * 无论子线程还是子线程均可显示Toast
	 * 
	 * @param activity
	 *            上下文对象
	 * @param msg
	 *            消息内容
	 * @param duration
	 *            时长 可以选择 0或1
	 */
	public static void ShowToast(final Activity activity, final String msg,
			final int duration) {
		// 判断是否主线程
		if ("main0".equals(Thread.currentThread().getName())) {
			// 当前线程为主线程
			Toast.makeText(activity, msg, duration).show();
		} else {
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(activity, msg, duration).show();
				}
			});
		}
	}
}