package com.tdin360.zjw.marathon.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的动态
 */

public class MyCircleActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.iv_my_circle_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_my_circle_pic)
    private ImageView ivPic;

    @ViewInject(R.id.rv_my_circle)
    private RecyclerView rvCircle;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();


    }

    private void initView() {
        ivBack.setOnClickListener(this);
        ivPic.setOnClickListener(this);
        for (int i = 0; i <4 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.item_my_circle) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {
                TextView tvContent = (TextView) holder.getViewById(R.id.tv_my_circle_content);
                //删除动态
                TextView tvDelete = (TextView) holder.getViewById(R.id.tv_my_circle_delete);
                if(getPosition(holder)==0){
                    tvDelete.setVisibility(View.GONE);
                    tvContent.setTextColor(Color.parseColor("#ff621a"));
                }else{
                    tvDelete.setVisibility(View.VISIBLE);
                }
                //九宫格展示图片
                NineGridView nine = (NineGridView) holder.getViewById(R.id.my_circle_nineGrid);
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
               // cirpicList = circleList.get(position <0? 0:position).getCirpicList();
                String str="http://www.eaglesoft.org/public/UploadFiles/image/20141017/20141017152856_751.jpg";
                for(int i=0;i<9;i++){
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(str);
                    info.setBigImageUrl(str);
                    imageInfo.add(info);
                }
                nine.setAdapter(new NineGridViewAdapter(MyCircleActivity.this, imageInfo) {
                    @Override
                    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                        super.onImageItemClick(context, nineGridView, index, imageInfo);
                        ImageView imageView1=new ImageView(MyCircleActivity.this);
                        /*List<MyStateBean.CircleBean.CircleListBean.CirpicListBean> cirpicList = circleList.get(position < 0 ? 0 : position).getCirpicList();
                        pictureList.clear();
                        for(int i=0;i<cirpicList.size();i++){
                            pictureList.add(cirpicList.get(i).getPicUrl());
                        }
                        MNImageBrowser.showImageBrowser(MyStateActivity.this,imageView1,0, pictureList);*/
                        //Toast.makeText(getContext(),pictureList.size()+"",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        adapter.addHeaderView(R.layout.item_my_circle_head);
        rvCircle.setAdapter(adapter);
        rvCircle.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_circle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_my_circle_back:
                //返回
                finish();
                break;
            case R.id.iv_my_circle_pic:
                //切换背景图
                break;
        }

    }
}
