package com.fhh.components.goodsdetail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.fhh.MainActivity;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseActivity;
import com.fhh.components.goods.model.GoodsModel;
import com.fhh.components.updategoods.UpdateGoodsActivity;
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
        showLeftView(true);
        goods_name = findViewById(R.id.goods_name);
        goods_name_full_pinyin = findViewById(R.id.goods_name_full_pinyin);
        goods_price = findViewById(R.id.goods_price);
        goods_type = findViewById(R.id.goods_type);
        create_time = findViewById(R.id.create_time);
        update_goods = findViewById(R.id.update_goods);
        del_goods = findViewById(R.id.del_goods);
        update_goods.setOnClickListener(this);
        del_goods.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_iv_left:
                backbtn(v);
                break;
            case R.id.update_goods:
                Intent intent = new Intent(getApplicationContext(), UpdateGoodsActivity.class);
                intent.putExtra("goodsId", goodsId);
                startActivity(intent);
                break;
            case R.id.del_goods:
                delGoods();
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    private void delGoods() {
        OkGo.<String>post(Constant.Url.BASE + Constant.Url.DELGOODS)
                .tag(this)
                .isMultipart(true)
                .retryCount(3)
                .params("goodsIds", goodsId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
