package com.fhh.components.user.adduser;

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
import com.fhh.utils.NullUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * @author biubiubiu小浩
 * @description 添加用户
 * @date 2018/11/8 20:51
 **/
public class AddUserActivity extends BaseActivity {

    private EditText real_name, nike_name;
    private Button add_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        findView();
    }

    private void findView() {
        initTopView();
        setTopTitle("新增用户");
        real_name = findViewById(R.id.real_name);
        nike_name = findViewById(R.id.nike_name);
        add_user = findViewById(R.id.add_user);
        add_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_user:
                addUser();
                break;
            default:
                break;
        }
    }

    private void addUser() {
        //数据校验
        if (NullUtils.isNull(real_name.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入用户真实姓名！", Toast.LENGTH_SHORT);
        }
        OkGo.<String>post(Constant.Url.BASE + Constant.Url.ADDUSER)
                .isMultipart(true)
                .retryCount(3)
                .params("realName", real_name.getText().toString())
                .params("nickName", nike_name.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT);
                        }
                    }
                });
    }
}
