package com.zhowin.viewlibrary.callback;

import android.graphics.RectF;

public interface ZhoSizeTester {
    /**
     * AutoFitEditText
     *
     * @param suggestedSize  Size of text to be tested
     * @param availableSpace available space in which text must fit
     * @return an integer < 0 if after applying {@code suggestedSize} to
     * text, it takes less space than {@code availableSpace}, > 0
     * otherwise
     */
    int onTestSize(int suggestedSize, RectF availableSpace);
}
