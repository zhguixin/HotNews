package site.zhguixin.hotnews.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
 * 实现懒加载机制，只有第一次可见的时候去加载数据，之后再可见不再去加载数据，通过下拉刷新去加载
 */
public abstract class LazyFragment extends Fragment {
    private static final String TAG = "LazyFragment";

    public View mView;
    private boolean isFirstVisile;

    public LazyFragment() {
        // Required empty public constructor
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(mView == null) {
            return;
        }
        if (isFirstVisile && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisile = false;
        }
        onFragmentVisible(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstVisile = true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = view;
        }
        super.onViewCreated(view, savedInstanceState);
    }

    public abstract void onFragmentFirstVisible();
    public abstract void onFragmentVisible(boolean isVisible);
}
