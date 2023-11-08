package cn.bigcore.framework.ui.core.url.utils;


import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.base.BaseController;
import cn.bigcore.framework.ui.core.controller.utils.AlertUtils;
import cn.bigcore.framework.ui.core.url.base.URLInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

/**
 * 界面集合
 */
public class FXMLBottomUtils {


    public static FXMLLoader loadFXML(Stage stage) {
        return loadFXML(ConfigParams.home_ui, null, stage, null, false);
    }

    public static FXMLLoader loadFXML(URLInterface uiBeanInterface, Pane vbox) {
        return loadFXML(uiBeanInterface, vbox, null, null, false);
    }

    public static FXMLLoader loadFXML(URLInterface uiBeanInterface, Pane vbox, Stage stage, Map<String, Object> transmit_data, boolean addBeforeCleanBody) {
        try {
            Stage stage_default = stage;
            FXMLLoader loader = new FXMLLoader(uiBeanInterface.getFXML());
            Parent parent = loader.load();
            if (stage_default != null) {//直接启动
                //, ConfigParams.bottom_width, ConfigParams.bottom_height
                Scene scene = new Scene(parent);
                stage_default.setTitle(ConfigParams.copyright);
                stage_default.setScene(scene);
                stage_default.setResizable(false);
                stage_default.getIcons().add(new Image(ConfigParams.iocn_path));
                initSizeX(parent);
                stage_default.show();
            } else if (vbox != null) {//在vbox加载页面
//                vbox.setPrefHeight(ConfigParams.bottom_height);
//                vbox.setPrefWidth(ConfigParams.bottom_width);
                vbox.getChildren().clear();
                vbox.getChildren().add(parent);
                initSizeX(vbox);
            } else {
                AlertUtils.err("系统异常!");

            }
            if (loader.getController() instanceof BaseController) {
                ((BaseController) loader.getController()).initData(transmit_data);
            }
            return loader;
        } catch (Exception e) {
            AlertUtils.err("系统异常!", e);
        }
        return null;
    }

    public static void initSizeX(Node node) {
        if (node instanceof VBox) {
            ((VBox) node).setMinSize(((VBox) node).getPrefWidth() * ConfigParams.w_bs, (((VBox) node).getPrefHeight() * ConfigParams.h_bs));
//            ((VBox) node).setPrefWidth(((VBox) node).getPrefWidth() * ConfigParams.w_bs);
//            ((VBox) node).setPrefHeight(((VBox) node).getPrefHeight() * ConfigParams.h_bs);
            ((VBox) node).setLayoutX(((VBox) node).getLayoutX() * ConfigParams.w_bs);
            ((VBox) node).setLayoutY(((VBox) node).getLayoutY() * ConfigParams.h_bs);
            for (int i = 0; i < ((Pane) node).getChildren().size(); i++) {
                initSizeX(((Pane) node).getChildren().get(i));
            }
        } else if (node instanceof Pane) {
            ((Pane) node).setMinSize(((Pane) node).getPrefWidth() * ConfigParams.w_bs, (((Pane) node).getPrefHeight() * ConfigParams.h_bs));
            ((Pane) node).setLayoutX(((Pane) node).getLayoutX() * ConfigParams.w_bs);
            ((Pane) node).setLayoutY(((Pane) node).getLayoutY() * ConfigParams.h_bs);
            ((Pane) node).setPrefWidth(((Pane) node).getPrefWidth() * ConfigParams.w_bs);
            ((Pane) node).setPrefHeight(((Pane) node).getPrefHeight() * ConfigParams.h_bs);
            for (int i = 0; i < ((Pane) node).getChildren().size(); i++) {
                initSizeX(((Pane) node).getChildren().get(i));
            }
        } else if (node instanceof MenuBar) {
            ((MenuBar) node).setMinSize(((MenuBar) node).getPrefWidth() * ConfigParams.w_bs, ((MenuBar) node).getPrefHeight() * ConfigParams.h_bs);
            ((MenuBar) node).setLayoutX(((MenuBar) node).getLayoutX() * ConfigParams.w_bs);
            ((MenuBar) node).setLayoutY(((MenuBar) node).getLayoutY() * ConfigParams.h_bs);
//            ((MenuBar) node).setPrefWidth(((MenuBar) node).getPrefWidth() * ConfigParams.w_bs);
//            ((MenuBar) node).setPrefHeight(((MenuBar) node).getPrefHeight() * ConfigParams.h_bs);
        } else if (node instanceof Control) {
            ((Control) node).setMinSize(((Control) node).getPrefWidth() * ConfigParams.w_bs, ((Control) node).getPrefHeight() * ConfigParams.h_bs);
            ((Control) node).setLayoutX(((Control) node).getLayoutX() * ConfigParams.w_bs);
            ((Control) node).setLayoutY(((Control) node).getLayoutY() * ConfigParams.h_bs);
//            ((Control) node).setPrefWidth(((Control) node).getPrefWidth() * ConfigParams.w_bs);
//            ((Control) node).setPrefHeight(((Control) node).getPrefHeight() * ConfigParams.h_bs);
        }

    }

}
