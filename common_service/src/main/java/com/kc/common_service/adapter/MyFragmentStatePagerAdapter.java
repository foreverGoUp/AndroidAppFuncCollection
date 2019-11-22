package com.kc.common_service.adapter;

import android.content.Context;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @Author: pyz
 * @Package: com.pyz.viewpagerdemo.adapter
 * @Description: TODO
 * @Project: ViewPagerDemo
 * @Date: 2016/8/18 11:49
 */
public class MyFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private Context mContext;
    private List<T> mList;
    private String[] mTitles;

    public MyFragmentStatePagerAdapter(Context context, FragmentManager fm, List<T> list) {
        this(context, fm, list, null);
    }

    public MyFragmentStatePagerAdapter(Context context, FragmentManager fm, List<T> list, String[] titles) {
        super(fm);
        mList = list;
        mContext = context.getApplicationContext();
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null && position < mTitles.length){
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }
}
