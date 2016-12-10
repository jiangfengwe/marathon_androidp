package com.tdin360.zjw.marathon.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView toolBarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        this.mToolBar= (Toolbar) this.findViewById(R.id.mToolBar);
        this.toolBarTitle= (TextView) this.findViewById(R.id.toolbar_title);
        setSupportActionBar(this.mToolBar);
        //设置默认标题不显示
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

//    设置标题
    public void setToolBarTitle(CharSequence title){

        if(title!=null){

            this.toolBarTitle.setText(title);
        }
    }

    /**
     * 设置标题字体颜色
     * @param color
     */
    public void setToolBarTitleColor(int color){

         this.toolBarTitle.setTextColor(color);
    }

    /**
     * 设置导航条颜色
     * @param color
     */
    public void setmToolBarColor(int color){

        this.mToolBar.setBackgroundColor(color);
    }

    /**
     * 显示返回按钮
     */
    public void showBackButton(){

        this.mToolBar.setNavigationIcon(R.drawable.nav_back_selector);
        this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public abstract int getLayout();
}
