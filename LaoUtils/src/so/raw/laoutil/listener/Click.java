package so.raw.laoutil.listener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * onclick事件的处理类。
 * @author z
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Click {

	int value();
}
