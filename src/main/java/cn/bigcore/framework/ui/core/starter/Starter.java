package cn.bigcore.framework.ui.core.starter;

import cn.bigcore.framework.ui.core.url.utils.FXMLBottomUtils;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 启动程序,并加载主菜单
 */
public class Starter extends Application {

    @Override
    public void start(Stage stage) {
        FXMLBottomUtils.loadFXML(stage);
    }

    public static void main(String[] args) {
        launch();
    }


}