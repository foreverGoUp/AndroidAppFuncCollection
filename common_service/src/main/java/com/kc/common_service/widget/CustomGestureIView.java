package com.kc.common_service.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomGestureIView extends AppCompatImageView {

    private OnCustomGestureIViewListener mListener;

    public CustomGestureIView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }


    @SuppressLint("NewApi")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mListener != null) {
                    mListener.onGestureDown(this);

                }
//                this.setBackground(getResources().getDrawable(R.drawable.bg_circle_press));//变成压下背景色
                return true;
            case MotionEvent.ACTION_UP:
                if (mListener != null) {
                    mListener.onGestureUp();

                }
//                this.setBackground(getResources().getDrawable(R.drawable.bg_circle));//恢复背景色
                break;

            default:
                break;
        }
//        Log.e("**(*&(*&&*", "super.onTouchEvent(event)="+super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

    public void setGestureListener(OnCustomGestureIViewListener listener) {
        this.mListener = listener;
    }

    public interface OnCustomGestureIViewListener {
        void onGestureDown(View view);

        void onGestureUp();

    }

}
