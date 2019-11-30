package com.kc.module_home.home;

import android.os.Bundle;

import com.kc.androiddevelophelp.base.mvvm.BaseViewModel;
import com.kc.common_service.base.AppBaseFragment;
import com.kc.module_home.R;
import com.kc.module_home.databinding.FragmentFocusBinding;


public class FocusFragment extends AppBaseFragment<FragmentFocusBinding, BaseViewModel> {

    public static FocusFragment newInstance() {
        Bundle args = new Bundle();

        FocusFragment fragment = new FocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_focus;
    }

    @Override
    protected void init() {

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        Log.e(TAG, "setUserVisibleHint-"+this.hashCode()+",visible:"+getUserVisibleHint()+","+isVisibleToUser);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Log.e(TAG, "onActivityCreated-"+this.hashCode());
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.e(TAG, "onCreateView-"+this.hashCode());
//        return super.onCreateView(inflater, container, savedInstanceState);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.e(TAG, "onDestroyView-"+this.hashCode());
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.e(TAG, "onPause-"+this.hashCode());
//
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        Log.e(TAG, "onStop-"+this.hashCode());
//
//    }
}
