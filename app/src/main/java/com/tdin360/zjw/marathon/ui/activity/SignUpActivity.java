package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.CityModel;
import com.tdin360.zjw.marathon.model.DistrictModel;
import com.tdin360.zjw.marathon.model.ProvinceModel;
import com.tdin360.zjw.marathon.model.SpinnerModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.MyDatePickerDialog;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.service.XmlParserHandler;
import com.tdin360.zjw.marathon.utils.ValidateUtil;
import com.tdin360.zjw.marathon.weight.AutoText;
import com.tdin360.zjw.marathon.weight.OnWheelChangedListener;
import com.tdin360.zjw.marathon.weight.WheelView;
import com.tdin360.zjw.marathon.weight.adapters.ArrayWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author zzj
 * 2016-8-11
 * 赛事报名表单
 */
public class SignUpActivity extends BaseActivity implements  OnWheelChangedListener,MyDatePickerDialog.OnMyDatePickerChangeListener {

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
    private List<SpinnerModel>idTypeList=new ArrayList<>();

    //证件号
    private EditText idCardNumber;
    //性别
    private RadioGroup radioGroup;
    private boolean gander=true;// 默认是男
    //国家
    private Spinner spinnerCountry;
    private String country;

    //服装尺码
    private Spinner clothesSize;
    private String clothesSizeString;
    private List<SpinnerModel>clothesSizeList=new ArrayList<>();
    //现居地址
    private EditText editTextAddress;
    //邮政编码
    private EditText editTextPost;
    //紧急联系人
    private EditText editTextLinkName;
    //紧急联系电话
    private EditText editTextLinkPhone;

    //出生日期选择相关
    private TextView dateSelect;
    private int mYear;
    private int mMonth;
    private int mDay;

    //省市区选择
    private TextView areaAddress;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    //参赛项目选择
    private Spinner projectSpinner;
    private String projectName;
    private List<SpinnerModel>projectList=new ArrayList<>();

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


    //加载控件


    private TextView loadFail;
    //主布局
    private LinearLayout main;

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
        this.autoText.initScrollTextView(this.getWindowManager(),"为了能够快速报名,请正确填写您的报名信息,如有问题请及时与我们联系!");
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

         this.main = (LinearLayout) this.findViewById(R.id.main);
         this.loadFail = (TextView) this.findViewById(R.id.loadFail);

