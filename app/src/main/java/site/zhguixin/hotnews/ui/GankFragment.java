package site.zhguixin.hotnews.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import site.zhguixin.hotnews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFragment extends LazyFragment {
    private static final String TAG = "GankFragment";

    public GankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onFragmentFirstVisible() {
        Log.d(TAG, "onFragmentFirstVisible: ");
    }

    @Override
    public void onFragmentVisible(boolean isVisible) {
        Log.d(TAG, "onFragmentVisible: isVisible=" + isVisible);
        if (isVisible) {
            mView.getRootView().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
        }
    }
}
