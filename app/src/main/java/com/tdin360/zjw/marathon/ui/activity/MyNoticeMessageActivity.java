package com.tdin360.zjw.marathon.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.utils.db.impl.NoticeMessageServiceImpl;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知消息
 */
public class MyNoticeMessageActivity extends BaseActivity {
    @ViewInject(R.id.navRightItemImage)
    private ImageView rightImage;
    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

   // private List<NoticeMessageModel> list = new ArrayList<>();
    @ViewInject(R.id.tip)
    private LinearLayout layoutTip;
    @ViewInject(R.id.rv_notice)
    private RecyclerView rvNotice;
    private List<String> list=new ArrayList<>();
    private RecyclerViewBaseAdapter adapter;

    //private   NoticeMessageListAdapter adapter;
    private NoticeMessageServiceImpl service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.service = new NoticeMessageServiceImpl(this);
        initToolbar();
        initView();
        loadData();
    }

    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText(R.string.notice_title);
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);
    }


    @Override
    public int getLayout() {
        return R.layout.activity_notice_message;
    }

    private void initView() {
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<String>(getApplicationContext(),list,R.layout.my_notice_mesage_list_item) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, String model) {
                TextView tvTitle = (TextView) holder.getViewById(R.id.tv_circle_message_title);


            }
        };
        rvNotice.setAdapter(adapter);
        rvNotice.setLayoutManager(new WrapContentLinearLayoutManager(this));
       // this.adapter = new NoticeMessageListAdapter();
       // mRecyclerView.setAdapter(adapter);



    }
    //从数据中获取所以通知消息
    private void loadData() {
        //list = service.getAllNotice();
        checkIsNoMessage();
    }
    //判断是否有消息
    private void checkIsNoMessage(){
        if(list.size()==0){
        layoutTip.setVisibility(View.VISIBLE);
        }else {
        layoutTip.setVisibility(View.GONE);
            //adapter.notifyDataSetChanged();
        }
    }
    /**
     * 通知消息列表适配器
     */

   /* private class NoticeMessageListAdapter extends RecyclerView.Adapter<NoticeMessageListAdapter.MyHolderView>{


        @Override
        public MyHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolderView(LayoutInflater.from(MyNoticeMessageActivity.this).inflate(R.layout.my_notice_mesage_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(MyHolderView holder, int position) {

            NoticeMessageModel messageItem = list.get(position);
            holder.time.setText(messageItem.getForTime());
            holder.name.setText("");
            holder.message.setText(messageItem.getMessage());

        }





        @Override
        public int getItemCount() {
            return list==null?0:list.size();
        }


        public class MyHolderView extends RecyclerView.ViewHolder {

            private TextView time;
            private TextView name;
            private TextView message;
            public MyHolderView(final View itemView) {
                super(itemView);

                this.time=(TextView)itemView.findViewById(R.id.time);
                this.name = (TextView) itemView.findViewById(R.id.name);
                this.message = (TextView) itemView.findViewById(R.id.message);

                //通知消息长按
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(MyNoticeMessageActivity.this);
                        alert.setTitle("操作");
                        alert.setMessage("确定删除这条消息吗?");
                         alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 NoticeMessageModel model = list.get(getAdapterPosition());
                                boolean success = service.deleteNotice(model.getId());
                                 if(success) {
                                     list.remove(model);
                                     notifyDataSetChanged();
                                     Toast.makeText(MyNoticeMessageActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                                 }else {
                                     Toast.makeText(MyNoticeMessageActivity.this,"删除失败!"+model.getId(),Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
                        alert.setNegativeButton("全部删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               boolean success = service.deleteAllNotice();
                                if(success) {
                                    list.clear();
                                    notifyDataSetChanged();
                                    Toast.makeText(MyNoticeMessageActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(MyNoticeMessageActivity.this,"删除失败!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        alert.show();
                        return false;
                    }
                });
            }
        }



    }*/



}
