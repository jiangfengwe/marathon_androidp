package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ChangeSignBean;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
* 修改签名
*/

public class ChangeSignActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;


    @ViewInject(R.id.btn_Back)
    private ImageView imageView;
    @ViewInject(R.id.line)
    private View viewline;
    @ViewInject(R.id.toolbar_title)
    private TextView titleTv;

    @ViewInject(R.id.btn_change_sign_sure)
    private Button btnSure;
    @ViewInject(R.id.et_change_sign)
    private EditText etSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
        initView();

    }

    private void initData() {
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String customerId = loginInfo.getId()+"";
        final String customerSign = etSign.getText().toString().trim();
        if(TextUtils.isEmpty(customerSign)){
            ToastUtils.showCenter(getApplicationContext(),"签名内容不能为空");
            return;
        }

        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
       /* layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();*/
        RequestParams params=new RequestParams(HttpUrlUtils.CHANGE_SIGN);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("customerId",customerId);
        params.addBodyParameter("customerSign",customerSign);
        params.setConnectTimeout(5000);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("sign", "onSuccess: "+result);
                Gson gson=new Gson();
                ChangeSignBean changeSignBean = gson.fromJson(result, ChangeSignBean.class);
                boolean state = changeSignBean.isState();
                if(state){
                    ToastUtils.showCenter(getApplicationContext(),changeSignBean.getMessage());
                    LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                    LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(loginInfo.getId(), loginInfo.getHeadImg(),
                            loginInfo.getNickName(), loginInfo.isGender(), loginInfo.getUnionid(), loginInfo.isIsBindPhone(),
                            customerSign, loginInfo.getPhone(),loginInfo.getLogin());
                    //保存用户登录数据
                    SharedPreferencesManager.saveLoginInfo(ChangeSignActivity.this,userBean);
                    //通知个人中心更新签名
                    Intent intent = new Intent(MyFragment.ACTION);
                    sendBroadcast(intent);

                    EnumEventBus sign = EnumEventBus.SIGN;
                    EventBus.getDefault().post(new EventBusClass(sign));
                    finish();
                }else{
                    ToastUtils.showCenter(getApplicationContext(),changeSignBean.getMessage());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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

    private void initView() {
        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
        String customerSign = loginInfo.getCustomerSign();
        etSign.setText(customerSign);
        etSign.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        btnSure.setOnClickListener(this);
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
        titleTv.setText("修改签名");

    }
    @Override
    public int getLayout() {
        return R.layout.activity_change_sign;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change_sign_sure:
                //ToastUtils.show(getApplicationContext(),"sure");
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

                break;
        }

    }
}
