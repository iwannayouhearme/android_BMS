package com.fhh.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * CopyRright (c)2015/12/15: <CPS Soft>
 *
 * @Description:根据版本号选择兼容的方法获取颜色
 * @FileName:GetResourcesUtil
 * @Namespace:com.example.utils
 * @CreateDate: 2015/12/15 16:21
 * @Mechanism:XBAS
 * @Author: JZG
 */
public class GetResourcesUtil {

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(id, null);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static final int getColor(View view, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            return view.getResources().getColor(id, null);
        } else {
            return view.getResources().getColor(id);
        }
    }

    public static final Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, null);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}
