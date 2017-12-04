package com.tdin360.zjw.marathon.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 评价
 */

public class CommentActivity extends BaseActivity {

    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();

    }
    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText("评价");
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_comment;
    }
}
