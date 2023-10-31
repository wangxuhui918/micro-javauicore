/*
 *
 *  * Copyright (c) 2022
 *  * http://license.coscl.org.cn/MulanPSL2
 *  * 汪旭辉
 *
 */

package com.guoshiyao.framework.utils.classfind;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.guoshiyao.framework.ConfigParams;
import com.guoshiyao.framework.utils.classfind.annotation.ClassFindAnnotaion;
import com.guoshiyao.framework.utils.log.LogUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author 汪旭辉
 * @date 2022/5/26
 * @readme 框架配置工具类
 */
public class ClassFindUtils {


    public <T> List<String> getPlugins(Class<T> a) {//获取功能插件列表
        List<String> returnlist = new ArrayList<>();
        List<T> plugins = getRuleOn(ClassFindAnnotaion.class, a, ClassUtil.getPackage(ConfigParams.class));//加載扩展插件属性信息
        for (int i = 0; i < plugins.size(); i++) {
            returnlist.add(plugins.get(i).getClass().getName());
        }
        return returnlist;
    }

    public static <A extends Annotation, T> List<T> getRuleOn(Class<A> annotaione, Class<T> classe, String packet) {
        return getRule(annotaione, classe, packet, null);
    }


    public static <A extends Annotation, T> List<T> getRule(Class<A> annotaione, Class<T> classe, String packet,
                                                            String key) {
        List<T> listarra = new ArrayList<>();
        Set<Class<?>> annotaiones = ClassUtil.scanPackageByAnnotation(packet, annotaione);
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(packet, classe);
        Collection<Class<?>> intersectionSet = CollUtil.intersection(annotaiones, classes);
        for (Class<?> class1 : intersectionSet) {
            try {
                T sd = (T) ClassUtil.loadClass(class1.getName(), false).newInstance();
                if (StrUtil.isBlank(key)) {
                    listarra.add(sd);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.err("转换失败,服务停止!!!");
            }
        }
        return listarra;
    }
}
