package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.PublicPagerModule;
import com.example.wanandroid.mvp.contract.PublicPagerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.wanandroid.mvp.ui.fragment.PublicPagerFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/11/2020 20:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PublicPagerModule.class, dependencies = AppComponent.class)
public interface PublicPagerComponent {
    void inject(PublicPagerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PublicPagerComponent.Builder view(PublicPagerContract.View view);

        PublicPagerComponent.Builder appComponent(AppComponent appComponent);

        PublicPagerComponent build();
    }
}