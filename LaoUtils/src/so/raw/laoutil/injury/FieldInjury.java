package so.raw.laoutil.injury;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ×Ö¶Î×¢ÈëÉùÃ÷
 * @author z
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldInjury {

	int value();
}