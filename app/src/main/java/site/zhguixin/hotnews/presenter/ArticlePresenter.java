package site.zhguixin.hotnews.presenter;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import site.zhguixin.hotnews.http.ApiService;
import site.zhguixin.hotnews.utils.Constant;

/**
 * Created by zhguixin on 2017/12/8.
 */

public class ArticlePresenter implements ArticleContract.Presenter {
    private static final String TAG = "ArticlePresenter";

    private ArticleContract.View mView;
    private Map<String, String> mArticleMap;

    public ArticlePresenter() {
        mArticleMap = new HashMap<>();

    }

    @Override
    public void attachView(ArticleContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mArticleMap = null;
    }

    @Override
    public void getArticle(String url) {
        ApiService.getArticle(url, new Callback<String>() {
            @Override
            public void onSuccess(String info) {
                Document document = Jsoup.parse(info);
                // 获得文章所有内容的父标签,即根据id找到div标签
                Element element = document.getElementById("article_show");

                // 获得文章标题,找到h1标签，获得文本内容
                Elements title = element.getElementsByTag("h1");
                Log.d(TAG, "onSuccess: title=" + title.text());
                mArticleMap.put(Constant.TITLE, title.text());

                // 获得作者姓名,根据样式找到div标签，获得文本内容
                Elements author = element.getElementsByClass("article_author");
                Log.d(TAG, "onSuccess: author=" + author.text());
                mArticleMap.put(Constant.AUTHOR, author.text());

                // 获得文章内容,根据样式找到div标签，再找到所有的p标签
                Elements text = element.getElementsByClass("article_text");
                Elements infos = text.get(0).getElementsByTag("p");
                String content = infos.toString()
                        .replaceAll("<p>", "\n")
                        .replaceAll("</p>", "");
//                System.out.println(content);
                mArticleMap.put(Constant.CONTENT, content);

                mView.showContent(mArticleMap);
            }

            @Override
            public void onFail(String error) {
                mView.showErrorMsg(error);
            }
        });
    }
}
