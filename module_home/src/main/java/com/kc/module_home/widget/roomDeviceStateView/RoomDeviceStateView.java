package com.kc.module_home.widget.roomDeviceStateView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kc.module_home.R;
import com.kc.module_home.widget.roomDeviceStateView.bean.DeviceList;
import com.kc.module_home.widget.roomDeviceStateView.bean.DeviceType;
import com.kc.module_home.widget.roomDeviceStateView.bean.RoomInfo;
import com.kc.module_home.widget.roomDeviceStateView.tool.AppConstant;
import com.kc.module_home.widget.roomDeviceStateView.tool.DeviceTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yorun on 2017/7/19.
 * updated by ckc on 20200307.
 * 设备顺序：灯光，空调，窗帘，窗户，插座，新风，地暖，加湿器，扫地机，空气净化器，音乐播放器
 */

public class RoomDeviceStateView extends RelativeLayout {

    private final String TAG = this.getClass().getSimpleName();
    private Adapter mAdapter;
    private int mNumColumn = 4;
    private int boxSize = 160;
    private int boxMargins = 10;

    private LinearLayout mTableLinearLayout;
    private LinearLayout mYLabelLinearLayout;
    private LinearLayout mXLabelLinearLayout;
    private ImageView mXLineImageView;
    private ImageView mYLineImageView;
    private View mLeftBottomView;

    private int mBackGroundColor = getContext().getResources().getColor(R.color.colorPrimary);
    private int mOpenColor = 0xFFFFE987;//黄色
    private int mLineColor = 0xFFE1F3FF;//淡蓝色
    private final ShapeDrawable mShapeDrawable;

    List<RoomInfo> mRoomInfos = new ArrayList<>();
    List<DeviceType> mDeviceTypes = new ArrayList<>();
    List<DeviceList> mDeviceLists = new ArrayList<>();
    List<Integer> mRoomImages = new ArrayList<>();
    List<Integer> mDeviceTypeImages = new ArrayList<>();

    //存放值为"房间ID,客户端设备类型"
    private List<String> mSelectedBoxs = new ArrayList<>(0);
    private ConcurrentHashMap<String, Integer> mRoomDeviceStateMap = new ConcurrentHashMap<>();

    public RoomDeviceStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setClipChildren(false);

        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.setColorFilter(mOpenColor, PorterDuff.Mode.SRC);

        setBackgroundColor(mBackGroundColor);

        addChildViews();

