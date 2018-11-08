package com.fhh.components.user.adapat;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fhh.R;
import com.fhh.components.user.UserFragment;
import com.fhh.components.user.model.UserModel;

import java.util.List;

public class UserAdapter extends BaseQuickAdapter<UserModel,BaseViewHolder> {

    private UserFragment muserFragment;
    private List<UserModel> mData;

    public UserAdapter(UserFragment userFragment,int layoutResId, @Nullable List<UserModel> data) {
        super(layoutResId, data);
        muserFragment = userFragment;
        mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserModel item) {
        helper.setText(R.id.item_user_real_name,"真实姓名：  "+item.getRealName());
        helper.setText(R.id.item_user_nike_name,"外号：  "+item.getNickName());
    }
}
