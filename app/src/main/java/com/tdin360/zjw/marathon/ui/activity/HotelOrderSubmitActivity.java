package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.CommonUtils;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MyDatePickerDialog;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import com.tdin360.zjw.marathon.utils.ValidateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;



/**
 * 酒店预订
 * 填写入住表单
 */
public class HotelOrderSubmitActivity extends BaseActivity {


    private long startDate;
    private String startDateStr;
    private long endDate;
    private String endDateStr;
    private int dayCount;
    private int roomCount=1;
    @ViewInject(R.id.roomCount_text)
    private TextView roomCount_text;
    @ViewInject(R.id.total)
    private TextView total_text;
    @ViewInject(R.id.tip)
    private TextView tip;
    @ViewInject(R.id.phone)
    private EditText edit_phone;
    private String phone;
    @ViewInject(R.id.name)
    private EditText edit_name;
    private String name;
    @ViewInject(R.id.idCardNumber)
    private EditText edit_idCard;
    private String idCard;
    @ViewInject(R.id.idCardType)
    private Spinner mSpinner;
    private String idCardType;
    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;
    private boolean sex;
    private String hotelId;
    private double price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showBackButton();


        Intent intent = getIntent();
        if(intent!=null){

            this.hotelId = intent.getStringExtra("id");
            this.price = intent.getDoubleExtra("price",0);
            String title = intent.getStringExtra("title");
            setToolBarTitle(title);
        }



