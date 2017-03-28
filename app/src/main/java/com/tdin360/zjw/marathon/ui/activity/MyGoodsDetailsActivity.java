package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.GoodsModel;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;

public class MyGoodsDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("物资详情");
        showBackButton();
        GoodsModel model = (GoodsModel) getIntent().getSerializableExtra("model");

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


    }

    @Override
    public int getLayout() {
        return R.layout.activity_my_goods_details;
    }


    /**
     * 申请快递
     * @param view
     */
    public void click(View view) {


        Intent intent = new Intent(this,ExpressActivity.class);
        startActivity(intent);

    }


    /**
     * 请求网络数据
     */
    private void httpRequest(){

        RequestParams params = new RequestParams(HttpUrlUtils.MY_GOODS_DETAIL);
        params.addQueryStringParameter("Id","");
        params.addBodyParameter("appKey",HttpUrlUtils.appKey);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.d("========>>", "onSuccess: "+result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
