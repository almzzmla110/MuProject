package com.example.muproject.ui.home.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.Dimension;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.muproject.R;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

/**
 * ****************************************
 * Created By LiXin  2020/11/9 10:14
 * ****************************************
 */
public class PcViewUtil {
    public static Drawable getLoadingDrawable(Context context) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, R.drawable.cc_loading));
        DrawableCompat.setTint(drawable, context.getResources().getColor(android.R.color.holo_blue_light));
        return drawable;
//        return ContextCompat.getDrawable(context, R.drawable.cc_loading);
    }

    //    public static int getColorPrimary(Context context) {
//
//        TypedValue typedValue = new TypedValue();
//        context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.colorPrimary, typedValue, true);
//        return typedValue.data;
//    }
    public static int dpToPx(Context context, @Dimension(unit = COMPLEX_UNIT_DIP) int dp) {
        return (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int getColorPrimary(Context context) {
        int[] attrsArray = {R.attr.colorPrimary};
        TypedArray typedArray = context.obtainStyledAttributes(attrsArray);
        int accentColor = typedArray.getColor(0, 0xff297be8);
        typedArray.recycle();
        return accentColor;
    }
}

