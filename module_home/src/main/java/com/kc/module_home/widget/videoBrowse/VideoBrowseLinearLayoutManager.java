package com.kc.module_home.widget.videoBrowse;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class VideoBrowseLinearLayoutManager extends LinearLayoutManager {

    private float mScrollRatio = 1f;


    public VideoBrowseLinearLayoutManager(Context context) {
        super(context);
    }

    public VideoBrowseLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public VideoBrowseLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
//        super.smoothScrollToPosition(recyclerView, state, position);
        CenterSmoothScroller scroller = new CenterSmoothScroller(recyclerView.getContext());
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }

    /**
     * 控制水平滚动的速度
     * <p>
     * 被RecyclerView调用
     * <p>
     * 缺点：smoothScrollToPosition方法可能也会调用该方法，所以会影响smoothScrollToPosition的正常使用。
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy((int) (dx * mScrollRatio), recycler, state);
    }

    /**
     * 控制垂直滚动的速度
     * <p>
     * 被RecyclerView调用
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy((int) (dy * mScrollRatio), recycler, state);
    }

}
