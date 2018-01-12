package site.zhguixin.hotnews.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import site.zhguixin.hotnews.entity.WXNewsBean;
import site.zhguixin.hotnews.entity.WXResult;
import site.zhguixin.hotnews.presenter.Callback;
import site.zhguixin.hotnews.presenter.MainContract;
import site.zhguixin.hotnews.utils.AppExecutors;
import site.zhguixin.hotnews.utils.Constant;

/**
 * Created by zhguixin on 2017/12/3.
 */

public class ApiService {
    private static final String TAG = ApiService.class.toString();

    private static AppExecutors mAppExecutors = new AppExecutors();

    public static void getWxListInfo(final MainContract.View view, int page) {
        final int mPage = page;
        BuildService.getInstance()
                .buildService(Constant.WX_HOST)
                .getWXHot(Constant.API_KEY,10,page)
                .flatMap(new Function<WXResult<List<WXNewsBean>>, Flowable<List<WXNewsBean>>>() {
                    @Override
                    public Flowable<List<WXNewsBean>> apply(WXResult<List<WXNewsBean>> listWXResult) throws Exception {
                        if(listWXResult.getCode() == 200) {
                            Log.d(TAG, "apply: thread=" + Thread.currentThread().getName());
                            return createData(listWXResult.getNewslist());
                        } else {
                            Log.d(TAG, "apply: error");
                            return Flowable.error(new Exception("访问服务器失败"));
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<List<WXNewsBean>>() {
                    @Override
                    public void onNext(List<WXNewsBean> wxNewsBeans) {
                        Log.d(TAG, "onNext: thread=" + Thread.currentThread().getName());
                        Log.d(TAG, "onNext: wxNewsBean size=" + wxNewsBeans.size() + " mPage=" + mPage);
                        if(mPage > 1) {
                            view.showMoreContent(wxNewsBeans);
                        } else {
                            view.showContent(wxNewsBeans);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: " + t.getMessage());
                        view.showErrorMsg(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

    }

    private static <T> Flowable<T> createData(final T t){
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                try {
                    e.onNext(t);
                    e.onComplete();
                } catch (Exception exception) {
                    e.onError(exception);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    public static void getArticle(String url, final Callback<String> myCallback) {
        Request request = new Request.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Safari/604.1.38")
                .url(Constant.MEIWEN_HOST + url)
                .build();
        Call call = BuildService.getInstance().getHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        myCallback.onFail("网络访问失败，请检查网络重试");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "getArticle onResponse: ");
                InputStream inputStream = null;
                StringBuilder tempResult = new StringBuilder();
                try {
                    inputStream = response.body().byteStream();
                    InputStreamReader instreamReader = new InputStreamReader(inputStream, "utf-8");
                    BufferedReader buffStr = new BufferedReader(instreamReader);
                    String temp = "";
                    while ((temp = buffStr.readLine()) != null) {
                        tempResult.append(temp + "\n");
                    }
                    inputStream.close();
                    final String result = tempResult.toString();
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            myCallback.onSuccess(result);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(inputStream != null) {
                        inputStream.close();
                    }
                    tempResult = null;
                }
            }
        });
    }

}
