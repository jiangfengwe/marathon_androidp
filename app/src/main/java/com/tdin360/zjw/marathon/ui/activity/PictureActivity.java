package com.tdin360.zjw.marathon.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 图集
 */

public class PictureActivity extends BaseActivity {
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;



    @ViewInject(R.id.rv_picture)
    private RecyclerView rvPiv;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();

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
        titleTv.setText("图集");
    }

    private void initView() {
        for (int i = 0; i <12 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.item_picture) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {

            }
        };
        rvPiv.setAdapter(adapter);
        rvPiv.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_picture;
    }
}
