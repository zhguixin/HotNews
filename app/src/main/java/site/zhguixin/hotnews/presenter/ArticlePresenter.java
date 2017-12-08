package site.zhguixin.hotnews.presenter;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import site.zhguixin.hotnews.http.ApiService;

/**
 * Created by zhguixin on 2017/12/8.
 */

public class ArticlePresenter implements ArticleContract.Presenter {
    private static final String TAG = "ArticlePresenter";

    private ArticleContract.View mView;

    @Override
    public void attachView(ArticleContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void getArticle() {
        ApiService.getArticle(new Callback<String>() {
            @Override
            public void onSuccess(String info) {
                Document document = Jsoup.parse(info);
                Elements elements = document.getElementsByTag("h1");
                for (Element element : elements) {
                    Log.d(TAG, "onSuccess: " + element.data());
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }
}
