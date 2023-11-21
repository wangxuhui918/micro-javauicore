package cn.bigcore.framework.ui.core.url.utils;

import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.utils.MinWindow;
import cn.bigcore.framework.utils.log.LogUtils;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.MD5;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 简单缩放
 */
public class SizeUtils {
    //计算后的横向缩放比
    public static double w_bs = 1;
    //计算后的纵向缩放比
    public static double h_bs = 1;
    //程序窗口宽高
    public static double bottom_height = 900;
    //程序窗口宽度
    public static double bottom_width = 1440;
    //title高度
    public static double title_geight = 31;
    //桌面
    public static Rectangle desktopBounds;
    //缩放链
    public static List<String> line_md5 = new ArrayList<String>();
    public static HashMap<String, Double> line_h = new HashMap<>();
    public static HashMap<String, Double> line_w = new HashMap<>();

    static {
        {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension screenSize = tk.getScreenSize();
            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            Insets screenInsets = tk.getScreenInsets(graphicsDevice.getDefaultConfiguration());
            desktopBounds = new Rectangle(screenInsets.left, screenInsets.top, screenSize.width - screenInsets.left - screenInsets.right, screenSize.height - screenInsets.top - screenInsets.bottom);
        }
        if (ConfigParams.sizeauto) {//获取当前屏幕大小,如果为自适应则获取最大分辨率,否则为当前自定义的分辨率
            ConfigParams.h_x = desktopBounds.getHeight() - title_geight;
            ConfigParams.w_x = desktopBounds.getWidth();
            SizeUtils.h_bs = (ConfigParams.h_x) / bottom_height;//
            SizeUtils.w_bs = ConfigParams.w_x / bottom_width;
        } else {
            ConfigParams.h_x = ConfigParams.h_x;
            ConfigParams.w_x = ConfigParams.w_x;
            SizeUtils.h_bs = ConfigParams.h_x / bottom_height;//
            SizeUtils.w_bs = ConfigParams.w_x / bottom_width;
        }
        if (ConfigParams.h_x > desktopBounds.getHeight() || ConfigParams.w_x > desktopBounds.getWidth()) {
            LogUtils.err("分辨率超出屏幕尺寸{}X{} / {}X{}", ConfigParams.w_x, ConfigParams.h_x, desktopBounds.getWidth(), desktopBounds.getHeight());
            System.exit(-1);
        }
        {
            String md5 = getmd5();
            line_md5.add(md5);
            line_h.put(md5, SizeUtils.h_bs);
            line_w.put(md5, SizeUtils.w_bs);
        }
    }

    public static String getmd5() {
        return MD5.create().digestHex(IdUtil.getSnowflakeNextIdStr());
    }

    public static void initBs() {
        initBs(ConfigParams.w_x, ConfigParams.h_x);
    }

    private static void initBs(double w, double h) {
        if (w < 0) {
            w = ConfigParams.w_x;
        }
        if (h < 0) {
            h = ConfigParams.h_x;
        }
        if (ConfigParams.sizeauto) {//自适应大小默认为分辨率的一般,弹窗为分辨率1/3
            SizeUtils.h_bs = h / ConfigParams.h_x;//
            SizeUtils.w_bs = w / ConfigParams.w_x;
        }
        ConfigParams.h_x = h;
        ConfigParams.w_x = w;
        {
            String md5 = getmd5();
            line_md5.add(md5);
            line_h.put(md5, SizeUtils.h_bs);
            line_w.put(md5, SizeUtils.w_bs);
        }
    }

    public static void addListener(Stage primaryStage) {
        if (ConfigParams.allowminwindow) {//允许最小化托盘
            MinWindow minwindows = MinWindow.getInstance();
            minwindows.listen(primaryStage);
        }
        if (ConfigParams.sizeauto) {//自适应大小默认为分辨率的一般,弹窗为分辨率1/3
            Node node = primaryStage.getScene().getRoot();
            //监听窗口高度改变
            primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    initBs(-1D, newValue.doubleValue());
                    initSizeX(node);
                }
            });
            //监听窗口宽度改变
            primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    initBs(newValue.doubleValue(), -1);
                    initSizeX(node);
                }
            });
            //        //监听X坐标
