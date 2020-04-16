package com.kc.common_service.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/11/17.
 */

public class FreezableViewPager extends ViewPager {

    private boolean mLayoutFrozen = false;

    public FreezableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mLayoutFrozen) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLayoutFrozen) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    public void setLayoutFrozen(boolean layoutFrozen) {
        mLayoutFrozen = layoutFrozen;
    }
}
