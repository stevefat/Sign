package com.gisroad.sign.ui.view;
/**
 * Created by stevefat on 17-5-19.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gisroad.sign.R;

/**
 * Author : stevefat
 * Email :ngh8897@gmail.com
 * Created : 17-5-19 下午5:56
 */
public class EmptyLayout extends RelativeLayout {

    private String text;  //加载时候的文字
    private int imgSrc;

    private ImageView imageView;  //图片
    private TextView textview;  //显示文字的信息

    private View view;


    /**
     * The empty state
     */
    public final static int TYPE_EMPTY = 1;
    /**
     * The loading state
     */
    public final static int TYPE_LOADING = 2;
    /**
     * The error state
     */
    public final static int TYPE_ERROR = 3;


    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout, 0, 0);
        text = ta.getString(R.styleable.EmptyLayout_mtext);
        imgSrc = ta.getResourceId(R.styleable.EmptyLayout_img_src, R.drawable.ic_menu_camera);

        ta.recycle();

        init();


    }

    private void init() {


        imageView = new ImageView(getContext());
        imageView.setImageResource(imgSrc);
        LayoutParams buttonParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imageView.setId(R.id.id_empty_img);
        addView(imageView, buttonParams);


        textview = new TextView(getContext());
        textview.setText(text);
        LayoutParams textViewParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textViewParams.addRule(RelativeLayout.BELOW, R.id.id_empty_img);
        textview.setTextSize(20);
        textview.setId(R.id.id_empty_text);

        addView(textview, textViewParams);

    }

    public void bindView(View v) {
        this.view = v;

    }

    public void setText(String text) {
        this.text = text;
    }



    public void loading() {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
        setVisibility(View.VISIBLE);
        textview.setText(text);
        imageView.setImageResource(imgSrc);


        Animation am = AnimationUtils.loadAnimation(getContext(), R.anim.loading);
        LinearInterpolator in = new LinearInterpolator();
        am.setInterpolator(in);

        imageView.startAnimation(am);


    }


    public void success() {
        setVisibility(View.GONE);
        cleanAm();
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void error() {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
        setVisibility(View.VISIBLE);
        textview.setText(text);
        imageView.setImageResource(imgSrc);
    }

    public void cleanAm() {
        imageView.clearAnimation();
    }

    public void noData() {
        text = "暂无数据";
        imgSrc = R.drawable.ic_no_data;
        error();
    }

    public void noWifi() {
        text = "网络连接失败，请检查网络";
        imgSrc = R.drawable.ic_no_net;
        error();
    }

}
