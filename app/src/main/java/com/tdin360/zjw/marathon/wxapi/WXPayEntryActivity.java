package com.tdin360.zjw.marathon.wxapi;

import com.tdin360.zjw.marathon.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

			switch (resp.errCode){

				case 0:

					if(listener!=null){
						listener.onWXPaySuccess();
					}
					break;
				case -1:
					Toast.makeText(this,"支付失败了,错误信息："+resp.errStr,Toast.LENGTH_SHORT).show();
					break;
				case -2:
					Toast.makeText(this,"支付已取消!",Toast.LENGTH_SHORT).show();
					break;

			}


			finish();
		}
	}
}