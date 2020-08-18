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
import android.widget.EditText;

import com.example.wanandroid.app.base.MySupportActivity;
import com.example.wanandroid.mvp.model.api.entity.User;
import com.example.wanandroid.mvp.model.api.entity.WanAndroidResponse;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerRegisterComponent;
import com.example.wanandroid.mvp.contract.RegisterContract;
import com.example.wanandroid.mvp.presenter.RegisterPresenter;

import com.example.wanandroid.R;


import butterknife.BindView;

import static android.text.TextUtils.isEmpty;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/15/2020 21:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RegisterActivity extends MySupportActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.toolbar_web)
    Toolbar mToolbar;
    @BindView(R.id.register_username)
    EditText mAccountEt;
    @BindView(R.id.register_pwd)
    EditText mPasswordEt;
    @BindView(R.id.register_pwd1)
    EditText mPasswordEt1;
    @BindView(R.id.register_clear)
    View mClearAccountBtn;
    @BindView(R.id.register_key)
    CheckBox mPasswordCb;
    @BindView(R.id.register_key1)
    CheckBox mPasswordCb1;
    @BindView(R.id.register_sub)
    View mRegisterBtn;

    private String mAccount;
    private String mPassword;
    private String mPassword1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbar();
        initListener();
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

        //显示密码按钮显示
        mPasswordEt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPasswordCb1.setVisibility(charSequence.length() > 0 ? View.VISIBLE : View.GONE);
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

        mPasswordCb1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (mPasswordCb1.isChecked()) {
                mPasswordEt1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                mPasswordEt1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            }
            mPasswordEt1.setSelection(mPasswordEt1.getText().length());
        });

        mRegisterBtn.setOnClickListener(view -> {
            if (isEmpty(mAccountEt.getText())) {
                showMessage("请填写账号");
            } else if (mAccountEt.getText().length() < 6) {
                showMessage("账号长度不能小于6位");
            } else {
                mAccount = mAccountEt.getText().toString();

                if (isEmpty(mPasswordEt.getText())) {
                    showMessage("请填写密码");
                } else if (mPasswordEt.getText().length() < 6) {
                    showMessage("密码长度不能小于6位");
                } else {

                    if (isEmpty(mPasswordEt1.getText())) {
                        showMessage("请填写确认密码");
                    } else {

                        if (!mPasswordEt.getText().toString().equals(mPasswordEt1.getText().toString())) {
                            showMessage("密码不一致");
                        } else {
                            mPassword = mPasswordEt.getText().toString();
                            mPassword1 = mPasswordEt1.getText().toString();

                            if (mPresenter != null) {
                                mPresenter.getRegister(mAccount, mPassword, mPassword1);
                            }
                        }
                    }
                }
            }

        });
    }

    private void initToolbar() {
        mToolbar.setTitle("注册");
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
    public void setRegister(WanAndroidResponse<User> data) {
        if (data.getData() != null) {
            ArmsUtils.makeText(this, "注册成功!");
            finish();
        } else {
            ArmsUtils.makeText(this, "注册失败!");
        }
    }
}
