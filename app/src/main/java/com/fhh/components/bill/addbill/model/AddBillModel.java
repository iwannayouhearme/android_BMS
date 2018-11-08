package com.fhh.components.bill.addbill.model;

/**
 * @description 添加账单模型
 * @author  biubiubiu小浩
 * @date 2018/10/29 21:22
 **/
public class AddBillModel {
    /**
     * 借款人id
     */
    private String borrowerManId;
    /**
     * 借款人名
     */
    private String borrowerManName;
    /**
     * 借款类别编号
     */
    private String borrowerTypeId;
    /**
     * 借款类别名称
     */
    private String borrowerTypeName;
    /**
     * 商品类别id
     */
    private String goodsTypeId;
    /**
     * 商品类别名称
     */
    private String goodsTypeName;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 金额
     */
    private String loanAmount;

    public String getBorrowerManId() {
        return borrowerManId;
    }

    public void setBorrowerManId(String borrowerManId) {
        this.borrowerManId = borrowerManId;
    }

    public String getBorrowerManName() {
        return borrowerManName;
    }

    public void setBorrowerManName(String borrowerManName) {
        this.borrowerManName = borrowerManName;
    }

    public String getBorrowerTypeId() {
        return borrowerTypeId;
    }

    public void setBorrowerTypeId(String borrowerTypeId) {
        this.borrowerTypeId = borrowerTypeId;
    }

    public String getBorrowerTypeName() {
        return borrowerTypeName;
    }

    public void setBorrowerTypeName(String borrowerTypeName) {
        this.borrowerTypeName = borrowerTypeName;
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

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }
}
