package site.zhguixin.hotnews.presenter;

/**
 * Created by zhguixin on 2017/12/3.
 */

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();
}
