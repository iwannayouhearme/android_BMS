package com.fhh.components.updategoodstype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhh.MainActivity;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseActivity;
import com.fhh.components.goods.model.GoodsTypeModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * @author biubiubiu小浩
 * @description 跟心商品
 * @date 2018/11/7 19:44
 **/
public class UpdateGoodsTypeActivity extends BaseActivity {

    private TextView create_time;
    private EditText goods_type_name;
    private Bundle bundle;
    private String goodsTypeId;
    private GoodsTypeModel goodsTypeModel;
    private Button update_goods_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goods_type);
        findView();
        init();
    }

    private void findView() {
        initTopView();
        setTopTitle("修改商品类型");
        showLeftView(true);
        goods_type_name = findViewById(R.id.goods_type_name);
        create_time = findViewById(R.id.create_time);
        update_goods_type = findViewById(R.id.update_goods_type);
        bundle = getIntent().getExtras();
        goodsTypeId = bundle.getString("goodsTypeId");
        update_goods_type.setOnClickListener(this);
    }

    private void init() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETGOODSTYPEBYID)
                .tag(this)
                .params("goodsTypeId", goodsTypeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            goodsTypeModel = jsonObject.parseObject(jsonObject.getString("data"), GoodsTypeModel.class);
                            goods_type_name.setText(goodsTypeModel.getGoodsTypeName());
                            create_time.setText(goodsTypeModel.getCreate_time());
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_goods_type:
                updateGoodsType();
                break;
            default:
                break;
        }
    }

    private void updateGoodsType() {
        OkGo.<String>post(Constant.Url.BASE + Constant.Url.UPDATEGOODSTYPE)
                .tag(this)
                .isMultipart(true)
                .retryCount(3)
                .params("goodsTypeId", goodsTypeId)
                .params("goodsTypeName", goods_type_name.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "更新成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
