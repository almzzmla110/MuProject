package com.example.muproject.ui.home.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.muproject.R;

/**
 * ****************************************
 * Created By LiXin  2020/11/9 10:12
 * ****************************************
 */
public class StateFrameLayout extends FrameLayout {
    private static final String TAG = "StateView";
    private String errorMessage, emptyMessage, loadingMessage, netErrorMessage;
    private Drawable errorDrawable, emptyDrawable, loadingDrawable, netErrorDrawable;

    private StateViewCover stateViewCover;

    public enum ViewState {LOADING, CONTENT, EMPTY, NET_ERROR, OTHER_ERROR}

    private OnReloadListener onReloadListener;

    private static final OnClickListener DO_NOTHING_ON_CLICK_LISTENER = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public enum Size {
        LARGE, SMALL
    }

    private Size size = Size.LARGE;
    /**
     * 防止loading闪屏
     */
    private boolean avoidLoadingFlash = true;

    public StateFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

    }

    public StateFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    private void init(AttributeSet attrs, int defStyleAttr) {
        stateViewCover = new StateViewCover(getContext());
        addView(stateViewCover, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        stateViewCover.setOnClickListener(DO_NOTHING_ON_CLICK_LISTENER);
        if (attrs != null) {
            obtainAttributes(attrs, defStyleAttr);
        }

        setViewStatus(isInEditMode() ? ViewState.CONTENT : ViewState.LOADING);

    }

    private void obtainAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StateFrameLayout, defStyleAttr, 0);


        errorMessage = typedArray.getString(R.styleable.StateFrameLayout_error_message);
        emptyMessage = typedArray.getString(R.styleable.StateFrameLayout_empty_message);
        loadingMessage = typedArray.getString(R.styleable.StateFrameLayout_loading_message);
        netErrorMessage = typedArray.getString(R.styleable.StateFrameLayout_net_error_message);

        errorDrawable = typedArray.getDrawable(R.styleable.StateFrameLayout_error_drawable);
        emptyDrawable = typedArray.getDrawable(R.styleable.StateFrameLayout_empty_drawable);
        loadingDrawable = typedArray.getDrawable(R.styleable.StateFrameLayout_loading_drawable);
        netErrorDrawable = typedArray.getDrawable(R.styleable.StateFrameLayout_net_error_drawable);

        size = Size.values()[typedArray.getInt(R.styleable.StateFrameLayout_size, 0)];

        stateViewCover.setSize(size);
        typedArray.recycle();
        Drawable retry = getResources().getDrawable(R.drawable.cc_retry);
        if (errorDrawable == null) {
            errorDrawable = retry;
        }
        if (netErrorDrawable == null) {
            netErrorDrawable = retry;
        }
        if (emptyDrawable == null) {
            emptyDrawable = getResources().getDrawable(R.drawable.cc_empty);

        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        if (stateViewCover != null) {
            stateViewCover.setBackground(background);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (stateViewCover != null) {
            stateViewCover.setBackgroundColor(color);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    private <T> T defaultIfNull(T e, T df) {
        return e == null ? df : e;
    }


    private int getColorPrimary() {


        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    private void setViewStatus(ViewState status) {
        if (status == null) {
            throw new IllegalArgumentException("status == null");
        }
        switch (status) {
            case LOADING:

                // 防止loading闪屏
                if (avoidLoadingFlash && stateViewCover.getVisibility() == GONE) {
                    break;
                }
                String message = defaultIfNull(loadingMessage, getResources().getString(R.string.view_state_loading_msg));
                showState(message, defaultIfNull(loadingDrawable, new LoadingDrawable(getContext())), false);

                break;
            case EMPTY:
                showState(defaultIfNull(emptyMessage, getResources().getString(R.string.view_state_empty_msg)), emptyDrawable, true);
                break;
            case CONTENT:
                stateViewCover.clearAnimator();
                stateViewCover.animate().cancel();
                stateViewCover.animate()
                        .alpha(0)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .setDuration(200)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                stateViewCover.setVisibility(GONE);
                            }
                        })
                        .start();
                break;
            case NET_ERROR:
                showState(defaultIfNull(netErrorMessage, getResources().getString(R.string.view_state_net_error_msg)), netErrorDrawable, true);
                break;
            case OTHER_ERROR:
                showState(defaultIfNull(errorMessage, getResources().getString(R.string.view_state_error_msg)), errorDrawable, true);
                break;
        }

    }

    private void showState(final String message, final Drawable drawable, boolean reloadEnable) {
        stateViewCover.clearAnimator();


        if (reloadEnable) {
            if (onReloadListener == null) {
                throw new RuntimeException("please set onReloadListener before");
            }
            stateViewCover.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoadingView();
                    if (onReloadListener == null) {
                        throw new RuntimeException("please set onReloadListener before");
                    }
                    onReloadListener.onReload(StateFrameLayout.this);
                }
            });
        } else {
            stateViewCover.setOnClickListener(DO_NOTHING_ON_CLICK_LISTENER);
        }

        if (stateViewCover.getVisibility() == VISIBLE) {
            // 切换状态
            stateViewCover.bringToFront();
            stateViewCover.animate().cancel();
            stateViewCover.animate()
                    .alpha(0)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            stateViewCover.setMessage(message);
                            stateViewCover.setDrawable(drawable);
                            stateViewCover.animate()
                                    .alpha(1)
                                    .setInterpolator(new AccelerateDecelerateInterpolator())
                                    .setDuration(200)
                                    .start();
                        }
                    })
                    .setDuration(200)
                    .start();
        } else {
            stateViewCover.setMessage(message);
            stateViewCover.setDrawable(drawable);
            stateViewCover.setAlpha(0);
            stateViewCover.setVisibility(VISIBLE);
            stateViewCover.bringToFront();
            // 从显示内容到显示状态
            stateViewCover.animate().cancel();
            stateViewCover.animate()
                    .alpha(1)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setDuration(200)
                    .start();
        }
    }


    public void showViewState(ViewState viewState) {
        showViewState(viewState, null, null);
    }

    public void showViewState(ViewState viewState, String message, Drawable drawable) {
        switch (viewState) {
            case LOADING:
                if (!TextUtils.isEmpty(message)) {
                    loadingMessage = message;
                }
                if (drawable != null) {
                    loadingDrawable = drawable;
                }
                break;
            case EMPTY:
                if (!TextUtils.isEmpty(message)) {
                    emptyMessage = message;
                }
                if (drawable != null) {
                    emptyDrawable = drawable;
                }
                break;
            case NET_ERROR:
                if (!TextUtils.isEmpty(message)) {
                    netErrorMessage = message;
                }
                if (drawable != null) {
                    netErrorDrawable = drawable;
                }
                break;
            case OTHER_ERROR:
                if (!TextUtils.isEmpty(message)) {
                    errorMessage = message;
                }
                if (drawable != null) {
                    errorDrawable = drawable;
                }
                break;
        }

        setViewStatus(viewState);

    }

    /**
     * 设置防止loading闪屏
     *
     * @param avoidLoadingFlash
     */
    public void setAvoidLoadingFlash(boolean avoidLoadingFlash) {
        this.avoidLoadingFlash = avoidLoadingFlash;
    }

    public void showContentView() {
        setViewStatus(ViewState.CONTENT);
    }

    public void showLoadingView() {
        setViewStatus(ViewState.LOADING);
    }

    public void showErrorView() {
        setViewStatus(ViewState.OTHER_ERROR);
    }

    public void showNetErrorView() {
        setViewStatus(ViewState.NET_ERROR);
    }

    public void showEmptyView() {
        setViewStatus(ViewState.EMPTY);
    }

    public interface OnReloadListener {
        void onReload(View view);
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    static class StateViewCover extends RelativeLayout {

        private final TextView textView;
        private final ImageView imageView;
        private final LinearLayout linearLayout;

        public StateViewCover(Context context) {
            super(context);
            linearLayout = new LinearLayout(context);
            LayoutParams containerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            containerParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

            imageView = new ImageView(context);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageParams.bottomMargin = PcViewUtil.dpToPx(getContext(), 15);
            linearLayout.addView(imageView, imageParams);
            textView = new TextView(context);
            textView.setSingleLine();
            linearLayout.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            setBackgroundColor(Color.WHITE);
            textView.setTextColor(Color.parseColor("#7a7a7a"));
            addView(linearLayout, containerParams);

        }

        public void setSize(Size size) {
            if (size == Size.LARGE) {
                imageView.setScaleX(1);
                imageView.setScaleY(1);
                textView.setTextSize(14);
                ((MarginLayoutParams) imageView.getLayoutParams()).bottomMargin = PcViewUtil.dpToPx(getContext(), 12);
                imageView.requestLayout();
            } else {
                imageView.setScaleX(0.7f);
                imageView.setScaleY(0.7f);
                textView.setTextSize(12);
                ((MarginLayoutParams) imageView.getLayoutParams()).bottomMargin = PcViewUtil.dpToPx(getContext(), 8);
                imageView.requestLayout();
            }
        }

        @Override
        public void setOnClickListener(OnClickListener l) {
            super.setOnClickListener(l);
            linearLayout.setOnClickListener(l);
        }

        public void setMessage(String message) {
            textView.setText(message);
            requestLayout();
        }

        public void clearAnimator() {
            super.clearAnimation();
            animate().cancel();
            if (imageView.getDrawable() instanceof AnimationDrawable) {
                ((AnimationDrawable) imageView.getDrawable()).stop();
            }
            imageView.clearAnimation();

        }

        public void setDrawable(Drawable drawable) {
            imageView.setImageDrawable(drawable);
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
        }
    }

}
