package com.example.wanandroid.mvp.model;

import android.app.Application;

import com.example.wanandroid.mvp.model.api.service.ApiService;
import com.example.wanandroid.mvp.model.api.entity.Article;
import com.example.wanandroid.mvp.model.api.entity.ArticleInfo;
import com.example.wanandroid.mvp.model.api.entity.HomeBanner;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.example.wanandroid.mvp.contract.HomeContract;

import java.util.List;

import io.reactivex.Observable;


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
@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    private ApiService mApiService;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
        mApiService = mRepositoryManager.obtainRetrofitService(ApiService.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WanAndroidResponse<List<HomeBanner>>> getHomeBanner() {
        return mApiService.onHomeBanner();
    }

    @Override
    public Observable<WanAndroidResponse<List<Article>>> getTopArticles() {
        return mApiService.onTopArticles();
    }

    @Override
    public Observable<WanAndroidResponse<ArticleInfo>> getHomeArticles(int page) {
        return mApiService.onHomeArticles(page);
    }
}