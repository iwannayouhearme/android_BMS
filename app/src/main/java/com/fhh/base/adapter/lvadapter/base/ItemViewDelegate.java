package com.fhh.base.adapter.lvadapter.base;


import com.fhh.base.adapter.lvadapter.ViewHolder;

/**
 * @author: nicai
 * @email : nicaicai
 * @blog : nicaicaicai
 * @time : 2017/11/30
 * @desc :
 */

public interface ItemViewDelegate<T> {

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);


}
