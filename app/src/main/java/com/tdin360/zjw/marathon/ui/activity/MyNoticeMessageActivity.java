package com.tdin360.zjw.marathon.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.WrapContentLinearLayoutManager;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.CirclePriseTableModel;
import com.tdin360.zjw.marathon.model.SystemNoticeBean;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.db.impl.CircleNoticeDetailsServiceImpl;
import com.tdin360.zjw.marathon.utils.db.impl.NoticeMessageServiceImpl;
import com.tdin360.zjw.marathon.utils.db.impl.SystemNoticeDetailsServiceImpl;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

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

    private ImageOptions imageOptionsCircle;

    //private   NoticeMessageListAdapter adapter;
    private NoticeMessageServiceImpl service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.service = new NoticeMessageServiceImpl(this);
        //systemNoticeDetailsService.addSystemNotice(circlePriseTableModel);
        imageOptionsCircle = new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
                .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                .build();
        initToolbar();
        initView();
        //loadData();
    }

    private void initToolbar() {
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        titleTv.setText(R.string.notice_title);
        titleTv.setTextColor(Color.WHITE);
        showBack(toolbar,imageView);
        /*SharedPreferencesManager.isNotice(getApplicationContext(),false);
        EnumEventBus notice = EnumEventBus.SYSTEMNOTICE;
        EventBus.getDefault().post(new EventBusClass(notice));
        finish();*/
    }


    @Override
    public int getLayout() {
        return R.layout.activity_notice_message;
    }

    private void initView() {
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        SystemNoticeDetailsServiceImpl systemNoticeDetailsService = new SystemNoticeDetailsServiceImpl(getApplicationContext());
        final List<SystemNoticeBean> allCircleNotice = systemNoticeDetailsService.getAllSystemNotice();
        if(allCircleNotice.size()<=0){
            ToastUtils.showCenter(getApplicationContext(),"暂时还没有通知");
            return;
        }
        adapter=new RecyclerViewBaseAdapter<SystemNoticeBean>(getApplicationContext(),allCircleNotice,R.layout.my_notice_mesage_list_item) {
            @Override
            protected void onBindNormalViewHolder(final NormalViewHolder holder, final SystemNoticeBean model) {
                //TextView tvTitle = (TextView) holder.getViewById(R.id.tv_circle_message_title);
                ImageView imageView = (ImageView) holder.getViewById(R.id.iv_system_pic);
               // x.image().bind(imageView,model.getHeadImg(),imageOptionsCircle);
                holder.setText(R.id.tv_system_title,model.getMessageIntroduce());
                holder.setText(R.id.tv_system_content,model.getMessageIntroduce());
                holder.setText(R.id.tv_system_time,model.getTime());
                final ImageView imageViewShow = (ImageView) holder.getViewById(R.id.iv_system_notice_detail_show);
                LinearLayout layout = (LinearLayout) holder.getViewById(R.id.layout_notice);
                String notice = model.getNotice();
                if(notice.equals("0")){
                    imageViewShow.setVisibility(View.VISIBLE);
                }else{
                    imageViewShow.setVisibility(View.GONE);
                }
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageViewShow.setVisibility(View.GONE);
                        model.setNotice("1");
                        SystemNoticeDetailsServiceImpl systemNoticeDetailsService = new SystemNoticeDetailsServiceImpl(getApplicationContext());
                        String position = getPosition(holder)+"";
                        systemNoticeDetailsService.update(model.getNotice(),model.getMessageId()+"");
                        // Log.d("notice1", "onItemClick: "+notice1);
                        EnumEventBus system = EnumEventBus.NOTICECLICK;
                        EventBus.getDefault().post(new EventBusClass(system));
                        Intent intent=new Intent(MyNoticeMessageActivity.this,MyNoticeDetailActivity.class);
                        int dynamicId = model.getMessageId();
                        Log.d("notice1dynamicId", "onItemClick: "+dynamicId);
                        intent.putExtra("Id",dynamicId);
                        startActivity(intent);
                    }
                });
              /*  layout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        return false;
                    }
                });*/
            }
        };
        rvNotice.setAdapter(adapter);
        rvNotice.setLayoutManager(new WrapContentLinearLayoutManager(this));
        adapter.setLongClickListener(new RecyclerViewBaseAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View view, final int position) {
                android.support.v7.app.AlertDialog.Builder normalDialog =new android.support.v7.app.AlertDialog.Builder(MyNoticeMessageActivity.this);
                normalDialog.setMessage("是否删除该消息");
                normalDialog.setPositiveButton("是",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //ToastUtils.showCenter(getApplicationContext(),"删除消息");
                                SystemNoticeBean systemNoticeBean = allCircleNotice.get(position);
                                allCircleNotice.remove(position);
                                SystemNoticeDetailsServiceImpl systemNoticeDetailsService = new SystemNoticeDetailsServiceImpl(getApplicationContext());
                                String messageId = systemNoticeBean.getMessageId()+"";
                                systemNoticeDetailsService.deleteAll(messageId);
                                adapter.notifyDataSetChanged();
                                systemNoticeBean.setNotice("1");
                                systemNoticeDetailsService.update(systemNoticeBean.getNotice(),systemNoticeBean.getMessageId()+"");
                                // Log.d("notice1", "onItemClick: "+notice1);
                                EnumEventBus system = EnumEventBus.NOTICEDELETE;
                                EventBus.getDefault().post(new EventBusClass(system));
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                dialog.dismiss();
                            }
                        });
                // 显示
                normalDialog.show();
            }
        });
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
