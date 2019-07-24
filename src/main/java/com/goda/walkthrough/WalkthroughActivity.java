package com.goda.walkthrough;

import android.graphics.drawable.Drawable;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.goda.walkthrough.transformation.AccordionTransformer;
import com.goda.walkthrough.transformation.BackgroundToForegroundTransformer;
import com.goda.walkthrough.transformation.DepthPageTransformer;
import com.goda.walkthrough.transformation.ForegroundToBackgroundTransformer;
import com.goda.walkthrough.transformation.ScaleInOutTransformer;
import com.goda.walkthrough.transformation.StackTransformer;
import com.goda.walkthrough.transformation.ZoomOutSlideTransformer;
import com.goda.walkthrough.transformation.ZoomOutTranformer;


public abstract class WalkthroughActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int BAR_TYPE = 0;
    public static final int DOTS_TYPE = 1;

    public static final int ACCORDION_TRANSFORMER = 1;
    public static final int BACK_TO_FORE_TRANSFORMER = 2;
    public static final int FORE_TO_BACK_TRANSFORMER = 3;
    public static final int DEPTH_TRANSFORMER = 4;
    public static final int SCALE_IN_OUT_TRANSFORMER = 5;
    public static final int STACK_TRANSFORMER = 6;
    public static final int ZOOM_OUT_SLIDE_TRANSFORMER = 7;
    public static final int ZOOM_OUT_TRANSFORMER = 8;


    Boolean mShowProgress = true;
    int mProgressType = DOTS_TYPE;

    ProgressBar mProgressBar;
    LinearLayout pager_indicator;
    WalkthroughPagerAdapter mAdapter;
    ViewPager mViewPager;
    Button enjoyBtn;
    ImageView ivPrev;
    ImageView ivNext;
    int dotsCount;
    ImageView[] dots;
    int mCurrentPosition;

    Drawable mSelectedDot;
    Drawable mNormalDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        mSelectedDot = getResources().getDrawable(R.drawable.selecteditem_dot);
        mNormalDot = getResources().getDrawable(R.drawable.nonselecteditem_dot);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        enjoyBtn = (Button) findViewById(R.id.enjoyBtn);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPrev = (ImageView) findViewById(R.id.ivPrev);
        mAdapter = new WalkthroughPagerAdapter(this);
        mProgressBar = (ProgressBar) findViewById(R.id.walkthrough_progressbar);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        setListener();
        setViewPagerSetting();
    }

    private void setViewPagerSetting() {
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                mProgressBar.setProgress((mCurrentPosition + 1) * (100 / mAdapter.getCount()));

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(mNormalDot);
                }
                dots[position].setImageDrawable(mSelectedDot);


                int firstPagePosition = 0;
                int lastPagePosition = mAdapter.getCount() - 1;
                if (position == lastPagePosition) {
                    pager_indicator.setVisibility(View.GONE);
                    ivPrev.setVisibility(View.GONE);
                    ivNext.setVisibility(View.GONE);

                    fadeIn(enjoyBtn);
                } else if (firstPagePosition == position) {
                    ivPrev.setVisibility(View.GONE);
                    ivNext.setVisibility(View.VISIBLE);
                    pager_indicator.setVisibility(View.VISIBLE);
                    enjoyBtn.setVisibility(View.GONE);

                } else {
                    enjoyBtn.setVisibility(View.GONE);
                    ivPrev.setVisibility(View.VISIBLE);
                    ivNext.setVisibility(View.VISIBLE);
                    pager_indicator.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setListener() {
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        enjoyBtn.setOnClickListener(this);

    }

    private void fadeOut(View v) {
        fadeOut(v, true);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        boolean isInFirstPage = mViewPager.getCurrentItem() == 0;
        boolean isInLastPage = mViewPager.getCurrentItem() == mAdapter.getCount() - 1;

        if (i == R.id.enjoyBtn && isInLastPage) {
            onFinishButtonPressed();
            onFinish();
        } else if (i == R.id.ivPrev && !isInFirstPage) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        } else if (i == R.id.ivNext && !isInLastPage) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    }

    abstract public void onFinishButtonPressed();

    private void fadeOut(final View v, boolean delay) {

        long duration = 0;
        if (delay) {
            duration = 300;
        }

        if (v.getVisibility() != View.GONE) {
            Animation fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
            fadeOut.setDuration(duration);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            v.startAnimation(fadeOut);
        }
    }

    private void fadeIn(final View v) {

        if (v.getVisibility() != View.VISIBLE) {
            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(200);
            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    v.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            v.startAnimation(fadeIn);
        }
    }

    public void updateProgress() {
        mProgressBar.setProgress(100 / mAdapter.getCount());
        dotsCount = mAdapter.getCount();
        pager_indicator.removeAllViews();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(mNormalDot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(15, 0, 15, 0);
            pager_indicator.addView(dots[i], params);
        }
        dots[0].setImageDrawable(mSelectedDot);
    }

    public void setProgressType(int progressType) {
        mProgressType = progressType;
        if (mProgressType == BAR_TYPE) {
            mProgressBar.setVisibility(View.VISIBLE);
            pager_indicator.setVisibility(View.GONE);
        } else if (mProgressType == DOTS_TYPE) {
            mProgressBar.setVisibility(View.GONE);
            pager_indicator.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        mShowProgress = false;
        mProgressBar.setVisibility(View.GONE);
        pager_indicator.setVisibility(View.GONE);
    }

    public void setTransitionType(int transitionType) {
        switch (transitionType) {
            case ACCORDION_TRANSFORMER:
                mViewPager.setPageTransformer(true, new AccordionTransformer());
                break;
            case BACK_TO_FORE_TRANSFORMER:
                mViewPager.setPageTransformer(true, new BackgroundToForegroundTransformer());
                break;
            case FORE_TO_BACK_TRANSFORMER:
                mViewPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());
                break;
            case DEPTH_TRANSFORMER:
                mViewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case SCALE_IN_OUT_TRANSFORMER:
                mViewPager.setPageTransformer(true, new ScaleInOutTransformer());
                break;
            case STACK_TRANSFORMER:
                mViewPager.setPageTransformer(true, new StackTransformer());
                break;
            case ZOOM_OUT_SLIDE_TRANSFORMER:
                mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
                break;
            case ZOOM_OUT_TRANSFORMER:
                mViewPager.setPageTransformer(true, new ZoomOutTranformer());
                break;
        }
    }

    public void addPage(WalkthroughItem walkthroughItem) {
        mAdapter.addItem(walkthroughItem);
        mAdapter.notifyDataSetChanged();
        updateProgress();
    }

    public void hideSkipButton() {
        // mSkipBtn.setVisibility(View.GONE);
    }

    public void setProgressBarColor(int color) {
        Drawable drawable = mProgressBar.getProgressDrawable();
        // drawable.setColorFilter(new LightingColorFilter(0x00000000, getResources().getColor(color)));
        // mSelectedDot.setColorFilter(new LightingColorFilter(0x00000000, getResources().getColor(color)));
        //mNormalDot.setColorFilter(new LightingColorFilter(0xFFFFFFFF, getResources().getColor(color)));
        updateProgress();
    }


    public void onFinish() {
        //finish();
    }

}
