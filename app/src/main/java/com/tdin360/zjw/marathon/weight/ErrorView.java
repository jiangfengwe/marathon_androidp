package com.tdin360.zjw.marathon.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

/**
 * Created by admin on 17/8/17.
 */

public class ErrorView extends LinearLayout {


    public enum ViewShowMode{

        NOT_DATA,
        ERROR,
        NOT_NETWORK,


    }
    private ViewShowMode mode;
    private View rootView;
    private ImageView imageView;
    private TextView textView;
    public ErrorView(Context context) {
        super(context,null);

    }

    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void  initView(Context context){

        this.rootView = View.inflate(context, R.layout.not_found_layout, null);
        this.rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                  listener.onErrorClick(mode);
                }
            }
        });
        this.rootView.setVisibility(GONE);
        this.imageView= (ImageView) rootView.findViewById(R.id.imageView);
        this.textView = (TextView) rootView.findViewById(R.id.not_found);
        LayoutParams params = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity= Gravity.CENTER;
        this.addView(rootView,params);
    }

    private void setImageResource(@DrawableRes int resId){

        if(imageView!=null){

          imageView.setImageResource(resId);
        }

    }

    private void setText(CharSequence text){

        if(textView!=null){

            textView.setText(text);
        }

    }


    /**
     * 显示
     * @param mainView 主布局view
     * @param mode  显示提示类型
     */

    public void show(View mainView,String errorMsg,ViewShowMode mode){

         this.mode=mode;
         mainView.setVisibility(GONE);
         this.rootView.setVisibility(VISIBLE);


        switch (mode){
            /**
             * 加载失败
             */
            case ERROR:
                setText(errorMsg);
                break;
            /**
             * 没有数据
             */
            case NOT_DATA:
               setText(errorMsg);
                break;
            /**
             * 网络错误
             */
            case NOT_NETWORK:

               setText(errorMsg);
                break;


        }
    }
    /**
     * 显示
     * @param mainView 主布局view
     * @param mode  显示提示类型
     */

    public void show(View mainView, String errorMsg, @DrawableRes int resId, ViewShowMode mode){

          if(mainView==null)return;
         this.mode=mode;
         mainView.setVisibility(GONE);

        this.rootView.setVisibility(VISIBLE);
        switch (mode){
            /**
             * 加载失败
             */
            case ERROR:
                setImageResource(resId);
                setText(errorMsg);
                break;
            /**
             * 没有数据
             */
            case NOT_DATA:
                setImageResource(resId);
                setText(errorMsg);
                break;
            /**
             * 网络错误
             */
            case NOT_NETWORK:
                setImageResource(resId);
                setText(errorMsg);
                break;


        }
    }

    /**
     * 正常显示
     * @param mainView
     */
    public void hideErrorView(View mainView){

        if(mainView==null)return;
        this.rootView.setVisibility(GONE);
        mainView.setVisibility(VISIBLE);

    }

    public interface ErrorOnClickListener{

        void onErrorClick(ViewShowMode mode);

    }

    private ErrorOnClickListener listener;

    public void setErrorListener(ErrorOnClickListener listener) {
        this.listener = listener;
    }
}
