package com.tdin360.zjw.marathon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.HttpUrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MyGoodsDetailsActivity extends BaseActivity {

    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("物资详情");
        showBackButton();
        Intent intent = getIntent();
         this.id = intent.getIntExtra("id", 0);

        httpRequest();

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
        params.addQueryStringParameter("Id",id+"");

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
