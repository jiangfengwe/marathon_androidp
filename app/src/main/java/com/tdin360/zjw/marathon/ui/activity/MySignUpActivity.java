package com.tdin360.zjw.marathon.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.SignUpInfoModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.weight.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的报名列表
 */
public class MySignUpActivity extends BaseActivity implements RefreshListView.OnRefreshListener{

    private RefreshListView listView;
    private TextView loadFail;
    private List<SignUpInfoModel> list = new ArrayList<>();
    private  MyAdapter myAdapter;

    private LinearLayout tip;
    private KProgressHUD hud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBarTitle("我的报名");
        showBackButton();
        initView();
    }
    @Override
    public int getLayout() {
        return R.layout.activity_my_sign_up;
    }
    private void initView() {
        this.listView = (RefreshListView) this.findViewById(R.id.refreshListView);
        this.listView.setOnRefreshListener(this);

        this.loadFail = (TextView) this.findViewById(R.id.loadFail);
        this.tip = (LinearLayout) this.findViewById(R.id.tipNotData);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SignUpInfoModel model = (SignUpInfoModel) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MySignUpActivity.this,MySigUpDetailActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }
        });
        this.myAdapter = new MyAdapter();
        this.listView.setAdapter(myAdapter);
        initHUD();
        loadData();
    }
    private class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list==null?0:list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                viewHolder = new ViewHolder();
                convertView=View.inflate(MySignUpActivity.this,R.layout.my_signup_list_item,null);
                viewHolder.matchTime= (TextView) convertView.findViewById(R.id.time);
                viewHolder.matchName= (TextView) convertView.findViewById(R.id.matchName);
                viewHolder.matchAchievement= (TextView) convertView.findViewById(R.id.projectName);
                viewHolder.isPay = (TextView) convertView.findViewById(R.id.isPay);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            SignUpInfoModel model = list.get(position);
            viewHolder.matchName.setText(model.getEventName());
            viewHolder.matchAchievement.setText(model.getAttendProject());
            viewHolder.isPay.setText(model.isPayed()==true?"已支付":"未支付");
            viewHolder.matchTime.setText(model.getCreateTime()+"");

            return convertView;
        }
        class ViewHolder{
            private ImageView imageView;
            private TextView matchTime;
            private TextView matchName;
            private TextView matchAchievement;
            private TextView isPay;

        }
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
    //加载数据(包括缓存数据和网络数据)
    private void loadData() {
        /**
         * 判断网络是否处于可用状态
         */
        if (NetWorkUtils.isNetworkAvailable(this)) {
            hud.show();
            //加载网络数据
            httpRequest();
        } else {
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            loadFail.setVisibility(View.VISIBLE);
            //获取缓存数据
            //如果获取得到缓存数据则加载本地数据
           hud.dismiss();
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
        }
    }
    @Override
    public void onDownPullRefresh() {
        list.clear();
        httpRequest();
    }
    @Override
    public void onLoadingMore() {
        httpRequest();
    }
    /**
     * 请求网络数据
     */
    private void httpRequest() {
        loadFail.setVisibility(View.GONE);
        RequestParams params = new RequestParams(HttpUrlUtils.MY_SIGNUP_SEARCH);
        params.addQueryStringParameter("phone", SharedPreferencesManager.getLoginInfo(this).getName());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(" -----signup--->>>", "onSuccess: " + result);



                try {
                    JSONObject json = new JSONObject(result);

                    JSONObject eventMobileMessage = json.getJSONObject("EventMobileMessage");

                    boolean success = eventMobileMessage.getBoolean("Success");
                    String reason = eventMobileMessage.getString("Reason");

                    if(success){

                         JSONArray registratorMessages = json.getJSONArray("RegistratorMessages");

                         for(int i=0;i<registratorMessages.length();i++){


                             JSONObject object = registratorMessages.getJSONObject(i);

                             Log.d("---------->>>", "onSuccess: "+object.getString("RegistratorName"));

                             String id = object.getString("Id");
                             //赛事名称
                             String eventName = object.getString("EventName");

                             //真实姓名
                             String name = object.getString("RegistratorName");
//                             性别
                             boolean sex = object.getBoolean("RegistratorSex");
//                             生日
                             String birth = object.getString("Birthday");

//                             现居地址
                             String address = object.getString("RegistratorPlace");
//                             国家
                             String country = object.getString("Country");

//                             省份
                             String province = object.getString("Province");
//                             城市
                             String city = object.getString("City");
//                             地区
                             String county = object.getString("County");
//                             手机号码
                             String phone = object.getString("RegistratorPhone");

//                             邮箱
                             String email = object.getString("RegistratorEmail");

//                             证件号码
                             String number = object.getString("RegistratorDocumentNumber");

//                             证件类型
                             String type = object.getString("RegistratorDocumentType");

//                             服装尺码
                             String size = object.getString("RegistratorSize");

//                             参赛项目
                             String projectType = object.getString("RegistratorCompeteType");

//                             是否支付
                             boolean isPay = object.getBoolean("RegistratorIsPay");

//                              参赛号码
                             String documentNumber = object.getString("RegistratorDocumentNumber");

//                             邮政编码
                             String postCode = object.getString("RegisterPostCode");

//                             报名费用
                             String money = object.getString("Money");
//                             紧急联系人
                             String emergencyContactName = object.getString("EmergencyContactName");

//                             紧急联系电话
                             String emergencyContactPhone = object.getString("EmergencyContactPhone");

//                             报名时间
                             String createTime = object.getString("CreateTime");

                             //订单号
                             String orderNo = object.getString("OrderNo");


                             list.add(new SignUpInfoModel(id,eventName,name,phone,email,birth,number,type,sex,country,province,city,county,projectType,size,address,postCode,emergencyContactName,emergencyContactPhone,documentNumber,isPay,createTime,orderNo,money));
                             list.add(new SignUpInfoModel(id,eventName,name,phone,email,birth,number,type,sex,country,province,city,county,projectType,size,address,postCode,emergencyContactName,emergencyContactPhone,documentNumber,isPay,createTime,orderNo,money));

                         }

                     }else {

                        Toast.makeText(MySignUpActivity.this,reason,Toast.LENGTH_SHORT).show();

                         //没有查询到报名信息
                     }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MySignUpActivity.this, "网络错误或访问服务器出错!", Toast.LENGTH_SHORT).show();
                loadFail.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {


                if(list.size()<=0){

                    tip.setVisibility(View.VISIBLE);
                }else {
                    tip.setVisibility(View.GONE);
                }
                 hud.dismiss();
                listView.hideHeaderView();
                listView.hideFooterView();
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}
