package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.AESPsw.AES;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.jiguan.ExampleUtil;
import com.tdin360.zjw.marathon.model.LoginBean;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.model.LoginUserInfoBean;
import com.tdin360.zjw.marathon.model.OtherLoginBean;
import com.tdin360.zjw.marathon.model.OtherLoginUserInfoBean;
import com.tdin360.zjw.marathon.model.RegisterSureBean;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.LoginNavigationConfig;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.weight.ErrorView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import uk.co.senab.photoview.log.LoggerDefault;

import static com.tdin360.zjw.marathon.R.id.tel;

/**
 * 用户登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.layout_lading)
    private RelativeLayout layoutLoading;
    @ViewInject(R.id.iv_loading)
    private ImageView ivLoading;

    @ViewInject(R.id.iv_login_back)
    private ImageView ivBack;
    @ViewInject(R.id.tv_login_register)
    private TextView tvRegester;
    @ViewInject(R.id.et_login_phone)
    private EditText etPhone;
    @ViewInject(R.id.iv_login_cancel)
    private ImageView ivCancel;
    @ViewInject(R.id.et_login_psw)
    private EditText etPsw;
    @ViewInject(R.id.iv_login_psw_show)
    private ImageView ivPsw;
    @ViewInject(R.id.tv_login_forget)
    private TextView tvForget;
    @ViewInject(R.id.btn_login_sure)
    private Button btnSure;
    @ViewInject(R.id.layout_login_weixin)
    private LinearLayout layoutWeixin;
    private boolean flag;
    @ViewInject(R.id.cb_login_psw)
    private CheckBox cbPsw;

    private UMShareAPI mShareAPI;
    private String screen_name="";
    private String profile_image_url="";
    private String uid="";
    private String gender ;


   /* @ViewInject(R.id.tel)
    private EditText editTextName;
    @ViewInject(R.id.password)
    private EditText editTextPass;
    @ViewInject(R.id.clear)
    private ImageView clearBtn;*/
   private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    //Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            Log.d("code", "gotResult: "+code);
            switch (code) {
                case 0:
                    ToastUtils.showCenter(getApplicationContext(),"codeeeee");
                    logs = "Set tag and alias success";
                    // Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    //Log.e(TAG, logs);
            }
            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initOtherLogin();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvRegester.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
        ivPsw.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        layoutWeixin.setOnClickListener(this);
        //监听输入框值的变化
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etPhone.getEditableText() + "";
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
        cbPsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //显示密码
                    etPsw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPsw.setSelection(etPsw.getText().length());
                }else {
                    //隐藏密码
                    etPsw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPsw.setSelection(etPsw.getText().length());
                }
            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.iv_login_back:
                //返回
                finish();
                break;
            case R.id.tv_login_register:
                //注册
                intent = new Intent(LoginActivity.this,RegisterOneActivity.class);
                //intent.putExtra("type","zc");
                //intent.putExtra("title","注册");
                startActivity(intent);
                break;
            case R.id.iv_login_cancel:
                //清空手机号
                etPhone.setText("");
                break;
            case R.id.tv_login_forget:
                //忘记密码
                intent = new Intent(LoginActivity.this,ForgetPswActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login_sure:
                //登录
                //判断网络是否处于可用状态
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    initLogin();
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
            case R.id.layout_login_weixin:
                //微信登录
                if(NetWorkUtils.isNetworkAvailable(this)){
                    //加载网络数据
                    setAut(SHARE_MEDIA.WEIXIN);
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

    private void initOtherLogin() {
        //第三方登录
        mShareAPI= UMShareAPI.get(LoginActivity.this);
    }
    private void setAut(SHARE_MEDIA share_media) {
        mShareAPI.getPlatformInfo(LoginActivity.this,share_media, umAuthListener);
    }
    UMAuthListener umAuthListener=new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            Intent intent=getIntent();
            //final String detail = intent.getStringExtra("detail");
            //Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
            if(share_media==SHARE_MEDIA.WEIXIN){
                Log.d("map", "onComplete: "+map);
                screen_name = map.get("screen_name");
                profile_image_url=map.get("profile_image_url");
                gender=map.get("gender");
                Log.d("map", "onComplete: "+screen_name);
                uid= map.get("uid");
                String unionid = map.get("unionid");
                map.get("gender");

                LoginUserInfoBean.UserBean loginInfo = SharedPreferencesManager.getLoginInfo(getApplicationContext());
                LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(uid,profile_image_url,
                        screen_name,loginInfo.isGender(), loginInfo.getUnionid(), loginInfo.isIsBindPhone(),
                        loginInfo.getCustomerSign(), loginInfo.getPhone());
                //保存用户登录数据
                SharedPreferencesManager.saveLoginInfo(LoginActivity.this,userBean);
                //layoutRefresh.setVisibility(View.GONE);
                ToastUtils.showCenter(getApplicationContext(),"登录成功");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
                            //imm.hideSoftInputFromWindow(buttonLogin.getWindowToken(), 0);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
            if(profile_image_url!=null&&!profile_image_url.equals("")){
                getImage(profile_image_url);
            }else{
                getImage(null);
            }


        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Toast.makeText(LoginActivity.this, "失败了", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };
    public void getImage(final String path) {
        //读取头像图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] data=null;
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(3000);
                    con.setRequestMethod("GET");
                    int responseCode = con.getResponseCode();
                    int len = 0;
                    data= new byte[1024];
                    if (responseCode == 200) {
                        in = con.getInputStream();
                        while ((len = in.read(data)) != -1) {
                            out.write(data, 0, len);
                        }
                        data = out.toByteArray();
                        Log.d("head", "run: "+data.length);
                        headImg(data);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }).start();


    }
    private void headImg(byte[] data) {
       /* final KProgressHUD hud = KProgressHUD.create(LoginActivity.this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();*/
       /* layoutLoading.setVisibility(View.VISIBLE);
        ivLoading.setBackgroundResource(R.drawable.loading_before);
        AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
        background.start();*/
        RequestParams params=new RequestParams(HttpUrlUtils.MARATHON_OTHERLOGIN);
        params.addBodyParameter("uId",uid);
        params.addBodyParameter("nickName",screen_name);
        params.addBodyParameter("gender",gender.equals("男") ? "true":"false");
        if(data!=null){
            params.addBodyParameter("uploadedFile",data,null);
        }
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("other", "onSuccess: "+result);
                Gson gson=new Gson();
                OtherLoginBean otherLoginBean = gson.fromJson(result, OtherLoginBean.class);
                boolean state = otherLoginBean.isState();
                if(state){
                    String userSecretMessage = otherLoginBean.getUserSecretMessage();
                    String decrypt = AES.decrypt(userSecretMessage);
                    Log.d("decrypt", "onSuccess: "+decrypt);
                    OtherLoginUserInfoBean loginUserInfoBean = gson.fromJson(decrypt, OtherLoginUserInfoBean.class);
                    OtherLoginUserInfoBean.UserBean user = loginUserInfoBean.getUser();
                    //String id = user.getUnionid();
                   // String userUnionid = user.getUnionid();
                    String id = user.getId()+"";
                    String headImg = user.getHeadImg();
                    String nickName = user.getNickName();
                    boolean gender = user.isGender();
                    String unionid = user.getUnionid();
                    boolean isBindPhone = user.isIsBindPhone();
                    String customerSign = user.getCustomerSign();
                    String phone = user.getPhone();
                    LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(id, headImg, nickName, gender, unionid, isBindPhone, customerSign, phone);
                    //保存用户登录数据
                    SharedPreferencesManager.saveLoginInfo(LoginActivity.this,userBean);
                    //通知个人中心修改登录状态
                    Intent intent  =new Intent(MyFragment.ACTION);
                    sendBroadcast(intent);
                    if(isBindPhone){
                        finish();
                    }else{
                        Intent intent1=new Intent(LoginActivity.this,BindPhoneActivity.class);
                        intent1.putExtra("uId",unionid);
                        startActivity(intent1);
                    }

                }else {
                    Toast.makeText(getApplicationContext(),otherLoginBean.getMessage(),Toast.LENGTH_SHORT).show();
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
                //hud.dismiss();

            }
        });
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
    //用户登录
    private void initLogin() {
        try{
            byte[] mBytes=null;
            final String phone = etPhone.getText().toString().trim();
            if(TextUtils.isEmpty(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码能为空");
                return;
            }
            if(!isMobileNO(phone)){
                ToastUtils.showCenter(getApplicationContext(),"电话号码不符合规则");
                return;
            }
            String psw = etPsw.getText().toString().trim();
            if(TextUtils.isEmpty(psw)){
                ToastUtils.showCenter(getApplicationContext(),"密码不能为空");
                return;
            }

           /* layoutLoading.setVisibility(View.VISIBLE);
            ivLoading.setBackgroundResource(R.drawable.loading_before);
            AnimationDrawable background =(AnimationDrawable) ivLoading.getBackground();
            background.start();*/
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            String string="{'userPhone':'"+phone+"','password':'"+psw+"','appKey': 'BJYDAppV-2'}";
            mBytes=string.getBytes("UTF8");
            String enString=AES.encrypt(mBytes);
            RequestParams params=new RequestParams(HttpUrlUtils.MARATHON_LOGIN);
            params.addBodyParameter("secretMessage",enString);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d("login", "onSuccess: "+result);
                    Gson gson=new Gson();
                    LoginBean loginBean = gson.fromJson(result, LoginBean.class);
                    boolean state = loginBean.isState();
                    if(state){
                        String userSecretMessage = loginBean.getUserSecretMessage();
                        String decrypt = AES.decrypt(userSecretMessage);
                        Log.d("decrypt", "onSuccess: "+decrypt);

                        LoginUserInfoBean loginUserInfoBean = gson.fromJson(decrypt, LoginUserInfoBean.class);
                        LoginUserInfoBean.UserBean user = loginUserInfoBean.getUser();
                        String id = user.getId()+"";
                        String headImg = user.getHeadImg();
                        String nickName = user.getNickName();
                        boolean gender = user.isGender();
                        String unionid = user.getUnionid();
                        boolean isBindPhone = user.isIsBindPhone();
                        String customerSign = user.getCustomerSign();
                        String customerAlias = user.getCustomerAlias();
                        // 调用 Handler 来异步设置别名
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, customerAlias));
                        SharedPreferencesManager.writeAlias(getApplicationContext(),customerAlias);
                        Log.d("customerAlias", "onSuccess: "+customerAlias);
                        LoginUserInfoBean.UserBean userBean = new LoginUserInfoBean.UserBean(id, headImg, nickName, gender, unionid, isBindPhone, customerSign, phone);
                        //保存用户登录数据
                        SharedPreferencesManager.saveLoginInfo(LoginActivity.this,userBean);
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);
                        finish();
                    }else{
                        ToastUtils.showCenter(getApplicationContext(),loginBean.getMessage());
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
                    //layoutLoading.setVisibility(View.GONE);
                    hud.dismiss();

                }
            });

        }catch(Exception e){
            Log.d("error", "initData: "+e.getMessage());
        }


    }


    //用户登录
    /*private void login(){

    //验证用户输入

        final String tel = this.editTextName.getText().toString().trim();
        final String pass  =this.editTextPass.getText().toString().trim();

        if(tel.length()<11){

            Toast.makeText(this,"手机号输入有误!",Toast.LENGTH_SHORT).show();
            editTextName.requestFocus();
            return;
        }

        if(pass.length()==0){

            Toast.makeText(this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            editTextPass.requestFocus();
            return;
        }

     //显示提示框
        final KProgressHUD hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();

        //提交到服务器验证
        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_LOGIN);
        params.addQueryStringParameter("phone",tel);
        params.addQueryStringParameter("password",pass);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                Log.d("-----登录--->", "onSuccess: "+s);
                try {
                    JSONObject json = new JSONObject(s);

                    JSONObject message = json.getJSONObject("EventMobileMessage");

                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");

                    String avatarUrl = json.getString("AvatarUrl");

                    String customerId = json.getString("CustomerId");
                    //报名成功跳转到登录界面
                    if(success){

                        //保存用户登录数据
                        SharedPreferencesManager.saveLoginInfo(LoginActivity.this,new LoginModel(customerId,tel,pass,avatarUrl));
                        Toast.makeText(LoginActivity.this,"登录成功!",Toast.LENGTH_SHORT).show();
                        //通知个人中心修改登录状态
                        Intent intent  =new Intent(MyFragment.ACTION);
                        sendBroadcast(intent);
                        hud.dismiss();

                        //登录成功后根据指定目标跳转到指定界面
                        switch (LoginNavigationConfig.instance().getNavType()){

                            case SignUp://跳转到报名界面
                                intent = new Intent(LoginActivity.this,SignUpActivity.class);
                                startActivity(intent);
                            break;
                            case MySignUp://跳转到我的报名界面
                                intent = new Intent(LoginActivity.this,MySignUpListActivity.class);
                                startActivity(intent);
                                break;
                            case MyMark://跳转到我的成绩界面
                                intent = new Intent(LoginActivity.this,MyAchievementListActivity.class);
                                startActivity(intent);
                                break;
                            case MyFeed://跳转到我的反馈界面
                                intent = new Intent(LoginActivity.this,FeedbackListActivity.class);
                                startActivity(intent);
                                break;

                            case MyGoods://跳转到我的物资界面

                                intent = new Intent(LoginActivity.this,MyGoodsListActivity.class);
                                startActivity(intent);
                                break;

                            case Team://团队报名
                                intent = new Intent(LoginActivity.this,TeamSignUpActivity.class);
                                startActivity(intent);
                                break;

                        }

                            finish();

                    }else {
                        Toast.makeText(LoginActivity.this,reason,Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Toast.makeText(LoginActivity.this, "网络错误或访问服务器失败!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

                hud.dismiss();

            }
        });


    }*/


}
