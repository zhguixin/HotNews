package site.zhguixin.hotnews.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhguixin on 2017/12/3.
 */

public class BuildService {

    private static BuildService INSTANCE;
    private Retrofit.Builder mBuilder;
    OkHttpClient.Builder mClientBuilder;

    public static BuildService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BuildService();
        }
        return INSTANCE;
    }

    private BuildService() {
        mBuilder = new Retrofit.Builder();
        mClientBuilder = new OkHttpClient.Builder();
        mClientBuilder.readTimeout(20, TimeUnit.SECONDS);
        mClientBuilder.writeTimeout(20,TimeUnit.SECONDS);
        mClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
    }


    public NetApi buildService(String host_url) {
        return createRetrofit(host_url).create(NetApi.class);
    }

    private Retrofit createRetrofit(String host_url) {
        return mBuilder.baseUrl(host_url)
                .client(mClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public OkHttpClient getHttpClient() {
        return mClientBuilder.build();
    }
}
