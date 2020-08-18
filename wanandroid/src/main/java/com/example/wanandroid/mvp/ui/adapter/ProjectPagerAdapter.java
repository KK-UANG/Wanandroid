package com.example.wanandroid.mvp.ui.adapter;


import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid.mvp.model.api.entity.Tab;
import com.example.wanandroid.mvp.ui.fragment.ProjectPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectPagerAdapter extends FragmentStatePagerAdapter {

    private List<Tab> mData = new ArrayList<>();

    public void setData(List<Tab> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public ProjectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public ProjectPagerFragment getItem(int i) {
        return ProjectPagerFragment.newInstance(mData.get(i));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getName();
    }

    @Override
    public int getCount() {
        return mData.size();
    }


}
