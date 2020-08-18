package com.example.wanandroid.mvp.presenter;

import android.app.Application;

import com.example.wanandroid.mvp.model.api.entity.ArticleInfo;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.example.wanandroid.mvp.contract.SystemArticlesContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.HashMap;
import java.util.Map;


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
public class SystemArticlesPresenter extends BasePresenter<SystemArticlesContract.Model, SystemArticlesContract.View> {
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
    public SystemArticlesPresenter(SystemArticlesContract.Model model, SystemArticlesContract.View rootView) {
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

    public void getSystemArticles(Integer id) {
        mModel.getSystemArticles(0, id)
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
                        mRootView.setSystemArticles(data.getData().getDatas());
                        pager.put(id, 1);
                    }
                });
    }

    public void getSystemArticle(Integer id) {
        Integer page = pager.get(id);
        mModel.getSystemArticles(page + 1, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<WanAndroidResponse<ArticleInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(WanAndroidResponse<ArticleInfo> data) {
                        mRootView.setSystemArticle(data.getData().getDatas());
                        pager.put(id, page + 1);
                    }
                });
    }
}
