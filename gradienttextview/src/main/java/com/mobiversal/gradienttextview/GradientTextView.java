package com.mobiversal.gradienttextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

import android.view.Gravity;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Csaba on 8/31/2019.
 */
public class GradientTextView extends AppCompatTextView {

    @ColorInt
    private int gradientStartColor;
    @ColorInt
    private int gradientCenterColor;
    @ColorInt
    private int gradientEndColor;

    private boolean isGradientEnabled = true;

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        safeExtractViewAttributes(context, attrs);
        showGradientIfNecessary();
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        safeExtractViewAttributes(context, attrs);
        showGradientIfNecessary();
    }

    private void showGradientIfNecessary() {
        if (isGradientEnabled)
            applyGradient();
    }

    private void safeExtractViewAttributes(@Nullable Context context, @Nullable AttributeSet attrs) {
        if (context != null && attrs != null)
            extractViewAttributes(context, attrs);
    }

    private void extractViewAttributes(@NonNull Context context, @NonNull AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        int defaultColor = getTextColors().getDefaultColor();
        if (typedArray != null) {
            gradientStartColor = typedArray.getColor(R.styleable.GradientTextView_textGradientStartColor, defaultColor);
            gradientCenterColor = typedArray.getColor(R.styleable.GradientTextView_textGradientCenterColor, defaultColor);
            gradientEndColor = typedArray.getColor(R.styleable.GradientTextView_textGradientEndColor, defaultColor);
            typedArray.recycle();
        } else {
            gradientStartColor = defaultColor;
            gradientCenterColor = defaultColor;
            gradientEndColor = defaultColor;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isGradientEnabled)
            applyGradient();
    }

    private void applyGradient() {
        final float textWidth = getPaint().measureText(this.getText().toString());
        final float lineHeight = (float) getLineHeight();
        final float paddingStart = getPaddingStart();
        final float startX = paddingStart;
        final float endX = textWidth + paddingStart;
        final Shader shader = new LinearGradient(startX, 0f, endX, lineHeight, new int[] { gradientStartColor, gradientCenterColor, gradientEndColor }, null, Shader.TileMode.CLAMP);
        getPaint().setShader(shader);
        invalidate();
    }

//    private void applyGradient() {
//        float lineHeight = getLineHeight();
//        GradientInterval interval = getGradientIntervalByGravity();
//        Shader shader = new LinearGradient(interval.getStartX(), 0f, interval.getEndX(), lineHeight, new int[]{gradientStartColor, gradientCenterColor, gradientEndColor}, null, Shader.TileMode.CLAMP);
//        getPaint().setShader(shader);
//    }

    private GradientInterval getGradientIntervalByGravity() {
        GradientInterval interval = new GradientInterval();
        int gravity = getGravity(); // gravity is center even if you set another gravity
        int buttonWidth = getMeasuredWidth();
        float textWidth = getPaint().measureText(getText().toString());

        // set for gravity center as default
        float startX = 0;
        float endX = 0;

        if (gravity == Gravity.CENTER || gravity == Gravity.CENTER_HORIZONTAL) {
            startX = (buttonWidth - textWidth) / 2;
            endX = startX + textWidth;
        } else if (gravity == Gravity.START || gravity == Gravity.LEFT) {
            endX = textWidth;
        } else if (gravity == Gravity.END || gravity == Gravity.RIGHT) {
            startX = buttonWidth - textWidth;
            endX = startX + textWidth;
        } // TODO padding

        interval.setStartX(startX);
        interval.setEndX(endX);

        return interval;
    }

    private void removeGradient() {
        this.getPaint().setShader(null);
        invalidate();
    }

    public void setGradientStartColor(@ColorInt int color) {
        gradientStartColor = color;
        applyGradient();
    }

    public void setGradientCenterColor(@ColorInt int color) {
        gradientCenterColor = color;
        applyGradient();
    }

    public void setGradientEndColor(@ColorInt int color) {
        gradientEndColor = color;
        applyGradient();
    }

    public void setGradientEnabled(boolean enabled) {
        if (enabled) applyGradient();
        else removeGradient();
    }

}
