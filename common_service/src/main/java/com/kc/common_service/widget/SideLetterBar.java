package com.kc.common_service.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 联系人侧边栏索引器View
 * Created by yunzhao.liu on 2017/11/11
 * <p>
 * 使用：
 * <TextView
 * android:id="@+id/tv_overlay"
 * android:layout_width="80dp"
 * android:layout_height="80dp"
 * android:layout_centerInParent="true"
 * android:background="@drawable/overlay_bg"
 * android:gravity="center"
 * android:textColor="@android:color/white"
 * android:textSize="46sp"
 * android:visibility="gone"
 * tools:visibility="visible" />
 * <p>
 * <com.hexagon.funny.widget.SideLetterBar
 * android:id="@+id/side_bar"
 * android:layout_width="30dp"
 * android:layout_height="match_parent"
 * android:layout_alignParentRight="true"
 * android:visibility="visible" />
 * <p>
 * sideLetterBar.setOverlay(tvOverlay);
 * sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
 *
 * @Override public void onLetterChanged(String letter) {
 * scrollToLetter(letter);
 * }
 * });
 */
public class SideLetterBar extends View {
    private static final String[] b = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint paint = new Paint();
    private OnLetterChangedListener onLetterChangedListener;
    private TextView overlay;

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideLetterBar(Context context) {
        super(context);
    }

    /**
     * 设置悬浮的textview
     */
    public void setOverlay(TextView overlay) {
        this.overlay = overlay;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //使最后一个索引和底部有些距离
        int height = getHeight() - 15;
        int width = getWidth();
        int singleHeight = height / b.length;
        for (int i = 0; i < b.length; i++) {
            paint.setTextSize(20);
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            if (i == choose) {
                paint.setColor(Color.RED);
                paint.setFakeBoldText(true);  //加粗
            }
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = onLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
//                setBackgroundResource(R.drawable.sidebar_up_bg);
                choose = -1;
                invalidate();
                if (overlay != null) {
                    overlay.setVisibility(GONE);
                }
                break;
            default:
//                setBackgroundResource(R.drawable.sidebar_down_bg);
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(b[c]);
                        }
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }
}
