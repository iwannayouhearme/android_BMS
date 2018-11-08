package com.fhh.components.goodstype.index;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fhh.MainActivity;
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseFragment;
import com.fhh.base.old.ItemViewDelegate;
import com.fhh.base.old.ViewHolder;
import com.fhh.components.goodstype.addgoodstype.AddGoodsTypeActivity;
import com.fhh.components.goods.index.model.GoodsTypeModel;
import com.fhh.components.goodstype.index.adapter.GoodsTypeAdapter;
import com.fhh.components.bill.index.adapter.RecyclerViewAdapter;
import com.fhh.components.goodstype.updategoodstype.UpdateGoodsTypeActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author biubiubiu小浩
 * @description 商品类别
 * @date 2018/11/5 21:54
 **/
public class GoodsTypeFragment extends BaseFragment {

    private RecyclerView goods_type_rv;
    private List<GoodsTypeModel> goodsTypeModels;
    private GoodsTypeAdapter adapter;

    public static GoodsTypeFragment newInstance() {
        return new GoodsTypeFragment();
    }

    @Override
    protected void initListener() {
        mTopIvRight.setOnClickListener(this);
//        update_goods_type.setOnClickListener(this);
//        del_goods_type.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_iv_right1:
                Intent intent = new Intent(getActivity(), AddGoodsTypeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void delGoodsType(int p) {
        OkGo.<String>post(Constant.Url.BASE + Constant.Url.DELGOODSTYPE)
                .isMultipart(true)
                .retryCount(3)
                .params("goodsTypeId", goodsTypeModels.get(p).getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void findView() {
        goods_type_rv = rootView.findViewById(R.id.goods_type_rv);
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
                            fullData(goodsTypeModels);
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    private void fullData(List<GoodsTypeModel> goodsTypeModel) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        goods_type_rv.setLayoutManager(layoutManager);
        goods_type_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 2;
            }
        });
        if (!goodsTypeModel.isEmpty()) {
            final List<GoodsTypeModel> mList = new ArrayList();
            mList.addAll(goodsTypeModel);
            adapter = new GoodsTypeAdapter(this, R.layout.item_recycle_goods_type, mList);
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.update_goods_type:
                            Intent intent = new Intent(getActivity(), UpdateGoodsTypeActivity.class);
                            intent.putExtra("goodsTypeId", mList.get(position).getId());
                            startActivity(intent);
                            break;
                        case R.id.del_goods_type:
                            delGoodsType(position);
                            break;
                        default:
                            break;
                    }
                }
            });
            goods_type_rv.setLayoutManager(layoutManager);
            goods_type_rv.setAdapter(adapter);
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
                    holder.setText(R.id.textViewMessage, "没有任何商品类型");
                    holder.setImageResource(R.id.imageViewMessage, R.mipmap.blank_icon_aircraft);
                }
            });
            goods_type_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            goods_type_rv.setAdapter(typeAdapter);
        }
    }

    @Override
    public void instanceRootView(LayoutInflater inflater) {
        rootView = inflater.inflate(R.layout.activity_goods_type, null);
        initHeaderView();
        setTopTitle("商品类别");
        showRightView(true, R.mipmap.meeting_icon_edit2x);
    }
}
