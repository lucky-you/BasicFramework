package com.zhowin.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.zhowin.library.R;

/**
 * author Z_B
 * date :2020/1/19 16:56
 * description: 可自定义设置Drawable宽高的TextView，
 * 拓展了 drawableWidth ，drawableHeight ，其他的用法和TextView一样
 */
public class ZhoDrawableTextView extends AppCompatTextView {

    public ZhoDrawableTextView(Context context) {
        super(context);
        init(null);
    }

    public ZhoDrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ZhoDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ZhoDrawableTextView);
            int width = a.getDimensionPixelSize(R.styleable.ZhoDrawableTextView_drawableWidth, 0);
            int height = a.getDimensionPixelSize(R.styleable.ZhoDrawableTextView_drawableHeight, 0);
            Drawable left = a.getDrawable(R.styleable.ZhoDrawableTextView_android_drawableLeft);
            Drawable top = a.getDrawable(R.styleable.ZhoDrawableTextView_android_drawableTop);
            Drawable right = a.getDrawable(R.styleable.ZhoDrawableTextView_android_drawableRight);
            Drawable bottom = a.getDrawable(R.styleable.ZhoDrawableTextView_android_drawableBottom);
            if (null != left && width > 0 && height > 0) {
                left.setBounds(0, 0, width, height);
            }
            if (null != top && width > 0 && height > 0) {
                top.setBounds(0, 0, width, height);
            }
            if (null != right && width > 0 && height > 0) {
                right.setBounds(0, 0, width, height);
            }
            if (null != bottom && width > 0 && height > 0) {
                bottom.setBounds(0, 0, width, height);
            }
            setCompoundDrawables(left, top, right, bottom);
            a.recycle();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable leftDrawable = drawables[0];
        if (leftDrawable != null) {
            int leftDrawableWidth = leftDrawable.getIntrinsicWidth();
            int drawablePadding = getCompoundDrawablePadding();
            int textWidth = (int) getPaint().measureText(getText().toString().trim());
            int bodyWidth = leftDrawableWidth + drawablePadding + textWidth;
            canvas.save();
            int width = getWidth() - getPaddingLeft() - getPaddingRight();
            if (width > bodyWidth) { //只有较大才偏移, 不然会缺部分图片
                canvas.translate((width - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }
}
