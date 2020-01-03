package com.kc.module_home.rv.mvvmRvAdapter.adapter;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.kc.common_service.base.adapter.BaseAdapter;
import com.kc.module_home.rv.mvvmRvAdapter.Commodity;


public class Adapter extends BaseAdapter<ViewHolder> {

    public Adapter(@NonNull ObservableList<Object> dataList) {
        super(dataList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getViewModel().setBaseModel((Commodity) mDataList.get(position));
    }
}
