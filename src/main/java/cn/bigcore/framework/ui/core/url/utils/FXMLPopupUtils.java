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

            //            stages.setTitle("每日打开提示");
            //        DialogPane dialogPane = new DialogPane();
//        dialogPane.setPadding(new Insets(5, 5, 5, 5));
//        ImageView imageView = new ImageView("/talk.png");
//        imageView.setFitWidth(50);
//        imageView.setPreserveRatio(true);
//        dialogPane.setGraphic(imageView);
//        dialogPane.setContentText("每日打开提示");
            //        stages.setScene(new Scene(dialogPane));
            SizeUtils.initSizeX(parent);

            Scene scene_alert = new Scene(parent);
            stage_default.initModality(Modality.APPLICATION_MODAL);
            stage_default.setResizable(false);
            stage_default.setFullScreen(false);
            stage_default.setAlwaysOnTop(true);
            stage_default.getIcons().add(new Image(ConfigParams.iocn_path));
//            stage_default.setHeight(ConfigParams.popup_height);
//            stage_default.setWidth(ConfigParams.popup_width);
            stage_default.setScene(scene_alert);
            stage_default.show();

            if (loader.getController() instanceof BaseController) {
                ((BaseController) loader.getController()).initData(transmit_data);
            }
//            setLayoutSize(parent);
            return loader;
        } catch (Exception e) {
            AlertUtils.err("系统异常!", e);
        }
        return null;
    }

//
//    /**
//     * 设置主画布大小
//     *
//     * @param parent
//     */
//    public static void setLayoutSize(Parent parent) {
//        if (parent != null) {
//            if (parent instanceof VBox) {
//                setLayoutSize((VBox) parent);
//            } else if (parent instanceof Pane) {
//                setLayoutSize((Pane) parent);
//            }
//        }
//    }
//
//    /**
//     * 设置主画布大小
//     *
//     * @param vbox
//     */
//    public static void setLayoutSize(VBox vbox) {
//        if (vbox != null) {
//            vbox.setPrefHeight(ConfigParams.popup_height);
//            vbox.setPrefWidth(ConfigParams.popup_width);
//            List<Node> listPane = vbox.getChildrenUnmodifiable().stream().filter(p -> p instanceof Pane).collect(Collectors.toList());
//            if (listPane != null && listPane.size() == 1) {
//                setLayoutSize((Pane) listPane.get(0));
//            }
//        }
//    }
//
//    /**
//     * 设置主画布大小
//     *
//     * @param pane
//     */
//    public static void setLayoutSize(Pane pane) {
//        if (pane != null) {
//            pane.setPrefHeight(ConfigParams.popup_height);
//            pane.setPrefWidth(ConfigParams.popup_width);
//        }
//    }
}
