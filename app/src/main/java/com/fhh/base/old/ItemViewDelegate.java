package com.fhh.base.old;

/**
 * @description
 * @author  biubiubiu小浩
 * @date 2018/10/24 13:12
 **/

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
