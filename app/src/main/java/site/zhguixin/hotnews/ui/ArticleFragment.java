package site.zhguixin.hotnews.ui;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import site.zhguixin.hotnews.R;
import site.zhguixin.hotnews.presenter.ArticleContract;
import site.zhguixin.hotnews.presenter.ArticlePresenter;
import site.zhguixin.hotnews.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends LazyFragment implements ArticleContract.View {
    private static final String TAG = "ArticleFragment";

    private ArticlePresenter mPresenter;
    private Unbinder mUnbinder;
    private FloatingActionButton mFloatingBtn;
    private AnimatorSet mAnimatorSet;

    @BindView(R.id.background_view)
    FrameLayout mBackgroundView;

    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;

    @BindView(R.id.title_view)
    TextView mTitleView;

    @BindView(R.id.author_view)
    TextView mAuthotView;

    @BindView(R.id.content_view)
    TextView mContentView;

    private SimpleTarget<GlideDrawable> mBackgroundTarget = new SimpleTarget<GlideDrawable>() {
        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
            mBackgroundView.setBackground(resource);
        }
    };

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter = new ArticlePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mPresenter.attachView(this);
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mAnimatorSet = new AnimatorSet();

        mFloatingBtn = view.getRootView().findViewById(R.id.fab);
        mFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "再换一篇文章？", Snackbar.LENGTH_LONG)
                        .setAction("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(),"hold on...",Toast.LENGTH_SHORT).show();
                                mPresenter.getArticle("random/");
                            }
                        }).show();
            }
        });

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY + 15) {
//                    Log.d(TAG, "onScrollChange: Down");
                    fabHide();
                } else if (scrollY + 15 < oldScrollY){
//                    Log.d(TAG, "onScrollChange: Up");
                    fabShow();
                }
            }
        });
        /*int random_bg = 1;
        Glide.with(getActivity().getApplicationContext())
                .load("https://meiriyiwen.com/images/new_feed/bg_"+random_bg+".jpg")
                .into(mBackgroundTarget);*/
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showContent(Map<String, String> article) {
        mTitleView.setText(article.get(Constant.TITLE));
        mAuthotView.setText(article.get(Constant.AUTHOR));
        mContentView.setText(article.get(Constant.CONTENT));
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onFragmentFirstVisible() {
        mPresenter.getArticle("");
    }

    @Override
    public void onFragmentVisible(boolean isVisible) {
        Log.d(TAG, "onFragmentVisible: isVisible=" + isVisible);
        if (isVisible) {
            mFloatingBtn.setVisibility(View.VISIBLE);
        }
    }

    private void fabShow() {
        if (mFloatingBtn.getAlpha() == 1f|| mAnimatorSet.isRunning()) {
            return;
        }

        ValueAnimator animatorX = ObjectAnimator.ofFloat(mFloatingBtn,"scaleX",0f,1f);
        ValueAnimator animatorY = ObjectAnimator.ofFloat(mFloatingBtn,"scaleY",0f,1f);
        ValueAnimator animatorAlpha = ObjectAnimator.ofFloat(mFloatingBtn, "alpha", 0f,1f);
        mAnimatorSet.setDuration(500);
        mAnimatorSet.playTogether(animatorX,animatorY,animatorAlpha);
        mAnimatorSet.start();
    }

    private void fabHide() {
        if (mFloatingBtn.getAlpha() == 0f || mAnimatorSet.isRunning()) {
            return;
        }

        ValueAnimator animatorX = ObjectAnimator.ofFloat(mFloatingBtn,"scaleX",1f,0f);
        ValueAnimator animatorY = ObjectAnimator.ofFloat(mFloatingBtn,"scaleY",1f,0f);
        ValueAnimator animatorAlpha = ObjectAnimator.ofFloat(mFloatingBtn, "alpha", 1f,0f);
        mAnimatorSet.setDuration(500);
        mAnimatorSet.playTogether(animatorX,animatorY,animatorAlpha);
        mAnimatorSet.start();
    }
}
