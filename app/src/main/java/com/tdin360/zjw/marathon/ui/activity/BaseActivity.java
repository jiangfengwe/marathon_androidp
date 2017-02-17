package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ShareInfo;
import com.tdin360.zjw.marathon.utils.ShareUtil;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView toolBarTitle;
    private ImageView shareImage;
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        this.mToolBar= (Toolbar) this.findViewById(R.id.mToolBar);
        this.toolBarTitle= (TextView) this.findViewById(R.id.toolbar_title);
        this.shareImage= (ImageView) this.findViewById(R.id.share);
        this.btnBack= (ImageView) this.findViewById(R.id.btn_Back);
        setSupportActionBar(this.mToolBar);
        //设置默认标题不显示
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

//    设置标题
    public void setToolBarTitle(CharSequence title){

        if(title!=null){

            this.toolBarTitle.setText(title);
        }
    }

    /**
     * 设置标题字体颜色
     * @param color
     */
    public void setToolBarTitleColor(int color){

         this.toolBarTitle.setTextColor(color);
    }

    /**
     * 设置导航条颜色
     * @param color
     */
    public void setmToolBarColor(int color){

        this.mToolBar.setBackgroundColor(color);
    }

    /**
     * 显示返回按钮
     */
    public void showBackButton(){

        this.btnBack.setVisibility(View.VISIBLE);
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public abstract int getLayout();


    //处理分享
    public void showShareButton(final ShareInfo shareInfo){


        this.shareImage.setVisibility(View.VISIBLE);
        this.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 share(shareInfo);
            }
        });

    }


    /**
     * 分享模块
     *
     */
    private void share(final ShareInfo shareInfo){
        ShareUtil.getInstance(this);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();

        View view = View.inflate(this, R.layout.share_selector_dialog, null);

        dialog.setView(view);

        //分享到微信朋友圈
        view.findViewById(R.id.share1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShareUtil.shareLink(shareInfo, ShareUtil.ShareType.TYPE1);
                dialog.dismiss();
            }
        });
        //分享给微信好友
            view.findViewById(R.id.share2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareLink(shareInfo, ShareUtil.ShareType.TYPE2);

                dialog.dismiss();
            }
        });

   // 微信收藏
            view.findViewById(R.id.share3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareLink(shareInfo, ShareUtil.ShareType.TYPE3);
                dialog.dismiss();
            }
        });

        //取消
             view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
