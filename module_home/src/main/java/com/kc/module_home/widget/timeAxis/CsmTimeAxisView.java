package com.kc.module_home.widget.timeAxis;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ckc.android.develophelp.lib.util.TimeUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ckc on 2017/9/1.
 */

public class CsmTimeAxisView extends View {

    private static final String TAG = "CsmTimeAxis";
    private final float SECOND_WIDTH = 0.08f;
    private final float DEFAULT_HEIGHT = 400;
    private float SCREEN_WIDTH;
    private final int INTERVAL_MINUTE = 10;
    private final float INTERVAL_WIDTH = INTERVAL_MINUTE * 60 * SECOND_WIDTH;
    //底部时间属性
    private int mNormalMarkTimeMarginTop = 10;
    private int mNormalMarkTimeTextSize = 26;

    private Paint mNormalMarkPaint, mNormalTimeMarkPaint, mDataMarkPaint;

    private OnCsmTimeAxisListener mListener;

    private float mLastX;
    private float mLastY;

    private Scroller mScroller = new Scroller(getContext());

    private Date mCurrentDate;

    private List<RecordFile> mTestDatas;
    private List<RecordFile> mDatas;

    private Disposable mAutoForwardDisposable;

    private float mMarkLineAreaHeight;
    //
    private boolean mEnableDraggable = true;
    //
    private boolean mIsMoving = false;

    public CsmTimeAxisView(Context context) {
        super(context);
        init(context, null);
    }

    public CsmTimeAxisView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Log.e(TAG, "init>>>>>>>>>>>>>>");
        mNormalMarkPaint = new Paint();
        mNormalMarkPaint.setAntiAlias(true);
        mNormalMarkPaint.setStyle(Paint.Style.STROKE);
//        mNormalMarkPaint.setColor(Color.parseColor("#cdcdcd"));
        mNormalMarkPaint.setColor(Color.GRAY);
        mNormalMarkPaint.setStrokeWidth(1);

        mNormalTimeMarkPaint = new Paint();
        mNormalTimeMarkPaint.setAntiAlias(true);
        mNormalTimeMarkPaint.setStyle(Paint.Style.FILL);
//        mNormalTimeMarkPaint.setColor(Color.parseColor("#cdcdcd"));
        mNormalTimeMarkPaint.setColor(Color.GRAY);
        mNormalTimeMarkPaint.setStrokeWidth(1);
        mNormalTimeMarkPaint.setTextSize(mNormalMarkTimeTextSize);
        mNormalTimeMarkPaint.setTextAlign(Paint.Align.CENTER);

