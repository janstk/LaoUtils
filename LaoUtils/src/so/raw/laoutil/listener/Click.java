package so.raw.laoutil.listener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * onclick�¼��Ĵ����ࡣ
 * @author z
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Click {

	int value();
}
