package com.guoshiyao.framework.ui.core.url;

import cn.hutool.core.io.resource.ResourceUtil;
import com.guoshiyao.framework.ui.core.controller.AboutController;
import com.guoshiyao.framework.ui.core.url.base.URLInterface;

import java.net.URL;

public class AboutURL implements URLInterface {
    @Override
    public URL getFXML() {
        return ResourceUtil.getResource("ui/core/about.fxml");
    }

    @Override
    public String getMark() {
        return "关于";
    }

    @Override
    public Class getController() {
        return AboutController.class;
    }
}
