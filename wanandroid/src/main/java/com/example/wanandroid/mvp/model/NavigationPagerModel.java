package com.example.wanandroid.mvp.model;

import android.app.Application;

import com.example.wanandroid.mvp.model.api.service.ApiService;
import com.example.wanandroid.mvp.model.api.entity.Navigation;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.example.wanandroid.mvp.contract.NavigationPagerContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/12/2020 15:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class NavigationPagerModel extends BaseModel implements NavigationPagerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public NavigationPagerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WanAndroidResponse<List<Navigation>>> getNavigation() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .onNavigation();
    }
}