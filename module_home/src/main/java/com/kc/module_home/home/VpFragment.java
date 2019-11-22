package com.kc.module_home.home;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.kc.common_service.base.BaseFragment;
import com.kc.common_service.base.vm.BaseViewModel;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentFindBinding;


public class VpFragment extends BaseFragment<FragmentFindBinding, BaseViewModel> {

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
