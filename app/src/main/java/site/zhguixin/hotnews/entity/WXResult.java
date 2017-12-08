package site.zhguixin.hotnews.entity;

import java.util.List;

/**
 * Created by zhguixin on 2017/12/2.
 */

public class WXResult<T> {

    private int code;
    private String msg;
    private T newslist;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setNewslist(T newslist) {
        this.newslist = newslist;
    }
    public T getNewslist() {
        return newslist;
    }
}
