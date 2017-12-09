package site.zhguixin.hotnews.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by zhguixin on 2017/12/9.
 */

public class AppExecutors {

    private Executor mMainThread;

    public AppExecutors() {
        this(new MainThreadExecutor());
    }

    private AppExecutors(Executor mainThread) {
        mMainThread = mainThread;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {

        Handler mainHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainHandler.post(command);
        }
    }
}
