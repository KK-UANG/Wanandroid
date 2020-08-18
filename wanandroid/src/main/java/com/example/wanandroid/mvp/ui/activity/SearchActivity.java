package com.example.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.wanandroid.app.base.MySupportActivity;
import com.example.wanandroid.mvp.model.api.entity.ArticleInfo;
import com.example.wanandroid.mvp.model.api.entity.SearchKey;
import com.example.wanandroid.mvp.ui.fragment.SearchFragment;
import com.example.wanandroid.mvp.ui.fragment.SearchRecordsFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerSearchComponent;
import com.example.wanandroid.mvp.contract.SearchContract;
import com.example.wanandroid.mvp.presenter.SearchPresenter;

import com.example.wanandroid.R;


import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

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
public class SearchActivity extends MySupportActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.toolbar_search)
    Toolbar mToolbar;
    @BindView(R.id.et_search)
    EditText mSearch;
    @BindView(R.id.toolbar_search_back)
    View mBack;
    @BindView(R.id.btn_search_clean)
    View mClean;

    private SupportFragment[] mFragments = new SupportFragment[2];
    private boolean mKey = true;
    private InputMethodManager mImm;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        initFragment();
        initListener();
    }

    private void initFragment() {
        SupportFragment firstFragment = findFragment(SearchRecordsFragment.class);
        if (firstFragment == null) {
            mFragments[0] = SearchRecordsFragment.newInstance();
            mFragments[1] = SearchFragment.newInstance();
            loadMultipleRootFragment(R.id.frame_search, 0,
                    mFragments[0],
                    mFragments[1]
            );
        } else {
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(SearchFragment.class);
        }
    }

    private void initListener() {

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mClean.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
                if (charSequence.length() <= 0) {
                    showHideFragment(mFragments[0]);
                    mKey = true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                Search();
            }
            return true;
        });

        mClean.setOnClickListener(view -> {
            mSearch.setText(null);
        });

        mBack.setOnClickListener(view -> {
            if (mKey) {
                finish();
            } else {
                showHideFragment(mFragments[0]);
                mKey = true;
            }
        });
    }

    public void Search() {
        String string = mSearch.getText().toString();
        if (!string.trim().isEmpty()) {
            showHideFragment(mFragments[1]);
            mKey = false;
            EventBus.getDefault().post(new SearchKey(string), "Search");
            mImm = (InputMethodManager) getSystemService(SearchActivity.INPUT_METHOD_SERVICE);
            mImm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } else {
            ArmsUtils.makeText(this, "请输入搜索内容");
        }
    }

    @Subscriber(tag = "SearchKey")
    private void Search(SearchKey searchKey) {
        mSearch.setText(searchKey.getKey());
        mSearch.setSelection(searchKey.getKey().length());
        Search();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Search();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        if (mKey) {
            finish();
        } else {
            showHideFragment(mFragments[0]);
            mKey = true;
        }
    }

    @Override
    public void setSearch(ArticleInfo data) {

    }
}
