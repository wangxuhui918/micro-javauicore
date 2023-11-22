package cn.bigcore.framework.ui.core.url.utils;


import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.base.BaseController;
import cn.bigcore.framework.ui.core.controller.utils.AlertUtils;
import cn.bigcore.framework.ui.core.url.base.URLInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Map;

/**
 * 界面集合
 */
public class FXMLPopupUtils {


    public static FXMLLoader loadFXML(URLInterface uiBeanInterface) {
        return loadFXML(uiBeanInterface, null, false, null);
    }

    public static FXMLLoader loadFXML(URLInterface uiBeanInterface, Stage stage, boolean alert, Map<String, Object> transmit_data) {
        try {
            Stage stage_default = stage;
            stage_default = stage_default != null ? stage_default : new Stage();
            FXMLLoader loader = new FXMLLoader(uiBeanInterface.getFXML());
            Parent parent = loader.load();
            Scene scene_alert = new Scene(parent);
            stage_default.initModality(Modality.APPLICATION_MODAL);
            stage_default.setResizable(false);
            stage_default.setFullScreen(false);
            stage_default.setAlwaysOnTop(true);
            stage_default.getIcons().add(new Image(ConfigParams.iocn_path));
            stage_default.setScene(scene_alert);
            SizeUtils.initW_HSize(parent);
            stage_default.show();

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
