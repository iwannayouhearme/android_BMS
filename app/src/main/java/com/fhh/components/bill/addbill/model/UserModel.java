package com.fhh.components.bill.addbill.model;

import com.fhh.common.ISingleChoiceModel;
import com.fhh.utils.NullUtils;

import java.io.Serializable;

/**
 * @author biubiubiu小浩
 * @description 用户模型
 * @date 2018/10/29 21:07
 **/
public class UserModel implements Serializable,ISingleChoiceModel {
    /**
     * 用户名
     */
    private String realName;
    /**
     * 用户id
     */
    private String id;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getItemTitle() {
        return NullUtils.filterEmpty(realName);
    }

    @Override
    public String getItemKey() {
        return NullUtils.filterEmpty(id);
    }

    @Override
    public String getItemValue() {
        return null;
    }
}
