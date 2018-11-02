package com.fhh.components.index.activity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseActivity;
import com.fhh.base.old.ItemViewDelegate;
import com.fhh.base.old.ViewHolder;
import com.fhh.components.index.adapter.RecyclerViewAdapter;
import com.fhh.components.index.model.HomeItem;
import com.fhh.components.index.model.IndexModel;
import com.fhh.components.index.model.UserBillListModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;

/**
 * @author biubiubiu小浩
 * @description 首页
 * @date 2018/10/22 10:11
 **/
public class IndexActivity extends BaseActivity {

    TextView index_tv, total_money, total_pay_money, total_unpay_money;
    RecyclerView index_rv;
    TextView item_borrower_time, item_borrower_type, item_goods_name, item_price, item_meeting_tv_status, index_btn;
    Spinner status_checkbox;
    private RecyclerViewAdapter adapter;

    private String status = "";

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        String date = new SimpleDateFormat("yyyy-MM").format(new Date());
        index_tv.setText(Integer.parseInt(date.substring(0, 4)) + "-" + Integer.parseInt(date.substring(5, 7)));
        getData();
        index_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker picker = new DatePicker(IndexActivity.this, DatePicker.YEAR_MONTH);
                picker.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                picker.setWidth(picker.getScreenWidthPixels());
                picker.setRangeStart(2018, 10, 14);
                picker.setRangeEnd(2999, 12, 30);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
                        index_tv.setText(year + "-" + month);
                        getData();
                    }
                });
                picker.show();
            }
        });
        String[] checkBok = {"全部", "已还款", "未还款"};
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, checkBok);
        status_checkbox.setAdapter(stringArrayAdapter);
        status_checkbox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        status = "";
                        break;
                    case 1:
                        status = "1";
                        break;
                    case 2:
                        status = "0";
                        break;
                    default:
                        break;
                }
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView() {
        index_btn = findViewById(R.id.index_btn);
        index_tv = findViewById(R.id.index_tv);
        index_rv = findViewById(R.id.index_rv);
        total_money = findViewById(R.id.total_money);
        total_pay_money = findViewById(R.id.total_pay_money);
        total_unpay_money = findViewById(R.id.total_unpay_money);
        item_borrower_time = (TextView) findViewById(R.id.item_borrower_time);
        item_borrower_type = (TextView) findViewById(R.id.item_borrower_type);
        item_goods_name = (TextView) findViewById(R.id.item_goods_name);
        item_price = (TextView) findViewById(R.id.item_price);
        item_meeting_tv_status = (TextView) findViewById(R.id.item_meeting_tv_status);
        status_checkbox = (Spinner) findViewById(R.id.status_checkbox);
    }


    /**
     * 获取数据
     *
     * @param
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/23 11:21
     **/
    private void getData() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETINDEX)
                .tag(this)
                .params("yearMonth", index_tv.getText().toString())
                .params("bstatus", status)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body().toString());
                        if ("true".equals(jsonObject.getString("success"))) {
                            IndexModel indexModel = JSONObject.parseObject(jsonObject.getString("data").toString(), IndexModel.class);
                            fullData(indexModel);
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * 填充数据
     *
     * @param
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/23 11:21
     **/
    private void fullData(IndexModel indexModel) {
        total_money.setText(indexModel.getTotalMoney());
        total_pay_money.setText(indexModel.getTotalPaymoney());
        total_unpay_money.setText(indexModel.getTotalUnpayMoney());
        List<HomeItem> userBillListArray = JSONArray.parseArray(indexModel.getUserBillList(), HomeItem.class);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(IndexActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        index_rv.setLayoutManager(layoutManager);
        index_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 2;
            }
        });
        if (!userBillListArray.isEmpty()) {
            final List mList = new ArrayList();
            mList.addAll(userBillListArray);
            int point = 1;
            for (HomeItem detailListString : userBillListArray) {
                List<UserBillListModel> detailList = JSONArray.parseArray(detailListString.getDetailList(), UserBillListModel.class);
                mList.addAll(point, detailList);
                point += detailList.size() + 1;
            }
            adapter = new RecyclerViewAdapter(this, mList);

            adapter.addItemViewDelegate(new ItemViewDelegate() {
                @Override
                public int getItemViewLayoutId() {
                    return R.layout.user_item_rv;
                }

                @Override
                public boolean isForViewType(Object item, int position) {
                    return item instanceof HomeItem;
                }

                @Override
                public void convert(ViewHolder holder, Object o, int position) {
                    HomeItem homeItem = JSONObject.parseObject(JSON.toJSONString(o), HomeItem.class);
                    holder.setText(R.id.item_bill_info, homeItem.getUserRealName() + " 外号:" + homeItem.getUserNickName() + " 总借款:" + homeItem.getTotalUserMoney() + " 未还款:" + homeItem.getTotalUserUnpayMoney());
                }
            });


            adapter.addItemViewDelegate(new ItemViewDelegate() {
                @Override
                public int getItemViewLayoutId() {
                    return R.layout.item_recycler_detail;
                }

                @Override
                public boolean isForViewType(Object item, int position) {
                    return item instanceof UserBillListModel;
                }

                @Override
                public void convert(ViewHolder holder, Object o, int position) {
                    UserBillListModel userBillListModel = JSONObject.parseObject(JSON.toJSONString(o), UserBillListModel.class);
                    holder.setText(R.id.item_borrower_time, "借款日期：" + userBillListModel.getCreate_time());
                    String[] type = new String[]{"商品", "现金"};
                    holder.setText(R.id.item_borrower_type, "借款类型：" + type[Integer.parseInt(userBillListModel.getBtype())]);
                    if ("1".equals(userBillListModel.getBtype())) {
                        holder.setText(R.id.item_goods_name, "商品名称：无");
                    } else {
                        holder.setText(R.id.item_goods_name, "商品名称：" + userBillListModel.getGoodsName());
                    }
                    holder.setText(R.id.item_price, "金额：" + userBillListModel.getLoanAmount());
                    String[] status = new String[]{"未还款", "已还款"};
                    holder.setText(R.id.item_meeting_tv_status, status[Integer.parseInt(userBillListModel.getBstatus())]);
                }
            });
            index_rv.setAdapter(adapter);
        } else {
            List<String> mList = new ArrayList<>();
            mList.add("");
            RecyclerViewAdapter typeAdapter = new RecyclerViewAdapter<>(this, mList);
            typeAdapter.addItemViewDelegate(new ItemViewDelegate() {
                @Override
                public int getItemViewLayoutId() {
                    return R.layout.item_empty_view;
                }

                @Override
                public boolean isForViewType(Object item, int position) {
                    return true;
                }

                @Override
                public void convert(ViewHolder holder, Object o, int position) {
                    holder.setVisible(R.id.buttonEmpty, false);
                    holder.setText(R.id.textViewMessage, "没有任何账单");
                    holder.setImageResource(R.id.imageViewMessage, R.mipmap.blank_icon_aircraft);
                }
            });
            index_rv.setLayoutManager(new LinearLayoutManager(this));
            index_rv.setAdapter(typeAdapter);
        }
    }
}
