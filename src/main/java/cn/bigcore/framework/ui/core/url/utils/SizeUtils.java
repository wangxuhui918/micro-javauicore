package cn.bigcore.framework.ui.core.url.utils;

import cn.bigcore.framework.ConfigParams;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SizeUtils {
    public static void initSizeX(Node node) {
        if (node instanceof VBox) {
            ((VBox) node).setMinSize(((VBox) node).getPrefWidth() * ConfigParams.w_bs, (((VBox) node).getPrefHeight() * ConfigParams.h_bs));
//            ((VBox) node).setPrefWidth(((VBox) node).getPrefWidth() * ConfigParams.w_bs);
//            ((VBox) node).setPrefHeight(((VBox) node).getPrefHeight() * ConfigParams.h_bs);
            ((VBox) node).setLayoutX(((VBox) node).getLayoutX() * ConfigParams.w_bs);
            ((VBox) node).setLayoutY(((VBox) node).getLayoutY() * ConfigParams.h_bs);
            for (int i = 0; i < ((Pane) node).getChildren().size(); i++) {
                initSizeX(((Pane) node).getChildren().get(i));
            }
        } else if (node instanceof Pane) {
            ((Pane) node).setMinSize(((Pane) node).getPrefWidth() * ConfigParams.w_bs, (((Pane) node).getPrefHeight() * ConfigParams.h_bs));
            ((Pane) node).setLayoutX(((Pane) node).getLayoutX() * ConfigParams.w_bs);
            ((Pane) node).setLayoutY(((Pane) node).getLayoutY() * ConfigParams.h_bs);
            ((Pane) node).setPrefWidth(((Pane) node).getPrefWidth() * ConfigParams.w_bs);
            ((Pane) node).setPrefHeight(((Pane) node).getPrefHeight() * ConfigParams.h_bs);
            for (int i = 0; i < ((Pane) node).getChildren().size(); i++) {
                initSizeX(((Pane) node).getChildren().get(i));
            }
        } else if (node instanceof MenuBar) {
            ((MenuBar) node).setMinSize(((MenuBar) node).getPrefWidth() * ConfigParams.w_bs, ((MenuBar) node).getPrefHeight() * ConfigParams.h_bs);
            ((MenuBar) node).setLayoutX(((MenuBar) node).getLayoutX() * ConfigParams.w_bs);
            ((MenuBar) node).setLayoutY(((MenuBar) node).getLayoutY() * ConfigParams.h_bs);
//            ((MenuBar) node).setPrefWidth(((MenuBar) node).getPrefWidth() * ConfigParams.w_bs);
//            ((MenuBar) node).setPrefHeight(((MenuBar) node).getPrefHeight() * ConfigParams.h_bs);
        } else if (node instanceof Control) {
            ((Control) node).setMinSize(((Control) node).getPrefWidth() * ConfigParams.w_bs, ((Control) node).getPrefHeight() * ConfigParams.h_bs);
            ((Control) node).setLayoutX(((Control) node).getLayoutX() * ConfigParams.w_bs);
            ((Control) node).setLayoutY(((Control) node).getLayoutY() * ConfigParams.h_bs);
//            ((Control) node).setPrefWidth(((Control) node).getPrefWidth() * ConfigParams.w_bs);
//            ((Control) node).setPrefHeight(((Control) node).getPrefHeight() * ConfigParams.h_bs);
        }

    }
}
