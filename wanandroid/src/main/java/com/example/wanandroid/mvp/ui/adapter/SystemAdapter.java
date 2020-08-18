package com.example.wanandroid.mvp.ui.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.mvp.model.api.entity.Tab;
import com.example.wanandroid.mvp.ui.Utils.ColorUtils;
import com.example.wanandroid.mvp.ui.Utils.Const;
import com.example.wanandroid.mvp.ui.activity.SystemArticlesActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

public class SystemAdapter extends BaseQuickAdapter<Tab, BaseViewHolder> {

    public SystemAdapter() {
        super(R.layout.item_system);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Tab item) {
        helper.setText(R.id.item_system_title, item.getName());
        TagFlowLayout flowLayout = helper.getView(R.id.item_system_flow_layout);
        flowLayout.setAdapter(new TagAdapter<Tab>(item.getChildren())
        {
            @Override
            public View getView(FlowLayout parent, int position, Tab tab)
            {
                TextView  tv = (TextView) mLayoutInflater.inflate(R.layout.flow_layout,
                        flowLayout, false);
                tv.setText(tab.getName());
                tv.setTextColor(ColorUtils.getRandomColor());
                return tv;
            }
        });
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(mContext, SystemArticlesActivity.class);
                intent.putExtra(Const.SYSTEM_ARTICLES_KEY, position);
                intent.putExtra(Const.SYSTEM_ARTICLES, item);
                mContext.startActivity(intent);
                return false;
            }
        });
    }
}
