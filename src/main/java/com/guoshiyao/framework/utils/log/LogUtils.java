

/*
 *
 *  * Copyright (c) 2022
 *  * http://license.coscl.org.cn/MulanPSL2
 *  * 汪旭辉
 *
 */

/**
 *
 */
package com.guoshiyao.framework.utils.log;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.guoshiyao.framework.ConfigParams;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * java 基础日志管理器
 */
public class LogUtils {
    public final static Logger jdkLogger = Logger.getLogger(LogUtils.class.getName());

    static {
        String syslogpath = ConfigParams.log_path + FileUtil.FILE_SEPARATOR + ConfigParams.project_name + ".%u.sys.log";
        try {
            FileUtil.mkParentDirs(syslogpath);
//
            FileHandler filehandler = new FileHandler(syslogpath, 1024000, 16);
            filehandler.setLevel(Level.ALL);
            filehandler.setFormatter(new LogFormatter());
            //
            ConsoleHandler myConsoleHandler = new ConsoleHandler();
            myConsoleHandler.setLevel(ConfigParams.log_level);
            myConsoleHandler.setFormatter(new LogFormatter());
//
            jdkLogger.setLevel(Level.ALL);
            jdkLogger.setUseParentHandlers(false);
            jdkLogger.addHandler(myConsoleHandler);
            jdkLogger.addHandler(filehandler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void debug(String str, Object... strs) {
        jdkLogger.finer(StrUtil.format(str, strs));
    }

    public static void info(String str, Object... strs) {
        jdkLogger.info(StrUtil.format(str, strs));
    }

    public static void warn(String str, Object... strs) {
        jdkLogger.warning(StrUtil.format(str, strs));
    }

    public static void err(String str, Object... strs) {
        jdkLogger.severe(StrUtil.format(str, strs));
    }

//
//    public static void debug(Class classes, String str, String... strs) {
//        jdkLogger.logp(Level.FINER, classes.getName(), "", StrUtil.format(str, strs));
//    }
//
//
//    public static void warn(Class classes, String str, String... strs) {
//        jdkLogger.logp(Level.WARNING, classes.getName(), "", StrUtil.format(str, strs));
//    }
//
//    public static void info(Class classes, String str, String... strs) {
//        jdkLogger.logp(Level.INFO, classes.getName(), "", StrUtil.format(str, strs));
//    }
//
//    public static void err(Class classes, String str, String... strs) {
//        jdkLogger.logp(Level.SEVERE, classes.getName(), "", StrUtil.format(str, strs));
//    }


}