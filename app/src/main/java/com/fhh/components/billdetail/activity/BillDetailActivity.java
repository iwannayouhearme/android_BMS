package com.fhh.components.billdetail.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseActivity;
import com.fhh.base.DialogView;
import com.fhh.components.index.model.UserBillListModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class BillDetailActivity extends BaseActivity implements View.OnClickListener {

    private Bundle bundle;
    private TextView borrower_man, borrower_type, loan_amount, goods_name, create_man, pay_date, pay_ope_man, pay_status, borrower_date;
    private String billId;
    private String[] borrowerType = {"商品", "现金"};
    private String[] payStatus = {"未还款", "已还款"};
    private Button update_bill, pay_for_bill;
    private UserBillListModel userBillListModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        init();
        findView();
        fullData();
    }


    private void init() {
        bundle = getIntent().getExtras();
        billId = bundle.getString("billId");
    }

    private void fullData() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETBILLBYBILLID)
                .tag(this)
                .params("billId", billId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            userBillListModel = JSONObject.parseObject(jsonObject.getString("data"), UserBillListModel.class);
                            BillDetailActivity.this.fullData(userBillListModel);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fullData(UserBillListModel userBillListModel) {
        borrower_man.setText(userBillListModel.getBorrowerMan());
        borrower_type.setText(borrowerType[Integer.parseInt(userBillListModel.getBtype())]);
        loan_amount.setText(userBillListModel.getLoanAmount());
        borrower_date.setText(userBillListModel.getCreate_time());
        if ("1".equals(userBillListModel.getBtype())) {
            goods_name.setText("无");
        } else {
            goods_name.setText(userBillListModel.getGoodsName());
        }
        if ("0".equals(userBillListModel.getBstatus())) {
            pay_date.setText("无");
            pay_ope_man.setText("无");
            //已还款状态，隐藏按钮
            update_bill.setVisibility(View.VISIBLE);
            pay_for_bill.setVisibility(View.VISIBLE);
        } else {
            pay_date.setText(userBillListModel.getPayDate());
            pay_ope_man.setText(userBillListModel.getPayOpeMan());
        }
        create_man.setText(userBillListModel.getCreateManName());
        pay_status.setText(payStatus[Integer.parseInt(userBillListModel.getBstatus())]);
    }

    private void findView() {
        initTopView();
        setTopTitle("账单详情");
        borrower_man = findViewById(R.id.borrower_man);
        borrower_type = findViewById(R.id.borrower_type);
        loan_amount = findViewById(R.id.loan_amount);
        goods_name = findViewById(R.id.goods_name);
        create_man = findViewById(R.id.create_man);
        pay_date = findViewById(R.id.pay_date);
        pay_ope_man = findViewById(R.id.pay_ope_man);
        pay_status = findViewById(R.id.pay_status);
        borrower_date = findViewById(R.id.borrower_date);
        update_bill = findViewById(R.id.update_bill);
        pay_for_bill = findViewById(R.id.pay_for_bill);
        pay_for_bill.setOnClickListener(this);
        update_bill.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_for_bill:
                payForBill();
                break;
            case R.id.update_bill:
                updateBill();
                break;
            default:
                break;
        }
    }

    private void updateBill() {

    }

    private void payForBill() {
        DialogView.showSureDialog(BillDetailActivity.this, "提示", "确认是否结账", "确认", "返回"
                , new DialogView.SureDialogViewListener() {
                    @Override
                    public void OnClickListener(View view, final AlertDialog dialog) {
                        OkGo.<String>post(Constant.Url.BASE + Constant.Url.PAYFORBILL)
                                .tag(this)
                                .isMultipart(true)
                                .retryCount(3)
                                .params("billIds", billId)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                                        if ("true".equals(jsonObject.getString("success"))) {
                                            Toast.makeText(getApplicationContext(), "操作成功！", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            fullData();
                                            update_bill.setVisibility(View.GONE);
                                            pay_for_bill.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    }
                });
    }
}
