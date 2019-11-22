package com.kc.module_home.rv.mvvmRvAdapter.adapter;

import android.view.ViewGroup;

import com.kc.common_service.base.adapter.BaseAdapter;
import com.kc.module_home.rv.mvvmRvAdapter.Commodity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;

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
