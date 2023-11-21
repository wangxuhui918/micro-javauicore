package cn.bigcore.framework.ui.core.url.utils;


import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.base.BaseController;
import cn.bigcore.framework.ui.core.controller.utils.AlertUtils;
import cn.bigcore.framework.ui.core.url.base.URLInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
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
                stage_default.setResizable(true);
                stage_default.getIcons().add(new Image(ConfigParams.iocn_path));
//                stage_default.initStyle(StageStyle.UNDECORATED);
                SizeUtils.initSizeX(parent);
                SizeUtils.addListener(stage_default);
                stage_default.show();
            }
            if (vbox != null) {//在vbox加载页面
//                vbox.setPrefHeight(ConfigParams.bottom_height);
//                vbox.setPrefWidth(ConfigParams.bottom_width);
                vbox.getChildren().clear();
                vbox.getChildren().add(parent);
                SizeUtils.initSizeX(vbox);
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

}
