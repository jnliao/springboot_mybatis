package www.sh.com.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionInitExtendList {

    PermissionInitExtend[] value();

    String resultType() default "ResultForWeb";

}
