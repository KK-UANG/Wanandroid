package com.example.wanandroid.mvp.model;

import android.app.Application;

import com.example.wanandroid.mvp.model.api.service.ApiService;
import com.example.wanandroid.mvp.model.api.entity.ArticleInfo;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.example.wanandroid.mvp.contract.SystemArticlesContract;

import io.reactivex.Observable;


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
@ActivityScope
public class SystemArticlesModel extends BaseModel implements SystemArticlesContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SystemArticlesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WanAndroidResponse<ArticleInfo>> getSystemArticles(int page, int id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .onSystemArticles(page, id);
    }
}