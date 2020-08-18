package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.SquareModule;
import com.example.wanandroid.mvp.contract.SquareContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.wanandroid.mvp.ui.fragment.SquareFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2020 12:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SquareModule.class, dependencies = AppComponent.class)
public interface SquareComponent {
    void inject(SquareFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SquareComponent.Builder view(SquareContract.View view);

        SquareComponent.Builder appComponent(AppComponent appComponent);

        SquareComponent build();
    }
}