package com.fhh.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhh.R;
import com.fhh.utils.ScreenUtils;


/**
 * CopyRright (c)2015/10/21: <CPS Soft>
 *
 * @Description:
 * @FileName:BaseFragment
 * @Namespace:com.olcps.cps
 * @CreateDate: 2015/10/21 9:37
 * @Mechanism:XBAS
 * @Author: JZG
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    /**
     * 当前上下页
     */
    protected Activity mActivity;

    /**
     * 缓存Fragment view
     */
    protected View rootView;
    protected TextView mTopTitle;
    protected ImageView mTopIvLeft;
    protected TextView mTopTvleft;
    protected ImageView mTopIvRight;
    protected TextView mTopTvRight;
    protected RelativeLayout mTopLlHeader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        if (rootView == null) {
            instanceRootView(inflater);
            findView();
            initData();
            initListener();
        }
        /**
         * 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，
         * 要不然会发生这个rootview已经有parent的错误。
         */
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }


        return rootView;
    }

    protected void initData() {
    }


    protected abstract void initListener();

    /**
     * 当无参数需要传递时，可调用单参的该函数
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 当无参数需要传递,但有返回结果，可向Bundle传递null
     *
     * @param pClass
     * @param requestCode
     */
    protected void openActivity(Class<?> pClass, int requestCode) {
        openActivity(pClass, null, requestCode);
    }

    /**
     * 传递不带返回值的意图
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 传递带有返回值的activity，简化了意图的代码
     *
     * @param pClass
     * @param pBundle
     * @param requestCode
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * @Description 通过findViewById()获取各个子视图，并做相应的设置
     * @paraminflater
     * @CreateDate 2015年7月6日
     * @Author ZhangMingHui
     * @Return
     */
    public abstract void findView();


    /**
     * @param inflater
     * @Description 初始化视图布局
     * @CreateDate 2015年7月6日
     * @Author ZhangMingHui
     * @Return
     */
    public abstract void instanceRootView(LayoutInflater inflater);

    public void setTopTitle(String title) {
        if (mTopTitle != null) {
            mTopTitle.setText(title);
        }
    }

    public void setTopTitle(int resId) {
        if (mTopTitle != null) {
            mTopTitle.setText(resId);
        }
    }


    public void showLeftView(boolean show, int resId) {
        if (mTopIvLeft != null) {
            if (show) {
                mTopIvLeft.setVisibility(View.VISIBLE);
                mTopIvLeft.setImageResource(resId);
            } else {
                mTopIvLeft.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void showRightView(boolean show, int resId) {
        if (mTopIvRight != null) {
            if (show) {
                mTopIvRight.setVisibility(View.VISIBLE);
                mTopIvRight.setImageResource(resId);
            } else {
                mTopIvRight.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void initHeaderView() {
        mTopTitle = (TextView) rootView.findViewById(R.id.top_tv_title1);
        mTopIvLeft = (ImageView) rootView.findViewById(R.id.top_iv_left1);
        mTopIvRight = (ImageView) rootView.findViewById(R.id.top_iv_right1);
        mTopTvRight = (TextView) rootView.findViewById(R.id.top_tv_right1);
        mTopLlHeader = (RelativeLayout) rootView.findViewById(R.id.top_ll_header1);
        mTopIvLeft.setOnClickListener(this);
        mTopIvRight.setOnClickListener(this);
        //现在的状态栏高度减去正常的状态栏高度(px)
        if (mTopLlHeader != null) {
            int statusOffsetHeight = ScreenUtils.getStatusBarHeight(getActivity()) - ScreenUtils.dp2px(getActivity(), 18);
            ViewGroup.LayoutParams lp = mTopLlHeader.getLayoutParams();
            lp.height += statusOffsetHeight;
            mTopLlHeader.setLayoutParams(lp);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
