package com.kc.module_home.rv.mvvmRvAdapter.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.kc.common_service.base.adapter.BaseViewHolder;
import com.kc.module_home.R;
import com.kc.module_home.databinding.ItemCommodityBinding;

public class ViewHolder extends BaseViewHolder<ItemCommodityBinding, ItemViewModel> {

    public ViewHolder(@NonNull ViewGroup parent) {
        super(parent, R.layout.item_commodity);
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ItemViewModel();
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setVm(mViewModel);
    }

    @Override
    protected void init() {

    }
}
