package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.LoginModel;
import com.tdin360.zjw.marathon.model.UserModel;
import com.tdin360.zjw.marathon.ui.fragment.MyFragment;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MyDatePickerDialog;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.db.impl.MyInfoServiceImpl;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.io.File;


/**
 *
 * 我的个人资料
 */
public class MyInfoActivity extends BaseActivity implements MyDatePickerDialog.OnMyDatePickerChangeListener{

    private ImageView imageView;
    private EditText editName;
    private EditText editEmail;
    private RadioButton gender1,gender2;
    private boolean isCanEdit;
    private LinearLayout main;
    private TextView loadFail;
    //出生日期选择相关
    private TextView editBirth;
    //用于存储用户选择裁剪后的头像
    private KProgressHUD hud;

    private MyInfoServiceImpl service;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("个人信息");
        showBackButton();

        navRightItemTitle().setText("编辑");

        initView();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_info;
    }

    private void initView() {

        this.service = new MyInfoServiceImpl(this);
        this.imageView = (ImageView) this.findViewById(R.id.imageView);
        this.editName = (EditText) this.findViewById(R.id.name);

        this.editEmail = (EditText) this.findViewById(R.id.email);
        this.editBirth = (TextView) this.findViewById(R.id.birthday);
        this.gender1 = (RadioButton) this.findViewById(R.id.radio1);
        this.gender2 = (RadioButton) this.findViewById(R.id.radio2);
        this.main = (LinearLayout) this.findViewById(R.id.main);
        this.loadFail = (TextView) this.findViewById(R.id.loadFail);

        initHUD();

        //加载失败点击重新加载
        this.loadFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               loadData();
            }
        });


        this.navRightItemTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (navRightItemTitle().getText().toString().equals("编辑")) {

                    setIsEdit(true);

                } else {
                    //保存
                    submit();

                }

            }
        });


            //更换头像
            this.findViewById(R.id.head_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        AlertDialog.Builder alert = new AlertDialog.Builder(MyInfoActivity.this);

                        alert.setTitle("更改头像");
                        alert.setCancelable(false);
                        alert.setItems(new String[]{"拍照", "相册", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0:
                                        //android 6.0 判断是否拥有打开照相机的权限
                                        if(hasPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                                            openCamera();
                                        }else {

                                            requestPermission(Constants.CAMERA_CODE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        }
                                        break;
                                    case 1:
                                        //检查sd卡读取权限
                                        if(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                                            selectPic();
                                        }else {

                                            requestPermission(Constants.WRITE_EXTERNAL_CODE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        }
                                        break;
                                    case 2:
                                        dialog.dismiss();
                                        break;

                                }
                            }
                        });
                        alert.show();


                }
            });

            this.findViewById(R.id.birthdayBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isCanEdit) {
                        MyDatePickerDialog dialog = new MyDatePickerDialog(MyInfoActivity.this);
                        dialog.setOnMyDatePickerChangeListener(MyInfoActivity.this);
                        dialog.show();
                    }
                }
            });


        showHeadImage();

        //加载用户信息
        loadData();

        }
    /**
     * 初始化提示框
     */
    private void initHUD(){

        //显示提示框
        this.hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.setCancellable(true);
        hud.setAnimationSpeed(1);
        hud.setDimAmount(0.5f);

    }
    /**
     * 显示头像
     */
    private void showHeadImage(){

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(80), DensityUtil.dip2px(80))//图片大小
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(80))
//                .setLoadingDrawableId(R.drawable.signup_photo)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.signup_photo)//加载失败后默认显示图片
                .build();
          x.image().bind(imageView,SharedPreferencesManager.getLoginInfo(this).getImageUrl(),imageOptions);


    }
    //加载数据(包括缓存数据和网络数据)
    private void loadData() {


        /**
         * 判断网络是否处于可用状态
         */
        if (NetWorkUtils.isNetworkAvailable(this)) {

            //加载网络数据
            httpRequest();
        } else {



            //获取缓存数据

            UserModel userModel = service.getUserModel(SharedPreferencesManager.getLoginInfo(this).getName());
            if(userModel==null){
                loadFail.setText("点击重新加载");
                loadFail.setVisibility(View.VISIBLE);

                //如果获取得到缓存数据则加载本地数据
                //如果缓存数据不存在则需要用户打开网络设置

                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setMessage("网络不可用，是否打开网络设置");
                alert.setCancelable(false);
                alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //打开网络设置

                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));

                    }
                });
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

            }else {

                showUserData(userModel);
            }



        }
    }

    /**
     * 获取网络数据
     */
    private void httpRequest(){

        service.delete();
         loadFail.setVisibility(View.GONE);

         hud.show();
        RequestParams params = new RequestParams(HttpUrlUtils.GET_MYINFO);
        params.addQueryStringParameter("phone",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject json = new JSONObject(s);

                    String name = json.getString("Name");

                    String email = json.getString("Email");
                    String birthDate = json.getString("BirthDate");
                    boolean gender = json.getBoolean("Gender");

                    UserModel model = new UserModel("",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName(),name,email,birthDate,gender);
                      showUserData(model);
                      service.addInfo(model);

                    //设置信息不可编辑
                    setIsEdit(false);
                    loadFail.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

               Toast.makeText(MyInfoActivity.this,"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();

                loadFail.setText("点击重新加载");
                loadFail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

                hud.dismiss();



            }
        });



}

    //显示用户信息
    private void showUserData(UserModel model){

        this.editName.setText((model.getName()==null || model.getName().equals("null"))? "未设置":model.getName());
        this.editEmail.setText((model.getEmail()==null || model.getEmail().equals("null"))? "未设置":model.getEmail());
        this.editBirth.setText((model.getBirthday()==null || model.getBirthday().equals("null"))? "未设置":model.getBirthday());
        if(model.isGender()){
           gender1.setChecked(true);
        }else {
          gender2.setChecked(true);
        }

        main.setVisibility(View.VISIBLE);
    }

    //打开相机
    private static  final  int OpenCameraRequestCode =1;
    private void openCamera(){

            try {


                Uri u = Uri.fromFile(new File(getExternalFilesDir("images").getPath()+"/temp.jpg"));
                //调用照相机
                Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.Images.Media.ORIENTATION,OpenCameraRequestCode);
                 intent.putExtra(MediaStore.EXTRA_OUTPUT,u);
                startActivityForResult(intent,OpenCameraRequestCode);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MyInfoActivity.this,"sd卡不可用",Toast.LENGTH_SHORT).show();
            }

    }

    //打开相册选择图片
    private static  final  int SelectRequestCode =2;
    private void selectPic(){

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,SelectRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode!= RESULT_OK){

            return;
        }

