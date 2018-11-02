package com.fhh.api;

/**
 * @description api常量类
 * @author  biubiubiu小浩
 * @date 2018/10/19 21:25
 **/
public class Constant {

    public class Url{
        /**
         * 本地地址
         */
//        public static final String BASE = "http://192.168.0.102:9090/bms/";
//        public static final String BASE = "http://192.168.2.80:9090/bms/";
        /**
         * 云服务器地址
         */
        public static final String BASE = "http://139.199.160.101:9090/bms/";

        /**
         * 登录接口
         */
        public static final String LOGIN = "user/login";
        /**
         * 获取首页数据
         */
        public static final String GETINDEX = "bill/getIndexPage";
        /**
         * 获取指定用户的账单
         */
        public static final String GETBILLBYUSER = "bill/getBillByUser";
        /**
         * 根据账单id获取账单详情
         */
        public static final String GETBILLBYBILLID = "bill/getBillById";
        /**
         * 结账
         */
        public static final String PAYFORBILL = "bill/payForBill";
        /**
         * 获取所有用户
         */
        public static final String GETALLUSER = "user/getUserList";
        /**
         * 获取所有商品类型
         */
        public static final String GETALLGOODSTYPE = "goodsType/getGoodsTypeList";
        /**
         * 获取所有商品
         */
        public static final String GETALLGOODSBYGOODSTYPEID = "goods/getGoodsListByGoodsTypeId";
        /**
         * 添加账单
         */
        public static final String ADDBILL = "bill/addBillByAdmin";
    }
}
