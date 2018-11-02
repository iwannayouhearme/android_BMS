package com.fhh.base.adapter.rvadapter;

import android.content.Context;
import android.view.LayoutInflater;


import com.fhh.base.adapter.rvadapter.base.ItemViewDelegate;
import com.fhh.base.adapter.rvadapter.base.ViewHolder;

import java.util.List;

/**
 * @author: nicai
 * @email : nicaicai
 * @blog : nicaicaicai
 * @time : 2017/11/30
 * @desc :
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


}
