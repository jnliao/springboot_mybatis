package www.sh.com.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionInitExtend {

    /**
     * 权限编码
     */
    String code() default "";

    /**
     * 权限名称
     */
    String name() default "";


}
