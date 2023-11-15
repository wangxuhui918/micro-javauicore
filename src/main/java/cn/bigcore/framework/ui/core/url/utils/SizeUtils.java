package cn.bigcore.framework.ui.core.url.utils;

import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.utils.log.LogUtils;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.awt.*;

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
    //程序popup窗口宽高
    public static double popup_height = bottom_height / 2;
    //程序popup窗口宽度
    public static double popup_width = bottom_width / 2;
    //title高度
    public static double title_geight = 70D;

    static {
        if (ConfigParams.sizeauto) {//自适应大小默认为分辨率的一般,弹窗为分辨率1/3
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension screenSize = tk.getScreenSize();
            ConfigParams.h_x = screenSize.getHeight();
            ConfigParams.w_x = screenSize.getWidth();
            SizeUtils.h_bs = (ConfigParams.h_x - title_geight) / bottom_height;
            SizeUtils.w_bs = ConfigParams.w_x / bottom_width;
            if (SizeUtils.w_bs < 1 || SizeUtils.h_bs < 1) {
                LogUtils.err("分辨率过低,标准分辨率:" + bottom_height + "X" + bottom_width);
                System.exit(-1);
            }
        }
    }

    public static void initSizeX(Node node) {
        if (node instanceof VBox) {
            ((VBox) node).setMinSize(((VBox) node).getPrefWidth() * w_bs, (((VBox) node).getPrefHeight() * h_bs));
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
            ((MenuBar) node).setMinSize(((MenuBar) node).getPrefWidth() * w_bs, ((MenuBar) node).getPrefHeight() * h_bs);
            ((MenuBar) node).setLayoutX(((MenuBar) node).getLayoutX() * w_bs);
            ((MenuBar) node).setLayoutY(((MenuBar) node).getLayoutY() * h_bs);
        } else if (node instanceof Line) {
            ((Line) node).setLayoutX(((Line) node).getLayoutX() * w_bs);
            ((Line) node).setLayoutY(((Line) node).getLayoutY() * h_bs);
            ((Line) node).setStartX(((Line) node).getStartX() * w_bs);
            ((Line) node).setStartY(((Line) node).getStartY() * w_bs);
        } else if (node instanceof ImageView) {
            ((ImageView) node).setLayoutX(((ImageView) node).getLayoutX() * w_bs);
            ((ImageView) node).setLayoutY(((ImageView) node).getLayoutY() * h_bs);
            ((ImageView) node).setFitWidth(((ImageView) node).getFitWidth() * w_bs);
            ((ImageView) node).setFitHeight(((ImageView) node).getFitHeight() * h_bs);
        } else if (node instanceof Control) {
            ((Control) node).setMinSize(((Control) node).getPrefWidth() * w_bs, ((Control) node).getPrefHeight() * h_bs);
            ((Control) node).setLayoutX(((Control) node).getLayoutX() * w_bs);
            ((Control) node).setLayoutY(((Control) node).getLayoutY() * h_bs);
        }

    }
}
