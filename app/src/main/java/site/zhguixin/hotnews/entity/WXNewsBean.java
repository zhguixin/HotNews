package site.zhguixin.hotnews.entity;

/**
 * Created by zhguixin on 2017/12/2.
 */

public class WXNewsBean {

    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getCtime() {
        return ctime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "WXNewsBean{" +
                "ctime='" + ctime + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
