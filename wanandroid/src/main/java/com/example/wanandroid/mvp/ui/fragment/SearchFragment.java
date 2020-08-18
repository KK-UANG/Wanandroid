package com.example.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.app.base.MySupportFragment;
import com.example.wanandroid.mvp.model.api.entity.ArticleInfo;
import com.example.wanandroid.mvp.model.api.entity.SearchKey;
import com.example.wanandroid.mvp.ui.Utils.Const;
import com.example.wanandroid.mvp.ui.activity.WebViewActivity;
import com.example.wanandroid.mvp.ui.adapter.HomeArticleAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerSearchComponent;
import com.example.wanandroid.mvp.contract.SearchContract;
import com.example.wanandroid.mvp.presenter.SearchPresenter;

import com.example.wanandroid.R;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class SearchFragment extends MySupportFragment<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.rv_search_content)
    RecyclerView mRecyclerView;
    private View mEmptyView;
    private View mLoadingView;
    private HomeArticleAdapter mAdapter;
    private String mKey;
    private Integer pager = 0;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mEmptyView = getLayoutInflater().inflate(R.layout.fragment_empty, null, false);
        mLoadingView = getLayoutInflater().inflate(R.layout.fragment_loading, null, false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomeArticleAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(() -> {
            if (mPresenter != null) {
                mPresenter.getSearch(pager, mKey);
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(Const.KEY, Const.ARTICLE_KEY);
            intent.putExtra(Const.ARTICLE, mAdapter.getData().get(position));
            launchActivity(intent);
        });

    }

    @Subscriber(tag = "Search")
    private void Search(SearchKey searchKey) {
        mKey = searchKey.getKey();
        if (mPresenter != null) {
            mPresenter.getSearch(0, mKey);
            pager = 0;
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        mAdapter.setNewData(null);
        mAdapter.setEmptyView(mLoadingView);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setSearch(ArticleInfo data) {
        if (data != null) {
            if (pager == 0) {
                mAdapter.setNewData(data.getDatas());
            } else {
                mAdapter.addData(data.getDatas());
                mAdapter.loadMoreComplete();
            }
            pager++;
        }
        mAdapter.setEmptyView(mEmptyView);
    }
}
