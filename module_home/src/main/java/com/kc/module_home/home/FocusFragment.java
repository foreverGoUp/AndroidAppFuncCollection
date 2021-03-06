package com.kc.module_home.home;

import android.os.Bundle;

import com.kc.common_service.base.AppBaseFragment;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentFocusBinding;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;


public class FocusFragment extends AppBaseFragment<FragmentFocusBinding, BaseViewModel> {

    public FocusFragment() {
    }

    public static FocusFragment newInstance() {
        Bundle args = new Bundle();

        FocusFragment fragment = new FocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onConfigViewLayout() {
        return R.layout.fragment_focus;
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }

}
