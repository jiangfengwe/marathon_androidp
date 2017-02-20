package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.Toast;
import com.tdin360.zjw.marathon.model.ShareInfo;
import com.tdin360.zjw.marathon.wxapi.Constants;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 分享工具类
 * Created by admin on 16/12/25.
 */

public class ShareUtil {

    private static ShareUtil shareUtil;
    private static IWXAPI wxapi;
    private static Context context;

    //分享类型
    public enum ShareType {

        TYPE1,//分享到微信朋友圈
        TYPE2,//分享给微信好友
        TYPE3 //微信收藏
    }

    private ShareUtil(Context context1) {
        context = context1;
        this.wxapi = WXAPIFactory.createWXAPI(context1, Constants.APP_ID);

    }


    public static ShareUtil getInstance(Context context) {

        if (shareUtil == null) {

            shareUtil = new ShareUtil(context);
        }

        return shareUtil;
    }


    /**
     * 分享连接
     *
     * @param shareInfo 分享信息
     * @param type      分享类型
     */
    public static void shareLink(ShareInfo shareInfo, ShareType type) {

        if (!wxapi.isWXAppInstalled()) {
            Toast.makeText(context, "您还未安装微信客户端",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareInfo.getUrl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareInfo.getTitle();
        msg.description = shareInfo.getDescribe();
        Bitmap thumb = createBitmapThumbnail(shareInfo.getBitmap());
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if (type.equals(ShareType.TYPE1)) {//分享到朋友圈

            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else if (type.equals(ShareType.TYPE2)) {//分享给好友

            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type.equals(ShareType.TYPE3)) {//微信收藏

            req.scene = SendMessageToWX.Req.WXSceneFavorite;
        }

        wxapi.sendReq(req);
    }

    //    对图片进行处理
    public static Bitmap createBitmapThumbnail(Bitmap bitMap) {

        if(bitMap==null){
            return null;
        }
        int width = bitMap.getWidth();
        int height = bitMap.getHeight();
        // 设置想要的大小
        int newWidth = 99;
        int newHeight = 99;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,
                matrix, true);
        return newBitMap;
    }
}