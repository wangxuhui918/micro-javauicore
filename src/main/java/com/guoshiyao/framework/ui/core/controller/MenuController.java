package com.guoshiyao.framework.ui.core.controller;

import com.guoshiyao.framework.ConfigParams;
import com.guoshiyao.framework.ui.core.controller.base.BaseController;
import com.guoshiyao.framework.ui.core.controller.utils.LoadingUtils;
import com.guoshiyao.framework.ui.core.url.AboutURL;
import com.guoshiyao.framework.ui.core.url.SpeakerURL;
import com.guoshiyao.framework.ui.core.url.UpdateSettingURL;
import com.guoshiyao.framework.ui.core.url.utils.FXMLBottomUtils;
import com.guoshiyao.framework.ui.core.url.utils.FXMLPopupUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Map;

public class MenuController implements BaseController {
    @FXML
    public Pane root_pane;
    @FXML
    public Pane context_pane;

    /**
     * 初始化界面
     *
     * @throws IOException
     */
    public void initialize() throws IOException {
        FXMLBottomUtils.loadFXML(ConfigParams.menu_home_ui, context_pane);
        LoadingUtils.mask_pane = root_pane;
    }

    public void speaker() throws IOException {
        FXMLBottomUtils.loadFXML(new SpeakerURL(), context_pane);
    }


    public void about() throws IOException {
        FXMLPopupUtils.loadFXML(new AboutURL());
    }

    public void updatesetting() throws IOException {
        FXMLBottomUtils.loadFXML(new UpdateSettingURL(), context_pane);
    }


    @Override
    public void initData(Map<String, Object> data) {

    }
}