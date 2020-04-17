package com.kc.common_service.widget.timeAxis;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/9/2.
 * 时间轴布局控件
 * 该控件主要实现添加时间轴中间红线。
 */
public class CsmTimeAxisLayout extends RelativeLayout {

    public static boolean DEBUG = true;
    private final String TAG = this.getClass().getSimpleName();

    public CsmTimeAxisLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                View child;
//                for (int i = 0; i < getChildCount(); i++) {
//                    child = getChildAt(i);
//                    if (child instanceof CsmTimeAxisView){
//                    }
//                }
//
//                getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });

        post(new Runnable() {
            @Override
            public void run() {
                View child;
                for (int i = 0; i < getChildCount(); i++) {
                    child = getChildAt(i);
                    if (child instanceof CsmTimeAxisView) {
                        CsmTimeAxisView csmTimeAxisView = (CsmTimeAxisView) child;
                        View indicatorView = new View(getContext());
                        indicatorView.setBackgroundColor(Color.RED);
                        LayoutParams layoutParams = new LayoutParams(2, csmTimeAxisView.getDataMarkHeight());
                        layoutParams.addRule(CENTER_HORIZONTAL);
                        addView(indicatorView, layoutParams);
                    }
                }
            }
        });
    }


}
