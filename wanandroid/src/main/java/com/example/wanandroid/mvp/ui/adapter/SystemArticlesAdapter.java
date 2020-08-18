package com.example.wanandroid.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid.mvp.model.api.entity.Tab;
import com.example.wanandroid.mvp.ui.fragment.SystemArticlesFragment;

import java.util.ArrayList;
import java.util.List;

public class SystemArticlesAdapter extends FragmentStatePagerAdapter {

    private List<Tab> mData = new ArrayList<>();

    public void setData(List<Tab> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public SystemArticlesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public SystemArticlesFragment getItem(int i) {
        return SystemArticlesFragment.newInstance(mData.get(i));
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