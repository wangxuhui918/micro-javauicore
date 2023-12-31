package cn.bigcore.framework.ui.core.url;

import cn.bigcore.framework.ui.core.controller.UpdateSettingController;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.bigcore.framework.ui.core.url.base.URLInterface;

import java.net.URL;

public class UpdateSettingURL implements URLInterface {
    @Override
    public URL getFXML() {
        return ResourceUtil.getResource("ui/core/updatesetting.fxml");
    }

    @Override
    public String getMark() {
        return "更新配置文件";
    }

    @Override
    public Class getController() {
        return UpdateSettingController.class;
    }
}
