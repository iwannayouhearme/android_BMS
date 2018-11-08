package com.fhh.components.user;

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
import com.fhh.R;
import com.fhh.api.Constant;
import com.fhh.base.BaseFragment;
import com.fhh.base.old.ItemViewDelegate;
import com.fhh.base.old.ViewHolder;
import com.fhh.components.bill.index.adapter.RecyclerViewAdapter;
import com.fhh.components.user.adapat.UserAdapter;
import com.fhh.components.user.adduser.AddUserActivity;
import com.fhh.components.user.model.UserModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends BaseFragment {

    private RecyclerView user_rv;
    private List<UserModel> userModels;
    private UserAdapter adapter;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    protected void initListener() {
        mTopIvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_iv_right1:
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void findView() {
        user_rv = rootView.findViewById(R.id.user_rv);
    }

    @Override
    protected void initData() {
        OkGo.<String>get(Constant.Url.BASE + Constant.Url.GETALLUSER)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSONObject.parseObject(response.body());
                        if ("true".equals(jsonObject.getString("success"))) {
                            userModels = JSONArray.parseArray(jsonObject.getString("data"), UserModel.class);
                            fullData(userModels);
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    private void fullData(List<UserModel> userModels) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        user_rv.setLayoutManager(layoutManager);
        user_rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 2;
            }
        });
        if (!userModels.isEmpty()) {
            final List<UserModel> mList = new ArrayList();
            mList.addAll(userModels);
            adapter = new UserAdapter(this, R.layout.item_recycler_user, mList);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                }
            });
            user_rv.setLayoutManager(layoutManager);
            user_rv.setAdapter(adapter);
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
                    holder.setText(R.id.textViewMessage, "没有任何用户");
                    holder.setImageResource(R.id.imageViewMessage, R.mipmap.blank_icon_aircraft);
                }
            });
            user_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            user_rv.setAdapter(typeAdapter);
        }
    }

    @Override
    public void instanceRootView(LayoutInflater inflater) {
        rootView = inflater.inflate(R.layout.activity_user, null);
        initHeaderView();
        setTopTitle("用户管理");
        showRightView(true, R.mipmap.meeting_icon_edit2x);
    }
}
