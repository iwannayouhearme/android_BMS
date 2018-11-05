package com.fhh.components.addgoods.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.fhh.components.addbill.model.GoodsTypeModel;
import com.fhh.components.addgoods.model.AddGoodsModel;
import com.fhh.components.goods.fragment.GoodsFragment;
import com.fhh.utils.NullUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * @author biubiubiu小浩
 * @description 添加商品
 * @date 2018/11/4 15:25
 **/
public class AddGoodsActivity extends BaseActivity {

    private Button add_goods;
    private TextView goods_type;
    private EditText goods_name, cache_loan_amount;
    private LinearLayout goods_type_layout;
    private SingleChoiceDialog goodsTypeChoiceDialog;
    private List<GoodsTypeModel> goodsTypeModels;
    private Activity addGoodsActivity;
    private AddGoodsModel addGoodsModel = new AddGoodsModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        addGoodsActivity = this;
        findView();
    }

    private void findView() {
        initTopView();
        setTopTitle("添加商品");
        showLeftView(true);
        add_goods = findViewById(R.id.add_goods);
        goods_type = findViewById(R.id.goods_type);
        goods_name = findViewById(R.id.goods_name);
        cache_loan_amount = findViewById(R.id.cache_loan_amount);
        goods_type_layout = findViewById(R.id.goods_type_layout);
        add_goods.setOnClickListener(this);
        goods_type_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_type_layout:
                if (goodsTypeChoiceDialog == null) {
                    showGoodsType();
                } else {
                    goodsTypeChoiceDialog.show();
                }
                break;
            case R.id.add_goods:
                addGoods();
                break;
            case R.id.top_iv_left:
                backbtn(v);
                break;
            default:
                break;
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
                                goodsTypeChoiceDialog = new SingleChoiceDialog(addGoodsActivity, "请选择商品类型", NullUtils.filterEmpty(addGoodsModel.getGoodsTypeId()), goodsTypeModels, new SingleChoiceDialog.ISingleChoiceDialog() {
                                    @Override
                                    public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                                        goods_type.setText(singleChoiceModel.getItemTitle());
                                        addGoodsModel.setGoodsTypeName(singleChoiceModel.getItemTitle());
                                        addGoodsModel.setGoodsTypeId(singleChoiceModel.getItemKey());
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

    private void addGoods() {
        //校验数据
        if (NullUtils.isNull(addGoodsModel.getGoodsTypeId())) {
            Toast.makeText(getApplicationContext(), "请选择商品类型！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (NullUtils.isNull(goods_name.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入商品名称！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (NullUtils.isNull(cache_loan_amount.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入商品价格！", Toast.LENGTH_SHORT).show();
            return;
        }
        OkGo.<String>post(Constant.Url.BASE + Constant.Url.ADDGOODS)
                .tag(this)
                .isMultipart(true)
                .retryCount(3)
                .params("goodsName", goods_name.getText().toString())
                .params("goodsPrice", cache_loan_amount.getText().toString())
                .params("goodsTypeId", addGoodsModel.getGoodsTypeId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "添加成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddGoodsActivity.this, GoodsFragment.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
