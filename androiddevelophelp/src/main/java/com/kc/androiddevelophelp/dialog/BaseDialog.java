package com.kc.androiddevelophelp.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 基本对话框
 * 所有对话框的基类。负责对话框背景设置，进场出场动画，自定义布局控件与点击事件的绑定。
 * <p>
 * 注意：布局文件的根布局外层必须包裹一个viewGroup,如FrameLayout,这样根布局的尺寸设置才有效。
 */
public class BaseDialog<D extends BaseDialog> extends DialogFragment {

    protected final String TAG = this.getClass().getSimpleName();

    //在该活动显示
    protected FragmentActivity mActivity;
    //布局ID
    private int mLayoutId;
    //显示宽度占屏幕宽度比例，默认0.8
    private float mWidthRatio = 0f;
    //显示高度占屏幕高度比例，默认0，表示高度由布局决定。
    private float mHeightRatio = 0;
    //进出场动画
    private int mAnimationStyleRes;
    //背景昏暗程度
    private float mBackgroundDimAmount = 1;

    //对话框布局view
    protected View mView = null;
    //存放控件id和控件及映射关系。
    private final SparseArray<View> mViews = new SparseArray<>();


    public BaseDialog(FragmentActivity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, ">>>>>>>>>>>>>>>>>onCreateDialog");
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //设置对话框无默认标题栏
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, ">>>>>>>>>>>>>>>>>onCreateView");
        if (mView == null) {
            throw new IllegalArgumentException("未调用setLayoutId方法设置布局ID。");
        }
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, ">>>>>>>>>>>>>>>>>onStart");
        Dialog dialog = getDialog();
        //对话框内部背景
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //进出场动画
        if (mAnimationStyleRes != 0) dialog.getWindow().setWindowAnimations(mAnimationStyleRes);
        //对话框宽高
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;//设置为MATCH_PARENT会导致对话框直接占满屏幕，点击空白无法关闭对话框。
        if (mWidthRatio != 0) {
            width = (int) (dm.widthPixels * mWidthRatio);
        }
        if (mHeightRatio != 0) {
            height = (int) (dm.heightPixels * mHeightRatio);
        }
        dialog.getWindow().setLayout(width, height);
        //对话框外部背景
        if (mBackgroundDimAmount != 1){
            Window window = dialog.getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = mBackgroundDimAmount;
            window.setAttributes(windowParams);
        }
    }

    private <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    ///////////////////////////////////////////////////////////////////////////
    // 设置方法
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置布局ID
     */
    public D setLayoutId(@LayoutRes int layoutId) {
        mLayoutId = layoutId;
        mView = mActivity.getLayoutInflater().inflate(mLayoutId, null);
        return (D) this;
    }

    /**
     * 为文本控件设置文本
     */
    public D setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return (D) this;
    }

    /**
     * 为控件设置点击事件监听器
     */
    public D setOnClickListener(@IdRes int viewId, final OnClickListener onClickListener) {
        View view = getView(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(BaseDialog.this);
            }
        });
        return (D) this;
    }

    /**
     * 设置显示宽度占屏幕宽度比例
     */
    public D setWidthRatio(float widthRatio) {
        mWidthRatio = widthRatio;
        return (D) this;
    }

    /**
     * 设置显示高度占屏幕高度比例
     */
    public D setHeightRatio(float heightRatio) {
        mHeightRatio = heightRatio;
        return (D) this;
    }

    /**
     * 设置进出场动画资源
     */
    public D setAnimationStyleRes(@StyleRes int animationStyleRes) {
        mAnimationStyleRes = animationStyleRes;
        return (D) this;
    }

    /**
     * 设置背景昏暗程度
     * @param backgroundDimAmount 值为[0，1]，1表示昏暗，0表示透明。
     * */
    public D setBackgroundDimAmount(float backgroundDimAmount) {
        mBackgroundDimAmount = backgroundDimAmount;
        return (D) this;
    }

    /**
     * 设置背景透明
     * */
    public D setBackGroundTransparent() {
        mBackgroundDimAmount = 0;
        return (D) this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 设置方法 end
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 显示对话框
     */
    public void show() {
        super.show(mActivity.getSupportFragmentManager(), this.getClass().getSimpleName());
    }

    /**
     * 点击事件监听器
     */
    public interface OnClickListener {
        /**
         * @param dialog 正在显示的对话框，可以根据需要强制转换成BaseDialog对应的子对话框。
         */
        void onClick(BaseDialog dialog);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, ">>>>>>>>>>>>>>>>>onDestroy");
        mActivity = null;
    }
}

