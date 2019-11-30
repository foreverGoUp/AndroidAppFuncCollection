package com.kc.module_home.rv.mvvmRvAdapter;

import com.blankj.utilcode.util.ToastUtils;
import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.util.RandomUtils;
import com.kc.common_service.util.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ViewModel extends BaseViewModel {

    final ObservableList<Object> mObservableList = new ObservableArrayList<>();

    public final ObservableInt mRefreshOrLoadState = new ObservableInt();

    public static final int REFRESH_FINISH = 0;
    public static final int REFRESH_FAIL = 1;
    public static final int REFRESH_FINISH_WITH_NO_MORE_DATA = 2;
    public static final int LOAD_FINISH = 10;
    public static final int LOAD_FAIL = 11;
    public static final int LOAD_FINISH_WITH_NO_MORE_DATA = 12;


    public void initData(){
        Observable.timer(3, TimeUnit.SECONDS)
                .compose(RxUtils.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        List<Commodity> data = new ArrayList<>();
                        for (int i = 0; i < 20; i++) {
                            data.add(new Commodity("商品"+(i+1)));
                        }
                        mObservableList.addAll(data);
                    }
                });
    }

    public void refreshData(){
        Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxUtils.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        List<Commodity> data = new ArrayList<>();
                        int size = RandomUtils.nextInt(9, 10);
                        for (int i = 0; i < size; i++) {
                            data.add(new Commodity(size+" 商品"+(i+1)));
                        }
                        mObservableList.clear();
                        mObservableList.addAll(data);
                        ToastUtils.showShort("刷新完条数："+size);

                        if (size != 10){
                            //可观察者变量值赋值相同时不会触发通知改变
                            if (mRefreshOrLoadState.get() != REFRESH_FINISH_WITH_NO_MORE_DATA){
                                mRefreshOrLoadState.set(REFRESH_FINISH_WITH_NO_MORE_DATA);
                            }else {
                                mRefreshOrLoadState.notifyChange();
                            }
                        }else {
                            //可观察者变量值赋值相同时不会触发通知改变
                            if (mRefreshOrLoadState.get() != REFRESH_FINISH){
                                mRefreshOrLoadState.set(REFRESH_FINISH);
                            }else {
                                mRefreshOrLoadState.notifyChange();
                            }
                        }
                    }
                });
    }

    public void loadData(){
        Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxUtils.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        List<Commodity> data = new ArrayList<>();
                        int size = RandomUtils.nextInt(9, 10);
                        for (int i = 0; i < size; i++) {
                            data.add(new Commodity(size+" 新商品"+(i+1)));
                        }
                        mObservableList.addAll(data);
                        ToastUtils.showShort("加载条数："+size);

                        if (size != 10){
                            //可观察者变量值赋值相同时不会触发通知改变
                            if (mRefreshOrLoadState.get() != LOAD_FINISH_WITH_NO_MORE_DATA){
                                mRefreshOrLoadState.set(LOAD_FINISH_WITH_NO_MORE_DATA);
                            }else {
                                mRefreshOrLoadState.notifyChange();
                            }
                        }else {
                            //可观察者变量值赋值相同时不会触发通知改变
                            if (mRefreshOrLoadState.get() != LOAD_FINISH){
                                mRefreshOrLoadState.set(LOAD_FINISH);
                            }else {
                                mRefreshOrLoadState.notifyChange();
                            }
                        }
                    }
                });
    }

    @BindingAdapter("refreshOrLoadState")
    public static void refreshOrLoadFinish(SmartRefreshLayout smartRefreshLayout, int state){
        switch (state){
            case REFRESH_FINISH:
                smartRefreshLayout.finishRefresh();
                break;
            case REFRESH_FAIL:
                smartRefreshLayout.finishRefresh(false);
                break;
            case REFRESH_FINISH_WITH_NO_MORE_DATA:
                smartRefreshLayout.finishRefreshWithNoMoreData();
                break;
            case LOAD_FINISH:
                smartRefreshLayout.finishLoadMore();
                break;
            case LOAD_FAIL:
                smartRefreshLayout.finishLoadMore(false);
                break;
            case LOAD_FINISH_WITH_NO_MORE_DATA:
                smartRefreshLayout.finishLoadMoreWithNoMoreData();
                break;
        }
    }
}
