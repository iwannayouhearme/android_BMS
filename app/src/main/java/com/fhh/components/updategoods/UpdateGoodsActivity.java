package com.fhh.components.updategoods;

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
import com.fhh.components.goods.model.GoodsModel;
import com.fhh.utils.NullUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * @author biubiubiu小浩
 * @description 更新商品
 * @date 2018/11/5 19:56
 **/
public class UpdateGoodsActivity extends BaseActivity {

    private Button update_goods;
    private TextView goods_type;
    private EditText goods_name, cache_loan_amount;
    private LinearLayout goods_type_layout;
    private SingleChoiceDialog goodsTypeChoiceDialog;
    private List<GoodsTypeModel> goodsTypeModels;
    private Activity upDateGoodsActivity;
    private GoodsModel goodsModel;
    private Bundle bundle;
    private String goodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goods);
        upDateGoodsActivity = this;
        findView();
        init();
    }

    /**
     * 初始化数据
     *
     * @author biubiubiu小浩
     * @date 2018/11/5 19:59
     **/
    private void init() {
        bundle = getIntent().getExtras();
        goodsId = bundle.getString("goodsId");
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
        goods_type.setText(goodsModel.getGoodsTypeName());
        cache_loan_amount.setText(goodsModel.getGoodsPrice());
        goods_name.setText(goodsModel.getGoodsName());
    }

    private void findView() {
        initTopView();
        setTopTitle("更新商品");
        showLeftView(true);
        update_goods = findViewById(R.id.update_goods);
        goods_type = findViewById(R.id.goods_type);
        goods_name = findViewById(R.id.goods_name);
        cache_loan_amount = findViewById(R.id.cache_loan_amount);
        goods_type_layout = findViewById(R.id.goods_type_layout);
        update_goods.setOnClickListener(this);
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
            case R.id.update_goods:
                updateGoods();
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
                                goodsTypeChoiceDialog = new SingleChoiceDialog(upDateGoodsActivity, "请选择商品类型", NullUtils.filterEmpty(goodsModel.getGoodsTypeId()), goodsTypeModels, new SingleChoiceDialog.ISingleChoiceDialog() {
                                    @Override
                                    public void onItemClickForSingleChoiceDialog(ISingleChoiceModel singleChoiceModel, int position) {
                                        goods_type.setText(singleChoiceModel.getItemTitle());
                                        goodsModel.setGoodsTypeName(singleChoiceModel.getItemTitle());
                                        goodsModel.setGoodsTypeId(singleChoiceModel.getItemKey());
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

    private void updateGoods() {
        //校验数据
        if (NullUtils.isNull(goodsModel.getGoodsTypeId())) {
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
        OkGo.<String>post(Constant.Url.BASE + Constant.Url.UPDATEGOODS)
                .tag(this)
                .isMultipart(true)
                .retryCount(3)
                .params("goodsId", goodsId)
                .params("goodsName", goods_name.getText().toString())
                .params("goodsPrice", cache_loan_amount.getText().toString())
                .params("goodsTypeId", goodsModel.getGoodsTypeId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getApplicationContext(), "更新成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateGoodsActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
