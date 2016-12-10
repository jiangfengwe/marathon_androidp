package com.tdin360.zjw.marathon.activity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.CityModel;
import com.tdin360.zjw.marathon.model.DistrictModel;
import com.tdin360.zjw.marathon.model.ProvinceModel;
import com.tdin360.zjw.marathon.model.SignUpInfo;
import com.tdin360.zjw.marathon.model.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.model.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.service.XmlParserHandler;
import com.tdin360.zjw.marathon.weight.AutoText;
import com.tdin360.zjw.marathon.weight.OnWheelChangedListener;
import com.tdin360.zjw.marathon.weight.WheelView;
import com.tdin360.zjw.marathon.weight.adapters.ArrayWheelAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author zzj
 * 2016-8-11
 * 马拉松报名表单
 */
public class SignUpActivity extends BaseActivity implements  OnWheelChangedListener {

    //滚动内容相关
    private AutoText autoText;

    //姓名
    private EditText editTextName;
    //手机号
    private EditText editTextPhone;
    //邮箱
    private EditText editTextEmail;
    //证件类型
    private Spinner idCardType;
    private String  idCardTypeString;

    //证件号
    private EditText idCardNumber;
    //性别
    private RadioGroup radioGroup;
    private String gander="男";
    //国家
    private Spinner spinnerCountry;
    private String country;

    //服装尺码
    private Spinner clothesSize;
    private String clothesSizeString;
    //现居地址
    private EditText editTextAddress;
    //邮政编码
    private EditText editTextPost;
    //紧急联系人
    private EditText editTextLinkName;
    //紧急联系电话
    private EditText editTextLinkPhone;

    //出生日期选择相关
    private EditText dateSelect;
    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int DATE_DIALOG_ID=0;

