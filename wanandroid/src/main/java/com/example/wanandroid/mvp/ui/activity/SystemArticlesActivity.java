package com.example.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.wanandroid.app.base.MySupportActivity;
import com.example.wanandroid.mvp.model.api.entity.Article;
import com.example.wanandroid.mvp.model.api.entity.Tab;
import com.example.wanandroid.mvp.ui.Utils.Const;
import com.example.wanandroid.mvp.ui.adapter.SystemArticlesAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerSystemArticlesComponent;
import com.example.wanandroid.mvp.contract.SystemArticlesContract;
import com.example.wanandroid.mvp.presenter.SystemArticlesPresenter;

import com.example.wanandroid.R;


import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SystemArticlesActivity extends MySupportActivity<SystemArticlesPresenter> implements SystemArticlesContract.View {

    @BindView(R.id.toolbar_web)
    Toolbar mToolbar;
    @BindView(R.id.system_articles_Tab)
    TabLayout mTabLayout;
    @BindView(R.id.system_articles_Vp)
    ViewPager mViewPager;

    private int mKey;
    private Tab mTab;
    private SystemArticlesAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSystemArticlesComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_system_articles; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            mKey = intent.getIntExtra(Const.SYSTEM_ARTICLES_KEY,0);
            mTab = intent.getParcelableExtra(Const.SYSTEM_ARTICLES);
        }

        mToolbar.setTitle(mTab.getName());
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(view -> finish());

        mTabLayout.setupWithViewPager(mViewPager);
        mAdapter = new SystemArticlesAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mAdapter.setData(mTab.getChildren());
        mTabLayout.getTabAt(mKey).select();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
        finish();
    }

    @Override
    public void setSystemArticles(List<Article> data) {

    }

    @Override
    public void setSystemArticle(List<Article> data) {

    }
}