        //加载失败点击重新获取
        this.loadFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadData();
            }
        });


        //出生日期选择部分
        this.dateSelect= (TextView) this.findViewById(R.id.dateSelect);
        this.findViewById(R.id.dateSelectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatePickerDialog dialog = new MyDatePickerDialog(SignUpActivity.this);
                dialog.setOnMyDatePickerChangeListener(SignUpActivity.this);
                dialog.show();

            }
        });



        //证件类型选择

         this.idCardType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                          idCardTypeString=idTypeList.get(position).getValue();
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

                    case R.id.radio1://男
                        gander=true;
                        break;
                    case R.id.radio2://女
                        gander=false;
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
        this.areaAddress= (TextView) this.findViewById(R.id.areaAddress);

        this.findViewById(R.id.areaAddressBtn).setOnClickListener(new View.OnClickListener() {
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


        this.projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectName=projectList.get(position).getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //服装尺码选择
            this.clothesSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     clothesSizeString=clothesSizeList.get(position).getValue();
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

             initHUD();

             loadData();
    }
    //加载数据(包括缓存数据和网络数据)
    private void loadData() {
        hud.show();
        /**
         * 判断网络是否处于可用状态
         */
        if (NetWorkUtils.isNetworkAvailable(this)) {

            //加载网络数据
            httpRequest();
        } else {

            hud.dismiss();
            loadFail.setText("点击重新加载");
            loadFail.setVisibility(View.VISIBLE);
            //获取缓存数据
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
        }
    }

    private KProgressHUD hud;


    private void initHUD(){

        //显示提示框
        hud = KProgressHUD.create(this);
        hud.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
                hud.setCancellable(true);
                hud.setAnimationSpeed(1);
                hud.setDimAmount(0.5f);


    }
    /**
     * 请求报名相关数据
     */
    private void httpRequest(){

        loadFail.setVisibility(View.GONE);
        RequestParams params = new RequestParams(HttpUrlUtils.MARATHON_SIGNUP);
        params.addQueryStringParameter("eventId",MarathonDataUtils.init().getEventId()+"");
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {

                try {
                    JSONObject json = new JSONObject(s);

                    //获取证件类型
                    JSONArray idNumberType = json.getJSONArray("AvailableDocumentType");

                    for (int i=0;i<idNumberType.length();i++){

                        JSONObject typeObj = idNumberType.getJSONObject(i);

                        String key = typeObj.getString("Text");
                        String value = typeObj.getString("Value");
                        //Log.d("----------->>>", "onSuccess: "+text);
                        idTypeList.add(new SpinnerModel(key,value));

                    }

                    //获取服装尺码
                    JSONArray avaliableSize = json.getJSONArray("AvaliableSize");
                    for (int i=0;i<avaliableSize.length() ;i++
                         ) {

                        JSONObject sizeObj = avaliableSize.getJSONObject(i);
                        String key = sizeObj.getString("Text");
                        String value = sizeObj.getString("Value");
                        //Log.d("----------->>>", "onSuccess: "+text);
                         clothesSizeList.add(new SpinnerModel(key,value));
                    }

                    //获取参赛项目
                    JSONArray availableProject = json.getJSONArray("AvailableProject");

                    for(int i=0;i<availableProject.length();i++){

                        JSONObject jsonObject = availableProject.getJSONObject(i);
                        String key = jsonObject.getString("Text");
                        String value = jsonObject.getString("Value");
                        //Log.d("----------->>>", "onSuccess: "+text);
                         projectList.add(new SpinnerModel(key,value));
                    }

                    loadFail.setVisibility(View.GONE);
                    //加载成功显示界面
                    main.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    loadFail.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onError(Throwable throwable, boolean b) {


                 Toast.makeText(SignUpActivity.this,"网络错误或访问服务器出错!",Toast.LENGTH_SHORT).show();

                loadFail.setText("点击重新加载");
                loadFail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                hud.dismiss();
                idCardType.setAdapter(new ArrayAdapter<>(SignUpActivity.this,android.R.layout.simple_list_item_1,idTypeList));
                clothesSize.setAdapter(new ArrayAdapter<>(SignUpActivity.this,android.R.layout.simple_list_item_1,clothesSizeList));
                projectSpinner.setAdapter(new ArrayAdapter<>(SignUpActivity.this,android.R.layout.simple_list_item_1,projectList));

            }
        });
    }


    @Override
    public void onChange(int year, int month, int day) {

        mYear=year;
        mMonth=month;
        mDay=day;
        dateSelect.setText(new StringBuilder().append( year).append("年").append(month).append("月").append(day)+"日");
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
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<>(SignUpActivity.this, mProvinceDatas));
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
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<>(this, areas));
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
        mViewCity.setViewAdapter(new ArrayWheelAdapter<>(this, cities));
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
     * 提交表单
     *
     */
    public void submit() {

        //验证表单

        //验证姓名
         if(editTextName.getText().toString().trim().length()==0){

             Toast.makeText(SignUpActivity.this,"姓名不能为空!",Toast.LENGTH_SHORT).show();
             editTextName.requestFocus();
             return;
         }
//        验证手机号码
        if(editTextPhone.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"手机号不能为空!",Toast.LENGTH_SHORT).show();
            editTextPhone.requestFocus();
            return;
        }
         else  if(!ValidateUtil.isMobileNO(editTextPhone.getText().toString().trim())){
            Toast.makeText(SignUpActivity.this,"手机号格式错误!",Toast.LENGTH_SHORT).show();
            editTextPhone.requestFocus();
            return;
        }

//        验证邮箱
        if(editTextEmail.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"邮箱不能为空!",Toast.LENGTH_SHORT).show();
            editTextEmail.requestFocus();
            return;

        }else if(!ValidateUtil.isEmailParams(editTextEmail.getText().toString().trim())){//正则表达式验证是否是邮箱格式

                Toast.makeText(SignUpActivity.this,"邮箱格式错误!",Toast.LENGTH_SHORT).show();
                editTextEmail.requestFocus();
                return;

        }



//        验证出生日期
         if(dateSelect.getText().toString().trim().equals("请选择出生日期")){
             Toast.makeText(SignUpActivity.this,"请选择出生日期!",Toast.LENGTH_SHORT).show();
             return;
         }
        //        验证证件类型
        if(idCardTypeString.length()==0||idCardTypeString.equals("请选择证件类型")){
            Toast.makeText(SignUpActivity.this,"请选择证件类型!",Toast.LENGTH_SHORT).show();

            return;

        }

//        验证证件号码
        if(idCardNumber.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"证件号码不能为空!",Toast.LENGTH_SHORT).show();
            idCardNumber.requestFocus();
            return;

        }

//        验证所在地
        if(areaAddress.getText().toString().trim().equals("请选择所在地")){
            Toast.makeText(SignUpActivity.this,"请选择所在地!",Toast.LENGTH_SHORT).show();
            return;
        }
        // 验证参赛项目
        if(projectName.length()==0||projectName.equals("请选择参赛项目")){
            Toast.makeText(SignUpActivity.this,"请选择参赛项目!",Toast.LENGTH_SHORT).show();

            return;

        }
        // 验证参赛项目
        if(clothesSizeString.length()==0||clothesSizeString.equals("请选择服装尺码")){
            Toast.makeText(SignUpActivity.this,"请选择服装尺码!",Toast.LENGTH_SHORT).show();

            return;

        }

