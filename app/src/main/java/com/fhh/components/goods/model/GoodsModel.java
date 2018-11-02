package com.fhh.components.goods.model;

import com.fhh.common.ISingleChoiceModel;
import com.fhh.utils.NullUtils;

import java.io.Serializable;

/**
 * @author biubiubiu小浩
 * @description 商品模型
 * @date 2018/10/30 12:49
 **/
public class GoodsModel {
    /**
     * 商品id
     */
    private String id;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品价格
     */
    private String goodsPrice;

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

}
