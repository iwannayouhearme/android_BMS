package com.fhh.components.bill.addbill.model;

import com.fhh.common.ISingleChoiceModel;
import com.fhh.utils.NullUtils;

import java.io.Serializable;

/**
 * @author biubiubiu小浩
 * @description 借款类型
 * @date 2018/10/30 15:36
 **/
public class BorrowerTypeModel implements Serializable, ISingleChoiceModel {
    private String id;
    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getItemTitle() {
        return NullUtils.filterEmpty(typeName);
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
