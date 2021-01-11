package com.zhowin.viewlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.zhowin.viewlibrary.R;


/**
 * author : zho
 * date  ：2020/12/3
 * desc ：弧形ImageView
 */
public class ArcImageView extends AppCompatImageView {
    /*
     *弧形高度
     */
    private int mArcHeight;
    private static final String TAG = "ArcImageView";

    public ArcImageView(Context context) {
        this(context, null);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcImageView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcImageView_arcIVHeight, 0);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, getHeight());
        path.quadTo(getWidth() / 2, getHeight() - 2 * mArcHeight, getWidth(), getHeight());
        path.lineTo(getWidth(), 0);
        path.close();
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

}