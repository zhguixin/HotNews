package site.zhguixin.hotnews.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhguixin on 2017/12/7.
 */

public class FragmentControler {

    private Context mContext;
    private static FragmentControler INSTANCE;
    private List<Fragment> mListFragments;
    private int mContainerId;
    private FragmentManager mManager;

    public static FragmentControler getInstance(Context context, int containerId) {
        if (INSTANCE == null) {
            INSTANCE = new FragmentControler(context, containerId);
        }
        return INSTANCE;
    }

    private FragmentControler(Context context, int containerId) {
        mContext = context;
        mContainerId = containerId;
        mManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
        initFragment();
    }

    // 想加载所有的Fragment
    private void initFragment() {
        mListFragments = new ArrayList<>();
        mListFragments.add(new WxNewsFragment());
        mListFragments.add(new ZhiHuFragment());
        mListFragments.add(new GankFragment());
        mListFragments.add(new ArticleFragment());

        FragmentTransaction transaction = mManager.beginTransaction();
        for (Fragment fragment : mListFragments) {
            transaction.add(mContainerId, fragment);
        }
        transaction.commit();
    }

    // 根据具体位置显示具体的Fragment
    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = mListFragments.get(position);
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.show(fragment);
        fragment.setUserVisibleHint(true);
        transaction.commit();
    }

    public Fragment getFragment(int position) {
        return mListFragments.get(position);
    }

    private void hideFragments() {
        FragmentTransaction transaction = mManager.beginTransaction();
        for (Fragment fragment : mListFragments) {
            transaction.hide(fragment);
            fragment.setUserVisibleHint(false);
        }
        transaction.commit();
    }

    public static void destoryControler() {
        INSTANCE = null;
    }
}
