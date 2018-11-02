package com.fhh.components.index.model;

public class HomeItem {
    private boolean isExpand = false;
    private String userNickName;
    private String totalUserMoney;
    private String userRealName;
    private String totalUserUnpayMoney;
    private String userId;
    private String totalUserPaymoney;
    private String countNumber;
    private String detailList;

    public String getDetailList() {
        return detailList;
    }

    public void setDetailList(String detailList) {
        this.detailList = detailList;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public String getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(String countNumber) {
        this.countNumber = countNumber;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getTotalUserMoney() {
        return totalUserMoney;
    }

    public void setTotalUserMoney(String totalUserMoney) {
        this.totalUserMoney = totalUserMoney;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getTotalUserUnpayMoney() {
        return totalUserUnpayMoney;
    }

    public void setTotalUserUnpayMoney(String totalUserUnpayMoney) {
        this.totalUserUnpayMoney = totalUserUnpayMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalUserPaymoney() {
        return totalUserPaymoney;
    }

    public void setTotalUserPaymoney(String totalUserPaymoney) {
        this.totalUserPaymoney = totalUserPaymoney;
    }
}