    //省市区选择
    private EditText areaAddress;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    //参赛项目选择
    private Spinner projectSpinner;
    private String projectName;


    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName ="";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("报名");
        showBackButton();
        initView();

    }

    @Override
    public int getLayout() {
        return R.layout.activity_sign_up;
    }



    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas()
    {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k=0; k<districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void initView() {

        //滚动内容部分
        this.autoText= (AutoText) this.findViewById(R.id.autoText);
        this.autoText.initScrollTextView(this.getWindowManager(),"为了您能够正常报名,请正确填写您的报名信息,如有问题请及时与我们联系!");
        this.autoText.setSpeed(1);
        this.autoText.starScroll();
           //表单控件初始化
         this.editTextName= (EditText) this.findViewById(R.id.name);
         this.editTextPhone= (EditText) this.findViewById(R.id.phone);
         this.editTextEmail= (EditText) this.findViewById(R.id.email);
         this.idCardType= (Spinner) this.findViewById(R.id.idCardType);
         this.idCardNumber= (EditText) this.findViewById(R.id.idCardNumber);
         this.radioGroup= (RadioGroup) this.findViewById(R.id.radioGroup);
         this.spinnerCountry= (Spinner) this.findViewById(R.id.country);
         this.clothesSize= (Spinner) this.findViewById(R.id.clothesSize);
         this.editTextAddress= (EditText) this.findViewById(R.id.address);
         this.editTextPost= (EditText) this.findViewById(R.id.post);
         this.editTextLinkName= (EditText) this.findViewById(R.id.linkName);
         this.editTextLinkPhone= (EditText) this.findViewById(R.id.linkPhone);


        //出生日期选择部分
        this.dateSelect= (EditText) this.findViewById(R.id.dateSelect);
        this.dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = onCrateDialog(DATE_DIALOG_ID);
                dialog.show();
            }
        });

        Calendar c = Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

        //证件类型选择

         this.idCardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                          idCardTypeString=parent.getItemAtPosition(position).toString();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        //性别选择

        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.radio1:
                        gander="男";
                        break;
                    case R.id.radio2:
                        gander="女";
                        break;
                }
            }
        });
        //选择国家
         spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                  country=parent.getItemAtPosition(position).toString();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });


        //省市县选择
        this.areaAddress= (EditText) this.findViewById(R.id.areaAddress);

        this.areaAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                View view = View.inflate(SignUpActivity.this,R.layout.province_select,null);
                setUpViews(view);
                setUpListener();
                setUpData();
                alertDialog.setView(view);
                alertDialog.setCancelable(false);
                view.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSelectedResult();
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        //参赛项目选择

         this.projectSpinner= (Spinner) this.findViewById(R.id.projectSpinner);
         String arr[]={"女子马拉松半程","女子马拉松全程","男子马拉松半程","男子马拉松全程","迷你马拉松"};
         this.projectSpinner.setAdapter(new ArrayAdapter<String>(SignUpActivity.this,android.R.layout.simple_list_item_1,arr));
        this.projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectName=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //服装尺码选择
            this.clothesSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     clothesSizeString=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        //表单提交
           this.findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   submit();
               }
           });
    }

    //出生日期选择部分
    private void updateDisplay(){

        dateSelect.setText(new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append(mDay));
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            updateDisplay();
        }
    };

    private Dialog onCrateDialog(int id){

        switch (id){

            case DATE_DIALOG_ID:
                return new DatePickerDialog(SignUpActivity.this,listener,mYear,mMonth,mDay);
        }

        return null;
    }

    //省县市选择部分
    private void setUpViews(View view) {
        mViewProvince = (WheelView)view.findViewById(R.id.id_province);
        mViewCity = (WheelView) view.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) view.findViewById(R.id.id_district);

    }
    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);

    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(SignUpActivity.this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }


    private void showSelectedResult() {

         areaAddress.setText(new StringBuilder().append(mCurrentProviceName).append(" ").append(mCurrentCityName).append(" ").append(mCurrentDistrictName));
    }
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 返回
     * @param view
     */
    public void back(View view) {
        finish();
    }


    /**
     * 提交表单
     *
     */
    public void submit() {

        //验证表单
         if(editTextName.getText().toString().trim().length()==0){

             Toast.makeText(SignUpActivity.this,"姓名不能为空!",Toast.LENGTH_SHORT).show();
             editTextName.requestFocus();
             return;
         }
        if(editTextPhone.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"手机号不能为空!",Toast.LENGTH_SHORT).show();
            editTextPhone.requestFocus();
            return;
        }
         else  if(editTextPhone.getText().toString().trim().length()>11){
            Toast.makeText(SignUpActivity.this,"手机号必须为11位数字!",Toast.LENGTH_SHORT).show();
            editTextPhone.requestFocus();
            return;
        }

        if(editTextEmail.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"邮箱不能为空!",Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
            return;
        }else{//正则表达式验证是否是邮箱格式
            String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(editTextEmail.getText().toString().trim());
            if(!m.matches()){
                Toast.makeText(SignUpActivity.this,"邮箱格式错误!",Toast.LENGTH_SHORT).show();
                editTextEmail.requestFocus();
                return;
            }
        }

         if(idCardNumber.getText().toString().trim().length()==0){
             Toast.makeText(SignUpActivity.this,"证件号码不能为空!",Toast.LENGTH_SHORT).show();
             idCardNumber.requestFocus();
             return;

         }
         if(dateSelect.getText().toString().trim().length()==0){
             Toast.makeText(SignUpActivity.this,"请选择出生日期!",Toast.LENGTH_SHORT).show();
             return;
         }
        if(areaAddress.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"请选择所在地!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(editTextAddress.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"现居地址不能为空!",Toast.LENGTH_SHORT).show();
            editTextAddress.requestFocus();
            return;
        }
        if(editTextPost.getText().toString().trim().length()==0){

            Toast.makeText(SignUpActivity.this,"邮政编码不能为空!",Toast.LENGTH_SHORT).show();
            editTextPost.requestFocus();
            return;
        }

        if(editTextLinkName.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"紧急联系人不能为空!",Toast.LENGTH_SHORT).show();
            editTextLinkName.requestFocus();
            return;
        }
        if(editTextLinkPhone.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"紧急联系人电话不能为空!",Toast.LENGTH_SHORT).show();
            editTextLinkPhone.requestFocus();
            return;
        }
        //提交到服务器
        showProgressDialog(true);


        //设置提交参数
        RequestParams param = new RequestParams(HttpUrlUtils.MARATHON_REGISTER);
        param.addQueryStringParameter("Name",editTextName.getText().toString().trim());
      //  param.addParameter("Email",editTextEmail.getText().toString().trim());
        param.addQueryStringParameter("Phone",editTextPhone.getText().toString().trim());
        param.addQueryStringParameter("Birthday",mYear+"-"+mMonth+"-"+mDay);
        param.addQueryStringParameter("IDNumber",idCardNumber.getText().toString().trim());
        param.addQueryStringParameter("CertificateType",idCardTypeString);
        param.addQueryStringParameter("Gender",gander);
        param.addQueryStringParameter("Nationality",country);
        param.addQueryStringParameter("Province",mCurrentProviceName);
        param.addQueryStringParameter("City",mCurrentCityName);
        param.addQueryStringParameter("County",mCurrentDistrictName);
        param.addQueryStringParameter("AttendProject",projectName);
        param.addQueryStringParameter("ClothingSize",clothesSizeString);
        param.addQueryStringParameter("Address",editTextAddress.getText().toString().trim());
        param.addQueryStringParameter("Postcode",editTextPost.getText().toString().trim());
        param.addQueryStringParameter("UrgencyLinkman",editTextLinkName.getText().toString().trim());
        param.addQueryStringParameter("UrgencyLinkmanPhone",editTextLinkPhone.getText().toString().trim());
        param.setMaxRetryCount(1);//最大重复请求次数
        param.setConnectTimeout(10*1000);

        //向服务器提交数据
           x.http().post(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String success = obj.getString("Success");
                    String reason=obj.getString("Reason");
                    //提示信息
                    Toast.makeText(x.app(),reason,Toast.LENGTH_SHORT).show();
                    //报名成功
                    if(success.equals("true")){
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
                        SignUpInfo signUpInfo = new SignUpInfo(editTextName.getText().toString().trim(),editTextPhone.getText().toString().trim(),mYear+"-"+mMonth+"-"+mDay,
                                idCardNumber.getText().toString().trim(),idCardTypeString,gander,country,mCurrentProviceName,mCurrentCityName,mCurrentDistrictName,projectName
                                ,clothesSizeString,editTextAddress.getText().toString().trim(),editTextPost.getText().toString().trim(),editTextLinkName.getText().toString().trim(),
                                editTextLinkPhone.getText().toString().trim(),getTime(),false,"0","个人",service,partner,input_charset,sign_type,notify_url,out_trade_no,subject,payment_type,seller_id,total_fee,body,sign);
                        /**
                         * 报名成功进入支付确认
                         */
                        Intent intent = new Intent(SignUpActivity.this, SignUpSearchResultActivity.class);
                        intent.putExtra("signUpInfo",signUpInfo);
                        intent.putExtra("title","报名信息确认");
                        startActivity(intent);
                        // 保存数据到手机
                        SharedPreferencesManager.insertValue(SignUpActivity.this,signUpInfo);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {


                if(ex instanceof HttpException){
                 Toast.makeText(SignUpActivity.this,"网络异常!",Toast.LENGTH_SHORT).show();
                }
                showProgressDialog(false);
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                builder.setTitle(ex.getMessage());
                builder.setMessage("提交失败了,请点击重试!");
                builder.setCancelable(false);
                builder.setPositiveButton("重试", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         submit();

                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          dialog.dismiss();
                    }
                });
                builder.show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
               showProgressDialog(false);
            }

        });

    }

//提交表单是显示提示对话框
    private ProgressDialog progressDialog;
    private void showProgressDialog(boolean isShow){
        if(isShow) {
            progressDialog = new ProgressDialog(SignUpActivity.this);
            progressDialog.setMessage("提交中,请稍后...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }else {

            if(progressDialog!=null)
            progressDialog.dismiss();
        }
    }

    /**
     * 获取当前时间
     * @return
     */
    private String getTime(){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return format.format(new Date(System.currentTimeMillis()));
    }
 }
