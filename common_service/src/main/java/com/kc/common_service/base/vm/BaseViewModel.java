package com.kc.common_service.base.vm;

import com.kc.common_service.base.viewNavigator.IBaseViewNavigator;

/**
 * * ckc 19-10-19
 * 基本的界面vm
 * */
public class BaseViewModel<NAVI extends IBaseViewNavigator> {

    //界面导航器
    protected NAVI mViewNavigator;
}
