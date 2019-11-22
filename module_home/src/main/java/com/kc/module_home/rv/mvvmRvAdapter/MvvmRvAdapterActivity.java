package com.kc.module_home.rv.mvvmRvAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dovar.router_annotation.Route;
import com.kc.common_service.base.BaseActivity;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ActivityMvvmRvAdapterBinding;
import com.kc.module_home.rv.mvvmRvAdapter.adapter.Adapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

@Route(path = "rv/mvvmAdapter")
public class MvvmRvAdapterActivity extends BaseActivity<ActivityMvvmRvAdapterBinding, ViewModel> {


    @Override
    protected int getLayout() {
        return R.layout.activity_mvvm_rv_adapter;
    }

    @Override
    protected void init() {
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
}
