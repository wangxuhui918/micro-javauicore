package com.guoshiyao.framework.ui.core.controller.utils;

import cn.hutool.core.util.StrUtil;
import javafx.scene.control.ComboBox;

import java.util.List;
import java.util.Map;

/**
 * 下拉框
 */
public class BoxDataUtils {

    public static void addData(ComboBox combobox, List<Map<String, Object>> data, String keyColumn) {
        combobox.getItems().clear();
        for (int i = 0; data != null && i < data.size(); i++) {
            if (i == 0) {
                combobox.setValue(data.get(i).get(keyColumn));
            }
            combobox.getItems().add(data.get(i).get(keyColumn));
        }
    }

    public static void addData(ComboBox combobox, String defaultValue, String value, boolean clear) {
        if (clear) {
            combobox.getItems().clear();
        }
        if (StrUtil.equals(value, defaultValue)) {
            combobox.setValue(value);
        }
        combobox.getItems().add(value);
    }


}
