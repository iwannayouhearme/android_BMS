package com.fhh.components.goods.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseFragment;
import com.fhh.base.old.ItemViewDelegate;
import com.fhh.base.old.ViewHolder;
import com.fhh.components.addgoods.activity.AddGoodsActivity;
import com.fhh.components.goods.model.GoodsModel;
import com.fhh.components.goods.model.GoodsTypeModel;
import com.fhh.components.goodsdetail.activity.GoodsDetailActivity;
import com.fhh.components.index.adapter.RecyclerViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class GoodsFragment extends BaseFragment {

    private Spinner type_checkbox;
    private String goodsTypeId;
    private List<GoodsTypeModel> goodsTypeModels = new ArrayList<>();
    private List<GoodsModel> goodsModels = new ArrayList<>();
    private RecyclerView goods_rv;
    private RecyclerViewAdapter adapter;


    public static GoodsFragment newInstance() {
        return new GoodsFragment();
    }

    @Override
    protected void initListener() {
        mTopIvRight.setOnClickListener(this);
    }

    @Override
    public void findView() {
        type_checkbox = rootView.findViewById(R.id.type_checkbox);
        goods_rv = rootView.findViewById(R.id.goods_rv);
    }

    @Override
    protected void initData() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETALLGOODSTYPE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            goodsTypeModels = JSONArray.parseArray(jsonObject.getString("data"), GoodsTypeModel.class);
                            String[] checkBox = new String[goodsTypeModels.size()];
                            for (int i = 0; i < goodsTypeModels.size(); i++) {
                                checkBox[i] = goodsTypeModels.get(i).getGoodsTypeName();
                            }
                            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, checkBox);
                            type_checkbox.setAdapter(stringArrayAdapter);
                            type_checkbox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    goodsTypeId = goodsTypeModels.get(position).getId();
                                    getData();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getData() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETALLGOODSBYGOODSTYPEID)
                .tag(this)
                .params("goodsTypeId", goodsTypeId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            goodsModels = JSONArray.parseArray(jsonObject.getString("data"), GoodsModel.class);
                            fullData(goodsModels);
                        } else {
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void fullData(List<GoodsModel> goodsModels) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        goods_rv.setLayoutManager(layoutManager);
        goods_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 2;
            }
        });
        if (!goodsModels.isEmpty()) {
            final List mList = new ArrayList();
            mList.addAll(goodsModels);
            adapter = new RecyclerViewAdapter(getActivity(), mList);
            adapter.addItemViewDelegate(new ItemViewDelegate() {
                @Override
                public int getItemViewLayoutId() {
                    return R.layout.item_recycle_goods;
                }

                @Override
                public boolean isForViewType(Object item, int position) {
                    return item instanceof GoodsModel;
                }

                @Override
                public void convert(ViewHolder holder, Object o, int position) {
                    GoodsModel goodsModel = JSONObject.parseObject(JSON.toJSONString(o), GoodsModel.class);
                    holder.setText(R.id.item_goods_name, "商品名称：" + goodsModel.getGoodsName());
                    holder.setText(R.id.goods_price, "￥" + goodsModel.getGoodsPrice());
                }
            });
            adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Bundle bundle = new Bundle();
                    GoodsModel goodsModel = (GoodsModel) mList.get(position);
                    bundle.putString("goodsId", goodsModel.getId());
                    openActivity(GoodsDetailActivity.class, bundle, 89);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            goods_rv.setLayoutManager(layoutManager);
            goods_rv.setAdapter(adapter);
        } else {
            List<String> mList = new ArrayList<>();
            mList.add("");
            RecyclerViewAdapter typeAdapter = new RecyclerViewAdapter<>(getActivity(), mList);
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
                    holder.setText(R.id.textViewMessage, "没有任何商品");
                    holder.setImageResource(R.id.imageViewMessage, R.mipmap.blank_icon_aircraft);
                }
            });
            goods_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            goods_rv.setAdapter(typeAdapter);
        }
    }


    @Override
    public void instanceRootView(LayoutInflater inflater) {
        rootView = inflater.inflate(R.layout.activity_goods, null);
        initHeaderView();
        setTopTitle("商品");
        showRightView(true, R.mipmap.meeting_icon_edit2x);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_iv_right1:
                Intent intent = new Intent(getActivity(),AddGoodsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
