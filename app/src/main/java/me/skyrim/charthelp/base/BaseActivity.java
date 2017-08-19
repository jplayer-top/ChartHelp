package me.skyrim.charthelp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import me.skyrim.charthelp.R;

/**
 * Created by Obl on 2017/8/18.
 * me.skyrim.charthelp
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar mToolbar;
    public FrameLayout mFlContent;
    protected View mRootView;
    public BaseActivity mBaseActivity;
    public Bundle mBundle;
    public TextView mTvToolBarRight;
    public TextView mTvTitle;
    public ImageView mIvToolBarLeft;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mFlContent = (FrameLayout) findViewById(R.id.flContent);
        mTvToolBarRight = (TextView) findViewById(R.id.tvToolBarRight);
        mIvToolBarLeft = (ImageView) findViewById(R.id.ivToolBarLeft);
        mTvTitle = (TextView) findViewById(R.id.tvTitle);
        initState(savedInstanceState);
        createContentView();
        mBaseActivity = this;
        mIvToolBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftButtonClick();
            }
        });
        mTvToolBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightButtonClick();
            }
        });
    }

    public void rightButtonClick() {

    }

    public void leftButtonClick() {
        finish();
    }

    private void createContentView() {
        mFlContent.removeAllViews();
        mRootView = initView();
        ButterKnife.bind(this, mRootView);
        mFlContent.addView(mRootView);
        bindData();
    }

    public abstract void bindData();

    public View initView() {
        return View.inflate(this, initLayout(), null);
    }

    public abstract int initLayout();

    protected void initState(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
