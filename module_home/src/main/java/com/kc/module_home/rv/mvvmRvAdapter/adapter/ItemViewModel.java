package com.kc.module_home.rv.mvvmRvAdapter.adapter;

import android.view.View;

import com.kc.common_service.base.adapter.BaseItemViewModel;
import com.kc.module_home.rv.mvvmRvAdapter.Commodity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

public class ItemViewModel extends BaseItemViewModel<Commodity> {

    public final ObservableField<String> mName = new ObservableField<>();
    public final ObservableField<String> mBuyNum = new ObservableField<>("0");


    @Override
    protected void setAllModel(@NonNull Commodity commodity) {
        mName.set(commodity.getName());
        //在此重新设置数据，否则列表滑动后会显示错误数据。此方法并没有用，因为Adapter有item vieW（ViewHolder）重用机制.
//        mBuyNum.set(mBuyNum.get());
        mBuyNum.set(String.valueOf(commodity.getBuyNum()));
    }


    public void clickLess(View view){
        int num = Integer.parseInt(mBuyNum.get());
        if (num > 0){
            mBuyNum.set(String.valueOf(num-1));
            mBaseModel.get().setBuyNum(num - 1);
        }
    }
    public void clickAdd(View view){
        int num = Integer.parseInt(mBuyNum.get());
        mBuyNum.set(String.valueOf(num+1));
        //保存item改变的数据到模型中，否则滑动后数据会不知所踪。
        mBaseModel.get().setBuyNum(num + 1);
    }
}
