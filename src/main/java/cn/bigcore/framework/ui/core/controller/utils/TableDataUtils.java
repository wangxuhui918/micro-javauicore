package cn.bigcore.framework.ui.core.controller.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Entity;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableDataUtils {

    public static void addEntityData(TableView tableView, List<Entity> data, boolean addColumn, boolean cleanOldData, boolean zeroDataToClear) {
        addEntityData(tableView, data, addColumn, cleanOldData, zeroDataToClear, null);
    }

    public static void addEntityData(TableView tableView, List<Entity> data, boolean addColumn, boolean cleanOldData, boolean zeroDataToClear, HashMap<String, HashMap<String, String>> convertColumData) {
        List<Map<String, Object>> data1 = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                data1.add(data.get(i));
            }
        }
        addMapData(tableView, data1, addColumn, cleanOldData, zeroDataToClear, convertColumData);
    }

    public static void clearItems(TableView tableView) {
        tableView.getItems().clear();
    }

    public static void addMapData(TableView tableView, List<Map<String, Object>> data, boolean addColumn, boolean cleanOldData, boolean zeroDataToClear) {
        addMapData(tableView, data, addColumn, cleanOldData, zeroDataToClear, null);
    }

    public static void addMapData(TableView tableView, List<Map<String, Object>> data, boolean addColumn, boolean cleanOldData, boolean zeroDataToClear, HashMap<String, HashMap<String, String>> convertColumData) {
        if (data != null && data.size() > 0) {
            List<String> columnsName = new ArrayList<>();
            //清空老旧数据
            if (cleanOldData) {
                tableView.getItems().clear();
            }
            //获取新数据或者老数据的列头
            {
                if (addColumn) {
                    columnsName = data.get(0).keySet().stream().toList();
                } else {
                    for (int i = 0; i < tableView.getColumns().size(); i++) {
                        TableColumn column = (TableColumn) tableView.getColumns().get(i);
                        columnsName.add(column.getText());
                    }
                }
                tableView.getColumns().clear();
            }
            //添加列
            for (int i = 0; i < columnsName.size(); i++) {
                String o = columnsName.get(i);
                TableColumn<Map, String> columeName = new TableColumn<>(o);
                columeName.setCellValueFactory(new MapValueFactory<>(o));
                tableView.getColumns().add(columeName);
            }
            //进行字典转换
            if (convertColumData != null && convertColumData.size() > 0) {
                if (data != null && data.size() > 0) {
                    for (Map<String, Object> o : data) {
                        for (String o1_1 : convertColumData.keySet()) {
                            Object val = convertColumData.get(o1_1).get(o.get(o1_1));
                            o.put(o1_1, val == null ? o.get(o1_1) : val);
                        }
                    }
                }
            }
            //添加数据,如果使用新列,直接使用新数据加入,如果使用老列,则筛选加入
            if (addColumn) {
                tableView.getItems().addAll(data);
            } else {
                List<Map<String, Object>> infomap = new ArrayList<>();
                for (Map<String, Object> o : data) {
                    Map<String, Object> info = new HashMap<>();
                    for (int i = 0; i < columnsName.size(); i++) {
                        if (o.get(columnsName.get(i)) != null && o.get(columnsName.get(i)) instanceof LocalDateTime) {
                            info.put(columnsName.get(i), DateUtil.format((LocalDateTime) o.get(columnsName.get(i)), DatePattern.NORM_DATETIME_PATTERN));
                        } else {
                            info.put(columnsName.get(i), o.get(columnsName.get(i)));
                        }
                    }
                    infomap.add(info);
                }
                tableView.getItems().addAll(infomap);
            }
        } else {
            if (cleanOldData) {
                tableView.getItems().clear();
            }
            if (zeroDataToClear) {
                tableView.getItems().clear();
            }
        }
    }

    /**
     * 专属log列
     *
     * @param tableView
     * @param clearData
     * @param log_id_column_name
     * @param log_state_column_name
     * @param log_message_column_name
     */
    public static void regularLogData(TableView tableView, boolean clearData, String log_id_column_name, String log_state_column_name, String log_message_column_name) {
        Platform.runLater(() -> {
            Map<String, Object> maps = new HashMap<>();
            List<Map<String, Object>> listdata = new ArrayList<>();
            maps.put("条目", log_id_column_name);
            maps.put("状态", log_state_column_name);
            maps.put("信息", log_message_column_name);
            listdata.add(maps);
            addMapData(tableView, listdata, false, clearData, false);
        });
    }
}
