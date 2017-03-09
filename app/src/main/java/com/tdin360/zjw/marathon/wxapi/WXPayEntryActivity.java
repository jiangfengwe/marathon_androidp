package com.tdin360.zjw.marathon.wxapi;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.ui.activity.PayResultActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

	
    private IWXAPI api;

	/**
	 * 微信支付回调成功
	 */
	public static WXPAYResultListener listener;
	public interface WXPAYResultListener{

		void onWXPaySuccess();
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {



		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

			//支付成功
			if(resp.errCode==0){

				Intent intent = new Intent(this, PayResultActivity.class);
				startActivity(intent);
				finish();

				if(listener!=null){
					listener.onWXPaySuccess();
				}

			}else {

				Toast.makeText(this,resp.errStr,Toast.LENGTH_SHORT).show();
			}
		}
	}
}