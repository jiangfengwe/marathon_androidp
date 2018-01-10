package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.CircleDetailAllCommentBean;
import com.tdin360.zjw.marathon.model.CircleDetailCommentBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * 评论详情以及列表
 */

public class CallBackActivity extends BaseActivity {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;


    @ViewInject(R.id.mToolBar)
    private Toolbar toolbar;
    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.rv_all_comment)
    private RecyclerView rvComment;
    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();
    private List<CircleDetailAllCommentBean.CommentModelBean.ReplayCommentListBean> replayCommentList=new ArrayList<>();

    ImageOptions imageOptions;
    @ViewInject(R.id.iv_all_comment_portrait)
    private ImageView ivPortrait;
    @ViewInject(R.id.tv_all_comment_name)
    private TextView tvName;
    @ViewInject(R.id.tv_all_comment_time)
    private TextView tvTime;
    @ViewInject(R.id.tv_all_comment_content)
    private TextView tvContent;

    @ViewInject(R.id.btn_all_comment)
    private Button btnSure;
    @ViewInject(R.id.et_all_comment)
    private EditText etComment;
    @ViewInject(R.id.layout_comment_detail)
    private LinearLayout layoutComment;

    private int totalPage;
    private int pageIndex=1;
    private int pageSize=10;
    private boolean flag=false;
    @ViewInject(R.id.springView)
    private SpringView springView;
    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;



    private int index;
    private String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageOptions = new ImageOptions.Builder()
