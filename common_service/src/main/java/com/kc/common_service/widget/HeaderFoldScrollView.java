package com.kc.common_service.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by peter on 2018/4/14.
 */

public class HeaderFoldScrollView extends ScrollView {

    private static final String TAG = "HeaderFoldScrollView";

    private int mHeaderHeight;
    private float mLastX, mLastY;
    private int mTouchSlop;

    public HeaderFoldScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 设置头部
     */
    public void setHeaderHeight(int height) {
        mHeaderHeight = height;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(ev);// =
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getRawX();
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getRawX();
                float y = ev.getRawY();
                float absX = Math.abs(mLastX - x);
                float absY = Math.abs(mLastY - y);
//                Log.e(TAG, "dfsdffffffff: "+getTranslationY());
                if (absY > mTouchSlop && absX < absY) {//(absX > mTouchSlop || absY > mTouchSlop)
                    boolean up = y - mLastY < 0;
                    int scrollY = getScrollY();
                    if ((scrollY >= 0 && scrollY < mHeaderHeight && up)
                            || (scrollY > 0 && scrollY <= mHeaderHeight && !up)
                    ) {
                        intercepted = true;
                    } else {
                        intercepted = false;
                    }
                }

                mLastX = x;
                mLastY = y;
                break;
        }
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onTouchEvent(ev);// =
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getRawX();
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getRawX();
                float y = ev.getRawY();
                float absX = Math.abs(mLastX - x);
                float absY = Math.abs(mLastY - y);
//                Log.e(TAG, "dfsdffffffff: "+getTranslationY());
                if (absY > mTouchSlop && absX < absY) {//(absX > mTouchSlop || absY > mTouchSlop)
                    boolean up = y - mLastY < 0;
                    int scrollY = getScrollY();
                    if ((scrollY >= 0 && scrollY < mHeaderHeight && up)
                            || (scrollY > 0 && scrollY <= mHeaderHeight && !up)
                    ) {
                        intercepted = true;
                    } else {
                        intercepted = false;
                    }
                }

                mLastX = x;
                mLastY = y;
                break;
        }
        return intercepted;
    }
}
