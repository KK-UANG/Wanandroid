package com.example.wanandroid.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.wanandroid.mvp.contract.SystemContract;
import com.example.wanandroid.mvp.model.SystemModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2020 12:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SystemModule {

    @Binds
    abstract SystemContract.Model bindSystemModel(SystemModel model);
}