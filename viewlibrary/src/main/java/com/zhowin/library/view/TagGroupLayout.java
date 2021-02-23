package com.zhowin.library.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhowin.library.R;

import java.util.Arrays;
import java.util.List;

/**
 * tag标签的纯展示，只能单行显示，不能自动换行，
 */
public class TagGroupLayout extends LinearLayout {

    public TagGroupLayout(Context context) {
        this(context, null);
    }

    public TagGroupLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagGroupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
    }

    public void setTagData(String[] tags, @ColorRes int tintColorRes) {
        if (tags == null || tags.length == 0) {
            return;
        }
        setTagData(Arrays.asList(tags), tintColorRes);
    }

    public void setTagData(List<String> tags, @ColorRes int tintColorRes) {
        setTagData(tags, 0, tintColorRes);
    }

    /**
     * @param tags         tga内容
     * @param textColorRes 文字颜色
     * @param tintColorRes 描边颜色
     */
    public void setTagData(List<String> tags, @ColorRes int textColorRes, @ColorRes int tintColorRes) {
        if (tags == null || tags.isEmpty()) {
            return;
        }
        removeAllViews();
        int tintColor = ContextCompat.getColor(getContext(), tintColorRes);
        for (String tag : tags) {
            createTag(tag, textColorRes, tintColor);
        }
        requestLayout();
    }

    private void createTag(final String s, @ColorRes int textColorRes, @ColorInt int tintColor) {
        TextView textView = new TextView(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.rightMargin = dp2px(4);
        textView.setLayoutParams(lp);
        textView.setTextSize(9);
        if (textColorRes != 0) {
            textView.setTextColor(ContextCompat.getColor(getContext(), textColorRes));
        } else {
            textView.setTextColor(Color.GRAY);
        }
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.shape_round_stroke_bg_tag);
        if (drawable != null)
            DrawableCompat.setTint(drawable, tintColor);
        textView.setBackground(drawable);
        textView.setPadding(dp2px(4), 0, dp2px(4), 0);
        textView.setText(s);
        addView(textView);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
