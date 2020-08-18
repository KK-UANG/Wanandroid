package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.SystemPagerModule;
import com.example.wanandroid.mvp.contract.SystemPagerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.wanandroid.mvp.ui.fragment.SystemPagerFragment;


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
@Component(modules = SystemPagerModule.class, dependencies = AppComponent.class)
public interface SystemPagerComponent {
    void inject(SystemPagerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SystemPagerComponent.Builder view(SystemPagerContract.View view);

        SystemPagerComponent.Builder appComponent(AppComponent appComponent);

        SystemPagerComponent build();
    }
}