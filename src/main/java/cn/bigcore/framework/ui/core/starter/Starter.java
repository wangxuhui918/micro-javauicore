package cn.bigcore.framework.ui.core.starter;

import cn.bigcore.framework.ui.core.controller.utils.MinWindow;
import cn.bigcore.framework.ui.core.url.utils.FXMLBottomUtils;
import cn.bigcore.framework.ConfigParams;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 启动程序,并加载主菜单
 */
public class Starter extends Application {

    @Override
    public void start(Stage stage) {
        if (ConfigParams.allowminwindow) {//允许最小化托盘
            MinWindow minwindows = MinWindow.getInstance();
            minwindows.listen(stage);
        }
        FXMLBottomUtils.loadFXML(stage);
    }

    public static void main(String[] args) {
        launch();
    }


}