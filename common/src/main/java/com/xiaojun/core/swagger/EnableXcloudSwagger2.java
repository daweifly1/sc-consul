package com.xiaojun.core.swagger;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author f00lish
 * @version 2018/7/16
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({Swagger2Configuration.class})
public @interface EnableXcloudSwagger2 {


}

