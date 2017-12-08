package site.zhguixin.hotnews.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import site.zhguixin.hotnews.R;
import site.zhguixin.hotnews.presenter.ArticleContract;
import site.zhguixin.hotnews.presenter.ArticlePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment implements ArticleContract.View {

    private ArticlePresenter mPresenter;

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ArticlePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mPresenter.attachView(this);
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getArticle();
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showContent(Map<String, String> article) {
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
//        mUnbinder.unbind();
        super.onDestroyView();
    }
}
