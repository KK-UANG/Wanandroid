package com.example.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid.app.base.MySupportActivity;
import com.example.wanandroid.mvp.model.api.entity.SearchKey;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.example.wanandroid.mvp.ui.Utils.SPUtils;
import com.example.wanandroid.mvp.ui.fragment.HomeFragment;
import com.example.wanandroid.mvp.ui.fragment.ProjectFragment;
import com.example.wanandroid.mvp.ui.fragment.PublicFragment;
import com.example.wanandroid.mvp.ui.fragment.SquareFragment;
import com.example.wanandroid.mvp.ui.fragment.SystemFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerMainComponent;
import com.example.wanandroid.mvp.contract.MainContract;
import com.example.wanandroid.mvp.presenter.MainPresenter;

import com.example.wanandroid.R;


import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 15:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends MySupportActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.bottomNav)
    BottomNavigationViewEx mBottomNavigationView;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title_main)
    TextView mTextView;

    private SupportFragment[] mFragments = new SupportFragment[5];
    private double firstTime = 0;
    private View mLoginBtn;
    private TextView mNameTv;
    private MenuItem mLogoutBtn;
    private SPUtils mSpUtils;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_search:
                launchActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTextView.setText(getString(R.string.home_main));
        // 初始化fragment
        initFragment();
        // 底部切换
        initBottomNav();
        // 用户界面点击
        initNavigationView();
        // 初始化用户信息
        mSpUtils = SPUtils.getInstance(this);
        initUserInfo();
    }

    @Subscriber(tag = "Login")
    private void Login(SearchKey searchKey) {
        initUserInfo();
    }

    private void initUserInfo() {
        if (mSpUtils.getUserInfo().isEmpty()) {
            mLogoutBtn.setVisible(false);
            mNameTv.setVisibility(View.GONE);
            mLoginBtn.setVisibility(View.VISIBLE);
        } else {
            mLogoutBtn.setVisible(true);
            mNameTv.setVisibility(View.VISIBLE);
            mNameTv.setText(mSpUtils.getUserInfo());
            mLoginBtn.setVisibility(View.GONE);
        }
    }

    private void initNavigationView() {
        mLogoutBtn = mNavigationView.getMenu().findItem(R.id.nav_logout);
        View headerView = mNavigationView.getHeaderView(0);
        mLoginBtn = headerView.findViewById(R.id.btn_login);
        mNameTv = headerView.findViewById(R.id.tv_name);

        mLoginBtn.setOnClickListener(view -> launchActivity(new Intent(this, LoginActivity.class)));

        mNavigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                //我的积分
                case R.id.nav_score:
                    ArmsUtils.makeText(MainActivity.this, getString(R.string.nav_my_score));
                    break;
                //我的收藏
                case R.id.nav_collect:
                    ArmsUtils.makeText(MainActivity.this, getString(R.string.nav_my_collect));
                    break;
                //我的分享
                case R.id.nav_share:
                    ArmsUtils.makeText(MainActivity.this, getString(R.string.my_share));
                    break;
                //to
                case R.id.nav_todo:
                    ArmsUtils.makeText(MainActivity.this, getString(R.string.nav_todo));
                    break;
                //夜间模式
                case R.id.nav_night_mode:
                    ArmsUtils.makeText(MainActivity.this, getString(R.string.nav_night_mode));
                    break;
                //系统设置
                case R.id.nav_setting:
                    ArmsUtils.makeText(MainActivity.this, getString(R.string.nav_setting));
                    break;
                //推出登录
                case R.id.nav_logout:
                    if (mPresenter != null) {
                        mPresenter.getLogout();
                    }
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    private void initFragment() {
        SupportFragment firstFragment = findFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[0] = HomeFragment.newInstance();//主页
            mFragments[1] = ProjectFragment.newInstance();//项目
            mFragments[2] = PublicFragment.newInstance();//公众号
            mFragments[3] = SystemFragment.newInstance();//体系
            mFragments[4] = SquareFragment.newInstance();//广场
            loadMultipleRootFragment(R.id.frame_content, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4]
            );
        } else {
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(ProjectFragment.class);
            mFragments[2] = findFragment(PublicFragment.class);
            mFragments[3] = findFragment(SystemFragment.class);
            mFragments[4] = findFragment(SquareFragment.class);
        }
    }

    private void initBottomNav() {
        mBottomNavigationView.setCurrentItem(0);
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_main:
                    showHideFragment(mFragments[0]);
                    mTextView.setText(getString(R.string.home_main));
                    break;
                case R.id.nav_project:
                    showHideFragment(mFragments[1]);
                    mTextView.setText(getString(R.string.home_project));
                    break;
                case R.id.nav_public:
                    showHideFragment(mFragments[2]);
                    mTextView.setText(getString(R.string.home_public));
                    break;
                case R.id.nav_system:
                    showHideFragment(mFragments[3]);
                    mTextView.setText(getString(R.string.home_system));
                    break;
                case R.id.nav_square:
                    showHideFragment(mFragments[4]);
                    mTextView.setText(getString(R.string.home_square));
                    break;
                default:
                    break;
            }
            return true;
        });
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
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ArmsUtils.makeText(this, "再按一次退出程序");
            firstTime = secondTime;
        } else {
            ArmsUtils.exitApp();
        }
    }

    @Override
    public void setLogout(WanAndroidResponse data) {
        if (data.getErrorCode() == 0) {
            ArmsUtils.makeText(MainActivity.this, "退出登录成功！");
            mSpUtils.cleanUserInfo();
            initUserInfo();
        } else {
            ArmsUtils.makeText(MainActivity.this, "退出登录失败！");
        }

    }
}
