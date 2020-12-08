package com.mobiversal.spannabletextview;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class CustomClickableSpan extends ClickableSpan {


    @Override
    public void onClick(View widget) {

    }


    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
