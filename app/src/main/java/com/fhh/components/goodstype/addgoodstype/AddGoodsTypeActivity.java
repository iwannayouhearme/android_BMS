package com.fhh.components.goodstype.addgoodstype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.fhh.MainActivity;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * @author biubiubiu小浩
 * @description 新增商品类型
 * @date 2018/11/7 20:43
 **/
public class AddGoodsTypeActivity extends BaseActivity {

    private EditText goods_type_name;
    private Button add_goods_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods_type);
        findView();
    }

    private void findView() {
        goods_type_name = findViewById(R.id.goods_type_name);
        add_goods_type = findViewById(R.id.add_goods_type);
        add_goods_type.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_goods_type:
                addGoodsType();
                break;
            default:
                break;
        }
    }

    private void addGoodsType() {
        OkGo.<String>post(Constant.Url.BASE + Constant.Url.ADDGOODSTYPE)
                .tag(this)
                .params("goodsTypeName", goods_type_name.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
