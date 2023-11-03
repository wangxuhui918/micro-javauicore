package cn.bigcore.framework.ui.core.url.utils;


import cn.bigcore.framework.ui.core.controller.utils.AlertUtils;
import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.base.BaseController;
import cn.bigcore.framework.ui.core.url.base.URLInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                Scene scene = new Scene(parent, ConfigParams.bottom_width, ConfigParams.bottom_height);
                stage_default.setTitle(ConfigParams.copyright);
                stage_default.setScene(scene);
                stage_default.setResizable(false);
                stage_default.getIcons().add(new Image(ConfigParams.iocn_path));
                stage_default.show();
            } else if (vbox != null) {//在vbox加载页面
                vbox.setPrefHeight(ConfigParams.bottom_height);
                vbox.setPrefWidth(ConfigParams.bottom_width);
                vbox.getChildren().clear();
                vbox.getChildren().add(parent);
            } else {
                AlertUtils.err("系统异常!");

            }
            if (loader.getController() instanceof BaseController) {
                ((BaseController) loader.getController()).initData(transmit_data);
            }
            setLayoutSize(parent);
            return loader;
        } catch (Exception e) {
            AlertUtils.err("系统异常!", e);
        }
        return null;
    }


    /**
     * 设置主画布大小
     *
     * @param parent
     */
    public static void setLayoutSize(Parent parent) {
        if (parent != null) {
            if (parent instanceof VBox) {
                setLayoutSize((VBox) parent);
            } else if (parent instanceof Pane) {
                setLayoutSize((Pane) parent);
            }
        }
    }

    /**
     * 设置主画布大小
     *
     * @param vbox
     */
    public static void setLayoutSize(VBox vbox) {
        if (vbox != null) {
            vbox.setPrefHeight(ConfigParams.bottom_height);
            vbox.setPrefWidth(ConfigParams.bottom_width);
            List<Node> listPane = vbox.getChildrenUnmodifiable().stream().filter(p -> p instanceof Pane).collect(Collectors.toList());
            if (listPane != null && listPane.size() == 1) {
                setLayoutSize((Pane) listPane.get(0));
            }
        }
    }

    /**
     * 设置主画布大小
     *
     * @param pane
     */
    public static void setLayoutSize(Pane pane) {
        if (pane != null) {
            pane.setPrefHeight(ConfigParams.bottom_height);
            pane.setPrefWidth(ConfigParams.bottom_width);
        }
    }
}
