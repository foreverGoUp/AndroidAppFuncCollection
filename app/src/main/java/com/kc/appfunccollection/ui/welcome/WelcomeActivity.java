package com.kc.appfunccollection.ui.welcome;

import android.os.Bundle;

import com.dovar.router_api.router.DRouter;
import com.kc.appfunccollection.R;
import com.kc.appfunccollection.databinding.ActivityWelcomeBinding;
import com.kc.common_service.base.AppBaseActivity;

public class WelcomeActivity extends AppBaseActivity<ActivityWelcomeBinding, ViewModel> implements INavigator {

    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
//        BarUtils.setStatusBarLightMode(this, true);

        mViewModel = new ViewModel(this);
        mDataBinding.setVm(mViewModel);

        mViewModel.countdown(true);
    }

    @Override
    public void onClickJump() {
        // app/guide
        DRouter.navigator("home/navigation").navigateTo(this);
        finish();
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
