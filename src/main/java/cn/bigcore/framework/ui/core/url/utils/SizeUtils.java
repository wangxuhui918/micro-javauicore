package cn.bigcore.framework.ui.core.url.utils;

import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.utils.MinWindow;
import cn.bigcore.framework.utils.log.LogUtils;
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

/**
 * 简单缩放
 */
public class SizeUtils {
    //计算后的横向缩放比
    public static double w_bs = 1;
    //计算后的纵向缩放比
    public static double h_bs = 1;
    //计算后的横向缩放比
    public static double w_cbs = 1;
    //计算后的纵向缩放比
    public static double h_cbs = 1;
    //程序窗口宽高
    public static double bottom_height = 900;
    //程序窗口宽度
    public static double bottom_width = 1440;
    //程序popup窗口宽高
    public static double popup_height = bottom_height / 2;
    //程序popup窗口宽度
    public static double popup_width = bottom_width / 2;
    //title高度
    public static double title_geight = 31;


    static {
        if (ConfigParams.sizeauto) {//自适应大小默认为分辨率的一般,弹窗为分辨率1/3
            Toolkit tk = Toolkit.getDefaultToolkit();
            Rectangle desktopBounds = null;
            {
                Dimension screenSize = tk.getScreenSize();
                GraphicsDevice graphicsDevice =
                        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                Insets screenInsets = tk.getScreenInsets(graphicsDevice.getDefaultConfiguration());
                desktopBounds = new Rectangle(
                        screenInsets.left, screenInsets.top,
                        screenSize.width - screenInsets.left - screenInsets.right,
                        screenSize.height - screenInsets.top - screenInsets.bottom);
            }
            ConfigParams.h_x = desktopBounds.getHeight();
            ConfigParams.w_x = desktopBounds.getWidth();
            SizeUtils.h_bs = (ConfigParams.h_x - title_geight) / bottom_height;//
            SizeUtils.w_bs = ConfigParams.w_x / bottom_width;
//            System.out.println("w_x = " + ConfigParams.w_x);
//            System.out.println("h_x = " + ConfigParams.h_x);
//            System.out.println("w_x = " + SizeUtils.w_bs);
//            System.out.println("h_x = " + SizeUtils.h_bs);
            if (SizeUtils.w_bs < 1 || SizeUtils.h_bs < 1) {
                LogUtils.err("分辨率过低,标准分辨率:" + bottom_height + "X" + bottom_width);
                System.exit(-1);
            }
            SizeUtils.h_cbs = SizeUtils.h_cbs * SizeUtils.h_bs;
            SizeUtils.w_cbs = SizeUtils.w_cbs * SizeUtils.w_bs;
        }
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
        SizeUtils.h_cbs = SizeUtils.h_cbs * SizeUtils.h_bs;
        SizeUtils.w_cbs = SizeUtils.w_cbs * SizeUtils.w_bs;
    }

