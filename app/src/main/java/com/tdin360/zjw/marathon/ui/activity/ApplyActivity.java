package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;
/**
 *  立即报名
 *
 */
public class ApplyActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.apply_back)
    private ImageView ivBack;
    @ViewInject(R.id.apply_flow)
    private TextView tvFlow;
    @ViewInject(R.id.layout_apply_personal)
    private RelativeLayout layoutPersonal;
    @ViewInject(R.id.layout_apply_team)
    private RelativeLayout layoutTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvFlow.setOnClickListener(this);
        layoutPersonal.setOnClickListener(this);
        layoutTeam.setOnClickListener(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_apply;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apply_back:
                finish();
                break;
            case R.id.apply_flow:
                ToastUtils.show(getApplicationContext(),"报名流程");
                break;
            case R.id.layout_apply_personal:
                ToastUtils.show(ApplyActivity.this,"个人报名");
                break;
            case R.id.layout_apply_team:
                Intent intent=new Intent(ApplyActivity.this,CreateTeamActivity.class);
                startActivity(intent);
                break;
        }
    }
}
