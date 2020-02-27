package com.kc.module_home.widget.horizontalScrollScaleGallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 控制fling速度的RecyclerView
 * <p>
 * Created by jameson on 9/1/16.
 */
public class FlingSpeedRecyclerView extends RecyclerView {

    private final String TAG = this.getClass().getSimpleName();
    public static boolean DEBUG = true;
    private float mFlingRatio = 0.5f; // 惯性滑动比例
    private int mFlingMaxVelocity = 2000; // 最大顺时滑动速度

    public FlingSpeedRecyclerView(Context context) {
        super(context);
    }

    public FlingSpeedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlingSpeedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
//        velocityX = solveVelocity(velocityX);
//        velocityY = solveVelocity(velocityY);
        if (DEBUG)
            Log.e(TAG, "fling velocityX=" + velocityX + ",velocityY=" + velocityY);
        velocityX *= mFlingRatio;
        velocityY *= mFlingRatio;
        return super.fling(velocityX, velocityY);
    }

    private int solveVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, mFlingMaxVelocity);
        } else {
            return Math.max(velocity, -mFlingMaxVelocity);
        }
    }

    public void setFlingRatio(float flingRatio) {
        mFlingRatio = flingRatio;
    }
}
