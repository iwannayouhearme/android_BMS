package com.fhh.components.goods.model;

/**
 * @author biubiubiu小浩
 * @description 商品类别模型
 * @date 2018/10/30 11:32
 **/
public class GoodsTypeModel {
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
}
