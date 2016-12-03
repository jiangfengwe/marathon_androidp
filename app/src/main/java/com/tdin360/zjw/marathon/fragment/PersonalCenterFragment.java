package com.tdin360.zjw.marathon.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.activity.AboutUsActivity;
import com.tdin360.zjw.marathon.activity.LoginActivity;
import com.tdin360.zjw.marathon.activity.MyInfoActivity;
import com.tdin360.zjw.marathon.activity.RegisterActivity;
import com.tdin360.zjw.marathon.activity.RestPassWordActivity;
import com.tdin360.zjw.marathon.activity.SettingActivity;
import com.tdin360.zjw.marathon.activity.SignUpSearchResultActivity;
import com.tdin360.zjw.marathon.model.SignUpInfo;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkStateUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**个人中心
 * Created by Administrator on 2016/8/9.
 */
public class PersonalCenterFragment extends Fragment implements SettingActivity.OnIsClearDataListener{

    private static PersonalCenterFragment personalCenterFragment;
    private  TextView userName;
    public static PersonalCenterFragment newInstance(){

        if(personalCenterFragment==null){
            personalCenterFragment=new PersonalCenterFragment();
        }
        return personalCenterFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.personal_center_fargment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SettingActivity.setOnIsDataListener(this);
        userName  = (TextView) view.findViewById(R.id.userName);
        userName.setText(SharedPreferencesManager.getUserName(getActivity()));
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = userName.getText().toString();
                if(s.equals("未登录")){

             Intent intent = new Intent(getActivity(),LoginActivity.class);
                  startActivity(intent);
                }else {
                  toMyInfo();
                }



            }
        });

      //报名查询
       view.findViewById(R.id.signUpSearch).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(SharedPreferencesManager.isLogin(getActivity())){}
           }
       });

        //成绩查询
         view.findViewById(R.id.search_bar).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


             }
         });
        //我的信息

        view.findViewById(R.id.mySignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登录

                if(SharedPreferencesManager.isLogin(getActivity())){
                    //跳转到我的信息界面
                    toMyInfo();
                }else {
                    //未登录跳转到登录界面

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                }


            }
        });
//        修改登录密码
        view.findViewById(R.id.editPass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestPassWordActivity.class);
                startActivity(intent);
            }
        });
        //关于我们
        view.findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });

       //系统设置
        view.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }


//    跳转到我的信息界面
    private void toMyInfo(){

        Intent intent = new Intent(getActivity(), MyInfoActivity.class);
        startActivity(intent);

    }
    /**
     * 报名查询
     * 姓名
     * @param name
     *证件号
     * @param idNumber
     * 是否的报名
     * @param isSelf
     */
    public void searchSignUpInfo(String name, String idNumber, final boolean isSelf){

        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_SignUpInfoSEARCH);
        params.addParameter("Name",name);
        params.addParameter("IDNumber",idNumber);
        params.setConnectTimeout(10*1000);
        params.setMaxRetryCount(1);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject obj = new JSONObject(result);

                    if(obj.getBoolean("Success")){

                        //获取报名信息
                        JSONObject registrationInformation = obj.getJSONObject("RegistrationInformation");

                        String name = registrationInformation.getString("Name");
                        String phone = registrationInformation.getString("Phone");
                        String brithdayStr = registrationInformation.getString("BrithdayStr");
                        String idNumber = registrationInformation.getString("IDNumber");
                        String certificateType = registrationInformation.getString("CertificateType");
                        String genderStr = registrationInformation.getString("GenderStr");
                        String nationality = registrationInformation.getString("Nationality");
                        String province = registrationInformation.getString("Province");
                        String city = registrationInformation.getString("City");
                        String county = registrationInformation.getString("County");
                        String attendProject = registrationInformation.getString("AttendProject");
                        String clothingSize = registrationInformation.getString("ClothingSize");
                        String address = registrationInformation.getString("Address");
                        String postcode = registrationInformation.getString("Postcode");
                        String urgencyLinkman = registrationInformation.getString("UrgencyLinkman");
                        String urgencyLinkmanPhone = registrationInformation.getString("UrgencyLinkmanPhone");
                        String createTime = registrationInformation.getString("CreateTimeStr");
                        boolean isPayed = registrationInformation.getBoolean("IsPayed");
                        String attendNumber = registrationInformation.getString("AttendNumber");
                        String applyNature = registrationInformation.getString("ApplyNature");

                        //获取订单信息
                        JSONObject requestData = obj.getJSONObject("RequestData");
                        String service = requestData.getString("Service");
                        String partner = requestData.getString("Partner");
                        String input_charset = requestData.getString("_input_charset");
                        String sign_type = requestData.getString("Sign_type");
                        String notify_url = requestData.getString("Notify_url");
                        String out_trade_no = requestData.getString("Out_trade_no");
                        String subject = requestData.getString("Subject");
                        String payment_type = requestData.getString("Payment_type");
                        String seller_id = requestData.getString("Seller_id");
                        String total_fee = requestData.getString("Total_fee");
                        String body = requestData.getString("Body");
                        String sign = requestData.getString("Sgin");

                        //组装数据
                       SignUpInfo signUpInfo = new SignUpInfo( name,phone,brithdayStr ,
                                idNumber,certificateType ,genderStr,nationality,province,city,county,attendProject
                                ,clothingSize,address,postcode,urgencyLinkman,urgencyLinkmanPhone,createTime,isPayed,attendNumber,applyNature,service,partner,input_charset,sign_type,notify_url,out_trade_no,subject,payment_type,seller_id,total_fee,body,sign);

                        //更新本地报名信息
                        if(isSelf){
                            SharedPreferencesManager.insertValue(getActivity(),signUpInfo);

                        }


                        Intent intent = new Intent(getActivity(), SignUpSearchResultActivity.class);
                        intent.putExtra("signUpInfo",signUpInfo);
                        intent.putExtra("title","报名查询");
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(),obj.getString("Reason"),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                if(ex instanceof HttpException){
                    Toast.makeText(getActivity(),"网络不给力!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),"查询出错!",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });


    }

    @Override
    public void isClear() {
         userName.setText("未报名");
    }
}
