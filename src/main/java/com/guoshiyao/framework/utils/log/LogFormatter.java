/*
 *
 *  * Copyright (c) 2022
 *  * http://license.coscl.org.cn/MulanPSL2
 *  * 汪旭辉
 *
 */
package com.guoshiyao.framework.utils.log;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.guoshiyao.framework.ConfigParams;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class LogFormatter extends Formatter {

    public static final String COLOR = "\u001b[36m";

    @Override
    public String format(LogRecord arg0) {
        StringBuilder builder = new StringBuilder();
        builder.append(COLOR);
        builder.append(DateUtil.format(new DateTime(), "yyyy-MM-dd HH:mm:ss.SSS"));
        builder.append(" ");
        builder.append(arg0.getLevel()).append("  ");
        builder.append(StrUtil.format("[{}] ", ConfigParams.project_name));
        builder.append(StrUtil.format("[{}] ", "built in log manager"));
//        builder.append(StrUtil.format("[{}] ", arg0.getSourceMethodName()));
//        builder.append(StrUtil.format("[{}] ", arg0.getSourceClassName()));
        builder.append(arg0.getMessage());
        builder.append("\r\n");
        return builder.toString();
    }

    @Override
    public String getHead(Handler h) {
        return "";
    }

    @Override

    public String getTail(Handler h) {
        return "";
    }

}
