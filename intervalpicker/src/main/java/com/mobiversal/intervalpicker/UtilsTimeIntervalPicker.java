package com.mobiversal.intervalpicker;

import android.widget.NumberPicker;

/**
 * Created by Csaba on 9/6/2019.
 */
public class UtilsTimeIntervalPicker {

    public static final NumberPicker.Formatter TWO_DIGIT_FORMATTER =
            new NumberPicker.Formatter() {

                @Override
                public String format(int value) {
                    return String.format("%2d", value);
                }
            };

}
