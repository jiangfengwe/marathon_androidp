package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.SingleClass;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.model.SearchBean;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.edit)
    private EditText editText;
    @ViewInject(R.id.mToolBar)
    private Toolbar mToolBar;
    @ViewInject(R.id.iv_search_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_clean)
    private ImageView ivClean;
    @ViewInject(R.id.iv_search)
    private ImageView tvSearch;
    @ViewInject(R.id.resultCount)
    private TextView resultCount;
    @ViewInject(R.id.rv_search)
    private RecyclerView rvSearche;
    private String key;


    private int pageIndex=1;
    private int totalPage;
    private int pageSize=10;
    private boolean flag=false;
    @ViewInject(R.id.springView)
    private SpringView springView;

    @ViewInject(R.id.errorView)
    private ErrorView mErrorView;

    private RecyclerViewBaseAdapter adapter;
    private List<String> list=new ArrayList<>();
    private List<SearchBean.ModelBean.BJEventSystemListModelBean> bjEventSystemListModel=new ArrayList<>();

    private ImageOptions imageOptions;
    private TagGroup mTagGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageOptions= new ImageOptions.Builder().setFadeIn(true)//淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(true) //设置图片显示为圆形
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //.setSquare(true) //设置图片显示为正方形
                .setCrop(true).setSize(130,130) //设置大小
                //.setAnimation(animation) //设置动画
                .setFailureDrawableId(R.drawable.add_lose_square) //设置加载失败的动画
                // .setFailureDrawableId(int failureDrawable) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.drawable.add_lose_square) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
                //.setRadius(10)
                .setUseMemCache(true).build();
        initToolbar();
        initRecyclerView();
        initView();
    }

    private void initView() {
        ivClean.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
    }
    private void initNet() {
        //加载失败点击重试
        mErrorView.setErrorListener(new ErrorView.ErrorOnClickListener() {
            @Override
            public void onErrorClick(ErrorView.ViewShowMode mode) {
                switch (mode){
                    case NOT_NETWORK:
                        initData();
                        break;

                }
            }
        });
        //判断网络是否处于可用状态
        if(NetWorkUtils.isNetworkAvailable(this)){
            //加载网络数据
            initData();
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
    private void initData() {
        bjEventSystemListModel.clear();
        bjEventSystemListModel.clear();
        // 1.获取输入的搜索关键词
        key= editText.getText().toString().trim();
        if(key.equals("")){
            Toast.makeText(this,"请输入关键字",Toast.LENGTH_SHORT).show();
            return;
        }
        layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();
        initSearch();
    }

    private void initSearch() {
        RequestParams params=new RequestParams(HttpUrlUtils.EVENT);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("pageSize",""+pageSize);
        params.addBodyParameter("pageIndex",""+pageIndex);
        params.addBodyParameter("IsStart","");
        params.addBodyParameter("name",key);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("eventsearch", "onSuccess: "+result);
                Gson gson=new Gson();
                SearchBean searchBean = gson.fromJson(result, SearchBean.class);
                boolean state = searchBean.isState();
                if(state){
                    SearchBean.ModelBean model = searchBean.getModel();
                    totalPage=model.getTotalPages();
                    bjEventSystemListModel.addAll(model.getBJEventSystemListModel());
                    if(bjEventSystemListModel.size()<=0){
                        mErrorView.show(rvSearche,"暂时没有数据", ErrorView.ViewShowMode.NOT_DATA);
                    }else {
                        mErrorView.hideErrorView(rvSearche);
                    }
                }else{
                    ToastUtils.showCenter(getContext(),searchBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                mErrorView.show(rvSearche,"加载失败,点击重试",ErrorView.ViewShowMode.NOT_NETWORK);
                ToastUtils.show(SearchActivity.this,"网络不给力,连接服务器异常!");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                //hud.dismiss();
                layoutLoading.setVisibility(View.GONE);
                adapter.update(bjEventSystemListModel);
                resultCount.setVisibility(View.VISIBLE);
                resultCount.setText("共搜到"+bjEventSystemListModel.size()+"条结果");

            }
        });
    }

    private void initRecyclerView() {
        for (int i = 0; i <9 ; i++) {
            list.add(""+i);
        }
        adapter=new RecyclerViewBaseAdapter<SearchBean.ModelBean.BJEventSystemListModelBean>(getApplicationContext(),bjEventSystemListModel,R.layout.item_search) {
            @Override
            protected void onBindNormalViewHolder(NormalViewHolder holder, SearchBean.ModelBean.BJEventSystemListModelBean model) {
                /*ImageView ivPic = (ImageView) holder.getViewById(R.id.iv_event);
                x.image().bind(ivPic,model.getEventAppCoverPictureUrl(),imageOptions);
                holder.setText(R.id.tv_event_time_competition,model.getApplyEndTimeStr());
                holder.setText(R.id.tv_event_time_apply,model.getApplyStartTimeStr());*/
                holder.setText(R.id.tv_search_name,model.getName());
               /* TextView apply = (TextView) holder.getViewById(R.id.tv_apply);
                boolean isRegister = model.isIsRegister();

                if(isRegister){
                    apply.setText("正在报名>>");
                    apply.setTextColor(Color.parseColor("#ff621a"));
                    apply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(SearchActivity.this,ApplyActivity.class);
                            startActivity(intent);

                        }
                    });
                }else {
                    apply.setText("报名截止>>");
                    apply.setTextColor(Color.parseColor("#9b9b9b"));
                }*/
            }
        };
        rvSearche.setAdapter(adapter);
        rvSearche.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String eventId = bjEventSystemListModel.get(position).getId()+"";
                SingleClass.getInstance().setEventId(eventId);
                Intent intent=new Intent(SearchActivity.this,WebActivity.class);
                startActivity(intent);
            }
        });
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.onFinishFreshAndLoad();
                bjEventSystemListModel.clear();
                pageIndex=1;
                initSearch();
            }

            @Override
            public void onLoadmore() {
                springView.onFinishFreshAndLoad();
                if(totalPage<=pageIndex){
                    return;
                }
                if(totalPage>pageIndex){
                    pageIndex++;
                    initSearch();
                }

            }
        });
        springView.setHeader(new DefaultHeader(getApplicationContext()));
        springView.setFooter(new DefaultFooter(getApplicationContext()));
    }
    private void initToolbar() {
        showBack(mToolBar,ivBack);
        //监听搜索框值的变化
        this.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    ivClean.setVisibility(View.VISIBLE);
                }else {
                    ivClean.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_clean:
                //一键清空
                editText.setText("");
                break;
            case R.id.iv_search:
                //点击搜索
                initNet();
                break;
        }

    }
}
