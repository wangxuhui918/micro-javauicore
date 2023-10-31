

/*
 *
 *  * Copyright (c) 2022
 *  * http://license.coscl.org.cn/MulanPSL2
 *  * 汪旭辉
 *
 */

package com.guoshiyao.framework.utils.classfind.annotation;

import java.lang.annotation.*;

/**
 * 用来标识有效规则的注解类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ClassFindAnnotaion {

}