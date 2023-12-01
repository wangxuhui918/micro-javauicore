package cn.bigcore.framework.ui.core.controller.utils.page;

import cn.bigcore.framework.ui.core.controller.utils.TableDataUtils;
import cn.bigcore.framework.ui.core.controller.utils.db.DataSourceUtils;
import cn.bigcore.framework.ui.core.controller.utils.db.SqlUtils;
import cn.bigcore.framework.ui.core.controller.utils.page.bean.ParamInfo;
import cn.bigcore.framework.utils.log.LogUtils;
import cn.hutool.core.util.StrUtil;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;

public class PagingSelectUtils {

    //每页记录数
    public static final int pagesize = 30;
    //展示最大分页数量
    public static final int ling_page_count = 20;

    /**
     * 分页绑定
     */
    public static void bind(String tableName, TableView usertable, Pagination pagination, List<ParamInfo> listParamInfo, List<ParamInfo> listParamInfoas) {
        bind(tableName, usertable, pagination, listParamInfo, listParamInfoas, null);
    }

    /**
     * 分页绑定
     */
    public static void bind(String tableName, TableView usertable, Pagination pagination, List<ParamInfo> listParamInfo, List<ParamInfo> listParamInfoas, HashMap<String, HashMap<String, String>> convertColumData) {
        boolean init = false;
        try {
            init = (Boolean) pagination.getProperties().get("init");
        } catch (Exception e) {
        }
        if (!init) {
            pagination.setMaxPageIndicatorCount(ling_page_count);
            pagination.setPageFactory((pageIndexx) -> {
                select(tableName, usertable, pagination, pageIndexx, listParamInfo, listParamInfoas, convertColumData);
                Label label1 = new Label();
                Label label2 = new Label();
                return new VBox(label1, label2);
            });
            pagination.getProperties().put("init", true);
        }
    }

    /**
     * 分页查询
     *
     * @param tableName
     * @param usertable
     * @param pagination
     * @param pageIndex
     * @param listParamInfo
     * @param listParamInfoas
     */
    public static void select(String tableName, TableView usertable, Pagination pagination, List<ParamInfo> listParamInfo, List<ParamInfo> listParamInfoas) {
        select(tableName, usertable, pagination, listParamInfo, listParamInfoas, null);
    }

    /**
     * 分页查询
     *
     * @param pageIndex
     * @param tableName
     * @param usertable
     * @param pagination
     * @param listParamInfo
     * @param listParamInfoas
     * @param convertColumData
     */
    public static void select(String tableName, TableView usertable, Pagination pagination, List<ParamInfo> listParamInfo, List<ParamInfo> listParamInfoas, HashMap<String, HashMap<String, String>> convertColumData) {
        select(tableName, usertable, pagination, 0, listParamInfo, listParamInfoas, convertColumData);
    }

    /**
     * 分页查询
     *
     * @param tableName
     * @param usertable
     * @param pagination
     * @param listParamInfo
     * @param listParamInfoas
     * @param convertColumData
     */
    public static void select(String tableName, TableView usertable, Pagination pagination, int pageIndex, List<ParamInfo> listParamInfo, List<ParamInfo> listParamInfoas) {
        select(tableName, usertable, pagination, pageIndex, listParamInfo, listParamInfoas, null);
    }

    /**
     * 分页查询
     *
     * @param tableName
     * @param usertable
     * @param pagination
     * @param listParamInfo
     * @param listParamInfoas
     */
    public static void select(String tableName, TableView usertable, Pagination pagination, int pageIndex, List<ParamInfo> listParamInfo, List<ParamInfo> listParamInfoas, HashMap<String, HashMap<String, String>> convertColumData) {
        //
        String sql_where = SqlUtils.conditionalSQL(listParamInfo);
        String sql_as = SqlUtils.conditionalSQL(listParamInfoas);
//
        LogUtils.debug("where = " + sql_where);
        LogUtils.debug("sql_as = " + sql_as);


        //
        String sql = StrUtil.format("select {} from {} {} LIMIT " + (pageIndex * pagesize) + "," + pagesize, sql_as, tableName, sql_where.toString());
        String countsql = StrUtil.format("select count(*) as C from {} {} ", tableName, sql_where);

        //
        int th = Integer.parseInt(DataSourceUtils.findTableData(countsql).get(0).get("C").toString());
        pagination.setCurrentPageIndex(pageIndex);
        pagination.setPageCount(th % pagesize > 0 ? th / pagesize + 1 : th / pagesize);
        //
        List data_list = DataSourceUtils.findTableData(sql);
        TableDataUtils.addMapData(usertable, data_list, false, true, true, convertColumData);
    }
}
