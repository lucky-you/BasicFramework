package com.zhowin.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhowin.library.R;
import com.zhowin.library.callback.onZhoTabItemClickListener;

/**
 * author Z_B
 * date :2020/1/19 9:47
 * description: 自定义的tabLayout 左侧图片，右侧文字，底部指示器
 */
public class ZhoTabLayout extends LinearLayout {

    private onZhoTabItemClickListener onZhoTabItemClickListener;

    public void setOnZhoTabItemClickListener(com.zhowin.library.callback.onZhoTabItemClickListener onZhoTabItemClickListener) {
        this.onZhoTabItemClickListener = onZhoTabItemClickListener;
    }

    private Context mContext;
    private static final int COLOR_INDICATOR_COLOR = Color.RED;
    private String[] mTitles;
    private int[] mTabIcons;
    private int mTabCount;
    private int mIndicatorColor = COLOR_INDICATOR_COLOR;
    private float mTranslationX;
    private Paint mPaint = new Paint();
    private int mTabWidth;
    private int mCurrPosition;
    private TextView[] mTextViews;

    public ZhoTabLayout(Context context) {
        this(context, null);
    }

    public ZhoTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint.setStrokeWidth(6.0F);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w / mTabCount;
    }

    public void setTitles(String[] titles, int[] mTabIcon) {
        mTitles = titles;
        mTabIcons = mTabIcon;
        mTabCount = titles.length;
        generateTitleView();

    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (null == mTitles || mTitles.length == 0) return;
        //绘制底部的指示器
        //文本宽度
        int textWidth = measuringTextWidth(mTextViews[mCurrPosition]);
        mPaint.setColor(mIndicatorColor);
        canvas.save();
        canvas.translate(mTranslationX, getHeight());
        canvas.drawLine(0, -20, textWidth, -20, mPaint);
        canvas.restore();
        Log.d("xy", "mTranslationX" + mTranslationX + "....mTabWidth..." + mTabWidth + "...textWidth..." + textWidth);
    }

    /**
     * @param textView 文本的宽度
     */
    private int measuringTextWidth(TextView textView) {
        textView.measure(0, 0);
        int measuredWidth = textView.getMeasuredWidth();//测量得到的textview的宽
        return measuredWidth;
    }

    /**
     * 滑动tab
     *
     * @param position 下标
     * @param offset   滚动的偏移量
     */
    public void scrollToTabItem(int position, float offset) {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */
        Log.d("xy", "position" + offset + "....mTabCount....." + mTabCount);
        if (null == mTitles || mTitles.length == 0) return;
        mTranslationX = getWidth() / mTabCount * position + ((getWidth() / mTabCount) - measuringTextWidth(mTextViews[position])) / 2;
        invalidate();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 处理子View Tab
     */
    private void generateTitleView() {
        if (getChildCount() > 0)
            this.removeAllViews();
        int count = mTitles.length;
        mTextViews = new TextView[count];
        setWeightSum(count);
        for (int i = 0; i < count; i++) {
            final int mPosition = i;
            //  父View
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            linearLayout.setGravity(Gravity.CENTER);
            layoutParams.weight = 1;
            linearLayout.setLayoutParams(layoutParams);

            //  ImageView
            if (mTabIcons.length > 0) {
                ImageView imageView = new ImageView(getContext());
                LinearLayout.LayoutParams imLay = new LinearLayout.LayoutParams(dp2px(mContext, 15), dp2px(mContext, 15));
                imLay.gravity = Gravity.CENTER;
                imageView.setImageResource(mTabIcons[i]);
                imageView.setLayoutParams(imLay);
                linearLayout.addView(imageView);
            }

            //  TextView
            if (mTitles.length > 0) {
                TextView mTvTitle = new TextView(getContext());
                LinearLayout.LayoutParams mTvTitleLay = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mTvTitleLay.gravity = Gravity.CENTER;
                mTvTitleLay.leftMargin = dp2px(mContext, 5);
                mTvTitle.setTextColor(getContext().getResources().getColor(R.color.tab_unselect));
                mTvTitle.setText(mTitles[i]);
                mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                mTvTitle.setLayoutParams(mTvTitleLay);
                mTvTitle.setGravity(Gravity.CENTER);
                linearLayout.addView(mTvTitle);
                //赋值
                mTextViews[i] = mTvTitle;
            }

            // 设置点击事件
            linearLayout.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != onZhoTabItemClickListener) {
                        mCurrPosition = mPosition;
                        onZhoTabItemClickListener.onTabItemClick(mPosition);
                    }
                }
            });
            //将View 添加到整体容器中
            addView(linearLayout);

        }
    }


    /**
     * 高亮选中字体颜色
     *
     * @param position
     */
    public void setHighlightTextColor(int position) {
        if (position <= mTabCount && null != getChildAt(position)) {
            resetTextColor();
            View childView = getChildAt(position);
            if (childView instanceof LinearLayout) {
                ((ImageView) ((LinearLayout) childView).getChildAt(0)).setSelected(true);
                ((TextView) ((LinearLayout) childView).getChildAt(1)).setTextColor(getContext().getResources().getColor(R.color.tab_select));
            }
        }
    }

    /**
     * 重置所有字体颜色
     */
    private void resetTextColor() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof LinearLayout) {
                ((ImageView) ((LinearLayout) childAt).getChildAt(0)).setSelected(false);
                ((TextView) ((LinearLayout) childAt).getChildAt(1)).setTextColor(getContext().getResources().getColor(R.color.tab_unselect));
            }
        }
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
