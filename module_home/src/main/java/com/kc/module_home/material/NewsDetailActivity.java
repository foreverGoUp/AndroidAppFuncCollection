package com.kc.module_home.material;

import android.os.Bundle;

import com.dovar.router_annotation.Route;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityNewsDetailBinding;

import ckc.android.develophelp.lib.base.mvvm.BaseViewModel;

@Route(path = "home/news_detail")
public class NewsDetailActivity extends AppBaseActivity<ActivityNewsDetailBinding, BaseViewModel> {

    @Override
    protected boolean onConfigStatusBarTransparent() {
        return true;
    }


    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        setSupportActionBar(mDataBinding.toolbar);
        mDataBinding.collapsingToolbarLayout.setTitle("我是一条有个性的新闻");
        mDataBinding.tvNewsContent.setText("有个性的新闻有个性的新闻有个性的新闻有个性的新闻" +
                "有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻" +
                "有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻" +
                "有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻" +
                "有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻有个性的新闻");
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
