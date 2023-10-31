package com.guoshiyao.framework.ui.core.url;

import cn.hutool.core.io.resource.ResourceUtil;
import com.guoshiyao.framework.ui.core.controller.MenuController;
import com.guoshiyao.framework.ui.core.url.base.URLInterface;

import java.net.URL;

public class MenuURL implements URLInterface {
    @Override
    public URL getFXML() {
        return ResourceUtil.getResource("ui/core/menu.fxml");
    }

    @Override
    public String getMark() {
        return "菜单";
    }

    @Override
    public Class getController() {
        return MenuController.class;
    }
}