    public static void addListener(Stage primaryStage) {
        if (ConfigParams.allowminwindow) {//允许最小化托盘
            MinWindow minwindows = MinWindow.getInstance();
            minwindows.listen(primaryStage);
        }
        Node node = primaryStage.getScene().getRoot();
        //监听窗口高度改变
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                System.out.println("当前高度：" + newValue);
                initBs(-1D, newValue.doubleValue());
                initSizeX(node);
            }
        });
        //监听窗口宽度改变
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                System.out.println("当前宽度：" + newValue);
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

    public static void initSizeX(Node node) {
        if (node instanceof VBox) {
//            ((VBox) node).setMinSize(((VBox) node).getPrefWidth() * w_bs, (((VBox) node).getPrefHeight() * h_bs));
            ((VBox) node).setPrefWidth(((VBox) node).getPrefWidth() * w_bs);
            ((VBox) node).setPrefHeight(((VBox) node).getPrefHeight() * h_bs);
            ((VBox) node).setLayoutX(((VBox) node).getLayoutX() * w_bs);
            ((VBox) node).setLayoutY(((VBox) node).getLayoutY() * h_bs);
            for (int i = 0; i < ((Pane) node).getChildren().size(); i++) {
                initSizeX(((Pane) node).getChildren().get(i));
            }
        } else if (node instanceof Pane) {
            ((Pane) node).setMinSize(((Pane) node).getPrefWidth() * w_bs, (((Pane) node).getPrefHeight() * h_bs));
            ((Pane) node).setLayoutX(((Pane) node).getLayoutX() * w_bs);
            ((Pane) node).setLayoutY(((Pane) node).getLayoutY() * h_bs);
            ((Pane) node).setPrefWidth(((Pane) node).getPrefWidth() * w_bs);
            ((Pane) node).setPrefHeight(((Pane) node).getPrefHeight() * h_bs);
            for (int i = 0; i < ((Pane) node).getChildren().size(); i++) {
                initSizeX(((Pane) node).getChildren().get(i));
            }
        } else if (node instanceof MenuBar) {
            ((MenuBar) node).setPrefWidth(((MenuBar) node).getPrefWidth() * w_bs);
            ((MenuBar) node).setPrefHeight(((MenuBar) node).getPrefHeight() * h_bs);
            ((MenuBar) node).setLayoutX(((MenuBar) node).getLayoutX() * w_bs);
            ((MenuBar) node).setLayoutY(((MenuBar) node).getLayoutY() * h_bs);
        } else if (node instanceof Line) {
            ((Line) node).setLayoutX(((Line) node).getLayoutX() * w_bs);
            ((Line) node).setLayoutY(((Line) node).getLayoutY() * h_bs);
            ((Line) node).setStartX(((Line) node).getStartX() * w_bs);
            ((Line) node).setEndX(((Line) node).getEndX() * w_bs);
            ((Line) node).setStartY(((Line) node).getStartY() * h_bs);
            ((Line) node).setEndY(((Line) node).getEndY() * h_bs);
        } else if (node instanceof ImageView) {
            ((ImageView) node).setLayoutX(((ImageView) node).getLayoutX() * w_bs);
            ((ImageView) node).setLayoutY(((ImageView) node).getLayoutY() * h_bs);
            ((ImageView) node).setFitWidth(((ImageView) node).getFitWidth() * w_bs);
            ((ImageView) node).setFitHeight(((ImageView) node).getFitHeight() * h_bs);
        } else if (node instanceof MenuBar) {
            ((MenuBar) node).setPrefWidth(((MenuBar) node).getPrefWidth() * w_bs);
            ((MenuBar) node).setPrefHeight(((MenuBar) node).getPrefHeight() * h_bs);
            ((MenuBar) node).setLayoutX(((MenuBar) node).getLayoutX() * w_bs);
            ((MenuBar) node).setLayoutY(((MenuBar) node).getLayoutY() * h_bs);
        } else if (node instanceof TextArea) {
            ((TextArea) node).setPrefWidth(((TextArea) node).getPrefWidth() * w_bs);
            ((TextArea) node).setPrefHeight(((TextArea) node).getPrefHeight() * h_bs);
            ((TextArea) node).setLayoutX(((TextArea) node).getLayoutX() * w_bs);
            ((TextArea) node).setLayoutY(((TextArea) node).getLayoutY() * h_bs);
        } else if (node instanceof TextField) {
            ((TextField) node).setPrefWidth(((TextField) node).getPrefWidth() * w_bs);
            ((TextField) node).setPrefHeight(((TextField) node).getPrefHeight() * h_bs);
            ((TextField) node).setLayoutX(((TextField) node).getLayoutX() * w_bs);
            ((TextField) node).setLayoutY(((TextField) node).getLayoutY() * h_bs);
        } else if (node instanceof Control) {
            ((Control) node).setMinSize(((Control) node).getPrefWidth() * w_bs, ((Control) node).getPrefHeight() * h_bs);
            ((Control) node).setLayoutX(((Control) node).getLayoutX() * w_bs);
            ((Control) node).setLayoutY(((Control) node).getLayoutY() * h_bs);
        }

    }
}
