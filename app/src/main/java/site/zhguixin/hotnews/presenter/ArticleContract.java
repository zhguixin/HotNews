package site.zhguixin.hotnews.presenter;

import java.util.Map;

/**
 * Created by zhguixin on 2017/12/8.
 */

public interface ArticleContract {
    interface View extends BaseView {
        void showContent(Map<String,String> article);
    }

    interface Presenter extends BasePresenter<View> {
        void getArticle(String url);
    }
}