//通过照相或者选择本地相册返回这里调用系统图片裁剪
        if(requestCode==SelectRequestCode)

        {
            //将选择回来的照片进行裁剪
            Uri uri = data.getData();
            startPhotoZoom(uri);
        }else if(requestCode==OpenCameraRequestCode){

            Uri uri = Uri.fromFile(new File(getExternalFilesDir("images").getPath()+"/temp.png"));
            startPhotoZoom(uri);
        }

        //图片裁剪后返回这里
        if(requestCode==Constants.EDIT_IMAGE_CODE){

             upLoadFile(new File(getExternalFilesDir("images").getPath()+"/temp.png"));

        }

    }


    /**
     * 打开相机权限授权成功
     */
    @Override
    public void doCameraPermission() {

        openCamera();
    }

    /**
     * sd卡读取权限授权成功
      */
    @Override
    public void doSDCardPermission() {

         selectPic();
    }

    /**
     * 上传文件
     * @param file 文件路径
     */
    private void upLoadFile(final File file){


        hud.show();
        RequestParams params = new RequestParams(HttpUrlUtils.UPLOAD_IMAGE);
        LoginModel info = SharedPreferencesManager.getLoginInfo(this);
        params.addQueryStringParameter("phone",info.getName());
        params.addQueryStringParameter("password",info.getPassword());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.setMultipart(true);
        try {
            params.addBodyParameter("uploadedFile",file,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {


                try {
                    JSONObject json  = new JSONObject(result);
                    JSONObject message = json.getJSONObject("EventMobileMessage");
                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");
                    String url = json.getString("AvatarUrl");

                    if(success){
                        Toast.makeText(MyInfoActivity.this,"头像上传成功",Toast.LENGTH_SHORT).show();
                        //上传成功将新的图片地址保存起来

                         SharedPreferencesManager.updateImageUrl(getApplicationContext(),url);
                        //显示上传的头像
                        showHeadImage();

                        /**
                         * 通知个人中心更新头像
                         */
                         Intent intent = new Intent(MyFragment.ACTION);
                         sendBroadcast(intent);

                    }else {

                        Toast.makeText(MyInfoActivity.this,reason,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Snackbar.make(main,"头像上传失败",Snackbar.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            Snackbar.make(main,"头像上传失败",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

                hud.dismiss();

            }
        });

    }

    //保存更改信息
    private void submit() {


//         保存个人信息之前判断网络是否可用
        if(!NetWorkUtils.isNetworkAvailable(this)){
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String birthday = editBirth.getText().toString().trim();
         boolean gender;

         if(gender1.isChecked()){
             gender=true;//男
         }else {
             gender=false;//女
         }

         hud.show();

        RequestParams params = new RequestParams(HttpUrlUtils.CHANGE_MYINFO);
        params.addBodyParameter("phone", SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName());
        params.addBodyParameter("Email",email);
        params.addBodyParameter("name",name=="未设置"?"":name);
        params.addBodyParameter("Gender",gender+"");
        params.addBodyParameter("BirthDate",birthday=="未设置"?"":birthday);
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        params.addBodyParameter("Password",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getPassword());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject json = new JSONObject(s);



                    boolean success = json.getBoolean("Success");
                    String reason = json.getString("Reason");
                    Toast.makeText(MyInfoActivity.this,reason,Toast.LENGTH_SHORT).show();
                    if(success){

                        setIsEdit(false);
                        isCanEdit=false;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Toast.makeText(MyInfoActivity.this,"网络错误或无法访问服务器!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

                hud.dismiss();

            }
        });


    }

//    设置edit是否可编辑
    private void isEdit(boolean value, EditText editText) {

        if (value) {

            editText.setFocusable(true);

            editText.setFocusableInTouchMode(true);

            editText.setFilters(new InputFilter[] { new InputFilter() {

                @Override

                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                    return null;

                }

            } });

        } else {

           //设置不可获取焦点

            editText.setFocusable(false);

            editText.setFocusableInTouchMode(false);

           //输入框无法输入新的内容

            editText.setFilters(new InputFilter[] { new InputFilter() {

                @Override

                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                    return source.length() < 1 ? dest.subSequence(dstart, dend) : "";

                }

            } });

        }

    }

   private void setIsEdit(boolean isEdit){

       if(isEdit){


           navRightItemTitle().setText("保存");


       }else {

           navRightItemTitle().setText("编辑");


       }
       this.gender1.setClickable(isEdit);
       this.gender2.setClickable(isEdit);
       this.isCanEdit=isEdit;
       this.isEdit(isEdit,editName);
       this.isEdit(isEdit,editEmail);

   }
    //返回
    public void back(View view) {


          checkSave();

    }


    @Override
    public void onChange(int year, int month, int day) {

        editBirth.setText(new StringBuilder().append( year).append("-").append(month).append("-").append(day));
    }

    @Override
    public void onBackPressed() {



        checkSave();

    }


    //检查用户编辑是否保存
    private void checkSave(){


        if(isCanEdit){

            AlertDialog.Builder alert = new AlertDialog.Builder(MyInfoActivity.this);

            alert.setTitle("信息尚未保存,确定要退出吗?");
            alert.setCancelable(false);
            alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alert.show();
        }else {

            finish();
        }
    }


    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {


        /*
         * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
         * yourself_sdk_path/docs/reference/android/content/Intent.html
         * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
         * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
         * 制做的了...吼吼
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        intent.putExtra("output", Uri.fromFile(new File(getExternalFilesDir("images").getPath()+"/temp.png")));
        intent.putExtra("outputFormat", "PNG");
        startActivityForResult(intent,Constants.EDIT_IMAGE_CODE);
    }

}
