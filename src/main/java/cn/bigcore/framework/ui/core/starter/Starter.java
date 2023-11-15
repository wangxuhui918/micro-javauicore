package cn.bigcore.framework.ui.core.starter;

import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.utils.MinWindow;
import cn.bigcore.framework.ui.core.url.utils.FXMLBottomUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

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
        FXMLLoader fxmlloader=FXMLBottomUtils.loadFXML(stage);
//        System.out.println("screenSize = " + Window.getWindows().get(0).getHeight());
//        System.out.println("screenSize = " + ((Pane)fxmlloader.getRoot()).getHeight());

    }

    public static void main(String[] args) {
        launch();
    }


}