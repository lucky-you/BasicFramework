package com.zhowin.viewlibrary.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * author : zho
 * date  ：2021/1/11
 * desc ：
 */
public class AdjustImageView extends AppCompatImageView {

    public AdjustImageView(Context context) {
        super(context);
    }

    public AdjustImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            setMeasuredDimension(width, height);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}