        mDataMarkPaint = new Paint();
        mDataMarkPaint.setAntiAlias(true);
        mDataMarkPaint.setStyle(Paint.Style.FILL);
//        mDataMarkPaint.setColor(Color.parseColor("#cdcdcd"));
        mDataMarkPaint.setColor(Color.parseColor("#aaEC9B7A"));
        mDataMarkPaint.setStrokeWidth(1);
    }

    private void initTestDatas() {
        mTestDatas = new ArrayList<>();
        RecordFile recordFile;
        for (int i = 0; i < 11; i++) {
            recordFile = new RecordFile();
            Calendar calendar = Calendar.getInstance();
//            Log.e(TAG, "initTestDatas: calendar1=" + calendar.hashCode());
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, i * 2 + 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            recordFile.setStartTime(calendar);
            calendar = Calendar.getInstance();
//            Log.e(TAG, "initTestDatas: calendar2=" + calendar.hashCode());
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, i * 2 + 2);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            recordFile.setStopTime(calendar);
            mTestDatas.add(recordFile);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) DEFAULT_HEIGHT;
        }
        //
        SCREEN_WIDTH = widthSize;
        //
        int width = (int) (SCREEN_WIDTH + 24 * 3600 * SECOND_WIDTH);
        setMeasuredDimension(width, heightSize);
        Log.e(TAG, "onMeasure SCREEN_WIDTH=" + SCREEN_WIDTH + ",width=" + width + ",height=" + heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw");
        drawTopBottomLine(canvas);
        drawNormalMarks(canvas);
        if (mDatas == null || mDatas.size() == 0) {
            if (mTestDatas == null) initTestDatas();
            mDatas = mTestDatas;
        }
        drawDataMarks(canvas);
    }

    private void drawDataMarks(Canvas canvas) {
        canvas.save();
        final int markLineAreaHeight = getHeight() - mNormalMarkTimeMarginTop - mNormalMarkTimeTextSize - getPaddingBottom() - getPaddingTop();
        //先把坐标系移动到00:00位置
        canvas.translate(SCREEN_WIDTH / 2, markLineAreaHeight + getPaddingTop());
        int size = mDatas.size();
        RecordFile ezDeviceRecordFile;
        Path path = new Path();
        float coordinateXMoveDis;
        float dataMarkAreaWidth;
        for (int i = 0; i < size; i++) {
            path.reset();
            ezDeviceRecordFile = mDatas.get(i);
            coordinateXMoveDis = getCoordinateXMoveDis(ezDeviceRecordFile.getStartTime());
            canvas.translate(coordinateXMoveDis, 0);//坐标原点移动到某个录像方形的左下角点位
            path.moveTo(0, 0);//从左下角开始画
            path.lineTo(0, -markLineAreaHeight);//连线到左上角
            dataMarkAreaWidth = getDataMarkAreaWidth(ezDeviceRecordFile.getStartTime(), ezDeviceRecordFile.getStopTime());
            path.lineTo(dataMarkAreaWidth, -markLineAreaHeight);//连线到右上角
            path.lineTo(dataMarkAreaWidth, 0);//连线到右下角
            path.close();
            canvas.drawPath(path, mDataMarkPaint);
            //将坐标恢复到00:00位置
            canvas.translate(-coordinateXMoveDis, 0);
        }
        canvas.restore();
    }

    private float getDataMarkAreaWidth(Calendar startTime, Calendar stopTime) {
        return (stopTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000 * SECOND_WIDTH;
    }

    /**
     * 坐标都是从00:00开始向右移动
     */
    private float getCoordinateXMoveDis(Calendar startTime) {
        long startTimeMilli = startTime.getTimeInMillis();
        Date dateBegin = TimeUtils.getDateBeginOrEnd(startTime.getTime(), TimeUtils.FLAG_DAY_BEGIN);
        long dateBeginMilli = dateBegin.getTime();
        return (startTimeMilli - dateBeginMilli) / 1000 * SECOND_WIDTH;
    }

    private void drawNormalMarks(Canvas canvas) {
        int size = 24 * 60 / INTERVAL_MINUTE;//
        final int reference = 60 / INTERVAL_MINUTE;
        final int markLineAreaHeight = getHeight() - mNormalMarkTimeMarginTop - mNormalMarkTimeTextSize - getPaddingBottom() - getPaddingTop();
//        Log.e(TAG, ">>>drawNormalMarks: getPaddingBottom()=" + getPaddingBottom());
        mMarkLineAreaHeight = markLineAreaHeight;
        final int timeMarkY = mNormalMarkTimeMarginTop + mNormalMarkTimeTextSize;
        final int hourMarkHeight = markLineAreaHeight;
        final int intervalMarkHeight = markLineAreaHeight * 1 / 3;
        final int halfHourMarkHeight = markLineAreaHeight * 2 / 3;
        canvas.save();
        canvas.translate(SCREEN_WIDTH / 2, markLineAreaHeight + getPaddingTop());
        //画00:00刻度线
        canvas.drawLine(0, 0, 0, -hourMarkHeight, mNormalMarkPaint);
        canvas.drawText(getHourString(0, false), 0, timeMarkY, mNormalTimeMarkPaint);
//        float markMoveDis = 0;
//        final float halfScreenW = SCREEN_WIDTH/2;
        for (int i = 0; i < size; i++) {
            canvas.translate(INTERVAL_WIDTH, 0);
            if ((i + 1) % reference == 0) {//画小时刻度
                canvas.drawLine(0, 0, 0, -hourMarkHeight, mNormalMarkPaint);
                canvas.drawText(getHourString((i + 1) / reference, false), 0, timeMarkY, mNormalTimeMarkPaint);
            } else if ((i + 1) % reference == reference / 2) {//画半小时刻度
                canvas.drawLine(0, 0, 0, -halfHourMarkHeight, mNormalMarkPaint);
                canvas.drawText(getHourString((i + 1) / reference, true), 0, timeMarkY, mNormalTimeMarkPaint);
            } else {
                canvas.drawLine(0, 0, 0, -intervalMarkHeight, mNormalMarkPaint);
            }
        }
        canvas.restore();
    }

    /**
     * 例子：传入1，返回：01:00
     */
    private static String getHourString(int hour, boolean halfHour) {
        if (hour < 10) {
            StringBuffer sb = new StringBuffer().append("0").append(hour);
            if (halfHour) {
                sb.append(":30");
            } else {
                sb.append(":00");
            }
            return sb.toString();
        } else {
            StringBuffer sb = new StringBuffer().append(hour);
            if (halfHour) {
                sb.append(":30");
            } else {
                sb.append(":00");
            }
            return sb.toString();
        }
    }

    private void drawTopBottomLine(Canvas canvas) {
//        canvas.drawLine(0, getPaddingTop(), getWidth(), getPaddingTop(), mNormalMarkPaint);
        int y = getHeight() - mNormalMarkTimeMarginTop - mNormalMarkTimeTextSize - getPaddingBottom();
        mNormalMarkPaint.setColor(Color.RED);
        canvas.drawLine(0, y, getWidth(), y, mNormalMarkPaint);
        mNormalMarkPaint.setColor(Color.GRAY);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            callbackTime(true);
            invalidate();
        }
    }

    private boolean mRequestDisallowInterceptTouchEvent;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean consume = true;
        int scrollX = getScrollX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mEnableDraggable) {
                    consume = false;
                    break;
                }
                float x = event.getRawX();
                float y = event.getRawY();
                int dx = (int) (x - mLastX);
                if (!mRequestDisallowInterceptTouchEvent) {
                    int dy = (int) (y - mLastY);
                    if (Math.abs(dx) >= Math.abs(dy)) {
                        mRequestDisallowInterceptTouchEvent = true;
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        consume = false;
                        break;
                    }
                }

