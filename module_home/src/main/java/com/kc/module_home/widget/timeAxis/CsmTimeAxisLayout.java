package com.kc.module_home.widget.timeAxis;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/9/2.
 */
//TODO 总结本次自定义经验
public class CsmTimeAxisLayout extends RelativeLayout {

    public static boolean DEBUG = true;
    private final String TAG = this.getClass().getSimpleName();

    float mOneSecondWidth = 0.08f;
    int mIntervalMinutes = 10;
    int mTimeTextMarginTop = 10;
    int mTimeTextSize = 26;

    private int mDataMarkHeight;

    public CsmTimeAxisLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //时间轴
        addView(mAdapter.getTimeAxisView());
        //标志线
        addView(mAdapter.getIndicatorView());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        Log.e(TAG, "onMeasure widthSize=" + widthSize + ",widthMode=" + widthMode + ",heightSize=" + heightSize + ",heightMode=" + heightMode);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int medHeight = getMeasuredHeight();
        mDataMarkHeight = medHeight - getPaddingTop() - getPaddingBottom() - mTimeTextMarginTop - mTimeTextSize;
        View indicatorView = mAdapter.getIndicatorView();
        ViewGroup.LayoutParams layoutParams = indicatorView.getLayoutParams();
        if (layoutParams.height != mDataMarkHeight) {
            layoutParams.height = mDataMarkHeight;
        }
        //这里必须多测量一次，否则CsmTimeAxisView的onMeasure方法最后一次被回调传入的的父亲宽度是子view设置的测量宽度。
        measureChild(mAdapter.getTimeAxisView(), widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
    }

    private Adapter mAdapter = new Adapter() {
        private CsmTimeAxisView mCsmTimeAxisView;
        private View mIndicatorView;

        @Override
        public CsmTimeAxisView getTimeAxisView() {
            if (mCsmTimeAxisView == null) {
                mCsmTimeAxisView = new CsmTimeAxisView(getContext(), CsmTimeAxisLayout.this);
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mCsmTimeAxisView.setLayoutParams(layoutParams);
            }
            return mCsmTimeAxisView;
        }

        @Override
        public View getIndicatorView() {//第一次返回的view确实属性：高度
            if (mIndicatorView == null) {
                mIndicatorView = new View(getContext());
                LayoutParams layoutParams = new LayoutParams(4, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                mIndicatorView.setLayoutParams(layoutParams);

                mIndicatorView.setBackgroundColor(Color.RED);
            }
            return mIndicatorView;
        }
    };

    private interface Adapter {
        CsmTimeAxisView getTimeAxisView();

        View getIndicatorView();
    }

    public CsmTimeAxisView getTimeAxisView() {
        return mAdapter.getTimeAxisView();
    }
}
