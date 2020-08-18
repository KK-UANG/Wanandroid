package com.example.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.app.base.MySupportFragment;
import com.example.wanandroid.mvp.model.api.entity.Article;
import com.example.wanandroid.mvp.model.api.entity.HomeBanner;
import com.example.wanandroid.mvp.ui.Utils.Const;
import com.example.wanandroid.mvp.ui.activity.WebViewActivity;
import com.example.wanandroid.mvp.ui.adapter.BannerViewAdapter;
import com.example.wanandroid.mvp.ui.adapter.HomeArticleAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerHomeComponent;
import com.example.wanandroid.mvp.contract.HomeContract;
import com.example.wanandroid.mvp.presenter.HomePresenter;

import com.example.wanandroid.R;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2020 12:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFragment extends MySupportFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.home_Rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.home_Sw)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private View mBannerView;
    private MZBannerView mBanner;
    private HomeArticleAdapter mAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initView();
        initAdapter();
        initData();
        initListener();
    }

    private void initView() {

        mBannerView = getLayoutInflater().inflate(R.layout.frament_banner, null, false);
        mBanner = mBannerView.findViewById(R.id.banner);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

    }

    private void initAdapter() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomeArticleAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mBannerView);

    }

    private void initData() {
        if (mPresenter != null) {
            mPresenter.getHomeBanner();
            mPresenter.getHomeArticles();
        }
    }

    private void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(() -> initData());

        mAdapter.setOnLoadMoreListener(() -> {
            if (mPresenter != null) {
                mPresenter.getHomeArticle();
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(Const.KEY, Const.ARTICLE_KEY);
            intent.putExtra(Const.ARTICLE, mAdapter.getData().get(position));
            launchActivity(intent);
        });

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setHomeBanner(List<HomeBanner> data) {

        mBanner.setBannerPageClickListener((view, position) -> {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(Const.KEY, Const.BANNER_KEY);
                    intent.putExtra(Const.BANNER, data.get(position));
                    launchActivity(intent);
                }
        );

        mBanner.setPages(data, (MZHolderCreator<BannerViewAdapter>) () ->
                new BannerViewAdapter());
        mBanner.start();
    }

    @Override
    public void setHomeArticles(List<Article> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void setHomeArticle(List<Article> data) {
        mAdapter.addData(data);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.start();//开始轮播
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.pause();//暂停轮播
    }
}
