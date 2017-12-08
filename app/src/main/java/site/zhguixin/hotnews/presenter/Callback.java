package site.zhguixin.hotnews.presenter;

/**
 * Created by zhguixin on 2017/12/8.
 * 网络访问成功与否的回调
 */

public interface Callback<T> {
    void onSuccess(T info);
    void onFail(String error);
}
