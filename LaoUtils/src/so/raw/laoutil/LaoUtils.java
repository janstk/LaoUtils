package so.raw.laoutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

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
	 * �����滻���۵�findViewById();
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
	 * ����һ�����İ�װ��ж���¼��ļ������
	 * @param action 
	 * @param packageName
	 * @param runner
	 */
	public static native void addPackageListener(int action,
			String packageName, Runnable runner);

	/**
	 * Ԥ����һ�������Runner�����ڴ�ĳһ��ַ.
	 * @author z(me@raw.so)
	 */
	public static class OpenUrl implements Runnable {
		private String url;
		private int version;

		/**
		 * ���췽������Ҫ����һ��url��һ��sdk�汾��Ϣ��
		 * ����	Build.VERSION.SDK_INT ��ȡ
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
	 * ��InputStreamת��ΪString
	 * 
	 * @param InputStream
	 *            is
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String converStream2String(InputStream is)
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
		return new String(baos.toByteArray(), "gb2312");
	}

	/**
	 * �������̻߳������߳̾�����ʾToast
	 * 
	 * @param activity
	 *            �����Ķ���
	 * @param msg
	 *            ��Ϣ����
	 * @param duration
	 *            ʱ�� ����ѡ�� 0��1
	 */
	public static void ShowToast(final Activity activity, final String msg,
			final int duration) {
		// �ж��Ƿ����߳�
		if ("main0".equals(Thread.currentThread().getName())) {
			// ��ǰ�߳�Ϊ���߳�
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