package com.kc.common_service.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/12/30.
 */

public class FreezableTabLayout extends TabLayout {

    private boolean mLayoutFrozen = false;
    private OnClickListener mOnClickListener;

    public FreezableTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mLayoutFrozen) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLayoutFrozen) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick(this);
            }
            return false;
        }
        return super.onTouchEvent(ev);
    }

    public void setLayoutFrozen(boolean layoutFrozen) {
        this.mLayoutFrozen = layoutFrozen;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mOnClickListener = l;
    }
}
