package site.zhguixin.hotnews.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import site.zhguixin.hotnews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiHuFragment extends Fragment {


    public ZhiHuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_zhi_hu, container, false);
    }

}
