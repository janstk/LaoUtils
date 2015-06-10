package so.raw.laoutil;

import java.io.IOException;

import android.app.Activity;
import android.view.View;

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
}