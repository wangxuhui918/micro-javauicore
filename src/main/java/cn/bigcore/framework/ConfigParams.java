package cn.bigcore.framework;

import cn.bigcore.framework.ui.core.controller.utils.AlertUtils;
import cn.bigcore.framework.ui.core.starter.Starter;
import cn.bigcore.framework.ui.core.url.SpeakerURL;
import cn.bigcore.framework.ui.core.url.base.URLInterface;
import cn.bigcore.framework.utils.log.LogUtils;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.setting.Setting;
import cn.hutool.system.SystemUtil;
import cn.bigcore.framework.ui.core.url.MenuURL;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.logging.Level;

public class ConfigParams {
    //允许窗口最小化
    public static Setting extend;
    //允许窗口最小化
    public static boolean allowminwindow = false;
    //系统首页,默认为菜单
    public static URLInterface home_ui = new MenuURL();
    //菜单首页
    public static URLInterface menu_home_ui = new SpeakerURL();
    //项目名
    public static String project_name = "micro-javaui";
    //默认日志级别
    public static Level log_level = Level.FINER;
    //默认日志的位置,一般为 用户目录/fyy/javaui/项目名
    public static String log_path = StrUtil.format(SystemUtil.getUserInfo().getHomeDir()//
                    + FileUtil.FILE_SEPARATOR + "{}" + FileUtil.FILE_SEPARATOR + //
                    FileUtil.FILE_SEPARATOR + "{}" + FileUtil.FILE_SEPARATOR +//
                    FileUtil.FILE_SEPARATOR + "{}" + FileUtil.FILE_SEPARATOR, //
            "fyy", "javaui", project_name);//
    //作者
    public static String author = "作者： 张三";
    //作者
    public static String author_email = "邮箱： zhangsan@mail.com";
    //phone
    public static String author_phone = "电话： XXXXXXXXXXX";
    //phone
    public static String author_qq = "QQ： XXXXXXXX";
    //网站
    public static String url = StrUtil.format("网站： http://www.guoshiyao.com");
    //产品说明
    public static String product = StrUtil.format("JAVA-UI框架 （{}）", project_name);
    //版权说明
    public static String copyright = StrUtil.format("版权声明： 开源软件 Copyright © 2019–{} 汪旭辉 s.r.o. ", DateUtil.format(new Date(), "yyyy"));
    //使用说明
    public static String useme = "使用说明： 遵循 木兰宽松许可证， 第2版 授予您永久性的、全球性的、免费的、非独占的、不可撤销的版权许可，您可以复制、使用、修改、分发其“贡献”，不论修改与否。";
    //使用说明
    public static String usemeurl = "http://license.coscl.org.cn/MulanPSL2";
    //配置文件
    public static String config_file_full_path = "";
    //
    //运行环境
    public static final String runtime = "运行环境： " + SystemUtil.getJavaRuntimeInfo().getName() + " " + SystemUtil.getJavaRuntimeInfo().getVersion();
    //系统环境
    public static final String systemos = "系统环境： " + SystemUtil.getOsInfo().getName() + " " + SystemUtil.getOsInfo().getVersion() + " " + SystemUtil.getOsInfo().getArch();
    //程序图标
    public static final String iocn_path = "images/iocn.png";
    //程序窗口宽高
    public static double bottom_height = 900;
    //程序窗口宽度
    public static double bottom_width = 1440;
    //程序popup窗口宽高
    public static double popup_height = bottom_height / 2;
    //程序popup窗口宽度
    public static double popup_width = bottom_width / 2;
    //配置文件
    public final static String config_file = "java_ui.ini";
    //配置文件样例
    public final static String resource_demo_config_file = "tools/java_ui_demo.ini";

