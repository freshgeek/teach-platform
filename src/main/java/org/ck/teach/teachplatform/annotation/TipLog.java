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
public @interface TipLog {

    /***
     *  内容格式化串
     *
     * @return
     */
    String format() default "";

    /**
     * 通知人
     *
     * @return
     */
    String userId() ;

    /***
     * 来源人
     * @return
     */
    String resourceUserId() ;


    /***
     *  内容值
     * @return
     */
    String[] value() default {};

}
