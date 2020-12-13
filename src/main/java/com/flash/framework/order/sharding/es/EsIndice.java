package com.flash.framework.order.sharding.es;

import java.lang.annotation.*;

/**
 * @author zhurg
 * @date 2020/9/20 - 下午6:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsIndice {

    String indice();

    String indiceMapping() default "indiceMapping";

    String indiceType() default "";
}