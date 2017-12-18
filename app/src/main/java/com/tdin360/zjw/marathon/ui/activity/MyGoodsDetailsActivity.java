package com.tdin360.zjw.marathon.ui.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.GoodsModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;
import com.tdin360.zjw.marathon.utils.MarathonDataUtils;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.Policy;

import static com.tdin360.zjw.marathon.R.id.btn;

public class MyGoodsDetailsActivity extends BaseActivity {

    private GoodsModel model;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("物资详情");
        showBackButton();
        this.model = (GoodsModel) getIntent().getSerializableExtra("model");

        showInfo(model);

    }

    /**
     *显示信息
     * @param model
     */
    private void showInfo(GoodsModel model) {
        if(model==null){

            return;
        }

        TextView name = (TextView) this.findViewById(R.id.name);
        name.setText(model.getName());
        TextView gander = (TextView) this.findViewById(R.id.sex);
        gander.setText(model.getGander());
        TextView idNumber = (TextView) this.findViewById(R.id.iDNumber);
        idNumber.setText(model.getIdNumber());
        TextView num = (TextView) this.findViewById(R.id.numBer);
        num.setText(model.getNumber());
        TextView size = (TextView) this.findViewById(R.id.clothesSize);
        size.setText(model.getSize());
        WebView info  = (WebView) this.findViewById(R.id.goodsInfo);
        info.loadData(model.getGoodsInfo(),"text/html; charset=UTF-8",null);
        this.btn = (Button) this.findViewById(R.id.btn_confirm);

         if(model.isReceive()){

         setConfirmState();
         }


    }


    private void setConfirmState(){

        btn.setText("已领取");
        btn.setEnabled(false);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_goods_details;
    }

    /***
     * 确认领取物资
     * @param view
     */
    public void confirmClick(View view) {


        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setCancelable(false);
        alert.setTitle("温馨提示");
        alert.setMessage("您确定已经领取到物资了吗?,确认后就表示您已经领取了物资,将不再具有领取物资的资格!");

         alert.setPositiveButton("确定领取", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();

                 //httpRequest();
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

    /**
     * 网络请求
     */
 /*   private void httpRequest(){

        RequestParams params = new RequestParams(HttpUrlUtils.CONFIRM_GOODS);
        params.addBodyParameter("id",model.getId()+"");
        params.addBodyParameter("phone", SharedPreferencesManager.getLoginInfo(getApplicationContext()).getName());
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject json = new JSONObject(result);

                    JSONObject message = json.getJSONObject("EventMobileMessage");

                    boolean success = message.getBoolean("Success");
                    String reason = message.getString("Reason");

                    if(success){
                        Toast.makeText(MyGoodsDetailsActivity.this,"领取成功",Toast.LENGTH_SHORT).show();

                        setConfirmState();
                    }else {

                   Toast.makeText(MyGoodsDetailsActivity.this,reason,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Toast.makeText(MyGoodsDetailsActivity.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            }
        });

    }*/


//    /**
//     * 申请快递
//     * @param view
//     */
//    public void click(View view) {
//
//
//        Intent intent = new Intent(this,ExpressActivity.class);
//        startActivity(intent);
//
//    }



}
