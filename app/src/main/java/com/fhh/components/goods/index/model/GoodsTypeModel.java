package com.fhh.components.goods.index.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @author biubiubiu小浩
 * @description 商品类别模型
 * @date 2018/10/30 11:32
 **/
public class GoodsTypeModel implements MultiItemEntity, Serializable {
    /**
     * 商品类别名称
     */
    private String goodsTypeName;
    /**
     * 商品类别id
     */
    private String id;
    /**
     * 创建时间
     */
    private String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

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
    public int getItemType() {
        return 1;
    }
}
