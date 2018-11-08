package com.fhh.components.goodstype.index.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fhh.R;
import com.fhh.base.BaseViewHolderImp;
import com.fhh.components.goods.index.model.GoodsTypeModel;
import com.fhh.components.goodstype.index.GoodsTypeFragment;

import java.util.List;

public class GoodsTypeAdapter extends BaseQuickAdapter<GoodsTypeModel, BaseViewHolderImp> {

    private GoodsTypeFragment mGoodsTypeFragment;
    private List<GoodsTypeModel> mData;

    public GoodsTypeAdapter(GoodsTypeFragment goodsTypeFragment, int layoutResId, @Nullable List<GoodsTypeModel> data) {
        super(layoutResId, data);
        mGoodsTypeFragment = goodsTypeFragment;
        mData = data;
    }

    @Override
    protected void convert(BaseViewHolderImp helper, final GoodsTypeModel item) {
        helper.setText(R.id.item_goods_type_name, "商品种类名称：" + item.getGoodsTypeName());
        helper.addOnClickListener(R.id.update_goods_type);
        helper.addOnClickListener(R.id.del_goods_type);
    }
}
