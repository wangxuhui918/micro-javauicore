package cn.bigcore.framework;

import cn.bigcore.framework.ui.core.starter.Starter;
import cn.bigcore.framework.ui.core.url.MenuURL;
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
    //运行环境
    public static final String runtime = "运行环境： " + SystemUtil.getJavaRuntimeInfo().getName() + " " + SystemUtil.getJavaRuntimeInfo().getVersion();
    //系统环境
    public static final String systemos = "系统环境： " + SystemUtil.getOsInfo().getName() + " " + SystemUtil.getOsInfo().getVersion() + " " + SystemUtil.getOsInfo().getArch();
    //程序图标
    public static final String iocn_path = "images/iocn.png";
    //是否自适应页面
    public static boolean sizeauto = false;
    //当前指定窗口高度,默认为0
    public static double h_x = 0D;
    //当前指定窗口宽度,默认为0
    public static double w_x = 0D;

    static {
        //配置文件名
        String config_file = "java_ui.ini";
        //配置文件样例
        String resource_demo_config_file = "tools/java_ui_demo.ini";
        try {//获取启动目录
            String startCommad = StrUtil.subBefore(System.getProperty("sun.java.command"), " ", false);
            if (URLUtil.isJarFileURL(new File(startCommad).toURI().toURL())) {//如果是运行时
                config_file_full_path = FileUtil.getParent(startCommad, 1) + File.separator + config_file;
            } else if (ClassUtil.isNormalClass(ClassUtil.loadClass(startCommad, false))) {//如果是非运行时
                String f1 = FileUtil.getAbsolutePath(ClassUtil.loadClass(Starter.class.getName()).getClassLoader().getResource("").getPath());
                for (int i = 0; ; i++) {
                    if (StrUtil.endWith(FileUtil.getParent(f1, i), "classes") && StrUtil.endWith(FileUtil.getParent(f1, i + 1), "target")) {
                        config_file_full_path = FileUtil.getParent(f1, i + 2) + FileUtil.FILE_SEPARATOR
                                + "src" + FileUtil.FILE_SEPARATOR //
                                + "main" + FileUtil.FILE_SEPARATOR //
                                + "resources" + FileUtil.FILE_SEPARATOR//
                                + config_file;//

                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
        try {
            if (!new File(config_file_full_path).exists()) {
                LogUtils.debug("配置文件不存在:{},新建", config_file_full_path);
                FileUtil.writeUtf8String(ResourceUtil.readUtf8Str(resource_demo_config_file), config_file_full_path);
            }
            LogUtils.debug("开始读取配置文件:{}", config_file_full_path);
            Setting config_pathseeting = new Setting(config_file_full_path);
            //
            int s = 0;
            LogUtils.debug("更新配置文件:{}", config_file_full_path);
            Setting resource_demo_config_file_setting = new Setting(ResourceUtil.getResource(resource_demo_config_file), Charset.forName("UTF-8"), true);
            for (String key : resource_demo_config_file_setting.getSetting("core").keySet()) {
                if (config_pathseeting.getSetting("core") == null || !config_pathseeting.getSetting("core").containsKey(key)) {
                    config_pathseeting.putByGroup(key, "core", resource_demo_config_file_setting.getSetting("core").get(key));
                    LogUtils.debug("更新配置文件:{}-core-{}", config_file_full_path, key);
                    s++;
                }
            }
            for (String key : resource_demo_config_file_setting.getSetting("extend").keySet()) {
                if (config_pathseeting.getSetting("extend") == null || !config_pathseeting.getSetting("extend").containsKey(key)) {
                    config_pathseeting.putByGroup(key, "extend", resource_demo_config_file_setting.getSetting("extend").get(key));
                    LogUtils.debug("更新配置文件:{}-extend-{}", config_file_full_path, key);
                    s++;
                }
            }
            if (s > 0) {
                config_pathseeting.store();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
        Setting config_pathseeting = new Setting(config_file_full_path);
        try {
            allowminwindow = config_pathseeting.getBool("javaui.allowminwindow", "core");
            home_ui = (URLInterface) ClassUtil.loadClass(config_pathseeting.get("core", "javaui.home_ui")).newInstance();
            try {
                menu_home_ui = (URLInterface) ClassUtil.loadClass(config_pathseeting.get("core", "javaui.menu_home_ui")).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                menu_home_ui = null;
            }
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
            sizeauto = config_pathseeting.getBool("javaui.sizeauto", "core", sizeauto);
            h_x = config_pathseeting.getDouble("javaui.h_x", "core", h_x);
            w_x = config_pathseeting.getDouble("javaui.w_x", "core", w_x);
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