//        验证现居地址
        if(editTextAddress.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"现居地址不能为空!",Toast.LENGTH_SHORT).show();
            editTextAddress.requestFocus();
            return;
        }




//        验证邮政编码
        if(editTextPost.getText().toString().trim().length()==0){

            Toast.makeText(SignUpActivity.this,"邮政编码不能为空!",Toast.LENGTH_SHORT).show();
            editTextPost.requestFocus();
            return;
        }

//        验证紧急联系人
        if(editTextLinkName.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"紧急联系人不能为空!",Toast.LENGTH_SHORT).show();
            editTextLinkName.requestFocus();
            return;
        }
//        验证紧急联系电话
        if(editTextLinkPhone.getText().toString().trim().length()==0){
            Toast.makeText(SignUpActivity.this,"紧急联系人电话不能为空!",Toast.LENGTH_SHORT).show();
            editTextLinkPhone.requestFocus();
            return;
        }

//        验证通过提示用户检查报名信息

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("温馨提示");
        dialog.setMessage("提交成功将无法更改，您确定填写的信息无误，并立即提交吗?");
        dialog.setPositiveButton("确定提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //执行提交
                submit2Server();
            }
        });
        dialog.setNegativeButton("仔细检查", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        dialog.show();




    }
    //提交到服务器
    private void submit2Server(){

        hud.show();

        //设置提交参数
        RequestParams param = new RequestParams(HttpUrlUtils.MARATHON_SIGNUP);
        //赛事id
        param.addBodyParameter("EventId", MarathonDataUtils.init().getEventId()+"");
        //姓名
        param.addBodyParameter("RegistratorName",editTextName.getText().toString().trim());
//        邮箱
        param.addBodyParameter("RegistratorEmail",editTextEmail.getText().toString().trim());
//        手机号码
        param.addBodyParameter("RegistratorPhone",editTextPhone.getText().toString().trim());
//        出生年
        param.addBodyParameter("DateOfBirthYear",mYear+"");
//        月
        param.addBodyParameter("DateOfBirthMonth",mMonth+"");
//        日
        param.addBodyParameter("DateOfBirthDay",mDay+"");
//        身份证号码
        param.addBodyParameter("RegistratorDocumentNumber",idCardNumber.getText().toString().trim());
//        证件类型
        param.addBodyParameter("RegistratorDocumentType",idCardTypeString);
//        性别
        param.addBodyParameter("RegistratorSex",gander+"");
//        国家
        param.addBodyParameter("Country",country);
//        省份
        param.addBodyParameter("Province",mCurrentProviceName);
//        城市
        param.addBodyParameter("City",mCurrentCityName);
//        地区
        param.addBodyParameter("County",mCurrentDistrictName);
//        参赛项目
        param.addBodyParameter("RegistratorCompeteType",projectName);
//        服装尺码
        param.addBodyParameter("RegistratorSize",clothesSizeString);
//        现居地址
        param.addBodyParameter("RegistratorPlace",editTextAddress.getText().toString().trim());
//        邮政编码
        param.addBodyParameter("RegisterPostCode",editTextPost.getText().toString().trim());
//        紧急联系人姓名
        param.addBodyParameter("EmergencyContactName",editTextLinkName.getText().toString().trim());
//        紧急联系电话
        param.addBodyParameter("EmergencyContactPhone",editTextLinkPhone.getText().toString().trim());
        //报名来源
        param.addBodyParameter("RegistratorSource","Android 客户端");
        param.setMaxRetryCount(0);//最大重复请求次数
        param.setConnectTimeout(5*1000);
        param.addBodyParameter("appKey",HttpUrlUtils.appKey);


        x.http().post(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {


                try {
                    JSONObject json = new JSONObject(result);

                   // Log.d("------->>", "onSuccess: "+result);

                    boolean success = json.getBoolean("Success");
                    String reason=json.getString("Reason");

                    //报名成功
                    if(success) {

                        //获取订单号去支付界面支付
                        String orderNo = json.getString("OrderNo");
                        String subject = json.getString("Subject");
                        String money = json.getString("Money");

                        //报名成功则跳转到支付界面
                        Intent intent = new Intent(SignUpActivity.this, PayActivity.class);
                        intent.putExtra("eventId",MarathonDataUtils.init().getEventId()+"");
                        intent.putExtra("order",orderNo);
                        intent.putExtra("subject",subject);
                        intent.putExtra("money",money);
                        intent.putExtra("from","signUp");
                        startActivity(intent);
                        finish();
                    }else {

                        //提示信息
                        Toast.makeText(x.app(),reason,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {


                Toast.makeText(SignUpActivity.this,"网络异常或服务器错误!",Toast.LENGTH_SHORT).show();

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

}