        setAdapter(new Adapter() {
            @Override
            public int getRowCount() {
                return mDeviceTypes.size();
            }

            @Override
            public int getColumnCount() {
                return mRoomInfos.size();
            }

            /**
             * 返回box
             */
            @Override
            public View onTableView(ViewGroup viewGroup, final int column, final int row) {
                final RoomInfo roomInfo = mRoomInfos.get(column);
                final DeviceType deviceType = mDeviceTypes.get(row);

                View view = viewGroup.getChildAt(0);
                int deviceStateTag = ViewUtil.getDeviceStateTag(roomInfo.getRoomId(), deviceType.getClientDeviceType(), mDeviceLists);
                switch (deviceStateTag) {
                    case ViewUtil.DEVICE_STATE_EMPTY:
                        view.setBackgroundResource(R.drawable.shape_my_zone_legend_empty);
                        break;
                    case ViewUtil.DEVICE_STATE_OFF:
                        view.setBackgroundResource(R.drawable.shape_my_zone_legend_off);
                        break;
                    case ViewUtil.DEVICE_STATE_ON:
                        view.setBackgroundResource(R.drawable.shape_my_zone_legend_on);
                        break;
                }
                mRoomDeviceStateMap.put(ViewUtil.getSelectedTag(roomInfo.getRoomId(), deviceType.getClientDeviceType()), deviceStateTag);

                View selectView = viewGroup.getChildAt(1);
                selectView.setVisibility(ViewUtil.isBoxSelected(roomInfo.getRoomId(), deviceType.getClientDeviceType(), mSelectedBoxs) ? View.VISIBLE : View.GONE);

                viewGroup.setClickable(true);
                viewGroup.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewUtil.toggleSelectedBox(roomInfo.getRoomId(), deviceType.getClientDeviceType(), mDeviceLists, mSelectedBoxs);
                        refreshTableData(false, column, row);
                    }
                });
                return viewGroup;
            }

            @Override
            public View onXLabelView(ViewGroup labelView, int column) {
                final RoomInfo roomInfo = mRoomInfos.get(column);

                ImageView imageView = (ImageView) labelView.getChildAt(0);
                imageView.setBackgroundResource(mRoomImages.get(column));

                TextView tvName = (TextView) labelView.getChildAt(1);
                tvName.setText(roomInfo.getRoomName());
                //计数开启的数量
                TextView hint = (TextView) labelView.getChildAt(2);
                int onCount = ViewUtil.getDeviceOnNumber(roomInfo.getRoomId(), mDeviceTypes, mDeviceLists);
                hint.setVisibility(onCount == 0 ? View.GONE : View.VISIBLE);
                hint.setText("" + onCount);

                labelView.setClickable(true);
                labelView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewUtil.toggleSelectedBoxForRoom(roomInfo.getRoomId()
                                , mDeviceTypes
                                , mSelectedBoxs, mDeviceLists);
                        refreshTableData(true, -1, -1);
                    }
                });
                return labelView;
            }

            @Override
            public View onYLabelView(ViewGroup labelView, int row) {
                final DeviceType deviceType = mDeviceTypes.get(row);

                ImageView imageView = (ImageView) labelView.getChildAt(0);
                imageView.setBackgroundResource(mDeviceTypeImages.get(row));

                TextView tvName = (TextView) labelView.getChildAt(1);
                tvName.setText(deviceType.getName());

                //计数开启的数量
                int onCount = ViewUtil.getDeviceOnNumber(deviceType.getClientDeviceType(), mRoomInfos, mDeviceLists);
                TextView hint = (TextView) labelView.getChildAt(2);
                hint.setVisibility(onCount == 0 ? View.GONE : View.VISIBLE);
                hint.setText("" + onCount);

                labelView.setClickable(true);
                labelView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewUtil.toggleSelectedBoxForDeviceType(deviceType.getClientDeviceType()
                                , mRoomInfos
                                , mSelectedBoxs
                                , mDeviceLists);
                        refreshTableData(true, -1, -1);
                    }
                });
                return labelView;
            }
        });
    }

    private ViewGroup newTableBox() {
        RelativeLayout relativeLayout = new RelativeLayout(getContext());

        View view = new View(getContext());
        LayoutParams viewLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View selectView = new View(getContext());
        selectView.setBackgroundResource(R.drawable.shape_my_zone_selected);
        LayoutParams selectViewLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(view, viewLp);
        relativeLayout.addView(selectView, selectViewLp);
        return relativeLayout;
    }


    private void addChildViews() {
        mTableLinearLayout = new MyLinearLayout(getContext(), MyLinearLayout.SCROLL_MODE_DOUBLE);
        mTableLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mTableLinearLayout.setClipChildren(false);

        mYLabelLinearLayout = new MyLinearLayout(getContext(), MyLinearLayout.SCROLL_MODE_Y);
        mYLabelLinearLayout.setBackgroundColor(mBackGroundColor);
        mYLabelLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mYLabelLinearLayout.setId(View.generateViewId());
        mYLabelLinearLayout.setGravity(Gravity.BOTTOM);
        mYLabelLinearLayout.setClipChildren(false);

        mXLabelLinearLayout = new MyLinearLayout(getContext(), MyLinearLayout.SCROLL_MODE_X);
        mXLabelLinearLayout.setBackgroundColor(mBackGroundColor);
        mXLabelLinearLayout.setId(View.generateViewId());
        mXLabelLinearLayout.setClipChildren(false);
        mXLabelLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        mXLineImageView = new ImageView(getContext());
        mXLineImageView.setId(View.generateViewId());
        mXLineImageView.setBackgroundColor(mBackGroundColor);
        mXLineImageView.setImageDrawable(new ColorDrawable(mLineColor));

        mYLineImageView = new ImageView(getContext());
        mYLineImageView.setId(View.generateViewId());
        mYLineImageView.setBackgroundColor(mBackGroundColor);
        mYLineImageView.setImageDrawable(new ColorDrawable(mLineColor));

        mLeftBottomView = new View(getContext());
        mLeftBottomView.setBackgroundColor(mBackGroundColor);


        addView(mTableLinearLayout);
        addView(mYLabelLinearLayout);
        addView(mXLabelLinearLayout);
        addView(mXLineImageView);
        addView(mYLineImageView);
        addView(mLeftBottomView);
    }

    private void updateLayoutParams() {
        {
            LayoutParams params = new LayoutParams(boxSize, LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, boxSize + boxMargins);
            params.addRule(ALIGN_PARENT_BOTTOM);
            mYLabelLinearLayout.setLayoutParams(params);
        }

        {
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, boxSize);
            params.setMargins(boxSize + boxMargins, 0, 0, 0);
            params.addRule(ALIGN_PARENT_BOTTOM);
            mXLabelLinearLayout.setLayoutParams(params);
        }

        {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, boxMargins);//
            params.addRule(ABOVE, mXLabelLinearLayout.getId());
            mXLineImageView.setLayoutParams(params);
            mXLineImageView.setPadding(boxSize * 2 / 3, 0, 0, 0);
        }

        {
            LayoutParams params = new LayoutParams(boxMargins, LayoutParams.MATCH_PARENT);
            params.addRule(RIGHT_OF, mYLabelLinearLayout.getId());
            params.addRule(ALIGN_PARENT_BOTTOM);
            mYLineImageView.setLayoutParams(params);
            mYLineImageView.setPadding(0, 0, 0, boxSize * 2 / 3);
        }

        {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.addRule(ALIGN_PARENT_BOTTOM);
//            params.setMargins(boxSize+boxMargins, 0, 0, boxSize+boxMargins);
            mTableLinearLayout.setGravity(Gravity.BOTTOM);
            params.addRule(ABOVE, mXLineImageView.getId());
            params.addRule(RIGHT_OF, mYLineImageView.getId());
            mTableLinearLayout.setLayoutParams(params);
        }

        {
            LayoutParams params = new LayoutParams(boxSize, boxSize);
            params.addRule(ALIGN_PARENT_BOTTOM);
            mLeftBottomView.setLayoutParams(params);
        }
    }


    private void scrollLabel(int x, int y) {
        mXLabelLinearLayout.scrollTo(x, 0);
        mYLabelLinearLayout.scrollTo(0, y);
    }

    private void scrollTable(boolean isX, int x, int y) {
        if (isX) {
            mTableLinearLayout.scrollTo(x, mTableLinearLayout.getScrollY());
        } else {
            mTableLinearLayout.scrollTo(mTableLinearLayout.getScrollX(), y);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e(TAG, "onLayout changed=" + changed + ",l=" + l + ",t=" + t + ",r=" + r + ",b=" + b);
        if (changed) {
            if (getWidth() != 0) {
                boxSize = getWidth() / (mNumColumn + 1) - boxMargins;
                updateLayoutParams();
                refreshData();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure");
    }

    /**
     * 返回x、y轴的标签
     */
    private ViewGroup newLabelView() {
//        Log.e(TAG, "----------------newLabelView------------");
        RelativeLayout layout = new RelativeLayout(getContext());

        LayoutParams imgLp = new LayoutParams(boxSize / 2,
                boxSize / 2);
        imgLp.addRule(ALIGN_PARENT_TOP);
        imgLp.addRule(CENTER_HORIZONTAL);
        imgLp.setMargins(0, boxSize / 10, 0, 0);

        ImageView imageView = new ImageView(getContext());
        imageView.setId(View.generateViewId());

        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(13);
        textView.setIncludeFontPadding(false);
        textView.setId(View.generateViewId());

        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.setMargins(0, boxSize / 20, 0, 0);
        textLp.addRule(BELOW, imageView.getId());
        textLp.addRule(CENTER_HORIZONTAL);

        layout.addView(imageView, imgLp);
        layout.addView(textView, textLp);

        TextView hintTextView = new TextView(getContext());
        hintTextView.setGravity(Gravity.CENTER);
        hintTextView.setTextColor(Color.RED);
        hintTextView.setBackground(mShapeDrawable);
        hintTextView.setTextSize(10);

        LayoutParams hintLp = new LayoutParams(boxSize / 4,
                boxSize / 4);
        hintLp.addRule(ALIGN_PARENT_TOP);
        hintLp.addRule(ALIGN_PARENT_RIGHT);

        layout.addView(hintTextView, hintLp);

        return layout;
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
    }

    public void setColumnNum(int number) {
        mNumColumn = number;
        requestLayout();
    }

    public void refreshData() {
        refreshXLayoutData(true, -1);
        refreshYLayoutData(true, -1);
        refreshTableData(true, -1, -1);
    }

    public void refreshXLayoutData(boolean isAll, int changedColumn) {
        if (isAll || mXLabelLinearLayout.getChildAt(0) == null) {
            mXLabelLinearLayout.removeAllViews();

            int colCount = mAdapter.getColumnCount();
            for (int col = 0; col < colCount; col++) {
                View labelView = mAdapter.onXLabelView(newLabelView(), col);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(boxSize,
                        boxSize);
                params.setMargins(boxMargins, 0, 0, 0);
                mXLabelLinearLayout.addView(labelView, col, params);
            }
        } else {
            mAdapter.onXLabelView((ViewGroup) mXLabelLinearLayout.getChildAt(changedColumn), changedColumn);
        }
    }

    public void refreshYLayoutData(boolean isAll, int changedRow) {
        if (isAll || mYLabelLinearLayout.getChildAt(0) == null) {
            mYLabelLinearLayout.removeAllViews();

            int rowCount = mAdapter.getRowCount();
            for (int row = rowCount - 1; row >= 0; row--) {
                View labelView = mAdapter.onYLabelView(newLabelView(), row);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(boxSize,
                        boxSize);
                params.setMargins(0, 0, 0, boxMargins);
                mYLabelLinearLayout.addView(labelView, rowCount - row - 1, params);
            }
        } else {
            mAdapter.onYLabelView((ViewGroup) mYLabelLinearLayout.getChildAt(mAdapter.getRowCount() - changedRow - 1), changedRow);
        }
    }

    public void refreshTableData(boolean isAll, int changedColumn, int changedRow) {
        if (isAll || mTableLinearLayout.getChildAt(0) == null) {//全部（覆盖）刷新
            int rowCount = mAdapter.getRowCount();
            int columnCount = mAdapter.getColumnCount();

            mTableLinearLayout.removeAllViews();
            for (int column = 0; column < columnCount; column++) {
                LinearLayout columnLayout = new LinearLayout(getContext());
                columnLayout.setOrientation(LinearLayout.VERTICAL);
                columnLayout.setClipChildren(false);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(boxSize, (boxSize + boxMargins) * rowCount);
                params.setMargins(boxMargins, 0, 0, 0);

                mTableLinearLayout.addView(columnLayout, params);

                for (int row = rowCount - 1; row >= 0; row--) {
                    View view = mAdapter.onTableView(newTableBox(), column, row);
                    LinearLayout.LayoutParams columnChildParams = new LinearLayout.LayoutParams(boxSize, boxSize);
                    columnChildParams.setMargins(0, 0, 0, boxMargins);
                    columnLayout.addView(view, rowCount - row - 1, columnChildParams);
                }
            }
        } else {//部分（数据）刷新
            if (changedColumn != -1 && changedRow != -1) {
                LinearLayout columnLayout = (LinearLayout) mTableLinearLayout.getChildAt(changedColumn);
                ViewGroup columnChild = (ViewGroup) columnLayout.getChildAt(mAdapter.getRowCount() - changedRow - 1);
                mAdapter.onTableView(columnChild, changedColumn, changedRow);
            } else if (changedColumn != -1) {
                LinearLayout columnLayout = (LinearLayout) mTableLinearLayout.getChildAt(changedColumn);
                int rowCount = mAdapter.getRowCount();
                ViewGroup columnChild;
                for (int i = 0; i < rowCount; i++) {
                    columnChild = (ViewGroup) columnLayout.getChildAt(rowCount - i - 1);
                    mAdapter.onTableView(columnChild, changedColumn, changedRow);
                }
            } else {
                int columnCount = mAdapter.getColumnCount();
                LinearLayout columnLayout;
                for (int i = 0; i < columnCount; i++) {
                    columnLayout = (LinearLayout) mTableLinearLayout.getChildAt(changedColumn);
                    mAdapter.onTableView((ViewGroup) columnLayout.getChildAt(mAdapter.getRowCount() - changedRow - 1), i, changedRow);
                }
            }
        }
    }

    public void setData(List<RoomInfo> roomInfos, List<Integer> roomImages, List<DeviceType> deviceTypes, List<Integer> deviceTypeImages, List<DeviceList> deviceLists) {
        ViewUtil.convertData(roomInfos, roomImages, deviceTypes, deviceTypeImages, deviceLists);
        this.mRoomInfos = roomInfos;
        this.mRoomImages = roomImages;
        this.mDeviceTypes = deviceTypes;
        this.mDeviceTypeImages = deviceTypeImages;
        this.mDeviceLists = deviceLists;
        refreshData();
    }

    public void setRoomInfos(List<RoomInfo> roomInfos) {
        this.mRoomInfos = roomInfos;
    }

    public void setDeviceTypes(List<DeviceType> deviceTypes) {
        this.mDeviceTypes = deviceTypes;
    }

    public void setDeviceLists(List<DeviceList> deviceLists) {
        this.mDeviceLists = deviceLists;
    }

    public void setRoomImages(List<Integer> roomImages) {
        this.mRoomImages = roomImages;
    }

    public void setDeviceTypeImages(List<Integer> deviceTypeImages) {
        this.mDeviceTypeImages = deviceTypeImages;
    }

    public List<String> getSelectedBoxs() {
        return mSelectedBoxs;
    }

    public void update(DeviceList deviceList) {
//        int pos = getPosInDeviceList(deviceList.getId(), mDeviceLists);
//        if (pos == -1){
//            return;
//        }
        if (deviceList == null || !mDeviceLists.contains(deviceList)) {
            return;
        }
        String selectedTag = ViewUtil.getSelectedTag(deviceList.getRoomId(), DeviceTool.getClientDeviceType(deviceList.getType(), deviceList.getDevType()));
        Integer stateTag = mRoomDeviceStateMap.get(selectedTag);
        if (stateTag != null && stateTag != ViewUtil.DEVICE_STATE_EMPTY && deviceList.getState() != null) {
            if (stateTag == ViewUtil.DEVICE_STATE_ON && deviceList.getState().equals(AppConstant.SERVER_DEVICE_STATE_OFF)) {
                refreshData();
            } else if (stateTag == ViewUtil.DEVICE_STATE_OFF && deviceList.getState().equals(AppConstant.SERVER_DEVICE_STATE_ON)) {
                refreshData();
            }
        }
    }

    public void update(RoomInfo roomInfo) {
//        int pos = getPosInRoomInfoList(roomInfo.getRoomId(), mRoomInfos);
//        if (pos == -1){
//            return;
//        }
        if (roomInfo == null || !mRoomInfos.contains(roomInfo)) {
            return;
        }
        refreshXLayoutData(true, -1);
    }

    private static int getPosInDeviceList(String id, List<DeviceList> list) {
        int ret = -1;
        if (id == null || list == null) {
            return ret;
        }
        int size = list.size();
        DeviceList dev = null;
        for (int i = 0; i < size; i++) {
            dev = list.get(i);
            if (dev.getId().equals(id)) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    private static int getPosInRoomInfoList(Integer roomId, List<RoomInfo> list) {
        int ret = -1;
        if (roomId == null || list == null) {
            return ret;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).getRoomId() == roomId) {
                ret = i;
                break;
            }
        }
        return ret;
    }

    public interface Adapter {

        int getRowCount();

        int getColumnCount();

        View onTableView(ViewGroup viewGroup, int column, int row);

        View onXLabelView(ViewGroup labelView, int column);

        View onYLabelView(ViewGroup labelView, int row);
    }

    private class MyLinearLayout extends LinearLayout {

        int scrollModel;
        int lastX;
        int lastY;

        public static final int SCROLL_MODE_DOUBLE = 0;
        public static final int SCROLL_MODE_X = 1;
        public static final int SCROLL_MODE_Y = 2;
        private int mScrollMode = SCROLL_MODE_DOUBLE;

        public MyLinearLayout(Context context, int scrollMode) {
            super(context);
            mScrollMode = scrollMode;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = x;
                    lastY = y;
                    return false;
                case MotionEvent.ACTION_MOVE:
                    int dx = x - lastX;
                    int dy = y - lastY;
                    int absDx = Math.abs(dx);
                    int absDy = Math.abs(dy);
                    if (absDx > 20 || absDy > 20) {
                        scrollModel = absDx > absDy ? SCROLL_MODE_X : SCROLL_MODE_Y;
                        switch (mScrollMode) {
                            case SCROLL_MODE_X:
                                if (scrollModel == SCROLL_MODE_X) {
                                    return true;
                                }
                                break;
                            case SCROLL_MODE_Y:
                                if (scrollModel == SCROLL_MODE_Y) {
                                    return true;
                                }
                                break;
                            case SCROLL_MODE_DOUBLE:
                                return true;
                        }
                        return false;
                    }
                case MotionEvent.ACTION_UP:
                    return false;
            }
            return true;
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            int x = (int) ev.getX();
            int y = (int) ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    int dx = x - lastX;
                    int dy = y - lastY;

//            View childAt = mTableLinearLayout.getChildAt(0);
//            childAt.setTranslationY(dy);
                    switch (mScrollMode) {
                        case SCROLL_MODE_X:
                            if (scrollModel == SCROLL_MODE_X) {
                                this.scrollBy(-dx, 0);
                                scrollTable(true, getScrollX(), 0);
                            }
                            break;
                        case SCROLL_MODE_Y:
                            if (scrollModel == SCROLL_MODE_Y) {
                                this.scrollBy(0, -dy);
                                scrollTable(false, 0, getScrollY());
                            }
                            break;
                        case SCROLL_MODE_DOUBLE:
                            if (scrollModel == SCROLL_MODE_X) {
                                this.scrollBy(-dx, 0);
                            } else {
                                this.scrollBy(0, -dy);
                            }
                            scrollLabel(getScrollX(), getScrollY());
                            break;
                    }
                    lastX = x;
                    lastY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    scrollBox();
                    break;
            }
            return true;
        }

        private void scrollBox() {

            final int scrollX = getScrollX();
            final int scrollY = getScrollY();

            int size = boxSize + boxMargins;

            if (scrollY % size != 0) {
                final int toScrollY = getToScroll(scrollY, size, false);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(scrollY, toScrollY).setDuration(120);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        scrollTo(scrollX, animatedValue);
                        switch (mScrollMode) {
                            case SCROLL_MODE_X:

                                break;
                            case SCROLL_MODE_Y:
                                scrollTable(false, 0, getScrollY());
                                break;
                            case SCROLL_MODE_DOUBLE:
                                scrollLabel(getScrollX(), getScrollY());
                                break;
                        }
                    }
                });
                valueAnimator.start();
            }
            if (scrollX % size != 0) {
                int toScrollX = getToScroll(scrollX, size, true);
                ValueAnimator valueAnimator = ValueAnimator.ofInt(scrollX, toScrollX).setDuration(120);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        scrollTo(animatedValue, scrollY);
                        switch (mScrollMode) {
                            case SCROLL_MODE_X:
                                scrollTable(true, getScrollX(), 0);
                                break;
                            case SCROLL_MODE_Y:
                                break;
                            case SCROLL_MODE_DOUBLE:
                                scrollLabel(getScrollX(), getScrollY());
                                break;
                        }
                    }
                });
                valueAnimator.start();
                scrollTo(toScrollX, scrollY);
            }
        }

        private int getToScroll(int scroll, int size, boolean isX) {


            int tabWidth = getWidth() - boxSize - boxMargins;
            int top;
            for (int i = 0; ; i++) {
                if (mTableLinearLayout.getHeight() + i * boxSize > mAdapter.getRowCount() * (boxSize + boxMargins)) {
                    top = i * (boxSize + boxMargins);
                    break;
                }
            }

            int right;
            for (int i = 0; ; i++) {
                if (tabWidth + i * boxSize > mAdapter.getColumnCount() * (boxSize + boxMargins)) {
                    right = i * (boxSize + boxMargins);
                    break;
                }
            }

            if (isX) {
                if (scroll < 0) {
                    return 0;
                }
//                    if (scroll+tabWidth>mAdapter.getColumnCount() * (boxSize + boxMargins)) {
//                        return right;
//                    }
            } else {
                if (scroll > 0) {
                    return 0;
                }
                if (-scroll + mTableLinearLayout.getHeight() > mAdapter.getRowCount() * (boxSize + boxMargins)) {
                    return -top;
                }
            }

            int t;
            for (int i = 0; ; i++) {
                if ((scroll + i) % size == 0) {
                    t = i;
                    break;
                }
            }
            int b;
            for (int i = 0; ; i--) {
                if ((scroll + i) % size == 0) {
                    b = i;
                    break;
                }
            }
            if (Math.abs(t) < Math.abs(b)) {
                return scroll + t;
            } else {
                return scroll + b;
            }
        }
    }
}