    static {
        String runtimeDir = "";
        try {
            String startCommad = System.getProperty("sun.java.command");
            LogUtils.debug("获取到启动命令值:[{}]", startCommad);
            if (StrUtil.isNotBlank(startCommad)) {
                startCommad = StrUtil.subBefore(startCommad, " ", false);
            }
            if (URLUtil.isJarFileURL(new File(startCommad).toURI().toURL())) {//如果是运行时
                LogUtils.debug("[jar]运行模式");
                runtimeDir = FileUtil.getParent(startCommad, 1);
            } else if (ClassUtil.isNormalClass(ClassUtil.loadClass(startCommad, false))) {//如果是非运行时
                String f1 = FileUtil.getAbsolutePath(ClassUtil.loadClass(Starter.class.getName()).getClassLoader().getResource("").getPath());
                for (int i = 0; ; i++) {
                    String classes = FileUtil.getParent(f1, i);
                    String target = FileUtil.getParent(f1, i + 1);
                    String root = FileUtil.getParent(f1, i + 2);
                    if (StrUtil.endWith(classes, "classes") && StrUtil.endWith(target, "target")) {
                        runtimeDir = root + FileUtil.FILE_SEPARATOR + "src" + FileUtil.FILE_SEPARATOR + "main" + FileUtil.FILE_SEPARATOR + "resources" + FileUtil.FILE_SEPARATOR;
                        break;
                    }
                }
            }

            try {
                String config_file_full_path_temp = runtimeDir + File.separator + config_file;
                if (!new File(config_file_full_path_temp).exists()) {
                    LogUtils.debug("配置文件不存在:{},新建", config_file_full_path_temp);
                    FileUtil.writeUtf8String(ResourceUtil.readUtf8Str(resource_demo_config_file), config_file_full_path_temp);
                }
                LogUtils.debug("开始读取配置文件:{}", config_file_full_path_temp);
                Setting config_pathseeting = new Setting(config_file_full_path_temp);
                //
                int s = 0;
                LogUtils.debug("更新配置文件:{}", config_file_full_path_temp);
                Setting resource_demo_config_file_setting = new Setting(ResourceUtil.getResource(resource_demo_config_file), Charset.forName("UTF-8"), true);
                for (String key : resource_demo_config_file_setting.getSetting("core").keySet()) {
                    if (config_pathseeting.getSetting("core") == null || !config_pathseeting.getSetting("core").containsKey(key)) {
                        config_pathseeting.putByGroup(key, "core", resource_demo_config_file_setting.getSetting("core").get(key));
                        LogUtils.debug("更新配置文件:{}-core-{}", config_file_full_path_temp, key);
                        s++;
                    }
                }
                for (String key : resource_demo_config_file_setting.getSetting("extend").keySet()) {
                    if (config_pathseeting.getSetting("extend") == null || !config_pathseeting.getSetting("extend").containsKey(key)) {
                        config_pathseeting.putByGroup(key, "extend", resource_demo_config_file_setting.getSetting("extend").get(key));
                        LogUtils.debug("更新配置文件:{}-extend-{}", config_file_full_path_temp, key);
                        s++;
                    }
                }
                if (s > 0) {
                    config_pathseeting.store();
                }
            } catch (Exception e) {
            }

        } catch (Exception e) {
            AlertUtils.err("配置文件读取失败", e);
        }
        try {
            config_file_full_path = runtimeDir + File.separator + config_file;
            Setting config_pathseeting = new Setting(config_file_full_path);
            allowminwindow = config_pathseeting.getBool("javaui.allowminwindow", "core");
            home_ui = (URLInterface) ClassUtil.loadClass(config_pathseeting.get("core", "javaui.home_ui")).newInstance();
            menu_home_ui = (URLInterface) ClassUtil.loadClass(config_pathseeting.get("core", "javaui.menu_home_ui")).newInstance();
            project_name = config_pathseeting.get("core", "javaui.project_name");
            log_level = Level.parse(config_pathseeting.get("core", "javaui.log_level"));
            author = config_pathseeting.get("core", "javaui.author");
            author_email = config_pathseeting.get("core", "javaui.author_email");
            author_phone = config_pathseeting.get("core", "javaui.author_phone");
            author_qq = config_pathseeting.get("core", "javaui.author_qq");
            url = config_pathseeting.get("core", "javaui.url");
            product = config_pathseeting.get("core", "javaui.product");
            copyright = config_pathseeting.get("core", "javaui.copyright");
            useme = config_pathseeting.get("core", "javaui.useme");
            usemeurl = config_pathseeting.get("core", "javaui.usemeurl");
            bottom_height = config_pathseeting.getDouble("javaui.bottom_height", "core", bottom_height);
            bottom_width = config_pathseeting.getDouble("javaui.bottom_width", "core", bottom_width);
            popup_height = config_pathseeting.getDouble("javaui.popup_height", "core", popup_height);
            popup_width = config_pathseeting.getDouble("javaui.bottom_height", "core", popup_width);
            extend = config_pathseeting.getSetting("extend");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        try {
            log_path = StrUtil.format(SystemUtil.getUserInfo().getHomeDir()//
                            + FileUtil.FILE_SEPARATOR + "{}" + FileUtil.FILE_SEPARATOR + //
                            FileUtil.FILE_SEPARATOR + "{}" + FileUtil.FILE_SEPARATOR +//
                            FileUtil.FILE_SEPARATOR + "{}" + FileUtil.FILE_SEPARATOR, //
                    "fyy", "javaui", project_name);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
