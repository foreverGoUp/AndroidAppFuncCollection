package com.kc.module_home.home;

import android.os.Bundle;
import android.view.View;

import com.kc.common_service.base.AppBaseFragment;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentVpBinding;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;


public class VpFragment extends AppBaseFragment<FragmentVpBinding, BaseViewModel> {

    public VpFragment() {
    }

    private int mIndex;

    public static VpFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt("index", index);
        VpFragment fragment = new VpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onConfigViewLayout() {
        return R.layout.fragment_vp;
    }

    @Override
    protected void OnInit(View view) {
        super.OnInit(view);
        mIndex = getArguments().getInt("index");
        mDataBinding.tvContent.setText(String.valueOf(mIndex));
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
//        ToastUtils.showShort("片段"+mIndex+"开始懒加载");
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
