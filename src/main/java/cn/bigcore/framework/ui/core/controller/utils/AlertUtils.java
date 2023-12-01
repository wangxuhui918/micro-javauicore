package cn.bigcore.framework.ui.core.controller.utils;

import cn.bigcore.framework.utils.log.LogUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtils {

    //
    public static ButtonType info(String headerText, String context) {
        return base(headerText, context, javafx.scene.control.Alert.AlertType.INFORMATION, null, false, false);
    }

    public static ButtonType info(String context) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.INFORMATION, null, false, false);
    }

    public static ButtonType info(String context, Exception e) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.INFORMATION, e, false, false);
    }

    //
    public static ButtonType warning(String headerText, String context) {
        return base(headerText, context, Alert.AlertType.WARNING, null, false, false);
    }

    public static ButtonType warning(String context) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.WARNING, null, false, false);
    }

    public static ButtonType warning(String context, Exception e) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.WARNING, e, false, false);
    }

    //
    public static ButtonType confirmation(String headerText, String context) {
        return base(headerText, context, Alert.AlertType.CONFIRMATION, null, false, false);
    }

    public static ButtonType confirmation(String context) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.CONFIRMATION, null, false, false);
    }

    public static ButtonType confirmation(String context, Exception e) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.CONFIRMATION, e, false, false);
    }

    //
    public static ButtonType err(String headerText, String context) {
        return base(headerText, context, javafx.scene.control.Alert.AlertType.ERROR, null, false, false);
    }

    public static ButtonType err(String context) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.ERROR, null, false, false);
    }

    public static ButtonType err(String context, Exception e) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.ERROR, e, false, false);
    }

    //
    public static ButtonType none(String headerText, String context) {
        return base(headerText, context, javafx.scene.control.Alert.AlertType.NONE, null, false, false);
    }

    public static ButtonType none(String context) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.NONE, null, false, false);
    }

    public static ButtonType none(String context, Exception e) {
        return base("温馨提示", context, javafx.scene.control.Alert.AlertType.NONE, e, false, false);
    }

    //
    protected static ButtonType base(String headerText, String context, Alert.AlertType alertType, Exception e, boolean thorowException, boolean runLater) {
//        if (runLater) {
//            Platform.runLater(() -> {
//                base(headerText, context, alertType, e, thorowException);
//            });
//        } else {
        return base(headerText, context, alertType, e, thorowException);
//        }
    }

    private static ButtonType base(String headerText, String context, javafx.scene.control.Alert.AlertType alertType, Exception e, boolean thorowException) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType); // 创建一个确认对话框
        alert.setHeaderText(headerText); // 设置对话框的头部文本
        if (e != null) {
            alert.setContentText(context + "\n" + e.toString());
        } else {
            alert.setContentText(context);
        }
        LogUtils.info(context);

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            // ... user chose OK
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        if (e != null) {
            LogUtils.err(e.toString());
            e.printStackTrace();
            if (thorowException) {
                throw new RuntimeException(e.toString());
            }
        } else {
            if (thorowException) {
                throw new RuntimeException(context);
            }
        }
        return buttonType.get();
    }
}
