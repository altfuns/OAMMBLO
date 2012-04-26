package com.fr4gus.android.oammblo.util;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UIUtil {
    public static void setFont(View view, Typeface font) {

        if (view == null) {
            return;
        }

        if (view instanceof ViewGroup) {
            for (int i = 0, lim = ((ViewGroup) view).getChildCount(); i < lim; i++) {
                setFont(((ViewGroup) view).getChildAt(i), font);
            }
            return;
        }

        if (view instanceof TextView) {
            ((TextView) view).setTypeface(font);
        }
    }

}