//        primaryStage.xProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                System.out.println("X：" + newValue);
//                initBs();
//                initSizeX(node);
//            }
//        });
//        //监听y坐标
//        primaryStage.yProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                System.out.println("y：" + newValue);
//                initBs();
//                initSizeX(node);
//            }
//        });
        }
    }

    public static void initSizeX(Node node) {
        double w_v_bs = 1;
        double h_v_bs = 1;
        String md5 = null;
        try {
            md5 = node.getProperties().get("md5").toString();
        } catch (Exception e) {
        }
        if (md5 == null) {
            for (int i = 0; i < line_md5.size(); i++) {
                w_v_bs = w_v_bs * line_w.get(line_md5.get(i));
                h_v_bs = h_v_bs * line_h.get(line_md5.get(i));
            }
        } else {
            int d = 0;
            for (int i = 0; i < line_md5.size(); i++) {
                if (md5.equals(line_md5.get(i))) {
                    d = i + 1;
                    break;
                }
            }
            for (int i = d; i < line_md5.size(); i++) {
                w_v_bs = w_v_bs * line_w.get(line_md5.get(i));
                h_v_bs = h_v_bs * line_h.get(line_md5.get(i));
            }
        }
        node.getProperties().put("md5", line_md5.get(line_md5.size() - 1));
        if (w_v_bs != 1 || h_v_bs != 1) {//如果倍数为1则不进行缩放
            if (node instanceof VBox) {
                ((VBox) node).setMinSize(((VBox) node).getPrefWidth() * w_v_bs, (((VBox) node).getPrefHeight() * h_v_bs));
                ((VBox) node).setPrefWidth(((VBox) node).getPrefWidth() * w_v_bs);
                ((VBox) node).setPrefHeight(((VBox) node).getPrefHeight() * h_v_bs);
                ((VBox) node).setLayoutX(((VBox) node).getLayoutX() * w_v_bs);
                ((VBox) node).setLayoutY(((VBox) node).getLayoutY() * h_v_bs);
                for (int i = 0; i < ((Pane) node).getChildren().size(); i++) {
                    initSizeX(((Pane) node).getChildren().get(i));
                }
            } else if (node instanceof Pane) {
                ((Pane) node).setMinSize(((Pane) node).getPrefWidth() * w_v_bs, (((Pane) node).getPrefHeight() * h_v_bs));
                ((Pane) node).setLayoutX(((Pane) node).getLayoutX() * w_v_bs);
                ((Pane) node).setLayoutY(((Pane) node).getLayoutY() * h_v_bs);
                ((Pane) node).setPrefWidth(((Pane) node).getPrefWidth() * w_v_bs);
                ((Pane) node).setPrefHeight(((Pane) node).getPrefHeight() * h_v_bs);
                for (int i = 0; i < ((Pane) node).getChildren().size(); i++) {
                    initSizeX(((Pane) node).getChildren().get(i));
                }
            } else if (node instanceof MenuBar) {
                ((MenuBar) node).setMinSize(((MenuBar) node).getPrefWidth() * w_v_bs, (((MenuBar) node).getPrefHeight() * h_v_bs));
                ((MenuBar) node).setPrefWidth(((MenuBar) node).getPrefWidth() * w_v_bs);
                ((MenuBar) node).setPrefHeight(((MenuBar) node).getPrefHeight() * h_v_bs);
                ((MenuBar) node).setLayoutX(((MenuBar) node).getLayoutX() * w_v_bs);
                ((MenuBar) node).setLayoutY(((MenuBar) node).getLayoutY() * h_v_bs);
            } else if (node instanceof Line) {
                ((Line) node).setLayoutX(((Line) node).getLayoutX() * w_v_bs);
                ((Line) node).setLayoutY(((Line) node).getLayoutY() * h_v_bs);
                ((Line) node).setStartX(((Line) node).getStartX() * w_v_bs);
                ((Line) node).setEndX(((Line) node).getEndX() * w_v_bs);
                ((Line) node).setStartY(((Line) node).getStartY() * h_v_bs);
                ((Line) node).setEndY(((Line) node).getEndY() * h_v_bs);
            } else if (node instanceof ImageView) {
                ((ImageView) node).setLayoutX(((ImageView) node).getLayoutX() * w_v_bs);
                ((ImageView) node).setLayoutY(((ImageView) node).getLayoutY() * h_v_bs);
                ((ImageView) node).setFitWidth(((ImageView) node).getFitWidth() * w_v_bs);
                ((ImageView) node).setFitHeight(((ImageView) node).getFitHeight() * h_v_bs);
            } else if (node instanceof MenuBar) {
                ((MenuBar) node).setPrefWidth(((MenuBar) node).getPrefWidth() * w_v_bs);
                ((MenuBar) node).setPrefHeight(((MenuBar) node).getPrefHeight() * h_v_bs);
                ((MenuBar) node).setLayoutX(((MenuBar) node).getLayoutX() * w_v_bs);
                ((MenuBar) node).setLayoutY(((MenuBar) node).getLayoutY() * h_v_bs);
            } else if (node instanceof TextArea) {
                ((TextArea) node).setMinSize(((TextArea) node).getPrefWidth() * w_v_bs, (((TextArea) node).getPrefHeight() * h_v_bs));
                ((TextArea) node).setPrefWidth(((TextArea) node).getPrefWidth() * w_v_bs);
                ((TextArea) node).setPrefHeight(((TextArea) node).getPrefHeight() * h_v_bs);
                ((TextArea) node).setLayoutX(((TextArea) node).getLayoutX() * w_v_bs);
                ((TextArea) node).setLayoutY(((TextArea) node).getLayoutY() * h_v_bs);
            } else if (node instanceof TextField) {
                ((TextField) node).setPrefWidth(((TextField) node).getPrefWidth() * w_v_bs);
                ((TextField) node).setPrefHeight(((TextField) node).getPrefHeight() * h_v_bs);
                ((TextField) node).setLayoutX(((TextField) node).getLayoutX() * w_v_bs);
                ((TextField) node).setLayoutY(((TextField) node).getLayoutY() * h_v_bs);
            } else if (node instanceof Control) {
                ((Control) node).setMinSize(((Control) node).getPrefWidth() * w_v_bs, ((Control) node).getPrefHeight() * h_v_bs);
                ((Control) node).setLayoutX(((Control) node).getLayoutX() * w_v_bs);
                ((Control) node).setLayoutY(((Control) node).getLayoutY() * h_v_bs);
            }
        }
    }
}
