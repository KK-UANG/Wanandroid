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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanandroid.app.base.MySupportFragment;
import com.example.wanandroid.mvp.model.api.entity.HotKey;
import com.example.wanandroid.mvp.model.api.entity.SearchKey;
import com.example.wanandroid.mvp.ui.Utils.SPUtils;
import com.example.wanandroid.mvp.ui.adapter.SearchHistoryAdapter;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.wanandroid.di.component.DaggerSearchRecordsComponent;
import com.example.wanandroid.mvp.contract.SearchRecordsContract;
import com.example.wanandroid.mvp.presenter.SearchRecordsPresenter;

import com.example.wanandroid.R;
import com.lxj.xpopup.XPopup;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 16:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchRecordsFragment extends MySupportFragment<SearchRecordsPresenter> implements SearchRecordsContract.View {

    @BindView(R.id.tl_hot_words)
    TagFlowLayout mHotWordsLayout;
    @BindView(R.id.ll_history)
    LinearLayout mHistoryLayout;
    @BindView(R.id.btn_clear_history)
    View mClearHistoryBtn;
    @BindView(R.id.rv_history)
    RecyclerView mHistoryRv;
    private SPUtils mSpUtils;
    private SearchHistoryAdapter mAdapter;

    public static SearchRecordsFragment newInstance() {
        SearchRecordsFragment fragment = new SearchRecordsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSearchRecordsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_records, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.getHotWords();
        }
        mSpUtils = SPUtils.getInstance(getContext());
        initAdapter();
        initView();
        initListener();
    }

    private void initListener() {

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            View IvRemove = view.findViewById(R.id.iv_remove);
            IvRemove.setVisibility(View.VISIBLE);
            return true;
        });

        mAdapter.setOnItemClickListener((adapter, view, position) ->
                EventBus.getDefault().post(new SearchKey(mAdapter.getData().get(position)), "SearchKey")
        );


        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_remove) {
                mAdapter.remove(position);
                mSpUtils.save(mAdapter.getData());
                initView();
            }
        });

        mClearHistoryBtn.setOnClickListener(view -> new XPopup.Builder(getContext())
                .asConfirm(null, "确定要清除搜索历史？",
                        () -> {
                            mAdapter.setNewData(null);
                            mSpUtils.save(mAdapter.getData());
                            initView();
                        })
                .show());
    }

    private void initAdapter() {
        mHistoryRv.setLayoutManager(new FlexboxLayoutManager(getContext()));
        mAdapter = new SearchHistoryAdapter();
        mHistoryRv.setAdapter(mAdapter);
        mAdapter.setNewData(mSpUtils.getHistoryList());
    }

    private void initView() {
        if (mAdapter == null) {
            mHistoryLayout.setVisibility(View.GONE);
        } else {
            if (mAdapter.getData().isEmpty()) {
                mHistoryLayout.setVisibility(View.GONE);
            } else {
                mHistoryLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Subscriber(tag = "Search")
    private void Search(SearchKey searchKey) {
        String key = searchKey.getKey();
        int indexOf = mAdapter.getData().indexOf(key);
        if (indexOf != -1) {
            mAdapter.remove(indexOf);
        }
        mAdapter.addData(0, key);
        if (mAdapter.getData().size() > 10) {
            mAdapter.remove(10);
        }
        mSpUtils.save(mAdapter.getData());
        smoothScrollTop(mHistoryRv);
        initView();
    }

    public static void smoothScrollTop(RecyclerView rv) {
        if (rv != null) {
            RecyclerView.LayoutManager layoutManager = rv.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int first = linearLayoutManager.findFirstVisibleItemPosition();
                int last = linearLayoutManager.findLastVisibleItemPosition();
                int visibleCount = last - first + 1;
                int scrollIndex = visibleCount * 2 - 1;
                if (first > scrollIndex) {
                    rv.scrollToPosition(scrollIndex);
                }
            }
            rv.smoothScrollToPosition(0);
        }
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
    public void setHotWords(List<HotKey> data) {
        mHotWordsLayout.setAdapter(new TagAdapter<HotKey>(data) {
            @Override
            public View getView(FlowLayout parent, int position, HotKey hotKey) {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.flow_item_search_hot,
                        mHistoryLayout, false);
                tv.setText(hotKey.getName());
                return tv;
            }
        });

        mHotWordsLayout.setOnTagClickListener((view, position, parent) -> {
            EventBus.getDefault().post(new SearchKey(data.get(position).getName()), "SearchKey");
            return false;
        });
    }
}
