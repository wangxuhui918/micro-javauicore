package cn.bigcore.framework.ui.core.controller;

import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.base.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class AboutController implements BaseController {
    @FXML
    ImageView iocn;
    @FXML
    Label product;
    @FXML
    Label author;
    @FXML
    Label support;
    @FXML
    Label url;
    @FXML
    Label copyright;
    @FXML
    Label useme;
    @FXML
    Label systemos;
    @FXML
    Label runtime;

    public void initialize() throws IOException {
        product.setText(ConfigParams.product);
        author.setText(ConfigParams.author);
        support.setText(ConfigParams.author_email + "  " + ConfigParams.author_qq);
        url.setText(ConfigParams.url);
        copyright.setText(ConfigParams.copyright);
        iocn.setImage(new javafx.scene.image.Image(ConfigParams.iocn_path.toString()));
        copyright.setText(ConfigParams.copyright);
        useme.setText(ConfigParams.useme);
        runtime.setText(ConfigParams.runtime);
        systemos.setText(ConfigParams.systemos);

    }

    public void usemebutton() {
        try {
            // 创建URI对象
            URI uri = new URI(ConfigParams.usemeurl);
            // 打开浏览器
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(Map<String, Object> data) {

    }
}