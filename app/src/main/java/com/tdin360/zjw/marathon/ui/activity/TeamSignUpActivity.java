package com.tdin360.zjw.marathon.ui.activity;


import android.os.Bundle;
import android.view.View;

import android.widget.RadioGroup;


import com.tdin360.zjw.marathon.R;

public class TeamSignUpActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private RadioGroup radioGroup;
    private View createTeamView,addTeamView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("团队报名");
        showBackButton();
        this.radioGroup = (RadioGroup) this.findViewById(R.id.teamGroup);
        this.radioGroup.setOnCheckedChangeListener(this);
        this.createTeamView = this.findViewById(R.id.createTeamView);
        this.addTeamView =   this.findViewById(R.id.addTeamView);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_team_sign_up;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        this.createTeamView.setVisibility(View.GONE);
        this.addTeamView.setVisibility(View.GONE);
        switch (checkedId){


            case R.id.tab1:
                this.createTeamView.setVisibility(View.VISIBLE);
                break;
            case R.id.tab2:
                this.addTeamView.setVisibility(View.VISIBLE);
                break;

        }
    }
}
