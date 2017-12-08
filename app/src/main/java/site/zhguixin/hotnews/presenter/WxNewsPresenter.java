package site.zhguixin.hotnews.presenter;

import site.zhguixin.hotnews.http.ApiService;

/**
 * Created by zhguixin on 2017/12/3.
 */

public class WxNewsPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    @Override
    public void attachView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getWxNewsInfo() {
        ApiService.getWxListInfo(mView);
    }
}
