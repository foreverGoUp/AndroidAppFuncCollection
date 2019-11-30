package com.kc.androiddevelophelp.base.common;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 基本懒加载片段
 * 20191130 ckc
 */
public class BaseLazyFragment extends Fragment {

    private boolean mIsLog = true;
    protected final String TAG = this.getClass().getSimpleName();

    private boolean mIsPreparedForLazyLoad = false;//是否为懒加载的视图准备好了
    private boolean mIsLazyLoaded = false;//是否已经懒加载
    private boolean mIsOnResumeCallBacked = false;//是否第一次onResume()被回调过

    /**
     * 经常被回调
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mIsLog)
            Log.e(TAG, hashCode() + " setUserVisibleHint >>>>>>>>>>>" + isVisibleToUser + ">>>>>>>>>>>");
        if (isVisibleToUser && mIsLazyLoaded) {
            onUserVisibleAfterLazyLoad();
        }
        lazyLoad();
    }

    /**
     * 在onCreateView执行完后被回调
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mIsLog) Log.e(TAG, hashCode() + " onActivityCreated >>>>>>>>>>>>>>>>>>>>>>");
        mIsPreparedForLazyLoad = true;
        lazyLoad();
    }


    /**
     * 懒加载
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && mIsPreparedForLazyLoad && !mIsLazyLoaded) {
            mIsLazyLoaded = true;
            onLazyLoad();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 懒加载回调
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 当片段通过viewpager adapter展示时。
     * <p>
     * 开始懒加载
     * 子类重写改方法执行第一次加载数据
     */
    protected void onLazyLoad() {
        if (mIsLog) Log.e(TAG, hashCode() + " onLazyLoad >>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * 当片段通过viewpager adapter展示时。
     * 懒加载之后，业务若需要片段每次可见时刷新数据则重写该方法执行启动刷新
     */
    protected void onUserVisibleAfterLazyLoad() {
        if (mIsLog) Log.e(TAG, hashCode() + " onUserVisibleAfterLazyLoad >>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * 当片段通过viewpager adapter展示时。
     * 懒加载之后，业务若需要片段每次活动从后台返回前台并处于可见时刷新数据则重写该方法执行启动刷新，该方法与onUserVisibleAfterLazyLoad不会被同时回调。
     */
    protected void onResumeAfterLazyLoad() {
        if (mIsLog) Log.e(TAG, hashCode() + " onResumeAfterLazyLoad >>>>>>>>>>>>>>>>>>>>>>");
    }

    ///////////////////////////////////////////////////////////////////////////
    // 懒加载回调end
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 当片段通过viewpager adapter展示时。
     * 若
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mIsLog) Log.e(TAG, hashCode() + " onResume >>>>>>>>>>>>>>>>>>>>>>");
        if (mIsOnResumeCallBacked && getUserVisibleHint()) {
            onResumeAfterLazyLoad();
        } else {
            mIsOnResumeCallBacked = true;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mIsLog) Log.e(TAG, hashCode() + " onDestroyView >>>>>>>>>>>>>>>>>>>>>>");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mIsLog) Log.e(TAG, hashCode() + " onDetach >>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsLog) Log.e(TAG, hashCode() + " onDestroy >>>>>>>>>>>>>>>>>>>>>>");

    }
}
