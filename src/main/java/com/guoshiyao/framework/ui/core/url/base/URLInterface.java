package com.guoshiyao.framework.ui.core.url.base;

import com.guoshiyao.framework.ui.core.controller.base.BaseController;

/**
 * 插件接口
 */
public interface URLInterface {
    /**
     * 获取插件界面
     *
     * @return
     */
    public java.net.URL getFXML();

    /**
     * 获取插件名
     *
     * @return
     */
    public String getMark();

    /**
     * 获取控制层
     *
     * @return
     */
    public Class<BaseController> getController();
}
