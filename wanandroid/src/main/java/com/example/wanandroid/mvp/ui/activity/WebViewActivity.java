package com.example.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.example.wanandroid.app.base.MySupportActivity;
import com.example.wanandroid.mvp.model.api.entity.Article;
import com.example.wanandroid.mvp.model.api.entity.HomeBanner;
import com.example.wanandroid.mvp.ui.Utils.Const;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerWebViewComponent;
import com.example.wanandroid.mvp.contract.WebViewContract;
import com.example.wanandroid.mvp.presenter.WebViewPresenter;

import com.example.wanandroid.R;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.LollipopFixedWebView;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/12/2020 17:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WebViewActivity extends MySupportActivity<WebViewPresenter> implements WebViewContract.View {

    @BindView(R.id.web_view_content)
    LinearLayout web;
    @BindView(R.id.toolbar_web)
    Toolbar mToolbar;

    private AgentWeb mAgentWeb;
    private String mUrl;
    private Article mArticle;
    private HomeBanner mBanner;
    private String mTitle;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebViewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_web_view; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initData();
        initWeb();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String key = intent.getStringExtra(Const.KEY);
            switch (key) {
                case Const.ARTICLE_KEY:
                    mArticle = intent.getParcelableExtra(Const.ARTICLE);
                    mUrl = mArticle.getLink();
                    mTitle = mArticle.getTitle();
                    break;
                case Const.BANNER_KEY:
                    mBanner = intent.getParcelableExtra(Const.BANNER);
                    mUrl = mBanner.getUrl();
                    mTitle = mBanner.getTitle();
                    break;
                default:
                    break;
            }
        }

        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initWeb() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(web, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebView(new LollipopFixedWebView(this))
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}
