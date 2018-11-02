package com.fhh.components.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

import java.util.List;

/**
 * @author biubiubiu小浩
 * @description 登录
 * @date 2018/10/19 16:29
 **/
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button btn;
    private EditText etUserName, etPassword;
    private List<String> mLoginModel;
    /**
     * 用户名和密码
     */
    private String userName = "", password = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findiView();

    }

    private void findiView() {
        etUserName = (EditText) findViewById(R.id.userName);
        etPassword = (EditText) findViewById(R.id.password);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Login();
                break;
            default:
                break;
        }
    }

    private void Login() {
        etUserName.setText("1000001");
        etPassword.setText("bms123456");
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
        if (userName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        OkGo.<String>post(Constant.Url.BASE + Constant.Url.LOGIN)
                .tag(this)
                .isMultipart(true)
//                .isSpliceUrl(true)
                .retryCount(3)
                .params("loginNumber", userName)
                .params("password", password)
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.d("LoginActivity", "response:" + response);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();
//                            BMSApplication.getInstance();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}
