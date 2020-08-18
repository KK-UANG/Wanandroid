package com.example.wanandroid.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid.mvp.ui.fragment.NavigationPagerFragment;
import com.example.wanandroid.mvp.ui.fragment.SystemPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class SystemPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mData = new ArrayList<>();

    public void setData(List<String> tab) {
        this.mData.clear();
        this.mData.addAll(tab);
        notifyDataSetChanged();
    }

    public SystemPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0){
            return SystemPagerFragment.newInstance();
        }else {
            return NavigationPagerFragment.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
