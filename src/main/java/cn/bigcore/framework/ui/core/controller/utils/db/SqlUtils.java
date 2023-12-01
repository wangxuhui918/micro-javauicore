package cn.bigcore.framework.ui.core.controller.utils.db;

import cn.bigcore.framework.ui.core.controller.utils.page.bean.ParamInfo;

import java.util.List;

/**
 * @author 汪旭辉
 * @date 2022/11/4
 * @readme
 */
public class SqlUtils {

    public static String conditionalSQL(List<ParamInfo> paramInfos) {
        if (paramInfos != null && paramInfos.size() > 0) {
            StringBuffer sql0 = new StringBuffer("");
            int count = 0;
            for (int i = 0; i < paramInfos.size(); i++) {
                ParamInfo paramInfo = paramInfos.get(i);
                sql0.append(paramInfo.getFragmentsql());
            }
            return sql0.toString();
        }
        return "";
    }


}
