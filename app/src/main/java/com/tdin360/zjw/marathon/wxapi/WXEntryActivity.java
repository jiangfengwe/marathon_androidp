package com.tdin360.zjw.marathon.wxapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alipay.security.mobile.module.commonutils.crypto.ByteUtil;
import com.tdin360.zjw.marathon.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        this.api= WXAPIFactory.createWXAPI(this,null);
        this.api.registerApp(Constants.APP_ID);
        api.handleIntent(getIntent(),this);
    }


    public void share(View view) {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(this, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()){

            case R.id.btn1:
                shareTextweixin();
                break;
            case R.id.btn2:
                shareLinkweixin();

                break;
            case R.id.btn3:
           shareImageweixin();
                break;
        }

    }

    //分享文字
    private void shareTextweixin() {



        WXTextObject object = new WXTextObject();
         object.text="微信分享测试";

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject=object;
        msg.description="测试";


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());  ;
        req.message = msg;

        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);


    }

    //分享连接
    private void shareLinkweixin(){

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.baidu.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = "网页标题";
        msg.description = "网页描述";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),
                R.drawable.go);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);



    }
    //分享图片
    private void shareImageweixin(){

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.guide2);
        WXImageObject obj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject=obj;

//        设置缩略图
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,400,400, true);

        bitmap.recycle();
        msg.setThumbImage(scaledBitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    String result="";
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "errcode_success";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "errcode_cancel";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "errcode_deny";
                break;
            default:
                result = "errcode_unknown";
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent,this);
    }
}
