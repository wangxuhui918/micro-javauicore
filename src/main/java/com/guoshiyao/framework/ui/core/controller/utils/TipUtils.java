package com.guoshiyao.framework.ui.core.controller.utils;

import com.guoshiyao.framework.utils.SpeakerUtils;
import javafx.application.Platform;
import javafx.scene.control.TextField;

public class TipUtils {
    public static void tip(String header, String context, javafx.scene.control.Alert.AlertType type, boolean speakHeader, boolean speakContext, TextField log) {
        tip(header, context, type, speakHeader, speakContext, true, log);
    }

    public static void tip(String header, String context, javafx.scene.control.Alert.AlertType type, boolean speakHeader, boolean speakContext) {
        tip(header, context, type, speakHeader, speakContext, true, null);
    }

    public static void tip(String header, String context, javafx.scene.control.Alert.AlertType type, boolean speakHeader, boolean speakContext, boolean alert, TextField log) {
        if (speakHeader) {
            SpeakerUtils.voicing(header);
        }
        if (speakContext) {
            SpeakerUtils.voicing(context);
        }
        if (log != null) {
            Platform.runLater(() -> {
                log.setText(context);
            });
        }
        if (alert) {
            AlertUtils.base(header, context, type, null, false, true);
        }
    }
}
