package com.fhh.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by djtao on 2016/6/20.
 * modify by nicaicaicai
 */

public class ScreenUtils {


    /**
     * 获取屏幕分辨率
     */
    public int[] getWidthAndHeight(Activity activity) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        return new int[]{width, height};
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    /**
     * 计算的GridView的高度
     */
    public static void setListViewHeightBasedOnChildren(GridView gridview) {
        if (gridview == null) {
            {
                return;
            }
        }
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight;
        //向上取整
        int count = (int) Math.ceil(listAdapter.getCount() / 5.0);
        //获取一个子view
        View itemView = listAdapter.getView(0, null, gridview);
        //测量View的大小
        itemView.measure(0, 0);
        totalHeight = itemView.getMeasuredHeight();
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        //设置GridView的布局高度
        params.height = totalHeight * count + dp2px(gridview.getContext(), 20);
        gridview.setLayoutParams(params);
    }


    /**
     * 计算的ListView的高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter.getCount() <= 3) {
            return;
        }

        if (listAdapter == null) {
            return;
        }
        // 固定3列
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        int count = 3;

        // 获取listview的每一个item
        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        // 获取item的高度和
        totalHeight = listItem.getMeasuredHeight() * count + listItem.getMeasuredHeight() / 2;

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置参数
        listView.setLayoutParams(params);
    }

    /**
     * 返回标题栏加上评论框的高度
     * @param activity
     * @return
     */
    public static int getTopViewAndCommentViewHeight(Activity activity) {
        return ScreenUtils.dp2px(activity, 165);
    }

    /**
     * 返回标题栏高度
     * @param context
     * @return
     */
    public static int getTopViewHeight(Context context) {
        return ScreenUtils.dp2px(context, 65);
    }

}
