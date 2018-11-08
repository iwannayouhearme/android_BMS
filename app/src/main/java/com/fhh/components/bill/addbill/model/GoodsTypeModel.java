package com.fhh.components.bill.addbill.model;

import com.fhh.common.ISingleChoiceModel;
import com.fhh.utils.NullUtils;

import java.io.Serializable;

/**
 * @author biubiubiu小浩
 * @description 商品类别模型
 * @date 2018/10/30 11:32
 **/
public class GoodsTypeModel implements Serializable, ISingleChoiceModel {

    /**
     * 商品类别名称
     */
    private String goodsTypeName;
    /**
     * 商品类别id
     */
    private String id;

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getItemTitle() {
        return NullUtils.filterEmpty(goodsTypeName);
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
