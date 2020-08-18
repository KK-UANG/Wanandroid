package com.example.wanandroid.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.example.wanandroid.di.module.SearchRecordsModule;
import com.example.wanandroid.mvp.contract.SearchRecordsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.example.wanandroid.mvp.ui.fragment.SearchRecordsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 16:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchRecordsModule.class, dependencies = AppComponent.class)
public interface SearchRecordsComponent {
    void inject(SearchRecordsFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchRecordsComponent.Builder view(SearchRecordsContract.View view);
        SearchRecordsComponent.Builder appComponent(AppComponent appComponent);
        SearchRecordsComponent build();
    }
}