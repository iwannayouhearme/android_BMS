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
    /**
     * 商品全拼
     */
    private String fullPinyin;
    /**
     * 商品类型id
     */
    private String goodsTypeId;
    /**
     * 商品类型名称
     */
    private String goodsTypeName;
    /**
     * 创建时间
     */
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFullPinyin() {
        return fullPinyin;
    }

    public void setFullPinyin(String fullPinyin) {
        this.fullPinyin = fullPinyin;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

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
