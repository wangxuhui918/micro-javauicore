package cn.bigcore.framework.utils.thread;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * @author 汪旭辉
 * @date 2022/11/8
 * @readme
 */
public class ThreadUtils {


    public static void run(ThreadInterface threadInterface, boolean bodyRunLater) {
        new Thread(() -> {
            Platform.runLater(() -> {
                threadInterface.before();
            });
            try {
                if (bodyRunLater) {
                    Platform.runLater(() -> {
                        threadInterface.body();
                    });
                } else {
                    threadInterface.body();
                }
            } catch (Exception e) {
                throw e;
            } finally {
                Platform.runLater(() -> {
                    threadInterface.after();
                });
            }
        }).start();
    }

    public static void confirmRun(ThreadInterface threadInterface) {
        run(threadInterface, false);
    }

    public static void confirmRun(ThreadInterface threadInterface, boolean bodyRunLater) {
        confirmRun(threadInterface, bodyRunLater, "温馨提示", "确定继续操作？");
    }

    public static void confirmRun(ThreadInterface threadInterface, boolean bodyRunLater, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // 创建一个确认对话框
        alert.setHeaderText(headerText); // 设置对话框的头部文本
        alert.setContentText(contentText);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) { // 单击了确定按钮OK_DONE
            ThreadUtils.run(threadInterface, bodyRunLater);
        }
    }

}
