package com.tdin360.zjw.marathon.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.tdin360.zjw.marathon.R;

public class MyTeamListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("我的团队");
        showBackButton();

        initView();
    }

    private void initView() {

        this.recyclerView = (RecyclerView) this.findViewById(R.id.listView);


    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_team_list;
    }
}
