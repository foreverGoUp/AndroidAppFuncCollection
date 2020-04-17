package com.kc.common_service.widget.timeAxis;

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
 * 时间轴控件
 *
 * 功能:
 * 时间范围：00：00-23：59：59
 * 小间隔：10分钟，中间隔：30分钟，大间隔：1小时
 * 手指横向滑动指定播放时间
 * 当播放时间未找到录像文件，则自动播放下一个录像文件，当到最后一个录像文件播放完成后，自动播放第一个录像文件。
 * 调用startAutoForward和stopAutoForward方法可开始/停止按1s前进。
 * setData方法设置录像文件列表
 * setListener方法可监听时间改变
 * setCurrentTime方法改变开始播放的时间
 * enableDraggable方法控制是否可以手指滑动
 */

public class CsmTimeAxisView extends View {

    private static final String TAG = "CsmTimeAxis";
    private float mParentGiveWidth = -1;

    private float mOneSecondWidth = 0.08f;
    private final int mIntervalMinutes = 10;
    private int mTimeTextMarginTop = 10;
    private int mTimeTextSize = 26;

    private float mIntervalWidth;

    private Paint mMarkLinePaint, mTimeTextPaint, mDataMarkPaint;

    private OnCsmTimeAxisListener mListener;

    private float mLastX;
    private float mLastY;

    private Scroller mScroller = new Scroller(getContext());

    private Date mCurrentDate;

    private List<RecordFile> mTestData;
    private List<RecordFile> mData;

    private Disposable mAutoForwardDisposable;

    //
    private boolean mEnableDraggable = true;
    //
    private boolean mIsMoving = false;

    public CsmTimeAxisView(Context context) {
        this(context, null);
    }

    public CsmTimeAxisView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mIntervalWidth = mIntervalMinutes * 60 * mOneSecondWidth;

        mMarkLinePaint = new Paint();
        mMarkLinePaint.setAntiAlias(true);
        mMarkLinePaint.setStyle(Paint.Style.STROKE);
        mMarkLinePaint.setColor(Color.GRAY);
        mMarkLinePaint.setStrokeWidth(1);

        mTimeTextPaint = new Paint();
        mTimeTextPaint.setAntiAlias(true);
        mTimeTextPaint.setStyle(Paint.Style.FILL);
        mTimeTextPaint.setColor(Color.GRAY);
        mTimeTextPaint.setStrokeWidth(1);
        mTimeTextPaint.setTextSize(mTimeTextSize);
        mTimeTextPaint.setTextAlign(Paint.Align.CENTER);

