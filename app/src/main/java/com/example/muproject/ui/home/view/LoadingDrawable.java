package com.example.muproject.ui.home.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;


public class LoadingDrawable extends DrawableWrapper implements Animatable {

    private ValueAnimator valueAnimator;

    public LoadingDrawable(Context context) {
        this(PcViewUtil.getLoadingDrawable(context));
    }

    public LoadingDrawable(@NonNull Drawable drawable) {
        super(drawable);


    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        Drawable drawable = getWrappedDrawable();
        if (drawable != null) {
            int index = canvas.save();
            if (valueAnimator != null) {
                canvas.rotate((int) valueAnimator.getAnimatedValue(), getIntrinsicWidth() / 2, getIntrinsicHeight() / 2);
            }
            drawable.draw(canvas);
            canvas.restoreToCount(index);
        }

    }


    @Override
    public void start() {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 360);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    invalidateSelf();
                }
            });
            valueAnimator.setDuration(800);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        }
        valueAnimator.start();
    }

    @Override
    public void stop() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override
    public boolean isRunning() {
        return valueAnimator != null && valueAnimator.isRunning();
    }
}
