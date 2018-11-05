package com.fhh.components.updatebill.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhh.MainActivity;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseActivity;
import com.fhh.common.ISingleChoiceModel;
import com.fhh.common.SingleChoiceDialog;
import com.fhh.components.addbill.model.BorrowerTypeModel;
import com.fhh.components.addbill.model.GoodsModel;
import com.fhh.components.addbill.model.GoodsTypeModel;
import com.fhh.components.addbill.model.UserModel;
import com.fhh.components.updatebill.model.UpdateBillModel;
import com.fhh.utils.NullUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author biubiubiu小浩
 * @description 更新账单
 * @date 2018/11/5 13:15
 **/
public class UpdateBillActivity extends BaseActivity {
    private TextView borrower_man, borrower_type, cache_loan_amount, goods_type, goods_name, loan_amount;

    private Button activity_update_bill;
    private LinearLayout goods_type_layout, goods_name_layout, loan_amount_layout, cache_loan_amount_layout, borrower_man_layout, borrower_type_layout;
    private Activity updateBillActivity;
    private SingleChoiceDialog borrowerManChoiceDialog;
    private SingleChoiceDialog borrowerTypeChoiceDialog;
    private SingleChoiceDialog goodsTypeChoiceDialog;
    private SingleChoiceDialog goodsNameChoiceDialog;
    private UpdateBillModel updateBillModel;
    private List<UserModel> userListModel;
    private List<GoodsTypeModel> goodsTypeModels;
    private List<GoodsModel> goodsModels;
    private List<BorrowerTypeModel> borrowerTypeModels = new ArrayList<>();
    private Bundle bundle;
    private String billId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateBillActivity = this;
        setContentView(R.layout.activity_update_bill);
        findView();
        init();
    }

    private void findView() {
        initTopView();
        setTopTitle("添加账单");
        showLeftView(true);
        //获取账单id
        bundle = getIntent().getExtras();
        billId = bundle.getString("billId");
        borrower_man = findViewById(R.id.borrower_man);
        borrower_type = findViewById(R.id.borrower_type);
        cache_loan_amount = findViewById(R.id.cache_loan_amount);
        goods_type = findViewById(R.id.goods_type);
        goods_name = findViewById(R.id.goods_name);
        loan_amount = findViewById(R.id.loan_amount);
        goods_type_layout = findViewById(R.id.goods_type_layout);
        goods_name_layout = findViewById(R.id.goods_name_layout);
        loan_amount_layout = findViewById(R.id.loan_amount_layout);
        cache_loan_amount_layout = findViewById(R.id.cache_loan_amount_layout);
        borrower_man_layout = findViewById(R.id.borrower_man_layout);
        borrower_type_layout = findViewById(R.id.borrower_type_layout);
        activity_update_bill = findViewById(R.id.activity_update_bill);
        activity_update_bill.setOnClickListener(this);
        goods_type_layout.setOnClickListener(this);
        goods_name_layout.setOnClickListener(this);
        loan_amount_layout.setOnClickListener(this);
        cache_loan_amount_layout.setOnClickListener(this);
        borrower_man_layout.setOnClickListener(this);
        borrower_type_layout.setOnClickListener(this);
    }

    private void init() {
        updateBillModel = new UpdateBillModel();
        BorrowerTypeModel borrowerTypeModel = new BorrowerTypeModel();
        borrowerTypeModel.setId("0");
        borrowerTypeModel.setTypeName("商品");
        borrowerTypeModels.add(borrowerTypeModel);
        borrowerTypeModel = new BorrowerTypeModel();
        borrowerTypeModel.setId("1");
        borrowerTypeModel.setTypeName("现金");
        borrowerTypeModels.add(borrowerTypeModel);
        updateBillModel.setBtype("0");
        updateBillModel.setBorrowerTypeName("商品");
        //获取账单的回显数据
        getBillInfo();
    }

    /**
     * 获取账单数据回显
     *
     * @author biubiubiu小浩
     * @date 2018/11/5 13:31
     **/
    private void getBillInfo() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETBILLBYBILLID)
                .tag(this)
                .params("billId", billId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            updateBillModel = JSONObject.parseObject(jsonObject.getString("data"), UpdateBillModel.class);
                            //判断账单为现金还是商品，展示不同的页面
                            //商品
                            if ("0".equals(updateBillModel.getBtype())) {
                                //显示商品类型，商品，商品金额
                                goods_type_layout.setVisibility(View.VISIBLE);
                                goods_name_layout.setVisibility(View.VISIBLE);
                                loan_amount_layout.setVisibility(View.VISIBLE);
                                cache_loan_amount_layout.setVisibility(View.GONE);
                                //设置回显内容
                                borrower_man.setText(updateBillModel.getBorrowerMan());
                                borrower_type.setText("商品");
                                goods_type.setText(updateBillModel.getGoodsTypeName());
                                goods_name.setText(updateBillModel.getGoodsName());
                                loan_amount.setText(updateBillModel.getLoanAmount());
                            } else {
                                //现金
                                //显示现金金额
                                borrower_man.setText(updateBillModel.getBorrowerMan());
                                borrower_type.setText("现金");
                                cache_loan_amount.setText(updateBillModel.getLoanAmount());
                                cache_loan_amount_layout.setVisibility(View.VISIBLE);
                                goods_type_layout.setVisibility(View.GONE);
                                goods_name_layout.setVisibility(View.GONE);
                                loan_amount_layout.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.borrower_type_layout:
                if (borrowerTypeChoiceDialog == null) {
                    showBorrowerType();
                } else {
                    borrowerTypeChoiceDialog.show();
                }
                break;
            case R.id.borrower_man_layout:
                if (borrowerManChoiceDialog == null) {
                    showBorrowerMan();
                } else {
                    borrowerManChoiceDialog.show();
                }
                break;
            case R.id.goods_name_layout:
                if (TextUtils.isEmpty(goods_type.getText())) {
                    Toast.makeText(getApplicationContext(), "请先选择商品类型！", Toast.LENGTH_SHORT).show();
                } else {
                    if (goodsNameChoiceDialog == null) {
                        showGoodsName();
                    } else {
                        goodsNameChoiceDialog.show();
                    }
                }
                break;
            case R.id.goods_type_layout:
                if (goodsTypeChoiceDialog == null) {
                    showGoodsType();
                } else {
                    goodsTypeChoiceDialog.show();
                }
                break;
            case R.id.activity_update_bill:
                updateBill();
                break;
            case R.id.top_iv_left:
                backbtn(v);
                break;
            default:
                break;
        }
    }

    private void updateBill() {
        //校验数据
        if (NullUtils.isNull(updateBillModel.getBorrowerManId())) {
            Toast.makeText(getApplicationContext(), "请选择借款人！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (NullUtils.isNull(updateBillModel.getBtype())) {
            Toast.makeText(getApplicationContext(), "请选择借款类型！", Toast.LENGTH_SHORT).show();
            return;
        }
        //现金
        if ("1".equals(updateBillModel.getBtype())) {
            if (NullUtils.isNull(cache_loan_amount.getText().toString())) {
                Toast.makeText(getApplicationContext(), "请输入借款金额！", Toast.LENGTH_SHORT).show();
                return;
            }
            updateBillModel.setLoanAmount(cache_loan_amount.getText().toString());
        } else {
            //商品
            if (NullUtils.isNull(updateBillModel.getGoodsTypeId())) {
                Toast.makeText(getApplicationContext(), "请选择商品类型！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (NullUtils.isNull(updateBillModel.getGoodsId())) {
                Toast.makeText(getApplicationContext(), "请选择商品！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (NullUtils.isNull(updateBillModel.getLoanAmount())) {
                Toast.makeText(getApplicationContext(), "请选择价格！", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        OkGo.<String>post(Constant.Url.BASE + Constant.Url.UPDATEBILL)
                .tag(this)
                .isMultipart(true)
//                .isSpliceUrl(true)
                .retryCount(3)
                .params("billId", billId)
                .params("borrowerManId", updateBillModel.getBorrowerManId())
                .params("btype", updateBillModel.getBtype())
                .params("goodsId", updateBillModel.getGoodsId())
                .params("loanAmount", updateBillModel.getLoanAmount())
                .params("borrowerMan", updateBillModel.getBorrowerMan())
                .params("goodsName", updateBillModel.getGoodsName())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "更新成功！", Toast.LENGTH_SHORT).show();
//                            BMSApplication.getInstance();
                            Intent intent = new Intent(UpdateBillActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void showBorrowerType() {
        if (borrowerTypeChoiceDialog == null) {
            borrowerTypeChoiceDialog = new SingleChoiceDialog(updateBillActivity, "请选择借款类型", NullUtils.filterEmpty(updateBillModel.getBtype()), borrowerTypeModels, new SingleChoiceDialog.ISingleChoiceDialog() {
                @Override
                public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                    //如果选择的为现金，将商品类型，商品，自动填充按钮隐藏，输入的金额框显示
                    if ("1".equals(singleChoiceModel.getItemKey())) {
                        goods_type_layout.setVisibility(View.GONE);
                        goods_name_layout.setVisibility(View.GONE);
                        loan_amount_layout.setVisibility(View.GONE);
                        cache_loan_amount_layout.setVisibility(View.VISIBLE);
                        //清空已选择的商品类型，商品，金额
                        goodsTypeChoiceDialog = null;
                        goodsNameChoiceDialog = null;
                        updateBillModel.setGoodsId("");
                        updateBillModel.setGoodsName("");
                        updateBillModel.setLoanAmount("");
                        updateBillModel.setGoodsTypeId("");
                        updateBillModel.setBorrowerTypeName("");
                        //填充借款类型
                        borrower_type.setText(singleChoiceModel.getItemTitle());
                        updateBillModel.setBorrowerTypeName(singleChoiceModel.getItemTitle());
                        updateBillModel.setBtype(singleChoiceModel.getItemKey());
                        goods_type.setText("");
                        goods_name.setText("");
                        loan_amount.setText("");
                    } else {
                        //如果选择的商品，将商品类型，商品，自动填充按钮显示，输入的金额框隐藏
                        goods_type_layout.setVisibility(View.VISIBLE);
                        goods_name_layout.setVisibility(View.VISIBLE);
                        loan_amount_layout.setVisibility(View.VISIBLE);
                        cache_loan_amount_layout.setVisibility(View.GONE);
                        borrower_type.setText(singleChoiceModel.getItemTitle());
                        updateBillModel.setBorrowerTypeName(singleChoiceModel.getItemTitle());
                        updateBillModel.setBtype(singleChoiceModel.getItemKey());
                        cache_loan_amount.setText("");
                    }
                }
            });
        }
    }

    private void showGoodsType() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETALLGOODSTYPE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            goodsTypeModels = JSONArray.parseArray(jsonObject.getString("data"), GoodsTypeModel.class);
                            if (goodsTypeModels.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "暂无商品类型，请先添加商品类型！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (goodsTypeChoiceDialog == null) {
                                goodsTypeChoiceDialog = new SingleChoiceDialog(updateBillActivity, "请选择商品类型", NullUtils.filterEmpty(updateBillModel.getGoodsTypeId()), goodsTypeModels, new SingleChoiceDialog.ISingleChoiceDialog() {
                                    @Override
                                    public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                                        //清空已经选择的商品内容和金额内容
                                        if (!NullUtils.filterEmpty(updateBillModel.getGoodsTypeId()).equals(NullUtils.filterEmpty(singleChoiceModel.getItemKey()))) {
                                            goods_name.setText("");
                                            loan_amount.setText("");
                                            updateBillModel.setGoodsName("");
                                            updateBillModel.setGoodsId("");
                                            updateBillModel.setLoanAmount("");
                                            goodsNameChoiceDialog = null;
                                        }
                                        goods_type.setText(singleChoiceModel.getItemTitle());
                                        updateBillModel.setGoodsTypeName(singleChoiceModel.getItemTitle());
                                        updateBillModel.setGoodsTypeId(singleChoiceModel.getItemKey());
                                    }

                                });
                                goodsTypeChoiceDialog.show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showGoodsName() {
        if (NullUtils.isNull(updateBillModel.getGoodsTypeName()) || NullUtils.isNull(updateBillModel.getGoodsTypeId())) {
            Toast.makeText(getApplicationContext(), "请先选择商品类型", Toast.LENGTH_SHORT).show();
        }
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETALLGOODSBYGOODSTYPEID)
                .tag(this)
                .params("goodsTypeId", updateBillModel.getGoodsTypeId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            goodsModels = JSONArray.parseArray(jsonObject.getString("data"), GoodsModel.class);
                            if (goodsModels.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "暂无商品，请先添加商品！", Toast.LENGTH_SHORT).show();
                                return;
                            }
//                            if (NullUtils.isNull(addBillModel.getGoodsName()) || NullUtils.isNull(addBillModel.getGoodsId())) {
//                                addBillModel.setGoodsId(goodsModels.get(0).getId());
//                                addBillModel.setGoodsName(goodsModels.get(0).getGoodsName());
//                            }
                            if (goodsNameChoiceDialog == null) {
                                goodsNameChoiceDialog = new SingleChoiceDialog(updateBillActivity, "请选择商品", NullUtils.filterEmpty(updateBillModel.getGoodsId()), goodsModels, new SingleChoiceDialog.ISingleChoiceDialog() {
                                    @Override
                                    public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                                        goods_name.setText(singleChoiceModel.getItemTitle());
                                        loan_amount.setText(goodsModels.get(position).getGoodsPrice());
                                        updateBillModel.setGoodsName(singleChoiceModel.getItemTitle());
                                        updateBillModel.setGoodsId(singleChoiceModel.getItemKey());
                                        updateBillModel.setLoanAmount(goodsModels.get(position).getGoodsPrice());
                                    }
                                });
                                goodsNameChoiceDialog.show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showBorrowerMan() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETALLUSER)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            userListModel = JSONArray.parseArray(jsonObject.getString("data"), UserModel.class);
                            if (userListModel.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "暂无用户，请先添加用户！", Toast.LENGTH_SHORT).show();
                                return;
                            }
//                            if (NullUtils.isNull(addBillModel.getBorrowerManId()) || NullUtils.isNull(addBillModel.getBorrowerManName())) {
//                                addBillModel.setBorrowerManId(userListModel.get(0).getId());
//                                addBillModel.setBorrowerManName(userListModel.get(0).getRealName());
//                            }
                            if (borrowerManChoiceDialog == null) {
                                borrowerManChoiceDialog = new SingleChoiceDialog(updateBillActivity, "请选择借款人", NullUtils.filterEmpty(updateBillModel.getBorrowerManId()), userListModel, new SingleChoiceDialog.ISingleChoiceDialog() {
                                    @Override
                                    public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                                        borrower_man.setText(singleChoiceModel.getItemTitle());
                                        updateBillModel.setBorrowerMan(singleChoiceModel.getItemTitle());
                                        updateBillModel.setBorrowerManId(singleChoiceModel.getItemKey());
                                    }
                                });
                                borrowerManChoiceDialog.show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
