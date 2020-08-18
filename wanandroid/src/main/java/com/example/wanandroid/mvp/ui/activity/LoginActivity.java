package com.example.wanandroid.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.wanandroid.app.base.MySupportActivity;
import com.example.wanandroid.mvp.model.api.entity.SearchKey;
import com.example.wanandroid.mvp.model.api.entity.User;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.example.wanandroid.mvp.ui.Utils.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerLoginComponent;
import com.example.wanandroid.mvp.contract.LoginContract;
import com.example.wanandroid.mvp.presenter.LoginPresenter;

import com.example.wanandroid.R;


import org.simple.eventbus.EventBus;

import butterknife.BindView;

import static android.text.TextUtils.isEmpty;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/15/2020 21:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends MySupportActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.toolbar_web)
    Toolbar mToolbar;
    @BindView(R.id.login_username)
    EditText mAccountEt;
    @BindView(R.id.login_pwd)
    EditText mPasswordEt;
    @BindView(R.id.login_clear)
    View mClearAccountBtn;
    @BindView(R.id.login_key)
    CheckBox mPasswordCb;
    @BindView(R.id.login_sub)
    View mLoginBtn;
    @BindView(R.id.login_go_register)
    View mRegisterBtn;
    private SPUtils mSpUtils;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbar();
        initListener();
        mSpUtils = SPUtils.getInstance(this);
    }

    private void initListener() {

        //清除账号按钮显示
        mAccountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mClearAccountBtn.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //显示密码按钮显示
        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPasswordCb.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //清除账号
        mClearAccountBtn.setOnClickListener(view -> mAccountEt.setText(null));

        //是否显示密码

        mPasswordCb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (mPasswordCb.isChecked()) {
                mPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                mPasswordEt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            }
            mPasswordEt.setSelection(mPasswordEt.getText().length());
        });

        //跳转注册页面
        mRegisterBtn.setOnClickListener(view -> launchActivity(new Intent(this, RegisterActivity.class)));

        mLoginBtn.setOnClickListener(view -> {
            if (isEmpty(mAccountEt.getText())) {
                showMessage("请填写账号");
            } else {

                if (isEmpty(mPasswordEt.getText())) {
                    showMessage("请填写密码");
                } else {
                    if (mPresenter != null) {
                        mPresenter.getLogin(mAccountEt.getText().toString(), mPasswordEt.getText().toString());
                    }
                }
            }
        });
    }

    private void initToolbar() {
        mToolbar.setTitle("登录");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(view -> finish());
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
    public void setLogin(WanAndroidResponse<User> data) {
        if (data.getData() != null) {
            mSpUtils.saveUserInfo(data.getData().getUsername());
            EventBus.getDefault().post(new SearchKey("string"), "Login");
            ArmsUtils.makeText(this, "登录成功!");
            finish();
        } else {
            ArmsUtils.makeText(this, "登录失败!");
        }
    }
}
