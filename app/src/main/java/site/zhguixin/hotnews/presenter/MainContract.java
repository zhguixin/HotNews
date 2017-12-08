package site.zhguixin.hotnews.presenter;

import java.util.List;

import site.zhguixin.hotnews.entity.WXNewsBean;

/**
 * Created by zhguixin on 2017/12/3.
 */

public interface MainContract {

    interface View extends BaseView {
        void showContent(List<WXNewsBean> infoList);
    }

    interface Presenter extends BasePresenter<View> {
        void getWxNewsInfo();
    }
}
