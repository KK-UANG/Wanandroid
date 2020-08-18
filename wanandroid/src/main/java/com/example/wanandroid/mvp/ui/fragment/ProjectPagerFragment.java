package com.example.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.app.base.MySupportFragment;
import com.example.wanandroid.mvp.model.api.entity.Article;
import com.example.wanandroid.mvp.model.api.entity.ArticleInfo;
import com.example.wanandroid.mvp.model.api.entity.Tab;
import com.example.wanandroid.mvp.ui.Utils.Const;
import com.example.wanandroid.mvp.ui.activity.WebViewActivity;
import com.example.wanandroid.mvp.ui.adapter.ProjectArticleAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerProjectPagerComponent;
import com.example.wanandroid.mvp.contract.ProjectPagerContract;
import com.example.wanandroid.mvp.presenter.ProjectPagerPresenter;

import com.example.wanandroid.R;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class ProjectPagerFragment extends MySupportFragment<ProjectPagerPresenter> implements ProjectPagerContract.View {

    @BindView(R.id.project_pager_Rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.project_pager_Sw)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private Integer mPagerId;
    private ProjectArticleAdapter mAdapter;
    private View mView;

    public static ProjectPagerFragment newInstance(Tab tab) {
        ProjectPagerFragment fragment = new ProjectPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key_project_pager_id", tab.getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerProjectPagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_project_pager, container, false);
        }
        return mView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mPagerId = getArguments().getInt("key_project_pager_id");
        }
        initData();
        initAdapter();
        initListener();
    }

    private void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(() -> initData());

        mAdapter.setOnLoadMoreListener(()->{
            mPresenter.getProjectArticle(mPagerId);
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(Const.KEY, Const.ARTICLE_KEY);
            intent.putExtra(Const.ARTICLE, mAdapter.getData().get(position));
            launchActivity(intent);
        });
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ProjectArticleAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        if (mPagerId != null && mPresenter != null) {
            mPresenter.getProjectArticles(mPagerId);
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
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
    public void setProjectArticles(ArticleInfo data) {
        mAdapter.setNewData(data.getDatas());
    }

    @Override
    public void setProjectArticle(ArticleInfo data) {
        List<Article> dabs = data.getDatas();
        if (dabs.isEmpty()) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.addData(dabs);
            mAdapter.loadMoreComplete();
        }
    }
}
