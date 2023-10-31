package com.guoshiyao.framework.ui.core.controller.utils;

import com.guoshiyao.framework.utils.log.LogUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtils {

    //
    public static void info(String headerText, String context) {
        base(headerText, context, javafx.scene.control.Alert.AlertType.INFORMATION, null, false, true);
    }

    public static void info(String context) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.INFORMATION, null, false, true);
    }

    public static void info(String context, Exception e) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.INFORMATION, e, false, true);
    }

    //
    public static void warning(String headerText, String context) {
        base(headerText, context, Alert.AlertType.WARNING, null, false, true);
    }

    public static void warning(String context) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.WARNING, null, false, true);
    }

    public static void warning(String context, Exception e) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.WARNING, e, false, true);
    }

    //
    public static void confirmation(String headerText, String context) {
        base(headerText, context, Alert.AlertType.CONFIRMATION, null, false, true);
    }

    public static void confirmation(String context) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.CONFIRMATION, null, false, true);
    }

    public static void confirmation(String context, Exception e) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.CONFIRMATION, e, false, true);
    }

    //
    public static void err(String headerText, String context) {
        base(headerText, context, javafx.scene.control.Alert.AlertType.ERROR, null, false, true);
    }

    public static void err(String context) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.ERROR, null, false, true);
    }

    public static void err(String context, Exception e) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.ERROR, e, false, true);
    }

    //
    public static void none(String headerText, String context) {
        base(headerText, context, javafx.scene.control.Alert.AlertType.NONE, null, false, true);
    }

    public static void none(String context) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.NONE, null, false, true);
    }

    public static void none(String context, Exception e) {
        base("温馨提示", context, javafx.scene.control.Alert.AlertType.NONE, e, false, true);
    }

    //
    protected static void base(String headerText, String context, Alert.AlertType alertType, Exception e, boolean thorowException, boolean runLater) {
        if (runLater) {
            Platform.runLater(() -> {
                base(headerText, context, alertType, e, thorowException);
            });
        } else {
            base(headerText, context, alertType, e, thorowException);
        }
    }

    private static void base(String headerText, String context, javafx.scene.control.Alert.AlertType alertType, Exception e, boolean thorowException) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType); // 创建一个确认对话框
        alert.setHeaderText(headerText); // 设置对话框的头部文本
        if (e != null) {
            alert.setContentText(context + "\n" + e.toString());
        } else {
            alert.setContentText(context);
        }
        LogUtils.info(context);

        Optional<ButtonType> buttonType = alert.showAndWait();
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
    }
}
