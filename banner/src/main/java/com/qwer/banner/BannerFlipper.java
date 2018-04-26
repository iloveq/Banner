package com.qwer.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoran on 2018/4/8.
 */

public class BannerFlipper extends RelativeLayout {

    public String tag = "com.qwer.banner.BannerFlipper";
    private Context context;
    private ViewFlipper mFlipper;

    private int mIndicatorMargin = BannerConfig.PADDING_SIZE;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int indicatorSize;
    private int mIndicatorSelectedResId = R.drawable.dot_select;
    private int mIndicatorUnselectedResId = R.drawable.dot;
    private int bannerStyle = BannerConfig.CIRCLE_INDICATOR;
    private int delayTime = BannerConfig.TIME;
    private int scrollTime = BannerConfig.DURATION;
    private boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;
    private boolean isScroll = BannerConfig.IS_SCROLL;
    private int mBannerLoadingViewImgRes;
    private int scaleType = 1;
    private LinearLayout indicator;
    private GestureDetector mGesture;
    private int button;
    private List imageUrls;
    private List<View> imageViews;
    private List<ImageView> indicatorImages;
    private OnBannerListener mListener;
    private View mBannerLoadingView;
    private int count = 0;
    private float mFlipGap;
    private ImageLoaderInterface imageLoader;
    private TranslateAnimation rightInAnim, leftOutAnim, rightOutAnim, leftInAnim;

    public BannerFlipper(Context context) {
        this(context, null, 0);
    }

    public BannerFlipper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerFlipper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mFlipGap = ViewConfiguration.get(context).getScaledTouchSlop();
        handleTypedArray(attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_flipper, this, true);
        mFlipper = view.findViewById(R.id.vf_banner);
        mBannerLoadingView = view.findViewById(R.id.view_banner_loading);
        indicator = view.findViewById(R.id.ll_homepage_bannerdot);
        mFlipper.setAutoStart(isScroll);
        mFlipper.setFlipInterval(delayTime);
        mGesture = new GestureDetector(new BannerGestureDetectorListener(this));
        imageUrls = new ArrayList<>();
        imageViews = new ArrayList<>();
        indicatorImages = new ArrayList<>();
        // 图片从右侧滑入
        rightInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        rightInAnim.setDuration(1000);

        // 图片从左侧滑出
        leftOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        leftOutAnim.setDuration(1000);

        // 图片从右侧滑出
        rightOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        rightOutAnim.setDuration(1000);

        // 图片从左侧滑入
        leftInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        leftInAnim.setDuration(1000);

    }

    private void handleTypedArray(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        mIndicatorWidth = typedArray.getDimensionPixelSize(R.styleable.Banner_banner_indicator_width, indicatorSize);
        mIndicatorHeight = typedArray.getDimensionPixelSize(R.styleable.Banner_banner_indicator_height, indicatorSize);
        mIndicatorMargin = typedArray.getDimensionPixelSize(R.styleable.Banner_banner_indicator_margin, BannerConfig.PADDING_SIZE);
        mIndicatorSelectedResId = typedArray.getResourceId(R.styleable.Banner_banner_indicator_drawable_selected, R.drawable.dot_select);
        mIndicatorUnselectedResId = typedArray.getResourceId(R.styleable.Banner_banner_indicator_drawable_unselected, R.drawable.dot);
        scaleType = typedArray.getInt(R.styleable.Banner_banner_image_scale_type, scaleType);
        delayTime = typedArray.getInt(R.styleable.Banner_banner_delay_time, BannerConfig.TIME);
        scrollTime = typedArray.getInt(R.styleable.Banner_banner_scroll_time, BannerConfig.DURATION);
        isAutoPlay = typedArray.getBoolean(R.styleable.Banner_banner_is_auto_play, BannerConfig.IS_AUTO_PLAY);
        mBannerLoadingViewImgRes = typedArray.getResourceId(R.styleable.Banner_banner_default_image, R.drawable.no_banner);
        typedArray.recycle();
    }

    public void setOnBannerListener(OnBannerListener listener) {
        mListener = listener;
    }

    public void start() {
        startFlip();
    }


    public void stopAutoPlay() {
        stopFlip();
    }

    private void stopFlip() {
        mFlipper.stopFlipping();
    }

    public BannerFlipper setImages(List<?> imageUrls) {
        this.imageUrls = imageUrls;
        this.count = imageUrls.size();
        setImageList(imageUrls);
        return this;
    }

    //向左滑
    private void startFlip() {

        mFlipper.startFlipping();
        mFlipper.setInAnimation(rightInAnim);
        mFlipper.setOutAnimation(leftOutAnim);
        mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));
        mFlipper.showNext();
    }

    //向右划
    private void backFlip() {
        mFlipper.startFlipping();
        mFlipper.setInAnimation(leftInAnim);
        mFlipper.setOutAnimation(rightOutAnim);
        mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));
        mFlipper.showPrevious();
    }

    private void setImageList(List<?> imagesUrl) {
        mBannerLoadingView.setVisibility(GONE);
        for (int i = 0; i < imagesUrl.size(); i++) {
            ImageView ivNew = new ImageView(context);
            ivNew.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            ivNew.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageLoader.displayImage(context, imagesUrl.get(i), ivNew);
            mFlipper.addView(ivNew);
        }

        count = imagesUrl.size();
        initSmallPoints();
        mFlipper.setDisplayedChild(count - 1);
        startFlip();
    }

    public void initSmallPoints() {
        imageViews.clear();
        indicatorImages.clear();
        indicator.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = mIndicatorMargin;
            params.rightMargin = mIndicatorMargin;
            if (i == 0) {
                imageView.setImageResource(mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(mIndicatorUnselectedResId);
            }
            indicatorImages.add(imageView);
            indicator.addView(imageView, params);

        }
    }

    private void updateSmallPoints(int position) {
        if (count > 1) {
            for (int m = 0; m < count; m++) {
                if (m == position % count) {
                    indicatorImages.get(m).setImageResource(mIndicatorSelectedResId);
                } else {
                    indicatorImages.get(m).setImageResource(mIndicatorUnselectedResId);
                }
            }
        }
    }

    public void setImageLoader(ImageLoaderInterface imageLoader) {
        this.imageLoader = imageLoader;
    }

    private class BannerGestureDetectorListener implements GestureDetector.OnGestureListener {

        BannerGestureDetectorListener(BannerFlipper bannerFlipper) {
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position = mFlipper.getDisplayedChild();
            if (mListener != null)
                mListener.onBannerClick(position);
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            boolean result = false; // true表示要继续处理
            if (Math.abs(distanceY) < Math.abs(distanceX)) {
                BannerFlipper.this.getParent().requestDisallowInterceptTouchEvent(false);
                result = true;
            }
            return result;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            stopAutoPlay();
            if (e1.getX() - e2.getX() > mFlipGap) {
                startFlip();
                return true;
            }
            if (e1.getX() - e2.getX() < -mFlipGap) {
                backFlip();
                return true;
            }
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        // 执行touch 事件
        super.onTouchEvent(event);
        return mGesture.onTouchEvent(event);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        this.getParent().getParent().requestDisallowInterceptTouchEvent(true);
        mGesture.onTouchEvent(event);
        return true;
    }

    private class BannerAnimationListener implements Animation.AnimationListener {
        BannerAnimationListener(BannerFlipper bannerFlipper) {
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            int position = mFlipper.getDisplayedChild();
            updateSmallPoints(position);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}
