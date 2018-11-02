package com.fhh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author: kadh
 * @email : 36870855@qq.com
 * @date : 2018/8/30
 * @blog : http://www.nicaicaicai.com
 * @desc :
 */
public class ScrollEditView extends android.support.v7.widget.AppCompatEditText {
    public ScrollEditView(Context context) {
        super(context);
    }

    public ScrollEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 通知其父控件，现在进行的是本控件的操作，不允许拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return super.onTouchEvent(event);
    }

}
