package com.example.wanandroid.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.wanandroid.R;
import com.example.wanandroid.mvp.model.api.entity.HomeBanner;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.zhouwei.mzbanner.holder.MZViewHolder;

public class BannerViewAdapter implements MZViewHolder<HomeBanner> {

    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
        mImageView = view.findViewById(R.id.banner_Iv);
        return view;
    }

    @Override
    public void onBind(Context context, int position, HomeBanner data) {
        ArmsUtils.obtainAppComponentFromContext(context)
                .imageLoader()
                .loadImage(context, ImageConfigImpl
                        .builder()
                        .imageView(mImageView)
                        .url(data.getImagePath())
                        .build());
    }
}
