package cn.bigcore.framework.ui.core.controller;


import cn.bigcore.framework.ui.core.controller.utils.LoadingUtils;
import cn.bigcore.framework.ui.core.controller.base.BaseController;
import cn.bigcore.framework.utils.SpeakerUtils;
import cn.bigcore.framework.utils.thread.ThreadInterface;
import cn.bigcore.framework.utils.thread.ThreadUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

import java.util.Map;

public class SpeakerController implements BaseController {
    @FXML
    TextArea text;
    @FXML
    TextField state;
    @FXML
    Button yuedubutton;
    @FXML
    Pane pane;
    @FXML
    WebView web;

    public void yuedu(MouseEvent evnt) {//tempgitproejcttable

        ThreadUtils.confirmRun(new ThreadInterface() {
            @Override
            public void before() {
//                LoadingUtils.open(yuedubutton);
//                LoadingUtils.open(pane, text);
                LoadingUtils.open();
            }

            @Override
            public void body() {
                state.setText("阅读中");
                SpeakerUtils.voicing(text.getText());
                state.setText("阅读结束");
            }

            @Override
            public void after() {
//                LoadingUtils.close(pane);
                LoadingUtils.close();
            }
        });
    }

    @Override
    public void initData(Map<String, Object> data) {


    }


}