//                     .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
                .setLoadingDrawableId(R.drawable.my_portrait)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.my_portrait)//加载失败后默认显示图片
                .build();
        initAnim();
        initToolbar();
        //initData();
        initNet();
        initComment();
        initView();

    }

    private void initAnim() {
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
    }
    private void initNet() {
        //加载失败点击重试
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                switch (mode){
                    case NOT_NETWORK:
                        initData(0);
                        break;

                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(this)){
            //加载网络数据
            initData(0);
        }else {
            layoutLoading.setVisibility(View.GONE);
            //如果缓存数据不存在则需要用户打开网络设置
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("网络不可用，是否打开网络设置");
            alert.setCancelable(false);
            alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //打开网络设置

                    startActivity(new Intent( android.provider.Settings.ACTION_SETTINGS));

                }
            });
            alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            alert.show();

        }
    }
    private void initComment() {
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String dynamicId= intent.getStringExtra("dynamicId");
                int commentId = intent.getIntExtra("commentId",-1);
                LoginUserInfoBean.UserBean modelInfo= SharedPreferencesManager.getLoginInfo(getApplicationContext());
                String commentContent = etComment.getText().toString().trim();
                String customerId = modelInfo.getId() + "";
                if (customerId == null || customerId.equals("")) {
                    Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent1);
                } else if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(getContext(),"评论内容不能为空", Toast.LENGTH_LONG).show();
                } else {
                    final KProgressHUD hud = KProgressHUD.create(CallBackActivity.this);
                    hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(1)
                            .setDimAmount(0.5f)
                            .show();
                    RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE_DETAIL_COMMENT);
                    params.addBodyParameter("appKey",HttpUrlUtils.appKey);
                    params.addBodyParameter("customerId",customerId);
                    params.addBodyParameter("dynamicId",dynamicId);
                    if(commentId==index){
                        params.addBodyParameter("commentId",commentId+"");
                    }else{
                        params.addBodyParameter("commentId",index+"");
                    }

                    params.addBodyParameter("commentContent",commentContent);
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d("commentallsure", "onSuccess: "+result);
                           Gson gson=new Gson();
                            CircleDetailAllCommentBean circleDetailAllCommentBean = gson.fromJson(result, CircleDetailAllCommentBean.class);
                            boolean state = circleDetailAllCommentBean.isState();
                            if(state){
                                ToastUtils.showCenter(getApplicationContext(),circleDetailAllCommentBean.getMessage());
                                initData(1);
                               /* commentCount = bjDynamicsCommentListModel.size();
                                commentCount++;
                                initData(1);*/
                                etComment.setText("");
                                EnumEventBus em = EnumEventBus.CIRCLEDETAILCOMMENT;
                                EventBus.getDefault().post(new EventBusClass(em));

                            }else{
                                ToastUtils.showCenter(getApplicationContext(),circleDetailAllCommentBean.getMessage());
                            }

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                           // mErrorView.show(rvComment,"加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                            ToastUtils.showCenter(getBaseContext(),"网络不给力,连接服务器异常!");
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {
                           // layoutLoading.setVisibility(View.GONE);
                            hud.dismiss();

                        }
                    });
                }
            }
        });
    }
    private void initView() {
        adapter=new RecyclerViewBaseAdapter<CircleDetailAllCommentBean.CommentModelBean.ReplayCommentListBean>(getApplicationContext(),
                replayCommentList,R.layout.item_circle_comment_back_detail) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, CircleDetailAllCommentBean.CommentModelBean.ReplayCommentListBean model) {
                String str="回复<font color='#ff621a'>@"+nickName+":"+"</font>"+model.getCommentContent();
                ImageView ivPortrait = (ImageView) holder.getViewById(R.id.iv_circle_detail_comment_portrait);
                holder.setText(R.id.tv_circle_detail_comment_name,model.getNickName());
                String replayerNickName = model.getReplayerNickName();
                Log.d("replayerNickName", "onBindNormalViewHolder: "+replayerNickName);
                if(!TextUtils.isEmpty(replayerNickName)){
                    holder.setText(R.id.tv_circle_detail_comment_content,Html.fromHtml(str));
                }else{
                    holder.setText(R.id.tv_circle_detail_comment_content,model.getCommentContent());
                }

                holder.setText(R.id.tv_circle_detail_comment_time,model.getCreateTimeStr());
                x.image().bind(ivPortrait,model.getHeadImg(),imageOptions);
            }
        };
        rvComment.setAdapter(adapter);
        rvComment.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CircleDetailAllCommentBean.CommentModelBean.ReplayCommentListBean replayCommentListBean = replayCommentList.get(position);
                index= replayCommentListBean.getId();
                Log.d("position", "onItemClick: "+index);
                nickName= replayCommentListBean.getNickName();
                etComment.setHint("回复@"+nickName);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                replayCommentList.clear();
                pageIndex=1;
                initData(0);
            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
                if(totalPage<=pageIndex){
                    return;
                }
                if(totalPage>pageIndex){
                    pageIndex++;
                    initData(0);
                }

            }
        });
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));

    }

    private void initData(int i) {
        //replayCommentList.clear();
        if(i==1){
            replayCommentList.clear();
        }
        Intent intent=getIntent();
        int commentId = intent.getIntExtra("commentId",-1);
        Log.d("comment", "initData: "+commentId);
        RequestParams params=new RequestParams(HttpUrlUtils.CIRCLE_DETAIL_COMMENT_BACK);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("commentId",commentId+"");
      /*  if(commentId==index){
            params.addBodyParameter("commentId",commentId+"");
        }else{
            params.addBodyParameter("commentId",index+"");
        }*/
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("allcomment", "onSuccess: "+result);
                Gson gson=new Gson();
                CircleDetailAllCommentBean circleDetailAllCommentBean = gson.fromJson(result, CircleDetailAllCommentBean.class);
                boolean state = circleDetailAllCommentBean.isState();
                if(state){
                    CircleDetailAllCommentBean.CommentModelBean commentModel = circleDetailAllCommentBean.getCommentModel();
                    totalPage=commentModel.getTotalPages();
                    tvContent.setText(commentModel.getCommentContent());
                    tvTime.setText(commentModel.getCreateTimeStr());
                    tvName.setText(commentModel.getNickName());
                    x.image().bind(ivPortrait,commentModel.getHeadImg(),imageOptions);
                    replayCommentList.addAll(commentModel.getReplayCommentList());
                    etComment.setText("");
                }else {
                    ToastUtils.showCenter(getApplicationContext(),circleDetailAllCommentBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvComment,"加载失败,点击重试", ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.showCenter(getBaseContext(),"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                adapter.update(replayCommentList);
                layoutLoading.setVisibility(View.GONE);
                //hud.dismiss();

            }
        });

    }

    private void initToolbar() {
        titleTv.setText("评论");
        titleTv.setTextColor(Color.WHITE);
        viewline.setBackgroundResource(R.color.home_tab_title_color_check);
        imageView.setImageResource(R.drawable.back);
        showBack(toolbar,imageView);
        toolbar.setBackgroundResource(R.color.home_tab_title_color_check);
        layoutComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etComment.setHint("评论");
            }
        });
    }
    @Override
    public int getLayout() {
        return R.layout.activity_call_back;
    }
}
