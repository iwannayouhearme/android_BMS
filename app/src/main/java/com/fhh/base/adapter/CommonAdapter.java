package com.fhh.base.adapter;

import android.content.Context;


import com.fhh.base.adapter.lvadapter.MultiItemTypeAdapter;
import com.fhh.base.adapter.lvadapter.ViewHolder;
import com.fhh.base.adapter.lvadapter.base.ItemViewDelegate;

import java.util.List;

/**
 * @author: nicai
 * @email : nicaicai
 * @blog : nicaicaicai
 * @time : 2017/11/30
 * @desc :
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);

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

    @Override
    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
