package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.graphics.Bitmap;


import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by admin on 17/2/23.
 * 分享信息类
 * @author zzhangzhijun
 */

public class ShareInfoManager {

    private Context context;
    private ShareType shareType;
    private UMImage umImage;
    private UMWeb umWeb;
    private String title;
    public enum ShareType{

        IMAGE,//分享图片
        LINK //分享网页链接
    }

    public ShareInfoManager(Context context){

       this.context=context;
    }

    //获取标题
    public String getTitle(){


        return this.title;
    }
    /**
     * 获取分享类型
     * @return
     */
    public ShareType getShareType(){

        return this.shareType;
    }

    /**
     *  设置分享图片
     * @param bitmap
     */
     public void buildShareBitmap(Bitmap bitmap){

           this.shareType=ShareType.IMAGE;
           this.umImage = new UMImage(context,bitmap);//bitmap文件

         //设置缩略图
          umImage.setThumb(umImage);
//         对图片进行压缩

          umImage.compressStyle = UMImage.CompressStyle.SCALE;//质量压缩，适合长图的分享
          umImage.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

     }

    //分享图片
  public UMImage ShareImage(){

        return this.umImage;
    }

    /**
     * 设置分享链接
     * @param title 链接标题
     * @param url 链接地址
     * @param description 链接描述
     * @param imageUrl 缩略图
     */
    public void buildShareWebLink(String title,String url,String description,String imageUrl){

        this.title=title;
        this.shareType=ShareType.LINK;
        this.umWeb = new UMWeb(url);
        umWeb.setTitle(title);
        umWeb.setDescription(description);

        if(imageUrl!=null&&!imageUrl.equals("")){
            UMImage thumb = new UMImage(context,imageUrl);
            //图片压缩
            thumb.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
            thumb.compressFormat = Bitmap.CompressFormat.JPEG;
            umWeb.setThumb(thumb);
        }


    }

    //分享网页
    public UMWeb shareLink(){

        return  this.umWeb;
    }

}
