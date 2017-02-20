package com.tdin360.zjw.marathon.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义赛事详情页菜单选项
 * @author zhangzhijun
 * Created by admin on 17/2/19.
 */

public class MenuView extends LinearLayout {

    private ImageView icon;
    private TextView titleView;
    private String title;
    private LinearLayout.LayoutParams iconParams;
    private MenuOnItemClickListener listener;
    private Object menuId;
    public MenuView(Context context) {
        super(context);
        initView(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){

        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,1);
        params.gravity= Gravity.CENTER;
        this.setLayoutParams(params);
        this.setOrientation(LinearLayout.VERTICAL);
        this.icon = new ImageView(context);
        this.icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.iconParams = new LayoutParams(120,120);
        this.iconParams.gravity=Gravity.CENTER;
        this.icon.setLayoutParams(iconParams);
        this.titleView = new TextView(context);
        LinearLayout.LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.titleView.setLayoutParams(titleParams);
        this.titleView.setTextSize(14);
        this.titleView.setTextColor(Color.BLACK);
        titleParams.topMargin=2;
        titleParams.gravity=Gravity.CENTER;
        this.addView(icon);
        this.addView(titleView);
        //设置菜单点击
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null){

                listener.onMenuClick(MenuView.this);
                }
            }
        });
    }


//    获取菜单id
    public Object getMenuId() {
        return menuId;
    }


    //获取菜单标题
    public String getTitle() {
        return title;
    }


    /**
     * 设置菜单内容
     * @param menuId 菜单id用于标示菜单选项
     * @param icon 菜单图标 默认是120*120的大小
     * @param title 菜单标题
     */
    public void setMenuContent(Object menuId,Bitmap icon,String title){

        this.menuId=menuId;
        this.title=title;
        if(icon!=null){

            this.icon.setImageBitmap(icon);
        }
        if(title!=null){

            this.titleView.setText(title);
        }
    }

    /**
     * 设置菜单内容
     * @param menuId 菜单id
     * @param icon 菜单图标
     * @param title 菜单标题
     * @param iconWidth 菜单图标宽度  默认是120*120的大小
     * @param iconHeight 菜单图标高度
     */
    public void setMenuContent(Object menuId,Bitmap icon,String title,int iconWidth,int iconHeight){

        this.menuId=menuId;
        this.title=title;
        if(icon!=null){

            this.icon.setImageBitmap(icon);
        }
        if(title!=null){

            this.titleView.setText(title);
        }

        this.iconParams = new LayoutParams(iconWidth,iconHeight,1);
        this.iconParams.gravity=Gravity.CENTER;
        this.icon.setLayoutParams(iconParams);

    }

    /**
     * 设置菜单标题颜色
     * @param color
     */
    public void setMenuTitleColor(int color){

           this.titleView.setTextColor(color);

    }

    /**
     * 设置菜单标题的大小
     * @param size
     */
    public void setMenuTitleSize(float size){

        this.titleView.setTextSize(size);
    }

    /**
     * 菜单按钮点击事件回调
     */
    public interface MenuOnItemClickListener{

       void onMenuClick(MenuView itemView);
    }
    //设置菜单点击监听回调
    public void setMenuOnItemClickListener(MenuOnItemClickListener listener){

        this.listener=listener;
    }
}
