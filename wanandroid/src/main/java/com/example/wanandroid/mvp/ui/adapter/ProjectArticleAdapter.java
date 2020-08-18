package com.example.wanandroid.mvp.ui.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.example.wanandroid.R;
import com.example.wanandroid.mvp.model.api.entity.Article;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import static android.text.TextUtils.isEmpty;

public class ProjectArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public ProjectArticleAdapter() {
        super(R.layout.item_project_pager);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Article item) {
        helper.setText(R.id.item_project_author, (isEmpty(item.getAuthor()) ? item.getShareUser() : item.getAuthor()))
                .setText(R.id.item_project_title, item.getTitle())
                .setText(R.id.item_project_type, item.getSuperChapterName() + "·" + item.getChapterName())
                .setText(R.id.item_project_data, item.getNiceDate())
                .setText(R.id.item_project_content, item.getDesc());
        ArmsUtils.obtainAppComponentFromContext(mContext)
                .imageLoader()
                .loadImage(mContext, ImageConfigImpl
                        .builder()
                        .imageView(helper.getView(R.id.item_project_img))
                        .url(item.getEnvelopePic())
                        .errorPic(R.drawable.default_project_img)
                        .fallback(R.drawable.default_project_img)
                        .isCrossFade(true)
                        .build());
    }
}
