package com.guoshiyao.framework.ui.core.controller.utils;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.controlsfx.control.MaskerPane;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 汪旭辉
 * @date 2022/11/8
 * @readme
 */
public class LoadingUtils {
    /**
     * 菜单底层Pane,可以选择为菜单的根节点,可以选择菜单的内容节点
     */
    public static Pane mask_pane;

    /**
     * @param context_pane
     * @param coverage_node 指定遮罩层覆盖范围的元素
     */
    public static void open(Pane context_pane_, Node coverage_node) {
        Pane pane = context_pane_ == null ? mask_pane : context_pane_;
        pane.setPadding(new Insets(0, 0, 0, 0));
        MaskerPane loginMaskerPane = new MaskerPane();
        {
            loginMaskerPane.setVisible(true);
            if (coverage_node != null) {
                loginMaskerPane.setPrefHeight(coverage_node.getLayoutBounds().getHeight());
                loginMaskerPane.setPrefWidth(coverage_node.getLayoutBounds().getWidth());
                loginMaskerPane.setLayoutX(coverage_node.getLayoutX());
                loginMaskerPane.setLayoutY(coverage_node.getLayoutY());
            } else {
                loginMaskerPane.setPrefHeight(pane.getPrefHeight());
                loginMaskerPane.setPrefWidth(pane.getPrefWidth());
            }
        }
        pane.getChildren().add(loginMaskerPane);
        pane.setVisible(true);
    }

    /**
     * @param coverage_node 指定遮罩层覆盖范围的元素
     */
    public static void open(Node coverage_node) {
        open(null, coverage_node);
    }

    /**
     * @param loading 载入遮罩层的元素,默认覆盖范围为该元素
     */
    public static void open() {
        open(null);
    }

    public static void close(Pane context_pane_) {
        Pane pane = context_pane_ == null ? mask_pane : context_pane_;
        List<Node> list = new ArrayList<>();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if (pane.getChildren().get(i) instanceof MaskerPane) {
                list.add(pane.getChildren().get(i));
            }
        }
        pane.getChildren().removeAll(list);
    }

    public static void close() {
        close(null);
    }


//    public static void loadData(StackPane loading, String txt) {
//        loadData(loading, txt, true);
//    }
//
//    public static void loadData(StackPane loading, String txt, boolean runLater) {
//        if (runLater) {
//            Platform.runLater(() -> {
//                if (loading.getChildren().size() > 0) {
//                    loading.getChildren().clear();
//                }
//                Label label = new Label(txt);
////            label.setFont(new Font(13));
////            label.setRotate(90);
////            label.setTextFill(Color.color(Math.random(), Math.random(), Math.random(), Math.random()));
//                label.setTextFill(Color.RED);
//                label.setFont(Font.font("TimesRomes", FontWeight.BOLD, FontPosture.ITALIC, 13));
//                loading.getChildren().add(label);
//            });
//        } else {
//            if (loading.getChildren().size() > 0) {
//                loading.getChildren().clear();
//            }
//            Label label = new Label(txt);
////            label.setFont(new Font(13));
////            label.setRotate(90);
////            label.setTextFill(Color.color(Math.random(), Math.random(), Math.random(), Math.random()));
//            label.setTextFill(Color.RED);
//            label.setFont(Font.font("TimesRomes", FontWeight.BOLD, FontPosture.ITALIC, 13));
//            loading.getChildren().add(label);
//        }
//
//    }

}
