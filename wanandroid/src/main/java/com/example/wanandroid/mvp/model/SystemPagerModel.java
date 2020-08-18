package com.example.wanandroid.mvp.model;

import android.app.Application;

import com.example.wanandroid.mvp.model.api.service.ApiService;
import com.example.wanandroid.mvp.model.api.entity.Tab;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.example.wanandroid.mvp.contract.SystemPagerContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/12/2020 15:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class SystemPagerModel extends BaseModel implements SystemPagerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SystemPagerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WanAndroidResponse<List<Tab>>> getSystem() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .onSystem();
    }
}