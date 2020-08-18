package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.NavigationPagerModule;
import com.example.wanandroid.mvp.contract.NavigationPagerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.wanandroid.mvp.ui.fragment.NavigationPagerFragment;


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
@Component(modules = NavigationPagerModule.class, dependencies = AppComponent.class)
public interface NavigationPagerComponent {
    void inject(NavigationPagerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NavigationPagerComponent.Builder view(NavigationPagerContract.View view);

        NavigationPagerComponent.Builder appComponent(AppComponent appComponent);

        NavigationPagerComponent build();
    }
}