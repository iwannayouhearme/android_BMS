package com.fhh.base;


import android.support.annotation.IdRes;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author: kadh
 * @email : 36870855@qq.com
 * @date : 2018/8/6
 * @blog : http://www.nicaicaicai.com
 * @desc :
 */
public class BaseViewHolderImp extends BaseViewHolder {
    public BaseViewHolderImp(View view) {
        super(view);
    }

    public BaseViewHolder setTextFlag(@IdRes int viewId, int resId) {
        TextView view = getView(viewId);
        view.getPaint().setFlags(resId);
        return this;
    }
}
