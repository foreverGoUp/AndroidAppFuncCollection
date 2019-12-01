package com.kc.module_home.home;

import android.os.Bundle;

import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseFragment;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentFocusBinding;


public class FocusFragment extends AppBaseFragment<FragmentFocusBinding, BaseViewModel> {

    public FocusFragment() {
        mIsLog = true;
    }

    public static FocusFragment newInstance() {
        Bundle args = new Bundle();

        FocusFragment fragment = new FocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onGetViewLayout() {
        return R.layout.fragment_focus;
    }

    @Override
    protected void initViewModelAndBindDataBinding() {

    }

}
