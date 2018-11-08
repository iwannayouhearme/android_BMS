package com.fhh.components.bill.updatebill.model;

/**
 * @author biubiubiu小浩
 * @description 添加账单模型
 * @date 2018/10/29 21:22
 **/
public class UpdateBillModel {
    /**
     * 借款人id
     */
    private String borrowerManId;
    /**
     * 借款人名
     */
    private String borrowerMan;
    /**
     * 借款人外号
     */
    private String borrowerNikeName;
    /**
     * 账单状态
     */
    private String bstatus;
    /**
     * 借款类别编号
     */
    private String btype;
    /**
     * 借款类别名称
     */
    private String borrowerTypeName;
    /**
     * 账单创建人id
     */
    private String createManId;
    /**
     * 账单创建人名
     */
    private String createManName;
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
     * 账单id
     */
    private String id;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 金额
     */
    private String loanAmount;
    /**
     * 还款日期
     */
    private String payDate;
    /**
     * 还款操作人名
     */
    private String payOpeMan;
    /**
     * 还款操作人id
     */
    private String payOpeManId;

    public String getBorrowerManId() {
        return borrowerManId;
    }

    public void setBorrowerManId(String borrowerManId) {
        this.borrowerManId = borrowerManId;
    }

    public String getBorrowerMan() {
        return borrowerMan;
    }

    public void setBorrowerMan(String borrowerMan) {
        this.borrowerMan = borrowerMan;
    }

    public String getBorrowerNikeName() {
        return borrowerNikeName;
    }

    public void setBorrowerNikeName(String borrowerNikeName) {
        this.borrowerNikeName = borrowerNikeName;
    }

    public String getBstatus() {
        return bstatus;
    }

    public void setBstatus(String bstatus) {
        this.bstatus = bstatus;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getBorrowerTypeName() {
        return borrowerTypeName;
    }

    public void setBorrowerTypeName(String borrowerTypeName) {
        this.borrowerTypeName = borrowerTypeName;
    }

    public String getCreateManId() {
        return createManId;
    }

    public void setCreateManId(String createManId) {
        this.createManId = createManId;
    }

    public String getCreateManName() {
        return createManName;
    }

    public void setCreateManName(String createManName) {
        this.createManName = createManName;
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

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayOpeMan() {
        return payOpeMan;
    }

    public void setPayOpeMan(String payOpeMan) {
        this.payOpeMan = payOpeMan;
    }

    public String getPayOpeManId() {
        return payOpeManId;
    }

    public void setPayOpeManId(String payOpeManId) {
        this.payOpeManId = payOpeManId;
    }
}
