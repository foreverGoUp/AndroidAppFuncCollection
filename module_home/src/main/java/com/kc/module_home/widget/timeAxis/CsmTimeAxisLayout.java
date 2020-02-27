package com.kc.module_home.widget.timeAxis;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ckc.android.develophelp.lib.util.TimeUtils;

/**
 * Created by Administrator on 2017/9/2.
 */
//TODO 总结本次自定义经验
public class CsmTimeAxisLayout extends RelativeLayout implements CsmTimeAxisView.OnCsmTimeAxisListener {

    public static boolean DEBUG = true;
    private static final String TAG = "CsmTimeAxisLayout";
    //    private int mTimeAxisViewPadding = 10;
//    private int mTimeAxisViewHeight = 300;
    private boolean mLayoutOnce;
    private boolean mMeasureOnce;

    public CsmTimeAxisLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //显示时间view
        addView(mAdapter.getShowTimeTextView());
        //时间轴
        addView(mAdapter.getTimeAxisView());
        //标志线
        addView(mAdapter.getIndicatorView());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        Log.e(TAG, "onMeasure: widthMode=" + widthMode + ",widthSize=" + widthSize + ",heightMode=" + heightMode + ",heightSize=" + heightSize);

        measureChild(mAdapter.getShowTimeTextView(), widthMeasureSpec, heightMeasureSpec);
        int showTimeTvHeight = mAdapter.getShowTimeTextView().getMeasuredHeight();
        //设置时间轴view的高度
        int timeAxisViewHeight = heightSize - showTimeTvHeight;
        ViewGroup.LayoutParams layoutParams = mAdapter.getTimeAxisView().getLayoutParams();
        layoutParams.height = timeAxisViewHeight;
        //测量时间轴view，确定时间轴底部时间线到view底部的距离
        measureChild(mAdapter.getTimeAxisView(), widthMeasureSpec, heightMeasureSpec);
        int indicatorViewHeight = (int) mAdapter.getTimeAxisView().getMarkLineAreaHeight();
        //设置指示线距离底部margin以及它的高度
        LayoutParams layoutParams1 = (LayoutParams) mAdapter.getIndicatorView().getLayoutParams();
        layoutParams1.height = indicatorViewHeight;
        layoutParams1.bottomMargin = (int) mAdapter.getTimeAxisView().getOffsetBottomHeight();
        measureChild(mAdapter.getIndicatorView(), widthMeasureSpec, heightMeasureSpec);

        //
        setMeasuredDimension(widthSize, heightSize);
    }

    private Adapter mAdapter = new Adapter() {
        private CsmTimeAxisView mCsmTimeAxisView;
        private View mIndicatorView;
        private TextView mTvShowTime;

        @Override
        public CsmTimeAxisView getTimeAxisView() {//int height
//            Log.e(TAG, ">>>>>getTimeAxisView: height=" + height);
            if (mCsmTimeAxisView == null) {
                mCsmTimeAxisView = new CsmTimeAxisView(getContext());
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                mCsmTimeAxisView.setLayoutParams(layoutParams);
                int padding = 10;
                mCsmTimeAxisView.setPadding(0, padding, 0, padding);
                //setPadding
                mCsmTimeAxisView.setListener(CsmTimeAxisLayout.this);
            }
            return mCsmTimeAxisView;
        }

        @Override
        public View getIndicatorView() {//int height, int marginBottom
            Log.e(TAG, "getIndicatorView");
            if (mIndicatorView == null) {
                mIndicatorView = new View(getContext());
                LayoutParams layoutParams = new LayoutParams(4, 20);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                layoutParams.bottomMargin = marginBottom;
                mIndicatorView.setLayoutParams(layoutParams);
                mIndicatorView.setBackgroundColor(Color.RED);
            }
            return mIndicatorView;
        }

        @Override
        public TextView getShowTimeTextView() {
            if (mTvShowTime == null) {
                mTvShowTime = new TextView(getContext());
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                mTvShowTime.setLayoutParams(layoutParams);
//                mTvShowTime.setGravity(Gravity.CENTER);
//                mTvShowTime.setPadding(20, 0, 20, 0);
                mTvShowTime.setBackground(getResources().getDrawable(android.R.drawable.toast_frame));
                mTvShowTime.setTextColor(Color.WHITE);
                mTvShowTime.setText("00:00:00");
            }
            return mTvShowTime;
        }
    };

    @Override
    public void onTimeAxisMoveStart() {
        if (mListener != null) {
            mListener.onTimeAxisMoveStart();
        }
    }

    @Override
    public void onTimeAxisMove(long timeMilli) {
        TextView textView = mAdapter.getShowTimeTextView();
        if (textView != null) {
            textView.setText(TimeUtils.getDefaultTime(timeMilli));
        }
        //
        if (mListener != null) {
            mListener.onTimeAxisMove(timeMilli);
        }
    }

    @Override
    public void onTimeAxisStop(long timeMilli) {
        TextView textView = mAdapter.getShowTimeTextView();
        if (textView != null) {
            textView.setText(TimeUtils.getDefaultTime(timeMilli));
        }
        //
        if (mListener != null) {
            mListener.onTimeAxisStop(timeMilli);
        }
    }

    private interface Adapter {
        CsmTimeAxisView getTimeAxisView();

        View getIndicatorView();//int height, int marginBottom

        TextView getShowTimeTextView();
    }

    private CsmTimeAxisView.OnCsmTimeAxisListener mListener;

    public void setListener(CsmTimeAxisView.OnCsmTimeAxisListener listener) {
        mListener = listener;
    }

    public CsmTimeAxisView getTimeAxisView() {
        return mAdapter.getTimeAxisView();
    }
}
