package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
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
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.ChangePhoneNewBean;
import com.tdin360.zjw.marathon.model.ChangePhoneNewSureBean;
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
 * 修改号码下一步，确认修改
 */

public class ChangePhoneNewActivity extends BaseActivity implements View.OnClickListener {
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


    @ViewInject(R.id.et_change_phone_new)
    private EditText etNew;
    @ViewInject(R.id.et_change_phone_input_code)
    private EditText etCode;
    @ViewInject(R.id.tv_change_phone_get_code)
    private TextView tvCode;
    @ViewInject(R.id.btn_change_new_phone_sure)
    private Button btnSure;
    @ViewInject(R.id.iv_change_phone_new_cancel)
    private ImageView ivCancel;

    private static final int CODE=0x0102;
    private int time;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.sendEmptyMessageDelayed(CODE,1000);
            if(time>0){
                tvCode.setText(time+"s后获取");
                tvCode.setBackgroundColor(Color.parseColor("#ebebeb"));
                tvCode.setTextColor(Color.parseColor("#9b9b9b"));
                tvCode.setClickable(false);
                time--;
            }else {
                tvCode.setClickable(true);
                handler.removeMessages(CODE);
                tvCode.setText("获取验证码");
                tvCode.setTextColor(Color.parseColor("#ffffff"));
                tvCode.setBackgroundColor(Color.parseColor("#ff621a"));
                tvCode.setEnabled(true);
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initView();
    }

    private void initView() {
        tvCode.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        //取消键的显示与隐藏
        etNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etNew.getEditableText() + "";
                if(!TextUtils.isEmpty(name)){
                    ivCancel.setVisibility(View.VISIBLE);
                }else{
                    ivCancel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        titleTv.setText("修改手机号");
    }
    @Override
    public int getLayout() {
        return R.layout.activity_change_phone_new;
    }
    /**
     * 开的倒计时
     */
    private void startTaskTimer(){
        time=60;
        tvCode.setEnabled(false);
        handler.sendEmptyMessage(CODE);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_change_phone_get_code:
                //新手机发送验证码
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                   initNewCode();
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
            case R.id.iv_change_phone_new_cancel:
                //清空新号码
                etNew.setText("");
                break;
            case R.id.btn_change_new_phone_sure:
                //确认更改手机号
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
    private void initData() {
        try{
            byte[] mBytes=null;
            String validCode = etCode.getText().toString().trim();
            final String phone = etNew.getText().toString().trim();
            if(TextUtils.isEmpty(phone)){
                ToastUtils.showCenter(getApplicationContext(),"号码不能为空");
                return;
            }
            if(!isMobileNO(phone)){
                ToastUtils.showCenter(getApplicationContext(),"号码不符合规则");
                return;
            }
            if(TextUtils.isEmpty(validCode)){
                ToastUtils.showCenter(getApplicationContext(),"验证码不能为空");
                return;
            }
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
            String customerId = loginInfo.getId()+"";
            String string="{'userNewPhone':'"+phone+"','validCode':'"+validCode+"','customerId':'"+customerId+"','appKey': 'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.CHANGE_NEW_PHONE);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("phonecodenew", "onSuccess: "+result);
                    Gson gson=new Gson();
                    ChangePhoneNewSureBean changePhoneNewSureBean = gson.fromJson(result, ChangePhoneNewSureBean.class);
                    boolean state = changePhoneNewSureBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),changePhoneNewSureBean.getMessage());
                        LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                        LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(loginInfo.getId(), loginInfo.getHeadImg(),
                                loginInfo.getNickName(), loginInfo.isGender(), loginInfo.getUnionid(), loginInfo.isIsBindPhone(),
                                loginInfo.getCustomerSign(),phone,loginInfo.getLogin() );
                        //保存用户登录数据
                        SharedPreferencesManager.saveLoginInfo(ChangePhoneNewActivity.this,userBean);
                        //通知号码更新
                        EnumEventBus phone = EnumEventBus.PHONE;
                        EventBus.getDefault().post(new EventBusClass(phone));
                        //跳转到信息显示页面
                        Intent intent=new Intent(ChangePhoneNewActivity.this,MyInfoActivity.class);
                        startActivity(intent);
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),changePhoneNewSureBean.getMessage());
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
                    hud.dismiss();

                }
            });
        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }

    }
    /**
      * 验证手机格式
      */
    public static boolean isMobileNO(String mobiles) {
     /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。  
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    //原手机验证码
    private void initNewCode() {
        try{
            byte[] mBytes=null;
            final String phone = etNew.getText().toString().trim();
            LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
            String customerId = loginInfo.getId()+"";
            if(TextUtils.isEmpty(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不能为空");
                return;
            }
            if(!isMobileNO(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
            startTaskTimer();
            String string="{'userNewPhone':'"+phone+"','customerId':'"+customerId+"','appKey': 'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString= AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.CHANGE_NEW_PHONE_CODE);
            params.addBodyParameter("secretMessage",enString);
            params.setConnectTimeout(5000);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("phonenew", "onSuccess: "+result);
                    Gson gson=new Gson();
                    ChangePhoneNewBean changePhoneNewBean = gson.fromJson(result, ChangePhoneNewBean.class);
                    boolean state = changePhoneNewBean.isState();
                    if(state){
                        ToastUtils.showCenter(getApplicationContext(),changePhoneNewBean.getMessage());
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),changePhoneNewBean.getMessage());
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
                    layoutLoading.setVisibility(View.GONE);

                }
            });
        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }

    }
}
