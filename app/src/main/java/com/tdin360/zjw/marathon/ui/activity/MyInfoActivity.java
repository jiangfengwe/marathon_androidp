package com.tdin360.zjw.marathon.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.UserModel;
import com.tdin360.zjw.marathon.utils.FileManager;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MyDatePickerDialog;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.weight.CircleImageView;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.app.ParamsBuilder;
import org.xutils.x;
import java.io.File;
import java.io.FileNotFoundException;


/**
 *
 * 我的信息
 */
public class MyInfoActivity extends Activity implements MyDatePickerDialog.OnMyDatePickerChangeListener{

    private TextView edit;
    private CircleImageView imageView;
    private EditText editName;
    private EditText editTel;
    private EditText editEmail;
    RadioButton gender1,gender2;
    private boolean isCanEdit;
    private TextView tip;

    //出生日期选择相关
    private TextView editBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        initView();

    }

    private void initView() {

        this.edit = (TextView) this.findViewById(R.id.edit);
        this.imageView = (CircleImageView) this.findViewById(R.id.imageView);
        this.imageView.setBorderColor(R.color.white);
        this.tip = (TextView) this.findViewById(R.id.tip);
        this.imageView.setBorderWidth(15);
        this.editName = (EditText) this.findViewById(R.id.name);
        this.editTel = (EditText) this.findViewById(R.id.tel);
        this.editEmail = (EditText) this.findViewById(R.id.email);
        this.editBirth = (TextView) this.findViewById(R.id.birthday);
        this.gender1 = (RadioButton) this.findViewById(R.id.radio1);
        this.gender2 = (RadioButton) this.findViewById(R.id.radio2);



        //加载用户信息
          loadData();

        this.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit.getText().toString().equals("编辑")) {

                    setIsEdit(true);

                } else {
                    //保存
                    submit();

                }

            }
        });


            //更换头像
            this.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isCanEdit) {//编辑状态下才能进行编辑
                        AlertDialog.Builder alert = new AlertDialog.Builder(MyInfoActivity.this);

                        alert.setTitle("更改头像");
                        alert.setCancelable(false);
                        alert.setItems(new String[]{"打开照相机", "从相册中选择", "取消"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0:
                                        //android 6.0 判断是否拥有打开照相机的权限
                                        if (ActivityCompat.shouldShowRequestPermissionRationale(MyInfoActivity.this, Manifest.permission.CAMERA)) {
                                            openCamera();
                                        } else {
//                                       android 6.0申请照相机和读写内存的权限
                                            ActivityCompat.requestPermissions(MyInfoActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                                        }
                                        break;
                                    case 1:
                                        selectPic();
                                        break;
                                    case 2:
                                        dialog.dismiss();
                                        break;

                                }
                            }
                        });
                        alert.show();

                    }
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



        }




private void loadData(){

        RequestParams params = new RequestParams(HttpUrlUtils.GET_MYINFO);
        params.addQueryStringParameter("phone",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject json = new JSONObject(s);

                    Log.d("-------->修改个人资料", "onSuccess: "+s);
                    String name = json.getString("Name");
                    String email = json.getString("Email");
                    String birthDate = json.getString("BirthDate");
                    boolean gender = json.getBoolean("Gender");

                    UserModel model = new UserModel("",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName(),name,email,birthDate,gender);
                      showUserData(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

                //设置信息不可编辑
                setIsEdit(false);
            }
        });



}

    //显示用户信息
    private void showUserData(UserModel model){

        this.editName.setText(model.getName());
        this.editTel.setText(model.getPhone());
        this.editEmail.setText(model.getEmail());
        this.editBirth.setText(model.getBirthday());
        if(model.isGender()){
           gender1.setChecked(true);
        }else {
          gender2.setChecked(true);
        }


    }

    //打开相机
    private static  final  int OpenCameraRequestCode =1;
    private void openCamera(){

            try {
             File file = FileManager.getFileManager().saveFilePath("user.jpg");
                Uri u = Uri.fromFile(file);
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


        if(resultCode!=Activity.RESULT_OK){

            return;
        }

        //照相
        if(requestCode==OpenCameraRequestCode){

            try {
                File file = FileManager.getFileManager().getFile();

                Uri u = Uri.fromFile(file);
               Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(u));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MyInfoActivity.this,"获取照片失败",Toast.LENGTH_SHORT).show();
            }


            //从相册选择
        }else if(requestCode==SelectRequestCode&&data!=null){

            Uri uri = data.getData();
            try {
              Bitmap bitmap  = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                if(bitmap!=null)
              imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    //获得权限后执行
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==0){

            openCamera();
        }
    }

    //保存更改信息
    private void submit() {



        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String birthday = editBirth.getText().toString().trim();
         boolean gender;

         if(gender1.isChecked()){
             gender=true;
         }else {

             gender=false;
         }



        RequestParams params = new RequestParams(HttpUrlUtils.CHANGE_MYINFO);
        params.addQueryStringParameter("phone", SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getName());
        params.addQueryStringParameter("Email",email);
        params.addQueryStringParameter("name",name);
        params.addQueryStringParameter("Gender",gender+"");
        params.addQueryStringParameter("BirthDate",birthday);
        params.addQueryStringParameter("Password",SharedPreferencesManager.getLoginInfo(MyInfoActivity.this).getPassword());

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


           edit.setText("保存");
           tip.setVisibility(View.VISIBLE);

       }else {

           edit.setText("编辑");
           tip.setVisibility(View.INVISIBLE);

       }
       this.gender1.setClickable(isEdit);
       this.gender2.setClickable(isEdit);
       this.isCanEdit=isEdit;
       this.isEdit(isEdit,editName);
       this.isEdit(isEdit,editTel);
       this.isEdit(isEdit,editEmail);

   }
    //返回
    public void back(View view) {

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


    @Override
    public void onChange(int year, int month, int day) {

        editBirth.setText(new StringBuilder().append( year).append("-").append(month).append("-").append(day));
    }
}
