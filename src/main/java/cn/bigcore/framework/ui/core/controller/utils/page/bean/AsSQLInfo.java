package cn.bigcore.framework.ui.core.controller.utils.page.bean;

public class AsSQLInfo extends ParamInfo {
    public AsSQLInfo(String key_en, boolean first, String comparisonFormula) {
        super(key_en, null, "___", false, false, first ? "" : ",", "as", comparisonFormula);
    }
}
