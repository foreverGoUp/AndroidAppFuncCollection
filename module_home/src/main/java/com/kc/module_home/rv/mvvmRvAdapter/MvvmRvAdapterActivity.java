package com.kc.module_home.rv.mvvmRvAdapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.dovar.router_annotation.Route;
import com.kc.common_service.base.AppBaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityMvvmRvAdapterBinding;
import com.kc.module_home.rv.mvvmRvAdapter.adapter.Adapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


@Route(path = "rv/mvvmAdapter")
public class MvvmRvAdapterActivity extends AppBaseActivity<ActivityMvvmRvAdapterBinding, ViewModel> {


    @Override
    protected int onConfigContentViewLayout() {
        return R.layout.activity_mvvm_rv_adapter;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        mViewModel = new ViewModel();
        mDataBinding.setVm(mViewModel);

        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mDataBinding.recyclerView.setAdapter(new Adapter(mViewModel.mObservableList));

        mDataBinding.smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.refreshData();
            }
        });
        mDataBinding.smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.loadData();
            }
        });

        mViewModel.initData();
    }

    @Override
    protected void onConfigViewModelAndBindDataBinding() {

    }
}
