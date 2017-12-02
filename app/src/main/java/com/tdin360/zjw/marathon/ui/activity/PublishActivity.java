package com.tdin360.zjw.marathon.ui.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

import org.xutils.view.annotation.ViewInject;

/**
 * 发布动态
 */

public class PublishActivity extends BaseActivity implements View.OnClickListener{

    @ViewInject(R.id.tv_publish_cancel)
    private TextView tvCancel;
    @ViewInject(R.id.tv_publish)
    private TextView tvPublish;
    @ViewInject(R.id.et_publish_content)
    private EditText etContent;
    @ViewInject(R.id.tv_publish_count)
    private TextView tvCount;
    @ViewInject(R.id.layout_publish)
    private LinearLayout layoutAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();


    }

    private void initView() {
        tvCancel.setOnClickListener(this);
        tvPublish.setOnClickListener(this);
        layoutAdd.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_publish;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_publish_cancel:
                //取消
                break;
            case R.id.layout_publish:
                //添加图片
                break;
            case R.id.tv_publish:
                //发布
                break;
        }

    }
}
