package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.SignUpInfoModel;
import com.tdin360.zjw.marathon.weight.SelectPayPopupWindow;

import cn.jpush.android.api.JPushInterface;

/**
 * 报名查询详情
 */
public class SignUpSearchResultActivity extends BaseActivity {


    private TextView title;//导航栏标题
    private TextView name;//用户名
    private TextView phone;//电话
    private TextView birthday;//出生日期
    private TextView idNumber;//证件号码
    private TextView certificateType;//证件类型
    private TextView gender;//性别
    private TextView nationality;//国家
    private TextView province;//省/市
    private TextView city;//城市
    private TextView county;//区县
    private TextView attendProject;//参赛项目
    private TextView clothingSize;//服装尺码
    private TextView address;//现居地址
    private TextView postcode;//邮政编码
    private TextView urgencyLinkman;//紧急联系人姓名
    private TextView urgencyLinkmanPhone;//紧急联系人电话
    private TextView createTime;//报名时间
    private TextView attendNumber;//参赛号码
    private TextView applyNature;//报名方式（单人、团体）
    private TextView money;//报名费用
    private TextView orderNumber;//订单号
    private Button payBtn;//立即支付按钮

    //自定义的弹出框类
    private SelectPayPopupWindow menuWindow;
    //提交表单信息模型
    private SignUpInfoModel signUpInfoModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setToolBarTitle("报名信息确认");

         showBackButton();


        initView();
        //loadData();
        /**
         * 进入支付
         */
        this.findViewById(R.id.payBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //pop弹出选择支付方式
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPayPopupWindow(SignUpSearchResultActivity.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(SignUpSearchResultActivity.this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sign_up_search_result;
    }

    //为弹出窗口实现事件监听
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_aliPay://支付宝支付

                    break;
                case R.id.btn_wXPay://微信支付

                    break;
                case R.id.btn_yLianPLAY://银联支付


                    break;
            }


        }

    };

    /**
     * 初始化
     */
    private void initView() {


        this.name = (TextView) this.findViewById(R.id.name);
        this.phone = (TextView) this.findViewById(R.id.phone);
        this.idNumber = (TextView) this.findViewById(R.id.IDNumber);
        this.birthday = (TextView) this.findViewById(R.id.birthday);
        this.certificateType = (TextView) this.findViewById(R.id.IDNumberType);
        this.gender = (TextView) this.findViewById(R.id.gander);
        this.nationality = (TextView) this.findViewById(R.id.country);
        this.province = (TextView) this.findViewById(R.id.province);
        this.city = (TextView) this.findViewById(R.id.city);
        this.county = (TextView) this.findViewById(R.id.county);
        this.attendProject = (TextView) this.findViewById(R.id.projectName);
        this.clothingSize = (TextView) this.findViewById(R.id.clothesSize);
        this.address = (TextView) this.findViewById(R.id.address);
        this.postcode = (TextView) this.findViewById(R.id.post);
        this.urgencyLinkman = (TextView) this.findViewById(R.id.contactsName);
        this.urgencyLinkmanPhone = (TextView) this.findViewById(R.id.contactsPhone);
        this.attendNumber = (TextView) this.findViewById(R.id.attendNumber);
        this.createTime = (TextView) this.findViewById(R.id.time);
        this.applyNature = (TextView) this.findViewById(R.id.applyNature);
        this.money = (TextView) this.findViewById(R.id.cost);
        this.orderNumber= (TextView) this.findViewById(R.id.orderNumber);
        this.payBtn = (Button) this.findViewById(R.id.payBtn);
    }

    /**
     * 加载数据
     */
    private void loadData() {

        Intent intent = this.getIntent();
  //      String title = intent.getStringExtra("title");
//        this.title.setText(title);
        this.signUpInfoModel = (SignUpInfoModel) intent.getSerializableExtra("signUpInfoModel");
        if(signUpInfoModel ==null){
            return;
        }
        /**
         * 绑定显示数据
         */

        this.name.setText(signUpInfoModel.getName());
        this.phone.setText(signUpInfoModel.getPhone());
        this.idNumber.setText(signUpInfoModel.getIdNumber());
        this.birthday.setText(signUpInfoModel.getBirthday());
        this.certificateType.setText(signUpInfoModel.getCertificateType());
        this.gender.setText(signUpInfoModel.getGender()+"");
        this.nationality.setText(signUpInfoModel.getNationality());
        this.province.setText(signUpInfoModel.getProvince());
        this.city.setText(signUpInfoModel.getCity());
        this.county.setText(signUpInfoModel.getCounty());
        this.attendProject.setText(signUpInfoModel.getAttendProject());
        this.clothingSize.setText(signUpInfoModel.getClothingSize());
        this.address.setText(signUpInfoModel.getAddress());
        this.postcode.setText(signUpInfoModel.getPostcode());
        this.urgencyLinkman.setText(signUpInfoModel.getUrgencyLinkman());
        this.urgencyLinkmanPhone.setText(signUpInfoModel.getUrgencyLinkmanPhone());
        this.attendNumber.setText(signUpInfoModel.getAttendNumber());
        this.createTime.setText(signUpInfoModel.getCreateTime());

        if(signUpInfoModel.isPayed()){//已支付
            payBtn.setText("已支付");
            payBtn.setEnabled(false);

        }
    }








    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


}