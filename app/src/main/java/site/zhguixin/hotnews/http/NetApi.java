package site.zhguixin.hotnews.http;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import site.zhguixin.hotnews.entity.WXNewsBean;
import site.zhguixin.hotnews.entity.WXResult;

/**
 * Created by zhguixin on 2017/12/3.
 */

public interface NetApi {

    @GET("wxnew")
    Flowable<WXResult<List<WXNewsBean>>> getWXHot(@Query("key") String key,
                                                  @Query("num") int num,
                                                  @Query("page") int page);
}
