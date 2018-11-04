package com.fhh.components.goodsdetail.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseActivity;
import com.fhh.components.goods.model.GoodsModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * @author biubiubiu小浩
 * @description 商品详情
 * @date 2018/11/2 13:51
 **/
public class GoodsDetailActivity extends BaseActivity {

    private Bundle bundle;
    private String goodsId;
    private GoodsModel goodsModel;
    private TextView goods_name, goods_name_full_pinyin, goods_price, goods_type, create_time;
    private Button update_goods, del_goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        init();
        findView();
        fullData();
    }


    private void init() {
        bundle = getIntent().getExtras();
        goodsId = bundle.getString("goodsId");
    }

    private void findView() {
        initTopView();
        setTopTitle("商品详情");
        goods_name = findViewById(R.id.goods_name);
        goods_name_full_pinyin = findViewById(R.id.goods_name_full_pinyin);
        goods_price = findViewById(R.id.goods_price);
        goods_type = findViewById(R.id.goods_type);
        create_time = findViewById(R.id.create_time);
        update_goods = findViewById(R.id.update_goods);
        del_goods = findViewById(R.id.del_goods);
    }

    private void fullData() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETGOODSDETAILBYID)
                .tag(this)
                .params("goodsId", goodsId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            goodsModel = JSONObject.parseObject(jsonObject.getString("data"), GoodsModel.class);
                            fullData(goodsModel);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fullData(GoodsModel goodsModel) {
        goods_name.setText(goodsModel.getGoodsName());
        goods_name_full_pinyin.setText(goodsModel.getFullPinyin());
        goods_price.setText(goodsModel.getGoodsPrice());
        goods_type.setText(goodsModel.getGoodsTypeName());
        create_time.setText(goodsModel.getCreateTime());
    }
}
