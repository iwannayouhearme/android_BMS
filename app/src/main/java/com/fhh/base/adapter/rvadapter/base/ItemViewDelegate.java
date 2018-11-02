package com.fhh.base.adapter.rvadapter.base;

/**
 * @author: nicai
 * @email : nicaicai
 * @blog : nicaicaicai
 * @time : 2017/11/30
 * @desc :
 */

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
