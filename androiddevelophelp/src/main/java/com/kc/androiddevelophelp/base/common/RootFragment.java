package com.kc.androiddevelophelp.base.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 根片段
 * 2019-12-01 ckc
 */
public abstract class RootFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();
    public static boolean DEBUG = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getView() == null) {
            if (DEBUG) Log.e(TAG, "[DEBUG] onCreateView inflate");
            View view = inflater.inflate(onGetViewLayout(), container, false);
            init(view);
            return view;
        } else {
            return getView();
        }
    }

    /**
     * 获得布局文件
     */
    protected abstract int onGetViewLayout();

    /**
     * 初始化
     */
    protected abstract void init(View view);

}
