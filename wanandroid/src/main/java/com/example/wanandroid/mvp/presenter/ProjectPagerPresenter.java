package com.example.wanandroid.mvp.presenter;

import android.app.Application;

import com.example.wanandroid.mvp.model.api.entity.ArticleInfo;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.example.wanandroid.mvp.contract.ProjectPagerContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.HashMap;
import java.util.Map;


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
public class ProjectPagerPresenter extends BasePresenter<ProjectPagerContract.Model, ProjectPagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private Map<Integer, Integer> pager = new HashMap<>();

    @Inject
    public ProjectPagerPresenter(ProjectPagerContract.Model model, ProjectPagerContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getProjectArticles(Integer pagerId) {
        mModel.getProjectArticles(1, pagerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<WanAndroidResponse<ArticleInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(WanAndroidResponse<ArticleInfo> data) {
                        mRootView.setProjectArticles(data.getData());
                        pager.put(pagerId, 1);
                    }
                });
    }

    public void getProjectArticle(Integer pagerId) {
        Integer page = pager.get(pagerId);
        mModel.getProjectArticles(page + 1, pagerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<WanAndroidResponse<ArticleInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(WanAndroidResponse<ArticleInfo> data) {
                        mRootView.setProjectArticle(data.getData());
                        pager.put(pagerId, page + 1);
                    }
                });
    }
}
