package com.example.wanandroid.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.wanandroid.R;
import com.example.wanandroid.mvp.model.api.entity.Article;

import static android.text.TextUtils.isEmpty;

public class HomeArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public HomeArticleAdapter() {
        super(R.layout.item_home);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Article item) {
        helper.setText(R.id.item_home_author, (isEmpty(item.getAuthor()) ? item.getShareUser() : item.getAuthor()))
                .setText(R.id.item_home_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.item_home_type, item.getSuperChapterName() + "·" + item.getChapterName())
                .setText(R.id.item_home_data, item.getNiceDate())
                .setGone(R.id.item_home_new, item.isFresh())
                .setGone(R.id.item_home_top, item.isTop())
                .setGone(R.id.item_home_ask, item.getSuperChapterName().equals("问答"));
    }
}
