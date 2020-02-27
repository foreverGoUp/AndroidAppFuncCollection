package com.kc.module_home.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ckc on 2018/1/18.
 * <p>
 * 类柱状图柱状的控件
 * <p>
 * 功能：
 * -进度改变时动画变化
 * -显示当前0-100内的值
 */
public class CsmColumnProgress extends View {

    private static final String TAG = "CsmColumnProgress";
    private Paint mPaint, mTextPaint;
    private int mColorGood, mColorOk, mColorBad, mColorNone;
    private int mProgress = 50;
    private float mTextHeight = -1;

    public CsmColumnProgress(Context context) {
        this(context, null);
    }

    public CsmColumnProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CsmColumnProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mColorNone = getResources().getColor(android.R.color.darker_gray);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(30);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = 50;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = 200;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(getHeight());

        //画底部灰色
        mPaint.setColor(mColorNone);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        //画上层进度
        if (mProgress > 0) {
            if (mTextHeight == -1) {
                mTextHeight = mTextPaint.getTextSize() * 3 / 2;
            }

            mPaint.setColor(getPaintColor(mProgress));
            canvas.drawRect(0, getHeight() * (100 - mProgress) / 100, getWidth(), getHeight(), mPaint);
            canvas.drawText(String.valueOf(mProgress), getWidth() / 2, getHeight() * (100 - mProgress) / 100 + mTextHeight, mTextPaint);
        }
    }

    private int getPaintColor(int progress) {
        int green = 255 * progress / 100;
        int blue = 255 - green;
        return Color.argb(255, 0, green, blue);
    }

    public void setProgress(int progress) {
        if (progress == mProgress || progress > 100 || progress < 0) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(mProgress, progress).setDuration(500);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}
