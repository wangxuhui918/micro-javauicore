package cn.bigcore.framework.ui.core.controller.utils.page.bean;

import cn.hutool.core.util.StrUtil;

public class ParamInfo {
    //列英文
    private String key_en;
    //列中文
    private String key_zh;
    //列值
    private Object value;
    //是否空
    private boolean isNull;
    //是否空串
    private boolean isBlank;
    //数据可链接
    private boolean valueLink;
    //空是否比对数据
    private boolean nullToLink = false;
    //空串否比对数据
    private boolean blankToLink = false;
    //连接符 string,and,or
    private String connector;
    //比对字符串 =,like 等
    private String comparisonSymbol;
    //比对公式 '{}' '%{}%'
    private String comparisonFormula;
    //获取片段sql
    private String fragmentsql;

    public ParamInfo() {
    }

    public ParamInfo(String key_en, String key_zh, Object value, boolean nullToLink, boolean blankToLink, String connector, String comparisonSymbol, String comparisonFormula) {
        this.key_en = key_en;
        this.key_zh = key_zh;
        this.value = value;
        this.nullToLink = nullToLink;
        this.blankToLink = blankToLink;
        this.connector = connector;
        this.comparisonSymbol = comparisonSymbol;
        this.comparisonFormula = comparisonFormula;
    }

    public String getFragmentsql() {
        StringBuffer sql0 = new StringBuffer("");
        boolean link = false;
        if (isValueLink() || isNullToLink() || isBlankToLink()) {
            link = true;
        }
        if (link) {
            //处理连接符
            sql0.append(" " + StrUtil.nullToDefault(getConnector(), "") + " ");

            if (isValueLink()) {
                //处理列表名
                sql0.append(" " + getKey_en() + " ");
                if (value instanceof String) {
                    //处理列表名
                    sql0.append(" " + StrUtil.nullToDefault(getComparisonSymbol(), "") + " ");
                    //比对公式
                    sql0.append(" " + StrUtil.format(StrUtil.nullToDefault(getComparisonFormula(), ""), value.toString()) + " ");
                }
            } else if (isBlankToLink() && isBlank()) {
                sql0.append(" " + getKey_en() + " ");
                if (value instanceof String) {
                    //处理列表名
                    sql0.append(" " + getComparisonSymbol() + " ");
                    //比对公式
                    sql0.append(" " + StrUtil.format(StrUtil.nullToDefault(getComparisonFormula(), ""), value.toString()) + " ");
                }

            } else if (isNullToLink() && isNull()) {
                sql0.append(" " + getKey_en() + " ");
                sql0.append(" is null ");
            }
        }
        return sql0.toString();
    }

    public void setFragmentsql(String fragmentsql) {
        this.fragmentsql = fragmentsql;
    }

    public boolean isValueLink() {
        return value != null && StrUtil.isNotBlank(value.toString()) ? true : false;
    }


    public boolean isNullToLink() {
        return nullToLink;
    }

    public void setNullToLink(boolean nullToLink) {
        this.nullToLink = nullToLink;
    }

    public boolean isBlankToLink() {
        return blankToLink;
    }

    public void setBlankToLink(boolean blankToLink) {
        this.blankToLink = blankToLink;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getKey_en() {
        return key_en;
    }

    public void setKey_en(String key_en) {
        if (StrUtil.isBlank(key_en)) {
            throw new RuntimeException("key_en不能为空");
        }
        this.key_en = key_en;
    }

    public String getKey_zh() {
        return StrUtil.nullToDefault(key_zh, key_en);
    }

    public void setKey_zh(String key_zh) {
        this.key_zh = key_zh;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isNull() {
        return value == null ? true : false;
    }

    public boolean isBlank() {
        return value == null || StrUtil.isBlank(value.toString()) ? true : false;
    }

    public String getComparisonSymbol() {
        return comparisonSymbol;
    }

    public void setComparisonSymbol(String comparisonSymbol) {
        this.comparisonSymbol = comparisonSymbol;
    }

    public String getComparisonFormula() {
        return comparisonFormula;
    }

    public void setComparisonFormula(String comparisonFormula) {
        this.comparisonFormula = comparisonFormula;
    }
}
