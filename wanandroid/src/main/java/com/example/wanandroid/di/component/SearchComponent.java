package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.example.wanandroid.mvp.ui.fragment.SearchRecordsFragment;
import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.SearchModule;
import com.example.wanandroid.mvp.contract.SearchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.example.wanandroid.mvp.ui.activity.SearchActivity;
import com.example.wanandroid.mvp.ui.fragment.SearchFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 16:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SearchModule.class, dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(SearchActivity activity);

    void inject(SearchFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchComponent.Builder view(SearchContract.View view);

        SearchComponent.Builder appComponent(AppComponent appComponent);

        SearchComponent build();
    }
}