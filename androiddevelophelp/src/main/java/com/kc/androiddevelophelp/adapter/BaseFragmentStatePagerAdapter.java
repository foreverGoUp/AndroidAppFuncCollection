package com.kc.androiddevelophelp.adapter;

import android.content.Context;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 *
 */
public class BaseFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private Context context;
    private List<T> mList;
    private String[] mTitles;

    public BaseFragmentStatePagerAdapter(Context context, FragmentManager fm, List<T> list) {
        this(context, fm, list, null);
    }

    public BaseFragmentStatePagerAdapter(Context context, FragmentManager fm, List<T> list, String[] titles) {
        super(fm);
        this.mList = list;
        this.context = context;
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
        if (mTitles != null && position < mTitles.length) {
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }
}