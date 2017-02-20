package com.tdin360.zjw.marathon.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.tdin360.zjw.marathon.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 分享结果监听
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entry);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(),this);

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

		
		if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
			Toast.makeText(this, "code = " + ((SendAuth.Resp) resp).code, Toast.LENGTH_SHORT).show();
		}

		String result = "";
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result =  "分享成功";

			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "取消分享";

			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result  ="分享错误";
			break;
		default:
			result ="分享错误";
			break;
		}
		
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		finish();
	}
	

}