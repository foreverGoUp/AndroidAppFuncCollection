package com.kc.module_home.home;

import android.os.Bundle;

import com.kc.common_service.base.AppBaseFragment;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentFindBinding;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;


public class FindFragment extends AppBaseFragment<FragmentFindBinding, BaseViewModel> {

    public FindFragment() {
    }

    public static FindFragment newInstance() {
        Bundle args = new Bundle();

        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onConfigViewLayout() {
        return R.layout.fragment_find;
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
