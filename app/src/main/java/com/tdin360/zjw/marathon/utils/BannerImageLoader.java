package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.widget.ImageView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.CarouselModel;
import com.youth.banner.loader.ImageLoader;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 *
 * 轮播图加载器
 * Created by admin on 17/7/29.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        ImageOptions imageOptions = new ImageOptions.Builder()
                //.setSize(DensityUtil.dip2px(340), DensityUtil.dip2px(300))//图片大小
                //.setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.loading_banner_default)//加载中默认显示图片
                .setUseMemCache(true)//设置使用缓存
                .setFailureDrawableId(R.drawable.loading_banner_error)//加载失败后默认显示图片
                .build();
        x.image().bind(imageView,((CarouselModel)path).getPicUrl(),imageOptions);
    }
}
