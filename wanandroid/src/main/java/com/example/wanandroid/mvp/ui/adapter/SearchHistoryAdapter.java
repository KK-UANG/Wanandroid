package com.example.wanandroid.mvp.ui.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;

public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchHistoryAdapter() {
        super(R.layout.item_search_history);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_key, item)
                .setGone(R.id.iv_remove,false)
                .addOnClickListener(R.id.iv_remove);
    }
}
