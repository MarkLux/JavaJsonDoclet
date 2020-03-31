package com.marklux.doclet.annotation;

/**
 * 服务定义
 *
 * @author lumin
 */
public @interface Service {

    /**
     * 别名
     */
    String alias() default "";

    /**
     * 命名空间
     */
    String namespace();

    /**
     * 标签
     */
    String[] tags() default {};
}
