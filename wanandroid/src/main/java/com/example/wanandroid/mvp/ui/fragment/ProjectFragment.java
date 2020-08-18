package com.example.wanandroid.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.app.base.MySupportFragment;
import com.example.wanandroid.mvp.model.api.entity.Tab;
import com.example.wanandroid.mvp.ui.adapter.ProjectPagerAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerProjectComponent;
import com.example.wanandroid.mvp.contract.ProjectContract;
import com.example.wanandroid.mvp.presenter.ProjectPresenter;

import com.example.wanandroid.R;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2020 12:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ProjectFragment extends MySupportFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.project_Tab)
    TabLayout mTabLayout;
    @BindView(R.id.project_Vp)
    ViewPager mViewPager;
    private ProjectPagerAdapter mPagerAdapter;

    public static ProjectFragment newInstance() {
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerProjectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.getProjectTab();
        }

        mTabLayout.setupWithViewPager(mViewPager);
        mPagerAdapter = new ProjectPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

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
    public void showData(List<Tab> data) {
        mPagerAdapter.setData(data);
    }
}
