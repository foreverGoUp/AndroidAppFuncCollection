package com.kc.module_home.widget.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

public class TestRelativeLayout extends RelativeLayout {

    private final String TAG = this.getClass().getSimpleName();

    public TestRelativeLayout(Context context) {
        super(context);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * viewGroup测量过程会回调2次子view的测量方法，第一次是父类把自己要求的尺寸提供给子view参考，子view设置自己的尺寸后，
     * 第二次回调父把子view自己设置的尺寸再提供给子view
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        Log.e(TAG, "onMeasure widthSize=" + widthSize + ",widthMode=" + widthMode + ",heightSize=" + heightSize + ",heightMode=" + heightMode);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