        mDataMarkPaint = new Paint();
        mDataMarkPaint.setAntiAlias(true);
        mDataMarkPaint.setStyle(Paint.Style.FILL);
        mDataMarkPaint.setColor(Color.parseColor("#aaEC9B7A"));
        mDataMarkPaint.setStrokeWidth(1);
    }

    private void initTestData() {
        mTestData = new ArrayList<>();
        RecordFile recordFile;
        for (int i = 0; i < 11; i++) {
            recordFile = new RecordFile();
            Calendar calendar = Calendar.getInstance();
//            Log.e(TAG, "initTestData: calendar1=" + calendar.hashCode());
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, i * 2 + 1);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            recordFile.setStartTime(calendar);
            calendar = Calendar.getInstance();
//            Log.e(TAG, "initTestData: calendar2=" + calendar.hashCode());
            calendar.setTime(new Date());
            if (i == 10) {
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
            } else {
                calendar.set(Calendar.HOUR_OF_DAY, i * 2 + 2);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
            }
            recordFile.setStopTime(calendar);
            mTestData.add(recordFile);
        }
    }

    //当子view设置的宽度超出父view提供的。会回调4次，giveWidth = 600（give）,7512(set),600,7512
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int giveWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int giveWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int giveHeight = MeasureSpec.getSize(heightMeasureSpec);
        int giveHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        Log.e(TAG, "onMeasure widthSize=" + giveWidth + ",widthMode=" + giveWidthMode + ",heightSize=" + giveHeight + ",heightMode=" + giveHeightMode);
        //
        if (mParentGiveWidth == -1 || mParentGiveWidth == giveWidth) {
            mParentGiveWidth = giveWidth;
            //计算自己需要的宽度
            int width = (int) (mParentGiveWidth + 24 * 3600 * mOneSecondWidth);

            setMeasuredDimension(width, giveHeight);
        } else {
            setMeasuredDimension(giveWidth, giveHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw");
        drawMarkLine(canvas);
        if (mData == null || mData.size() == 0) {
            if (mTestData == null) initTestData();
            mData = mTestData;
        }
        drawDataMarkArea(canvas);
    }

    private void drawDataMarkArea(Canvas canvas) {
        canvas.save();
        final int height = getDataMarkHeight();
        //先把坐标系移动到00:00位置
        canvas.translate(mParentGiveWidth / 2, getPaddingTop() + height);
        int areaCount = mData.size();
        RecordFile ezDeviceRecordFile;
        Path path = new Path();
        float coordinateXMoveDis;
        float areaWidth;
        for (int i = 0; i < areaCount; i++) {
            path.reset();
            ezDeviceRecordFile = mData.get(i);
            coordinateXMoveDis = getCoordinateXMoveDis(ezDeviceRecordFile.getStartTime());
            //将坐标系原点移动到某个录像方形的左下角点位
            canvas.translate(coordinateXMoveDis, 0);
            path.moveTo(0, 0);//从左下角开始画
            path.lineTo(0, -height);//连线到左上角
            areaWidth = getDataMarkAreaWidth(ezDeviceRecordFile.getStartTime(), ezDeviceRecordFile.getStopTime());
            path.lineTo(areaWidth, -height);//连线到右上角
            path.lineTo(areaWidth, 0);//连线到右下角
            path.close();
            canvas.drawPath(path, mDataMarkPaint);
            //将坐标系恢复原位
            canvas.translate(-coordinateXMoveDis, 0);
        }
        canvas.restore();
    }

    private float getDataMarkAreaWidth(Calendar startTime, Calendar stopTime) {
        return (stopTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000 * mOneSecondWidth;
    }

    public int getDataMarkHeight() {
        return getHeight() - mTimeTextMarginTop - mTimeTextSize - getPaddingBottom() - getPaddingTop();
    }

    /**
     * 坐标都是从00:00开始向右移动
     */
    private float getCoordinateXMoveDis(Calendar startTime) {
        long startTimeMilli = startTime.getTimeInMillis();
        Date dateBegin = TimeUtils.getDateBeginOrEnd(startTime.getTime(), TimeUtils.FLAG_DAY_BEGIN);
        long dateBeginMilli = dateBegin.getTime();
        return (startTimeMilli - dateBeginMilli) / 1000 * mOneSecondWidth;
    }

    private void drawMarkLine(Canvas canvas) {
        //底部记号线
        int y = getHeight() - mTimeTextMarginTop - mTimeTextSize - getPaddingBottom();
        mMarkLinePaint.setColor(Color.RED);
        canvas.drawLine(0, y, getWidth(), y, mMarkLinePaint);

        //垂直记号线
        mMarkLinePaint.setColor(Color.GRAY);

        int intervalCount = 24 * 60 / mIntervalMinutes;
        final int hourMarkLineIndex = 60 / mIntervalMinutes;
        final int timeMarkHeight = mTimeTextMarginTop + mTimeTextSize;
        final int hourMarkLineHeight = getDataMarkHeight();
        ;
        final int intervalMarkLineHeight = hourMarkLineHeight * 1 / 3;
        final int halfHourMarkLineHeight = hourMarkLineHeight * 2 / 3;
        canvas.save();
        canvas.translate(mParentGiveWidth / 2, getPaddingTop() + hourMarkLineHeight);
        //画00:00标记线
        canvas.drawLine(0, 0, 0, -hourMarkLineHeight, mMarkLinePaint);
        canvas.drawText(getHourString(0, false), 0, timeMarkHeight, mTimeTextPaint);
        //画后面的标记线
        for (int i = 0; i < intervalCount; i++) {
            canvas.translate(mIntervalWidth, 0);
            if ((i + 1) % hourMarkLineIndex == 0) {//画小时刻度
                canvas.drawLine(0, 0, 0, -hourMarkLineHeight, mMarkLinePaint);
                canvas.drawText(getHourString((i + 1) / hourMarkLineIndex, false), 0, timeMarkHeight, mTimeTextPaint);
            } else if ((i + 1) % hourMarkLineIndex == hourMarkLineIndex / 2) {//画半小时刻度
                canvas.drawLine(0, 0, 0, -halfHourMarkLineHeight, mMarkLinePaint);
                canvas.drawText(getHourString((i + 1) / hourMarkLineIndex, true), 0, timeMarkHeight, mTimeTextPaint);
            } else {
                canvas.drawLine(0, 0, 0, -intervalMarkLineHeight, mMarkLinePaint);
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
                if (CsmTimeAxisLayout.DEBUG) Log.e(TAG, "onTouchEvent 手指按下");

                if (!mEnableDraggable) {
                    consume = false;
                    break;
                }

                mLastX = event.getRawX();
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
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

                //停止自动前进
                stopAutoForward();
                //
                scrollBy(-dx, 0);//同步执行
                //回调时间
                scrollX = getScrollX();
                if (scrollX >= 0 && scrollX < getWidth() - mParentGiveWidth) {
                    callbackTime(true);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mRequestDisallowInterceptTouchEvent = false;

                if (scrollX < 0) {
                    scrollTo(0, 0);
                } else if (scrollX > getWidth() - mParentGiveWidth) {
                    scrollTo((int) (getWidth() - mParentGiveWidth), 0);
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
        int intervalSeconds = (int) (scrollX / mOneSecondWidth);
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
                        if (!isBelongTo(timeMilli, getNextRecordFileToPlay(timeMilli, mData))) {
                            timeMilli = getPlayStartTime(timeMilli, getNextRecordFileToPlay(timeMilli, mData)).getTimeInMillis();
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
        mData = datas;
        if (mData != null && mData.size() > 0) {
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
        float finalScrollX = (timeMilli - timeBeginMilli) / 1000 * mOneSecondWidth;
        int dx = (int) (finalScrollX - getScrollX());
        if (Math.abs(dx) < 1) {//大于一个像素才移动
            Log.e(TAG, ">>>setCurrentTime: 准备移动距离:" + dx);
            mListener.onTimeAxisMove(timeMilli);
            return;
        }
        mScroller.startScroll(getScrollX(), getScrollY(), dx, 0);
        invalidate();
    }

    /**
     * x轴方向红线到view底部的高度
     */
    public float getOffsetBottomHeight() {
        return getPaddingBottom() + mTimeTextMarginTop + mTimeTextSize;
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
