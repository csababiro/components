package com.mobiversal.spannabletextview;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import androidx.annotation.ColorInt;

public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface newType;
    private final int mTextColor;

    public CustomTypefaceSpan(Typeface type, @ColorInt int txtColor) {
        super("");
        newType = type;
        mTextColor = txtColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        applyCustomTypeFace(ds, newType, mTextColor);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(paint, newType, mTextColor);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf, int txtColor) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        if (txtColor != -1)
            paint.setColor(txtColor);
        paint.setTypeface(tf);
    }
}
