package com.fhh.components.user.model;

/**
 * @author biubiubiu小浩
 * @description 用户模型
 * @date 2018/11/8 20:21
 **/
public class UserModel {
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 外号
     */
    private String nickName;
    /**
     * 用户id
     */
    private String id;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
