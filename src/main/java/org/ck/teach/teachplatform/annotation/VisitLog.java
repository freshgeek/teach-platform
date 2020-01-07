package org.ck.teach.teachplatform.annotation;

import java.lang.annotation.*;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/20 18:42
 * @description
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VisitLog {

    /**
     *  足迹内容
     * @return
     */
    String value() default "";


}
