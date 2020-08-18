package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.SystemArticlesModule;
import com.example.wanandroid.mvp.contract.SystemArticlesContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.wanandroid.mvp.ui.activity.SystemArticlesActivity;
import com.example.wanandroid.mvp.ui.fragment.SystemArticlesFragment;


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
@Component(modules = SystemArticlesModule.class, dependencies = AppComponent.class)
public interface SystemArticlesComponent {
    void inject(SystemArticlesActivity activity);

    void inject(SystemArticlesFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SystemArticlesComponent.Builder view(SystemArticlesContract.View view);

        SystemArticlesComponent.Builder appComponent(AppComponent appComponent);

        SystemArticlesComponent build();
    }
}