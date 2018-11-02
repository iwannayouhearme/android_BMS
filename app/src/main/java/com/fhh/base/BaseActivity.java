package com.fhh.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhh.R;
import com.fhh.utils.ScreenUtils;

/**
 * @author biubiubiu小浩
 * @description 基类
 * @date 2018/10/19 16:27
 **/
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {


    private TextView mTopTitle;
    private ImageView mTopIvLeft;
    private TextView mTopTvRight;
    private RelativeLayout mTopLlHeader;
    private ImageView mTopIvLeftTwo;
    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //锁定竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏/导航栏
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        mActivity = this;
        initSystemFont();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initSystemFont() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    public void backbtn(View v) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.leftmove2, R.anim.rightmove2);//上一步
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 当无参数需要传递时，可调用单参的该函数
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 当无参数需要传递,但有返回结果，可向Bundle传递null
     */
    public void openActivity(Class<?> pClass, int requestCode) {
        openActivity(pClass, null, requestCode);
    }

    /**
     * 传递不带返回值的意图
     */
    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent i = new Intent(this, pClass);
        if (pBundle != null) {
            i.putExtras(pBundle);
        }
        startActivity(i);
    }

    /**
     * 传递带有返回值的activity，简化了意图的代码
     */
    public void openActivity(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent i = new Intent(this, pClass);
        if (pBundle != null) {
            i.putExtras(pBundle);
        }
        startActivityForResult(i, requestCode);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
//            overridePendingTransition(R.anim.leftmove2, R.anim.rightmove2);//上一步
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initTopView() {
        mTopTitle = (TextView) findViewById(R.id.top_tv_title);
        mTopIvLeft = (ImageView) findViewById(R.id.top_iv_left);
        mTopIvLeftTwo = (ImageView) findViewById(R.id.top_iv_left_two);
        mTopTvRight = (TextView) findViewById(R.id.top_tv_right);
        mTopLlHeader = (RelativeLayout) findViewById(R.id.top_ll_header);
        mTopTvRight.setOnClickListener(this);
        mTopIvLeftTwo.setOnClickListener(this);
        //现在的状态栏高度减去正常的状态栏高度(px)
        if (mTopLlHeader != null) {
            int statusOffsetHeight = ScreenUtils.getStatusBarHeight(this) - ScreenUtils.dp2px(this, 18);
//            mTopLlHeader.setPadding(mTopLlHeader.getPaddingLeft() , mTopLlHeader.getPaddingTop() + statusOffsetHeight, mTopLlHeader.getPaddingRight(),mTopLlHeader.getPaddingBottom());
            ViewGroup.LayoutParams lp = mTopLlHeader.getLayoutParams();
            lp.height += statusOffsetHeight;
            mTopLlHeader.setLayoutParams(lp);
        }
    }

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

    public void showLeftViewTwo(boolean show) {
        if (mTopIvLeftTwo != null) {
            if (show) {
                mTopIvLeftTwo.setVisibility(View.VISIBLE);
            } else {
                mTopIvLeftTwo.setVisibility(View.GONE);
            }
        }
    }

    public void showLeftView(int resId) {
        showLeftView(true, resId);
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
    public TextView getTopTitle() {
        return mTopTitle;
    }

    public TextView getTopTvRight() {
        return mTopTvRight;
    }

    public RelativeLayout getTopLlHeader() {
        return mTopLlHeader;
    }
}
