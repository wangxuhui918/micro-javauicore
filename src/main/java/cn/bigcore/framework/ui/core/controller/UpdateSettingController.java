package cn.bigcore.framework.ui.core.controller;


import cn.hutool.core.io.FileUtil;
import cn.bigcore.framework.ConfigParams;
import cn.bigcore.framework.ui.core.controller.base.BaseController;
import cn.bigcore.framework.ui.core.controller.utils.AlertUtils;
import cn.bigcore.framework.ui.core.controller.utils.LoadingUtils;
import cn.bigcore.framework.utils.thread.ThreadInterface;
import cn.bigcore.framework.utils.thread.ThreadUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.util.Map;

public class UpdateSettingController implements BaseController {
    @FXML
    TextArea settingtext;
    @FXML
    TextArea settingtextcache;
    @FXML
    Button updatesetting;

    public void updatesetting(MouseEvent evnt) {//tempgitproejcttable

        ThreadUtils.confirmRun(new ThreadInterface() {
            @Override
            public void before() {
                LoadingUtils.open();
            }

            @Override
            public void body() {
                FileUtil.writeUtf8String(settingtext.getText(), ConfigParams.config_file_full_path);
                AlertUtils.info("配置更新成功!重启后生效!");
            }

            @Override
            public void after() {
                LoadingUtils.close();
            }
        });
    }

    public void change(MouseEvent evnt) {//tempgitproejcttable

        ThreadUtils.run(new ThreadInterface() {
            @Override
            public void before() {
                LoadingUtils.open();
            }

            @Override
            public void body() {
                if (!updatesetting.isDisable()) {
                    settingtextcache.setText(settingtext.getText());
                    settingtext.setText(FileUtil.readUtf8String(ConfigParams.resource_demo_config_file));
                    settingtext.setEditable(false);
                    updatesetting.setDisable(true);
                } else {
                    settingtext.setText(settingtextcache.getText());
                    settingtextcache.setText("");
                    settingtext.setEditable(true);
                    updatesetting.setDisable(false);
                }
            }

            @Override
            public void after() {
                LoadingUtils.close();
            }
        }, true);
    }

    @Override
    public void initData(Map<String, Object> data) {
        settingtextcache.setVisible(false);
        settingtext.setText(FileUtil.readUtf8String(ConfigParams.config_file_full_path));
    }

}