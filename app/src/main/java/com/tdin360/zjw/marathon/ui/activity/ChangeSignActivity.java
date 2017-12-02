package com.tdin360.zjw.marathon.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;

/**
* 修改签名
*/

public class ChangeSignActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.btn_change_sign_sure)
    private Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
    }

    private void initView() {
        btnSure.setOnClickListener(this);
    }

    private void initToolbar() {
        imageView.setImageResource(R.drawable.back_black);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewline.setVisibility(View.GONE);
        titleTv.setText("修改签名");

    }
    @Override
    public int getLayout() {
        return R.layout.activity_change_sign;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change_sign_sure:
                ToastUtils.show(getApplicationContext(),"sure");
                break;
        }

    }
}