//                Log.d(TAG, ">>>>>>>>>>>>>>>>>onTouchEvent: 时间轴处理移动事件");
                //
                if (!mIsMoving) {
                    callbackMoveStart();
                }
                //标志为正在移动
                mIsMoving = true;

//                if (getScrollX() <= 0 && dx > 0) {//在最左边且向右滑
//                    consume = false;
//                    break;
//                } else if (getScrollX() >= getWidth() - SCREEN_WIDTH && dx < 0) {//在最右边且向左滑
//                    consume = false;
//                    break;
//                }

                //停止自动前进
                stopAutoForward();
                //
                scrollBy(-dx, 0);
                //回调时间
                if (scrollX >= 0 && scrollX <= getWidth() - SCREEN_WIDTH) {
                    callbackTime(true);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mRequestDisallowInterceptTouchEvent = false;

                if (scrollX < 0) {
                    scrollTo(0, 0);
                } else if (scrollX > getWidth() - SCREEN_WIDTH) {
                    scrollTo((int) (getWidth() - SCREEN_WIDTH), 0);
                }
                if (CsmTimeAxisLayout.DEBUG) Log.e(TAG, "onTouchEvent 手指抬起 scrollX=" + scrollX);
                //回调时间
                callbackTime(false);
                //标志为不在移动
                mIsMoving = false;
                break;
        }
        return consume;
    }

    private void callbackTimeBeginOrEnd(boolean isBegin) {
        Date date;
        long timeMilli;
        if (isBegin) {
            date = TimeUtils.getDateBeginOrEnd(getCurrentTime(), TimeUtils.FLAG_DAY_BEGIN);
        } else {
            date = TimeUtils.getDateBeginOrEnd(getCurrentTime(), TimeUtils.FLAG_DAY_END);
        }
        timeMilli = date.getTime();
        if (mListener != null) {
            mListener.onTimeAxisStop(timeMilli);
        }
    }

    private void callbackMoveStart() {
        if (CsmTimeAxisLayout.DEBUG) Log.d(TAG, "callbackMoveStart");
        if (mListener != null) {
            mListener.onTimeAxisMoveStart();
        }
    }

    private void callbackTime(boolean isMoving) {
        Date dateBegin = TimeUtils.getDateBeginOrEnd(getCurrentTime(), TimeUtils.FLAG_DAY_BEGIN);
        long timeBeginMilli = dateBegin.getTime();
        int scrollX = getScrollX();
        int intervalSeconds = (int) (scrollX / SECOND_WIDTH);
        long timeMilli = timeBeginMilli + intervalSeconds * 1000;
        if (CsmTimeAxisLayout.DEBUG)
            Log.e(TAG, "callbackTime isMoving=" + isMoving + ",timeMilli=" + TimeUtils.getDefaultDateTime(timeMilli));

        if (isMoving) {
            if (mListener != null) mListener.onTimeAxisMove(timeMilli);
        } else {
            mCurrentDate = new Date(timeMilli);
            startAutoForward();
        }
    }

    public void setListener(OnCsmTimeAxisListener listener) {
        mListener = listener;
    }

    public interface OnCsmTimeAxisListener {

        void onTimeAxisMoveStart();

        void onTimeAxisMove(long timeMilli);

        void onTimeAxisStop(long timeMilli);
    }

    private Date getCurrentTime() {
        if (mCurrentDate == null) {
            mCurrentDate = new Date();
        }
        return mCurrentDate;
    }

    public void startAutoForward() {
        stopAutoForward();
//        if (mAutoForwardDisposable != null && !mAutoForwardDisposable.isDisposed()) {
//            return;
//        }
        Log.e(TAG, "startAutoForward: ！！！");
        mAutoForwardDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "时间轴正在自动前进...");
                        long timeMilli = getCurrentTime().getTime();
                        if (!isBelongTo(timeMilli, getNextRecordFileToPlay(timeMilli, mDatas))) {
                            timeMilli = getPlayStartTime(timeMilli, getNextRecordFileToPlay(timeMilli, mDatas)).getTimeInMillis();
                        }

                        setCurrentTime(timeMilli + 1000);
                    }
                });
    }

    public void stopAutoForward() {
        if (mAutoForwardDisposable != null && !mAutoForwardDisposable.isDisposed()) {
            Log.e(TAG, "stopAutoForward: ！！！");
            mAutoForwardDisposable.dispose();
            mAutoForwardDisposable = null;
        }
    }

    public void setData(List<RecordFile> datas) {
        Log.e(TAG, "setData: ");
        mDatas = datas;
        if (mDatas != null && mDatas.size() > 0) {
            invalidate();
        }
    }

    public void setCurrentTime(Calendar calendar) {
        setCurrentTime(calendar.getTime());
    }

    public void setCurrentTime(Date date) {
        setCurrentTime(date.getTime());
    }

    public void setCurrentTime(long timeMilli) {
        if (mIsMoving) {
            Log.d(TAG, ">>>>>>>>>>>>>>>>>setCurrentTime: 时间轴正在手动移动，取消被动移动");
            return;
        }
        Date date = new Date(timeMilli);
        mCurrentDate = date;

        Date dateBegin = TimeUtils.getDateBeginOrEnd(date, TimeUtils.FLAG_DAY_BEGIN);
        long timeBeginMilli = dateBegin.getTime();
        float finalScrollX = (timeMilli - timeBeginMilli) / 1000 * SECOND_WIDTH;
        int dx = (int) (finalScrollX - getScrollX());
        if (Math.abs(dx) < 1) {//大于一个像素才移动
            Log.e(TAG, ">>>setCurrentTime: 准备移动距离:" + dx);
            mListener.onTimeAxisMove(timeMilli);
            return;
        }
        mScroller.startScroll(getScrollX(), getScrollY(), dx, 0);
        invalidate();
    }

    public float getMarkLineAreaHeight() {
        return mMarkLineAreaHeight;
    }

    /**
     * x轴方向红线到view底部的高度
     */
    public float getOffsetBottomHeight() {
        return getPaddingBottom() + mNormalMarkTimeMarginTop + mNormalMarkTimeTextSize;
    }

    /**
     * 启用可拖动时间轴
     */
    public void enableDraggable(boolean enable) {
        mEnableDraggable = enable;
    }


    /**
     * 在录像文件列表中查找指定时间所属的录像文件或下一个录像文件，未找到则返回第一个录像文件。
     */
    public static RecordFile getNextRecordFileToPlay(long specificTimeMillis, List<RecordFile> list) {
        Log.d(TAG, "getNextRecordFileToPlay: 传入日期：" + TimeUtils.getDefaultDate(specificTimeMillis));
        if (list == null || list.size() == 0) {
            return null;
        }
        int size = list.size();
        RecordFile ezDeviceRecordFile;
        for (int i = 0; i < size; i++) {
            ezDeviceRecordFile = list.get(i);
            if (specificTimeMillis < ezDeviceRecordFile.getStartTime().getTimeInMillis()
                    || isBelongTo(specificTimeMillis, ezDeviceRecordFile)) {
                Log.d(TAG, "getNextRecordFileToPlay: 得到录像文件：" + TimeUtils.getDefaultDateTime(ezDeviceRecordFile.getStartTime().getTimeInMillis())
                        + "--->" + TimeUtils.getDefaultDateTime(ezDeviceRecordFile.getStopTime().getTimeInMillis()));
                return ezDeviceRecordFile;
            }
        }
        Log.e(TAG, "getNextRecordFileToPlay: 查找返回第一个录像文件");
        //返回第一个录像文件
        return list.get(0);
    }

    /**
     * @param ezDeviceRecordFile 根据specificTimeMillis查找到的录像文件
     */
    public static Calendar getPlayStartTime(long specificTimeMillis, RecordFile ezDeviceRecordFile) {
        if (isBelongTo(specificTimeMillis, ezDeviceRecordFile)) {
            Calendar startTime = Calendar.getInstance();
            startTime.setTimeInMillis(specificTimeMillis);
            return startTime;
        } else {
            return ezDeviceRecordFile.getStartTime();
        }
    }


    public static boolean isBelongTo(long specificTimeMillis, RecordFile ezDeviceRecordFile) {
        if (ezDeviceRecordFile == null) {
            return false;
        }
        if (specificTimeMillis >= ezDeviceRecordFile.getStartTime().getTimeInMillis()
                && specificTimeMillis < ezDeviceRecordFile.getStopTime().getTimeInMillis()) {
            return true;
        }
        return false;
    }


}
