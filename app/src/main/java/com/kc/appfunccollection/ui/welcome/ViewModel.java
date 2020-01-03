package com.kc.appfunccollection.ui.welcome;

import android.databinding.ObservableField;
import android.view.View;

import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.util.RxUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ViewModel extends BaseViewModel {

    private INavigator mNavigator;

    public final ObservableField<String> mJumpBtnText = new ObservableField<>();
    private Disposable mCountdownDisposable;

    public ViewModel(INavigator mNavigator) {
        this.mNavigator = mNavigator;
    }

    /**
     * 点击跳过
     * */
    public void clickJump(View view){
        mNavigator.onClickJump();
        RxUtils.dispose(mCountdownDisposable);
    }

    /**
     * 自动关闭倒计时
     * @param isStart 是否启动
     * */
    public void countdown(boolean isStart){
        if (isStart){
            mJumpBtnText.set("3s后自动跳转，点击马上跳转");
            mCountdownDisposable = Observable.interval(1, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            mJumpBtnText.set((1-aLong)+"s后自动跳转，点击马上跳转");
                            if (aLong >= 1){
                                mNavigator.onClickJump();
                                mCountdownDisposable.dispose();
                            }
                        }
                    });
        }else {
            RxUtils.dispose(mCountdownDisposable);
        }
    }
}
