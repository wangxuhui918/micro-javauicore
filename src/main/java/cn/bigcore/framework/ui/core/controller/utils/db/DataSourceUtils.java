package cn.bigcore.framework.ui.core.controller.utils.db;

import cn.bigcore.framework.ConfigParams;
import cn.hutool.db.DbUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 汪旭辉
 * @date 2022/11/4
 * @readme
 */
public class DataSourceUtils {

    public static DruidDataSource druiddatasource;

    static {
        druiddatasource = new DruidDataSource();
        druiddatasource.setUrl(ConfigParams.extend.getStr("extend.db.url"));
        druiddatasource.setUsername(ConfigParams.extend.getStr("extend.db.username"));
        druiddatasource.setPassword(ConfigParams.extend.getStr("extend.db.password"));
    }

    public static List<cn.hutool.db.Entity> findTableDataEntity(String sql) {
        HashMap<String, String> map = new HashMap<>();
        try {
            List<cn.hutool.db.Entity> list = DbUtil.use(druiddatasource).query(sql);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map<String, Object>> findTableData(String sql, Object... param) {
        HashMap<String, String> map = new HashMap<>();
        try {
            List<Map<String, Object>> list = JdbcUtils.executeQuery(druiddatasource, sql, param);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int executeUpdate(String sql, Object... param) {
        try {
            return JdbcUtils.executeUpdate(druiddatasource, sql, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static boolean addTableData(String tableName, HashMap<String, Object> data) {
        try {
            JdbcUtils.insertToTable(druiddatasource, tableName, data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void addTableDatas(String tableName, List<HashMap<String, Object>> data) {
        try {
            for (int i = 0; i < data.size(); i++) {
                JdbcUtils.insertToTable(druiddatasource, tableName, data.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static HashMap<String, String> pingDb() {
//        Config config = ConfigUtils.getDBConfig();
//        return pingDb(config.getUrl(), config.getUsername(), config.getPassword());
//    }

    public static HashMap<String, String> pingDb(String url, String username, String password) {
        HashMap<String, String> map = new HashMap<>();
        try {
            DruidDataSource druiddatasource = new DruidDataSource();
            druiddatasource.setUrl(url);
            druiddatasource.setUsername(username);
            druiddatasource.setPassword(password);
//            druiddatasource.setLoginTimeout(3);
            // 失败后重连的次数
            druiddatasource.setConnectionErrorRetryAttempts(1);
// 请求失败之后中断
            druiddatasource.setBreakAfterAcquireFailure(true);
            druiddatasource.setTimeBetweenEvictionRunsMillis(3000);
            druiddatasource.setFailFast(true);
            DbUtil.use(druiddatasource).query("select 1");
            map.put("result", "true");
            map.put("message", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "false");
            map.put("message", e.getMessage());
        }
        return map;
    }

}
