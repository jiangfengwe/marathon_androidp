package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.NoticeMessageModel;
import com.tdin360.zjw.marathon.utils.db.impl.NoticeServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知消息
 */
public class MyNoticeMessageActivity extends BaseActivity {

    private List<NoticeMessageModel> list = new ArrayList<>();
    private LinearLayout tip;
    private   NoticeMessageListAdapter adapter;
    private NoticeServiceImpl service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.service = new NoticeServiceImpl(this);
        setToolBarTitle("通知消息");
        showBackButton();
        initView();

        loadData();
    }




    @Override
    public int getLayout() {
        return R.layout.activity_notice_message;
    }

    private void initView() {

        RecyclerView  mRecyclerView = (RecyclerView) this.findViewById(R.id.mMessageView);
        this.tip = (LinearLayout) this.findViewById(R.id.tip);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new NoticeMessageListAdapter();
        mRecyclerView.setAdapter(adapter);



    }
    //从数据中获取所以通知消息
    private void loadData() {

        list = service.getAllNotice();
        checkIsNoMessage();
    }
    //判断是否有消息
    private void checkIsNoMessage(){
        if(list.size()==0){

        tip.setVisibility(View.VISIBLE);
        }else {
        tip.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }

    }

    /**
     * 通知消息列表适配器
     */

    private class NoticeMessageListAdapter extends RecyclerView.Adapter<NoticeMessageListAdapter.MyHolderView>{


        @Override
        public MyHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolderView(LayoutInflater.from(MyNoticeMessageActivity.this).inflate(R.layout.my_notice_mesage_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(MyHolderView holder, int position) {

            NoticeMessageModel messageItem = list.get(position);
            holder.time.setText(messageItem.getForTime());
            holder.name.setText(messageItem.getForName());
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



    }



}
