package com.fhh.components.addbill.activity;

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
import com.fhh.components.addbill.model.AddBillModel;
import com.fhh.components.addbill.model.BorrowerTypeModel;
import com.fhh.components.addbill.model.GoodsModel;
import com.fhh.components.addbill.model.GoodsTypeModel;
import com.fhh.components.addbill.model.UserModel;
import com.fhh.utils.NullUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class AddBillActivity extends BaseActivity implements View.OnClickListener {

    private TextView borrower_man, borrower_type, cache_loan_amount, goods_type, goods_name, loan_amount;

    private Button activity_add_bill;
    private LinearLayout goods_type_layout, goods_name_layout, loan_amount_layout, cache_loan_amount_layout, borrower_man_layout, borrower_type_layout;
    private Activity addBillActivity;
    private SingleChoiceDialog borrowerManChoiceDialog;
    private SingleChoiceDialog borrowerTypeChoiceDialog;
    private SingleChoiceDialog goodsTypeChoiceDialog;
    private SingleChoiceDialog goodsNameChoiceDialog;
    private AddBillModel addBillModel;
    private List<UserModel> userListModel;
    private List<GoodsTypeModel> goodsTypeModels;
    private List<GoodsModel> goodsModels;
    private List<BorrowerTypeModel> borrowerTypeModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBillActivity = this;
        setContentView(R.layout.activity_add_bill);
        findView();
        init();
    }
    private void findView() {
        initTopView();
        setTopTitle("添加账单");
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
        activity_add_bill = findViewById(R.id.activity_add_bill);
        activity_add_bill.setOnClickListener(this);
        goods_type_layout.setOnClickListener(this);
        goods_name_layout.setOnClickListener(this);
        loan_amount_layout.setOnClickListener(this);
        cache_loan_amount_layout.setOnClickListener(this);
        borrower_man_layout.setOnClickListener(this);
        borrower_type_layout.setOnClickListener(this);
    }

    private void init() {
        //默认借款类型为商品，将商品种类和商品类型，关联金额框置为可见
        borrower_type.setText("商品");
        goods_type_layout.setVisibility(View.VISIBLE);
        goods_name_layout.setVisibility(View.VISIBLE);
        loan_amount_layout.setVisibility(View.VISIBLE);
        addBillModel = new AddBillModel();
        BorrowerTypeModel borrowerTypeModel = new BorrowerTypeModel();
        borrowerTypeModel.setId("0");
        borrowerTypeModel.setTypeName("商品");
        borrowerTypeModels.add(borrowerTypeModel);
        borrowerTypeModel = new BorrowerTypeModel();
        borrowerTypeModel.setId("1");
        borrowerTypeModel.setTypeName("现金");
        borrowerTypeModels.add(borrowerTypeModel);
        addBillModel.setBorrowerTypeId("0");
        addBillModel.setBorrowerTypeName("商品");
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
            case R.id.activity_add_bill:
                addBill();
                break;
            default:
                break;
        }
    }

    private void addBill() {
        //校验数据
        if (NullUtils.isNull(addBillModel.getBorrowerManId())) {
            Toast.makeText(getApplicationContext(), "请选择借款人！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (NullUtils.isNull(addBillModel.getBorrowerTypeId())) {
            Toast.makeText(getApplicationContext(), "请选择借款类型！", Toast.LENGTH_SHORT).show();
            return;
        }
        //现金
        if ("1".equals(addBillModel.getBorrowerTypeId())) {
            if (NullUtils.isNull(cache_loan_amount.getText().toString())) {
                Toast.makeText(getApplicationContext(), "请输入借款金额！", Toast.LENGTH_SHORT).show();
                return;
            }
            addBillModel.setLoanAmount(cache_loan_amount.getText().toString());
        } else {
            //商品
            if (NullUtils.isNull(addBillModel.getGoodsTypeId())) {
                Toast.makeText(getApplicationContext(), "请选择商品类型！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (NullUtils.isNull(addBillModel.getGoodsId())) {
                Toast.makeText(getApplicationContext(), "请选择商品！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (NullUtils.isNull(addBillModel.getLoanAmount())) {
                Toast.makeText(getApplicationContext(), "请选择价格！", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        OkGo.<String>post(Constant.Url.BASE + Constant.Url.ADDBILL)
                .tag(this)
                .isMultipart(true)
//                .isSpliceUrl(true)
                .retryCount(3)
                .params("borrowerManId", addBillModel.getBorrowerManId())
                .params("btype", addBillModel.getBorrowerTypeId())
                .params("goodsId", addBillModel.getGoodsId())
                .params("loanAmount", addBillModel.getLoanAmount())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_SHORT).show();
//                            BMSApplication.getInstance();
                            Intent intent = new Intent(AddBillActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void showBorrowerType() {
        if (borrowerTypeChoiceDialog == null) {
            borrowerTypeChoiceDialog = new SingleChoiceDialog(addBillActivity, "请选择借款类型", NullUtils.filterEmpty(addBillModel.getBorrowerTypeId()), borrowerTypeModels, new SingleChoiceDialog.ISingleChoiceDialog() {
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
                        addBillModel.setGoodsId("");
                        addBillModel.setGoodsName("");
                        addBillModel.setLoanAmount("");
                        addBillModel.setGoodsTypeId("");
                        addBillModel.setBorrowerTypeName("");
                        //填充借款类型
                        borrower_type.setText(singleChoiceModel.getItemTitle());
                        addBillModel.setBorrowerTypeName(singleChoiceModel.getItemTitle());
                        addBillModel.setBorrowerTypeId(singleChoiceModel.getItemKey());
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
                        addBillModel.setBorrowerTypeName(singleChoiceModel.getItemTitle());
                        addBillModel.setBorrowerTypeId(singleChoiceModel.getItemKey());
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
                                goodsTypeChoiceDialog = new SingleChoiceDialog(addBillActivity, "请选择商品类型", NullUtils.filterEmpty(addBillModel.getGoodsTypeId()), goodsTypeModels, new SingleChoiceDialog.ISingleChoiceDialog() {
                                    @Override
                                    public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                                        //清空已经选择的商品内容和金额内容
                                        if (!NullUtils.filterEmpty(addBillModel.getGoodsTypeId()).equals(NullUtils.filterEmpty(singleChoiceModel.getItemKey()))) {
                                            goods_name.setText("");
                                            loan_amount.setText("");
                                            addBillModel.setGoodsName("");
                                            addBillModel.setGoodsId("");
                                            addBillModel.setLoanAmount("");
                                            goodsNameChoiceDialog = null;
                                        }
                                        goods_type.setText(singleChoiceModel.getItemTitle());
                                        addBillModel.setGoodsTypeName(singleChoiceModel.getItemTitle());
                                        addBillModel.setGoodsTypeId(singleChoiceModel.getItemKey());
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
        if (NullUtils.isNull(addBillModel.getGoodsTypeName()) || NullUtils.isNull(addBillModel.getGoodsTypeId())) {
            Toast.makeText(getApplicationContext(), "请先选择商品类型", Toast.LENGTH_SHORT).show();
        }
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETALLGOODSBYGOODSTYPEID)
                .tag(this)
                .params("goodsTypeId", addBillModel.getGoodsTypeId())
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
                                goodsNameChoiceDialog = new SingleChoiceDialog(addBillActivity, "请选择商品", NullUtils.filterEmpty(addBillModel.getGoodsId()), goodsModels, new SingleChoiceDialog.ISingleChoiceDialog() {
                                    @Override
                                    public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                                        goods_name.setText(singleChoiceModel.getItemTitle());
                                        loan_amount.setText(goodsModels.get(position).getGoodsPrice());
                                        addBillModel.setGoodsName(singleChoiceModel.getItemTitle());
                                        addBillModel.setGoodsId(singleChoiceModel.getItemKey());
                                        addBillModel.setLoanAmount(goodsModels.get(position).getGoodsPrice());
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
                                borrowerManChoiceDialog = new SingleChoiceDialog(addBillActivity, "请选择借款人", NullUtils.filterEmpty(addBillModel.getBorrowerManId()), userListModel, new SingleChoiceDialog.ISingleChoiceDialog() {
                                    @Override
                                    public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                                        borrower_man.setText(singleChoiceModel.getItemTitle());
                                        addBillModel.setBorrowerManName(singleChoiceModel.getItemTitle());
                                        addBillModel.setBorrowerManId(singleChoiceModel.getItemKey());
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
