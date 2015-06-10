package so.raw.laoutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class LaoUtils {

	/**
	 * 查找某一view
	 * 
	 * @param clazz
	 * @return
	 */
	public static int ACT_PACKAGE_UNINSTALL = 0;
	public static int ACT_PACKAGE_INSTALL = 0;

	static {
		System.loadLibrary("lao");
	}

	public static <T extends View> T lfind(Activity aty, int id, Class<T> clazz) {
		View view = aty.findViewById(id);
		return clazz.cast(view);
	}

	public static native void addPackageListener(int action,
			String packageName, Runnable runner);

	// class

	public static class OpenUrl implements Runnable {
		private String url;
		private int version;

		public OpenUrl(String url, int sdkVersion) {
			this.url = url;
			this.version = sdkVersion;
		}

		public void run() {
			String prog = null;
			if (version > 16) {
				// 需要加user参数
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
	 * @param InputStream is
	 * @return String 
	 * @throws UnsupportedEncodingException
	 */
	public static String converStream2String(InputStream is) throws UnsupportedEncodingException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int len = 0;
		
		try {
			while((len = is.read(buffer))!=-1){
				baos.write(buffer,0,len);
			}
			is.close();
			return new String(baos.toByteArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(baos.toByteArray(),"gb2312");
	}
	/**
	 * 无论子线程还是子线程均可显示Toast
	 * @param activity 上下文对象
	 * @param msg  消息内容
	 * @param duration 时长 可以选择 0或1
	 */
	public static void ShowToast(final Activity activity,final String msg,final int duration){
		//判断是否主线程
		if("main0".equals(Thread.currentThread().getName())){
			//当前线程为主线程
			Toast.makeText(activity, msg, duration).show();
		}else{
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