package com.example.wanandroid.mvp.ui.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.mvp.model.api.entity.Article;
import com.example.wanandroid.mvp.model.api.entity.Navigation;
import com.example.wanandroid.mvp.ui.Utils.ColorUtils;
import com.example.wanandroid.mvp.ui.Utils.Const;
import com.example.wanandroid.mvp.ui.activity.WebViewActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

public class NavigationAdapter extends BaseQuickAdapter<Navigation, BaseViewHolder> {

    public NavigationAdapter() {
        super(R.layout.item_system);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Navigation item) {
        helper.setText(R.id.item_system_title, item.getName());
        TagFlowLayout flowLayout = helper.getView(R.id.item_system_flow_layout);
        flowLayout.setAdapter(new TagAdapter<Article>(item.getArticles()) {
            @Override
            public View getView(FlowLayout parent, int position, Article tab) {
                TextView tv = (TextView) mLayoutInflater.inflate(R.layout.flow_layout,
                        flowLayout, false);
                tv.setText(tab.getTitle());
                tv.setTextColor(ColorUtils.getRandomColor());
                return tv;
            }
        });
        flowLayout.setOnTagClickListener((view, position, parent) -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(Const.KEY, Const.ARTICLE_KEY);
            intent.putExtra(Const.ARTICLE, item.getArticles().get(position));
            mContext.startActivity(intent);
            return false;
        });
    }
}
