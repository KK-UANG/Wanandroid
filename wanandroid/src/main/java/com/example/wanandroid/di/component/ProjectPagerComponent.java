package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.ProjectPagerModule;
import com.example.wanandroid.mvp.contract.ProjectPagerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.wanandroid.mvp.ui.fragment.ProjectPagerFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/11/2020 14:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ProjectPagerModule.class, dependencies = AppComponent.class)
public interface ProjectPagerComponent {
    void inject(ProjectPagerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ProjectPagerComponent.Builder view(ProjectPagerContract.View view);

        ProjectPagerComponent.Builder appComponent(AppComponent appComponent);

        ProjectPagerComponent build();
    }
}