package com.zhowin.basicframework.common.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhowin.basicframework.R;


/**
 * 标题栏
 */
public class TitleBuilder {

    private View viewTitle;
    private TextView tvTitle;
    private TextView tvLeft;
    private ImageView ivLeft;
    private ImageView ivRight;
    private TextView tvRight;
    private Activity context;

    public TitleBuilder(final Activity context) {
        this.context = context;
        viewTitle = context.findViewById(R.id.rl_titlebar);
        tvTitle = (TextView) viewTitle.findViewById(R.id.titlebar_tv);
        ivLeft = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_left);
        ivRight = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_right);
        tvLeft = (TextView) viewTitle.findViewById(R.id.titlebar_tv_left);
        tvRight = (TextView) viewTitle.findViewById(R.id.titlebar_tv_right);
        ivLeft.setOnClickListener(new ExitListener());
    }

    /**
     * 针对fragment
     *
     * @param context
     */
    public TitleBuilder(View context) {
        viewTitle = context.findViewById(R.id.rl_titlebar);
        tvTitle = (TextView) viewTitle.findViewById(R.id.titlebar_tv);
        ivLeft = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_left);
        ivRight = (ImageView) viewTitle.findViewById(R.id.titlebar_iv_right);
        tvLeft = (TextView) viewTitle.findViewById(R.id.titlebar_tv_left);
        tvRight = (TextView) viewTitle.findViewById(R.id.titlebar_tv_right);
        ivLeft.setOnClickListener(new ExitListener());
    }

    public TitleBuilder setTextColor(int color) {
        tvLeft.setTextColor(color);
        tvTitle.setTextColor(color);
        tvRight.setTextColor(color);
        return this;
    }

    // title
    public TitleBuilder setTitleBgColor(int color) {
        viewTitle.setBackgroundColor(color);
        return this;
    }

    public TitleBuilder setTitleText(String text) {
        tvTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvTitle.setText(text);
        return this;
    }

    public TitleBuilder setTitleText(int res) {
        tvTitle.setVisibility(res == 0 ? View.GONE : View.VISIBLE);
        tvTitle.setText(res);
        return this;
    }

    public TitleBuilder setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
        return this;
    }

    // left
    public TitleBuilder setLeftImage(int resId) {
        ivLeft.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        ivLeft.setImageResource(resId);
        return this;
    }


    public TitleBuilder noBack() {
        ivLeft.setVisibility(View.GONE);
        return this;
    }

    public TitleBuilder setLeftText(String text) {
        tvLeft.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvLeft.setText(text);
        return this;
    }

    public TitleBuilder setLeftTextColor(int leftTextColor) {
        tvLeft.setTextColor(leftTextColor);
        return this;
    }

    public TitleBuilder setLeftDrawable(Drawable drawable) {
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        tvLeft.setCompoundDrawables(drawable, null, null, null);
        return this;
    }

    public TitleBuilder setLeftOnClickListener(OnClickListener listener) {
        if (ivLeft.getVisibility() == View.VISIBLE) {
            if (listener != null) {
                ivLeft.setOnClickListener(listener);
            } else {
                ivLeft.setOnClickListener(new ExitListener());
            }
        } else if (tvLeft.getVisibility() == View.VISIBLE) {
            if (listener != null) {
                tvLeft.setOnClickListener(listener);
            } else {
                tvLeft.setOnClickListener(new ExitListener());
            }
        }
        return this;
    }

    // right
    public TitleBuilder setRightImage(int resId) {
        ivRight.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        ivRight.setImageResource(resId);
        return this;
    }

    public TitleBuilder setRightText(String text) {
        tvRight.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvRight.setText(text);
        return this;
    }

    public TitleBuilder setRightTextColor(int rightTextColor) {
        tvRight.setTextColor(rightTextColor);
        return this;
    }

    public String getRightText() {
        return tvRight.getText().toString();
    }

    public TitleBuilder setRightDrawable(Drawable drawable) {
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        tvRight.setCompoundDrawables(null, null, drawable, null);
        return this;
    }

    public TitleBuilder setRightOnClickListener(OnClickListener listener) {
        if (ivRight.getVisibility() == View.VISIBLE) {
            ivRight.setOnClickListener(listener);
        } else if (tvRight.getVisibility() == View.VISIBLE) {
            tvRight.setOnClickListener(listener);
        }
        return this;
    }


    public void hide() {
        viewTitle.setVisibility(View.GONE);
    }

    public void show() {
        viewTitle.setVisibility(View.VISIBLE);
    }

    private class ExitListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            context.finish();
        }
    }


}
