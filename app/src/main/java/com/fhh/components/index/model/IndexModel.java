package com.fhh.components.index.model;


/**
 * @author biubiubiu小浩
 * @description 首页数据模型
 * @date 2018/10/22 19:09
 **/
public class IndexModel {
    private String totalUnpayMoney;
    private String totalMoney;
    private String userBillList;
    private String totalPaymoney;

    public String getTotalUnpayMoney() {
        return totalUnpayMoney;
    }

    public void setTotalUnpayMoney(String totalUnpayMoney) {
        this.totalUnpayMoney = totalUnpayMoney;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUserBillList() {
        return userBillList;
    }

    public void setUserBillList(String userBillList) {
        this.userBillList = userBillList;
    }

    public String getTotalPaymoney() {
        return totalPaymoney;
    }

    public void setTotalPaymoney(String totalPaymoney) {
        this.totalPaymoney = totalPaymoney;
    }
}
