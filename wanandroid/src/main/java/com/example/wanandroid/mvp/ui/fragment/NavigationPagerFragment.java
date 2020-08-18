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
import com.example.wanandroid.mvp.model.api.entity.Navigation;
import com.example.wanandroid.mvp.ui.adapter.NavigationAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerNavigationPagerComponent;
import com.example.wanandroid.mvp.contract.NavigationPagerContract;
import com.example.wanandroid.mvp.presenter.NavigationPagerPresenter;

import com.example.wanandroid.R;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/12/2020 15:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NavigationPagerFragment extends MySupportFragment<NavigationPagerPresenter> implements NavigationPagerContract.View {

    @BindView(R.id.navigation_pager_Rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.navigation_pager_Sw)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private NavigationAdapter mAdapter;

    public static NavigationPagerFragment newInstance() {
        NavigationPagerFragment fragment = new NavigationPagerFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNavigationPagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_pager, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initData();
        initAdapter();
        initListener();
    }

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> initData());
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NavigationAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        if (mPresenter != null) {
            mPresenter.getNavigation();
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
    public void setNavigation(List<Navigation> data) {
        mAdapter.setNewData(data);
    }
}
