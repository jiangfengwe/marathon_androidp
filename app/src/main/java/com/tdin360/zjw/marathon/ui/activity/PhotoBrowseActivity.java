package com.tdin360.zjw.marathon.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.Constants;
import com.tdin360.zjw.marathon.utils.ToastUtils;
import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片浏览器
 */
public class PhotoBrowseActivity extends BaseActivity implements ViewPager.OnPageChangeListener{


    @ViewInject(R.id.viewPager)
    private ViewPager mViewPager;

    @ViewInject(R.id.showCount)
    private TextView tip;
    private int count;
    private int index;
    private List<String> imagesUrl=new ArrayList<>();
    private List<View>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         navBar().setVisibility(View.GONE);

        imagesUrl = getIntent().getStringArrayListExtra("list");


        if(imagesUrl==null)return;


        count = imagesUrl.size();
        tip.setText("1/"+count);
        for(int i=0;i<imagesUrl.size();i++){

          final ImageView imageView  =new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            x.image().bind(imageView,imagesUrl.get(i));
          list.add(imageView);
        }

        this.mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new PhotoAdapter());

    }

    @Override
    public int getLayout() {
        return R.layout.activity_photo_browse;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        this.index=position;

        tip.setText((position+1)+"/"+count);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void click(View view) {


        switch (view.getId()){

            case R.id.back:
                finish();
                break;
            case R.id.save:
              if(!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                  requestPermission(Constants.WRITE_EXTERNAL_CODE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
              }  else {

                  saveImageToGallery();

              }

                break;
        }
    }


    @Override
    public void doSDCardPermission() {

        saveImageToGallery();
    }

    class PhotoAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return imagesUrl==null? 0:imagesUrl.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = list.get(position);
            container.addView(view);
            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);

            container.removeView(list.get(position));

        }
    }

        //保存图片到相册
   public  void saveImageToGallery() {


           if(imagesUrl==null||imagesUrl.size()<=0)return;
            x.image().loadFile(imagesUrl.get(index), null, new Callback.CacheCallback<File>() {
                @Override
                public boolean onCache(File result) {

                   Bitmap  bitmap = BitmapFactory.decodeFile(result.getPath());

                    if(bitmap==null){

                        showTip("保存失败");

                        return false;
                    }

                    // 首先保存图片
                    String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "baijar";
                    File appDir = new File(storePath);
                    if (!appDir.exists()) {
                        appDir.mkdir();
                    }
                    String fileName = System.currentTimeMillis() + ".jpg";
                    File file = new File(appDir, fileName);



                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        //通过io流的方式来压缩保存图片
                        boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                        fos.flush();
                        fos.close();

                        //把文件插入到系统图库
                        //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

                        //保存图片后发送广播通知更新数据库
                        Uri uri = Uri.fromFile(file);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        if (isSuccess) {

                            showTip("保存成功!");

                        } else {
                            showTip("保存失败!");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        showTip("保存失败!");

                    }


                    return false;
                }

                @Override
                public void onSuccess(File result) {


                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    showTip("保存失败!");
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }


        private void showTip(String msg){

            ToastUtils.show(this,msg);

        }
}
