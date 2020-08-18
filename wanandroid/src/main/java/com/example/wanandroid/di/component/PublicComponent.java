package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.PublicModule;
import com.example.wanandroid.mvp.contract.PublicContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.wanandroid.mvp.ui.fragment.PublicFragment;


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
@FragmentScope
@Component(modules = PublicModule.class, dependencies = AppComponent.class)
public interface PublicComponent {
    void inject(PublicFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PublicComponent.Builder view(PublicContract.View view);

        PublicComponent.Builder appComponent(AppComponent appComponent);

        PublicComponent build();
    }
}