        /**
         * 获取性别选择
         */
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (group.getId()){

                    case R.id.radio1:
                        sex=false;
                        break;
                    case R.id.radio2:
                        sex=true;
                        break;
                }

            }
        });

        /**
         * 获取证件类型
         */
        this.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                idCardType = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_hotel_order_submit;
    }




    @Event(value = R.id.select_in_date,type= View.OnClickListener.class)
    private void selectInDate(final View view){

        MyDatePickerDialog dialog = new MyDatePickerDialog(this);

        dialog.setOnMyDatePickerChangeListener(new MyDatePickerDialog.OnMyDatePickerChangeListener() {
            @Override
            public void onChange(int year, int month, int day) {

               long start =CommonUtils.getTimeByString(year+"/"+(month < 10? "0"+month:month)  +"/"+(day<10?"0"+day:day),"yyyy/MM/dd");

                if(start>=CommonUtils.getTimeByString(CommonUtils.getNowTimeByFormat("yyyy/MM/dd"),"yyyy/MM/dd")){
                    startDate=start;
                    ((TextView)view).setText(year+"年"+month+"月"+day+"日");
                     startDateStr = year+"/"+(month < 10? "0"+month:month)  +"/"+(day<10?"0"+day:day);
                    if(endDate>0){

                        dayCount=(int)((endDate-startDate)/1000/3600/24);
                        if(dayCount<=0){
                            ((TextView)view).setText(null);
                            ToastUtils.showCenter(getBaseContext(),"入住天数至少1天");
                            startDate=0;
                            dayCount=0;
                            updateTotal();
                        }else {

                            ((TextView)view).setText(year+"年"+month+"月"+day+"日");
                            updateTotal();
                        }

                    }
                }else {
                    ((TextView)view).setText(null);
                    ToastUtils.showCenter(getBaseContext(),"入住时间不合法,请重新选择");
                   startDate=0;
                    dayCount=0;
                    updateTotal();
                }




            }
        });

        dialog.show();



    }


    @Event(value = R.id.select_out_date,type= View.OnClickListener.class)
    private void selectOutDate(final View view){

        MyDatePickerDialog dialog = new MyDatePickerDialog(this);

        dialog.setOnMyDatePickerChangeListener(new MyDatePickerDialog.OnMyDatePickerChangeListener() {
            @Override
            public void onChange(int year, int month, int day) {

                endDate=CommonUtils.getTimeByString(year+"/"+(month < 10? "0"+month:month)+"/"+(day<10?"0"+day:day),"yyyy/MM/dd");

                if(startDate>=CommonUtils.getTimeByString(CommonUtils.getNowTimeByFormat("yyyy/MM/dd"),"yyyy/MM/dd")){
                    dayCount=(int)((endDate-startDate)/1000/3600/24);
                    if(dayCount<=0){
                        endDate=0;
                        dayCount=0;
                        ((TextView)view).setText(null);
                        ToastUtils.showCenter(getBaseContext(),"入住天数至少1天");
                        updateTotal();
                    }else {

                        ((TextView)view).setText(year+"年"+month+"月"+day+"日");
                        endDateStr=year+"/"+(month < 10? "0"+month:month)  +"/"+(day<10?"0"+day:day);
                        updateTotal();
                    }

                }else {
                    ((TextView)view).setText(null);
                    endDate=0;
                    dayCount=0;
                    updateTotal();
                    ToastUtils.showCenter(getBaseContext(),"入住日期不合法,请重新选择");
                }
            }
        });
        dialog.show();


    }

    /**
     * 添加和减少房间个数
     */


    @Event(value = R.id.down,type = View.OnClickListener.class)
    private void cutRoomCount(View view){

        if(roomCount>1){

            roomCount--;
        }

        updateTotal();
    }

    @Event(value = R.id.up,type = View.OnClickListener.class)
    private void addRoomCount(View view){

        roomCount++;

        updateTotal();
    }


    /**
     * 动态计算费用
     */
    private void updateTotal(){

        this.tip.setText("入住"+dayCount+"天,共计:");
        this.roomCount_text.setText(roomCount+"");
        double total = price * roomCount * dayCount;
        total_text.setText(total+"元");
    }

    /**
     * 验证表单输入
     */

     private boolean validInput(){

         this.phone = edit_phone.getText().toString().trim();

         if(phone.length()==0){

          ToastUtils.showCenter(getBaseContext(),"请输入手机号");
           edit_phone.requestFocus();
             return false;
         }

         if(!ValidateUtils.isMobileNO(phone)){

             ToastUtils.showCenter(getBaseContext(),"手机号格式有误");
             edit_phone.requestFocus();
             return false;
         }

         this.name=edit_name.getText().toString().trim();
         if(this.name.length()==0){

            ToastUtils.showCenter(getBaseContext(),"请输入姓名");
             edit_name.requestFocus();
             return  false;
         }

         this.idCard = this.edit_idCard.getText().toString().trim();
         if(this.idCard.length()==0){

             ToastUtils.showCenter(getBaseContext(),"请输入证件号码");
             edit_idCard.requestFocus();
             return false;

         }

         if(startDate==0){

             ToastUtils.showCenter(getBaseContext(),"请选择入住日期");

             return false;

         }
         if(endDate==0){

             ToastUtils.showCenter(getBaseContext(),"请选择离开日期");

             return false;

         }


         return true;

     }


     boolean b=true;
    /**
     * 提交
     * @param view
     */
   /* public void submit(View view) {


        if(b){

            String money = "0.01";
            String orderNo="15761628423201708111953345047417";
            Intent intent = new Intent(HotelOrderSubmitActivity.this,PayActivity.class);
            intent.putExtra("money",money);
            intent.putExtra("order",orderNo);
            intent.putExtra("subject","酒店预订费用");
            intent.putExtra("isHotel",true);
            startActivity(intent);

            return;
        }



        if(validInput()){



            //显示提示框
            final KProgressHUD hud = KProgressHUD.create(this);
            hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
                    .show();
            RequestParams params = new RequestParams(HttpUrlUtils.HOTEL_BUY);
            params.addBodyParameter("Name",name);
            params.addBodyParameter("Gender",sex==true? "true":"false");
            params.addBodyParameter("CustomerId", SharedPreferencesManager.getLoginInfo(getApplicationContext()).getId());
            params.addBodyParameter("EnterDate",startDateStr);
            params.addBodyParameter("LeaveDate",endDateStr);
            params.addBodyParameter("HotelTypeId",hotelId);
            params.addBodyParameter("IDNumber",idCard);
            params.addBodyParameter("IDType",idCardType);
            params.addBodyParameter("Phone",phone);
            params.addBodyParameter("RoomNumber",roomCount+"");

            params.addBodyParameter("appKey",HttpUrlUtils.appKey);

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {

//                    Log.d("-------->", "onSuccess: "+result);
                    try {
                        JSONObject object = new JSONObject(result);

                       JSONObject message = object.getJSONObject("EventMobileMessage");
                        boolean success = message.getBoolean("Success");
                        String reason = message.getString("Reason");

                        String orderNo = object.getString("OrderNo");
                        String money = object.getString("TotalMoney");

                        if(success){
                            ToastUtils.show(getBaseContext(),"提交成功!");

                            //去支付
                           Intent intent = new Intent(HotelOrderSubmitActivity.this,PayActivity.class);
                            intent.putExtra("money",money);
                            intent.putExtra("order",orderNo);
                            intent.putExtra("subject","酒店预订费用");
                            intent.putExtra("isHotel",true);
                            startActivity(intent);


                        }else {

                          ToastUtils.showCenter(getBaseContext(),reason);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

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

    }*/
}
