package com.example.wanandroid.mvp.model;

import android.app.Application;

import com.example.wanandroid.mvp.model.api.entity.User;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.example.wanandroid.mvp.model.api.service.ApiService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.example.wanandroid.mvp.contract.LoginContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/15/2020 21:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WanAndroidResponse<User>> getLogin(String account, String password) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .onLogin(account, password);
    }
}