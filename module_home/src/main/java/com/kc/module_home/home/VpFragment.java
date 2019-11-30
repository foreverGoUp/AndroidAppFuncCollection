package com.kc.module_home.home;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseFragment;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentFindBinding;


public class VpFragment extends AppBaseFragment<FragmentFindBinding, BaseViewModel> {

    private int mIndex;

    public static VpFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt("index", index);
        VpFragment fragment = new VpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_vp;
    }

    @Override
    protected void init() {
        mIndex = getArguments().getInt("index");
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        ToastUtils.showShort("片段"+mIndex+"开始懒加载");
    }
}
