package so.raw.laoutil;

import android.app.Activity;
import android.view.View;

public class LaoUtils {

	/**
	 * ≤È’“ƒ≥“ªview
	 * @param clazz
	 * @return
	 */
	public static<T extends View> T lfind(Activity aty,int id,Class<T> clazz )
	{
		View view = aty.findViewById(id);
		return  clazz.cast(view);
	}
}
