package so.raw.laoutil.listener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * onclick�¼�ע������
 * @author z
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Click {

	int value();
}
