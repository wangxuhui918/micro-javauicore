package com.guoshiyao.framework.ui.core.url;

import cn.hutool.core.io.resource.ResourceUtil;
import com.guoshiyao.framework.ui.core.controller.SpeakerController;
import com.guoshiyao.framework.ui.core.url.base.URLInterface;

import java.net.URL;

public class SpeakerURL implements URLInterface {
    @Override
    public URL getFXML() {
        return ResourceUtil.getResource("ui/core/speaker.fxml");
    }

    @Override
    public String getMark() {
        return "文字阅读";
    }

    @Override
    public Class getController() {
        return SpeakerController.class;
    }
}
