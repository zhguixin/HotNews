package site.zhguixin.hotnews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import site.zhguixin.hotnews.R;
import site.zhguixin.hotnews.adapter.FooterViewWrapper;
import site.zhguixin.hotnews.adapter.WxAdapter;
import site.zhguixin.hotnews.entity.WXNewsBean;
import site.zhguixin.hotnews.presenter.MainContract;
import site.zhguixin.hotnews.presenter.WxNewsPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link WxNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WxNewsFragment extends Fragment implements MainContract.View {
    private static final String TAG = WxNewsFragment.class.toString();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Unbinder mUnbinder;
    private WxNewsPresenter mPresenter;
    private List<WXNewsBean> mList;
    private FooterViewWrapper mAdapter;
    private boolean mIsLoading = false;

    @BindView(R.id.news_list) RecyclerView mNewsList;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefresh;

    public WxNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WxNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WxNewsFragment newInstance(String param1, String param2) {
        WxNewsFragment fragment = new WxNewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mPresenter = new WxNewsPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mPresenter.attachView(this);
        return inflater.inflate(R.layout.fragment_wx_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);
        init();
    }

    private void init() {
        mList = new ArrayList<>();
        WxAdapter adapter = new WxAdapter(getActivity(), mList);
        mAdapter = new FooterViewWrapper(getActivity(), adapter);
        mNewsList.setAdapter(mAdapter);
        mNewsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalItem;
            private int firstVisible;
            private int visibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItem = mNewsList.getChildCount();
                totalItem = mNewsList.getLayoutManager().getItemCount();
                firstVisible = ((LinearLayoutManager)mNewsList.getLayoutManager()).findFirstVisibleItemPosition();
                if (!mIsLoading && (totalItem - visibleItem) <= firstVisible && visibleItem > 1) {
                    mIsLoading = true;
                    Log.d(TAG, "onScrolled: load more");
                    mPresenter.loadMoreNews();
                }
//                Log.d(TAG, "onScrolled: totalItem=" + totalItem + " firstVisible=" + firstVisible +
//                    " visibleItem=" + visibleItem);
            }
        });

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWxNewsInfo();
            }
        });
        mPresenter.getWxNewsInfo();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void showErrorMsg(String msg) {
        Log.d(TAG, "showErrorMsg: " + msg);
        mAdapter.setVisible(false);
        Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showContent(List<WXNewsBean> infoList) {
        if(mRefresh.isRefreshing()) {
            mRefresh.setRefreshing(false);
        }
        Log.d(TAG, "showContent: info size" + infoList.size());
        mList.clear();
        mList.addAll(infoList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContent(List<WXNewsBean> infoList) {
        mList.addAll(infoList);
        mAdapter.notifyDataSetChanged();
        mIsLoading = false;
    }
}
