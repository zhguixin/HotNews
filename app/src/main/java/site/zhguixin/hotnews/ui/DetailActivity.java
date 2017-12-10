package site.zhguixin.hotnews.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.zhguixin.hotnews.R;
import site.zhguixin.hotnews.utils.Constant;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.toString();
    private String mUrl;
    private String mTitle;
    private Context mContext;

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.tip_progress)
    ProgressBar mTipProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mContext = this;

        ActionBar actionBar = getSupportActionBar();


        Intent intent = getIntent();
        mUrl = intent.getExtras().getString(Constant.URL);
        mTitle = intent.getExtras().getString(Constant.TITLE);
        if (actionBar != null) {
            actionBar.setTitle(mTitle);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        init();
    }

    private void init() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG, "onProgressChanged: " + newProgress);
                if (newProgress == 100) {
                    mTipProgress.setVisibility(View.GONE);
                } else {
                    mTipProgress.setVisibility(View.VISIBLE);
                    mTipProgress.setProgress(newProgress);
                }
            }
        });

        mWebView.loadUrl(mUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void launch(Builder builder) {
        Intent intent = new Intent();
        intent.setClass(builder.mContext, DetailActivity.class);
        intent.putExtra(Constant.URL, builder.mUrl);
        intent.putExtra(Constant.TITLE, builder.mTitle);
        builder.mContext.startActivity(intent);
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mDescription;

        private String mUrl;

        public Builder() {
        }

        public Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setDescription(String description) {
            mDescription = description;
            return this;
        }
        public Builder setUrl(String url) {
            mUrl = url;
            return this;
        }
    }
}
