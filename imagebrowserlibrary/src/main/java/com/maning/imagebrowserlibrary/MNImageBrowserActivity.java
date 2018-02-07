package com.maning.imagebrowserlibrary;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.maning.imagebrowserlibrary.transforms.DefaultTransformer;
import com.maning.imagebrowserlibrary.transforms.DepthPageTransformer;
import com.maning.imagebrowserlibrary.transforms.RotateDownTransformer;
import com.maning.imagebrowserlibrary.transforms.RotateUpTransformer;
import com.maning.imagebrowserlibrary.transforms.ZoomInTransformer;
import com.maning.imagebrowserlibrary.transforms.ZoomOutSlideTransformer;
import com.maning.imagebrowserlibrary.transforms.ZoomOutTransformer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



/**
 * 图片浏览的页面
 */
public class MNImageBrowserActivity extends AppCompatActivity {

    public final static String IntentKey_ImageList = "IntentKey_ImageList";
    public final static String IntentKey_CurrentPosition = "IntentKey_CurrentPosition";
    public final static String IntentKey_ViewPagerTransformType = "IntentKey_ViewPagerTransformType";
    public final static int ViewPagerTransform_Default = 0;
    public final static int ViewPagerTransform_DepthPage = 1;
    public final static int ViewPagerTransform_RotateDown = 2;
    public final static int ViewPagerTransform_RotateUp = 3;
    public final static int ViewPagerTransform_ZoomIn = 4;
    public final static int ViewPagerTransform_ZoomOutSlide = 5;
    public final static int ViewPagerTransform_ZoomOut = 6;

    private Context context;

    private MNGestureView mnGestureView;
    private MNViewPager viewPagerBrowser;
    private TextView tvNumShow;
    private RelativeLayout rl_black_bg;

    private ArrayList<String> imageUrlList = new ArrayList<>();
    private int currentPosition;
    private int currentViewPagerTransform;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowFullScreen();
        setContentView(R.layout.activity_mnimage_browser);
        context = this;

        initIntent();

        initViews();

        initData();

        initViewPager();

    }

    private void setWindowFullScreen() {
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 19) {
            // 虚拟导航栏透明
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void initIntent() {
        imageUrlList = getIntent().getStringArrayListExtra(IntentKey_ImageList);
        currentPosition = getIntent().getIntExtra(IntentKey_CurrentPosition, 1);
        currentViewPagerTransform = getIntent().getIntExtra(IntentKey_ViewPagerTransformType, ViewPagerTransform_Default);
    }

    private void initViews() {
        viewPagerBrowser = (MNViewPager) findViewById(R.id.viewPagerBrowser);
        mnGestureView = (MNGestureView) findViewById(R.id.mnGestureView);
        tvNumShow = (TextView) findViewById(R.id.tvNumShow);
        rl_black_bg = (RelativeLayout) findViewById(R.id.rl_black_bg);

    }

    private void initData() {
        tvNumShow.setText(String.valueOf((currentPosition + 1) + "/" + imageUrlList.size()));
    }

    private void initViewPager() {
        viewPagerBrowser.setAdapter(new MyAdapter());
        viewPagerBrowser.setCurrentItem(currentPosition);
        setViewPagerTransforms();
        viewPagerBrowser.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index=position;
                tvNumShow.setText(String.valueOf((position + 1) + "/" + imageUrlList.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mnGestureView.setOnSwipeListener(new MNGestureView.OnSwipeListener() {
            @Override
            public void downSwipe() {
                finishBrowser();
            }

            @Override
            public void onSwiping(float deltaY) {
                tvNumShow.setVisibility(View.GONE);

                float mAlpha = 1 - deltaY / 500;
                if (mAlpha < 0.3) {
                    mAlpha = 0.3f;
                }
                if (mAlpha > 1) {
                    mAlpha = 1;
                }
                rl_black_bg.setAlpha(mAlpha);
            }

            @Override
            public void overSwipe() {
                tvNumShow.setVisibility(View.VISIBLE);
                rl_black_bg.setAlpha(1);
            }
        });
    }

    private void setViewPagerTransforms() {
        if (currentViewPagerTransform == ViewPagerTransform_Default) {
            viewPagerBrowser.setPageTransformer(true, new DefaultTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_DepthPage) {
            viewPagerBrowser.setPageTransformer(true, new DepthPageTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_RotateDown) {
            viewPagerBrowser.setPageTransformer(true, new RotateDownTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_RotateUp) {
            viewPagerBrowser.setPageTransformer(true, new RotateUpTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_ZoomIn) {
            viewPagerBrowser.setPageTransformer(true, new ZoomInTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_ZoomOutSlide) {
            viewPagerBrowser.setPageTransformer(true, new ZoomOutSlideTransformer());
        } else if (currentViewPagerTransform == ViewPagerTransform_ZoomOut) {
            viewPagerBrowser.setPageTransformer(true, new ZoomOutTransformer());
        } else {
            viewPagerBrowser.setPageTransformer(true, new ZoomOutSlideTransformer());
        }
    }

    private void finishBrowser() {
        tvNumShow.setVisibility(View.GONE);
        rl_black_bg.setAlpha(0);
        finish();
        this.overridePendingTransition(0, R.anim.browser_exit_anim);
    }

    @Override
    public void onBackPressed() {
        finishBrowser();
    }


    private class MyAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyAdapter() {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imageUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View inflate = layoutInflater.inflate(R.layout.mn_image_browser_item_show_image, container, false);
            PhotoView imageView = (PhotoView) inflate.findViewById(R.id.imageView);
            RelativeLayout rl_browser_root = (RelativeLayout) inflate.findViewById(R.id.rl_browser_root);
            final ProgressWheel progressWheel = (ProgressWheel) inflate.findViewById(R.id.progressWheel);
            final RelativeLayout rl_image_placeholder_bg = (RelativeLayout) inflate.findViewById(R.id.rl_image_placeholder_bg);
            final ImageView iv_fail = (ImageView) inflate.findViewById(R.id.iv_fail);

            iv_fail.setVisibility(View.GONE);

            String url = imageUrlList.get(position);
            Picasso.with(context).load(url).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    progressWheel.setVisibility(View.GONE);
                    rl_image_placeholder_bg.setVisibility(View.GONE);
                    iv_fail.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progressWheel.setVisibility(View.GONE);
                    iv_fail.setVisibility(View.VISIBLE);
                }
            });

            rl_browser_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishBrowser();
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishBrowser();
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    android.support.v7.app.AlertDialog.Builder normalDialog =new android.support.v7.app.AlertDialog.Builder(MNImageBrowserActivity.this);
                    normalDialog.setMessage("是否保存该图片");
                    normalDialog.setPositiveButton("是",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
                                        saveImageToGallery();
                                    }else {
                                        if(ActivityCompat.checkSelfPermission(MNImageBrowserActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                            ActivityCompat.requestPermissions(MNImageBrowserActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0101);
                                        }else {
                                            saveImageToGallery();
                                        }
                                    }
                                }
                            });
                    normalDialog.setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                    dialog.dismiss();
                                }
                            });
                    // 显示
                    normalDialog.show();



                    return false;
                }
            });

            container.addView(inflate);
            return inflate;
        }
    }
    //保存图片到相册
    public  void saveImageToGallery() {

        if(imageUrlList==null||imageUrlList.size()<=0)return;
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "baijia";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        final File file = new File(appDir, fileName);
        try {
            final FileOutputStream fos = new FileOutputStream(file);
            String url = imageUrlList.get(index);
            URL path = new URL(url);
            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            //通过io流的方式来压缩保存图片
            HttpURLConnection conn = (HttpURLConnection)path.openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setConnectTimeout(10 * 1000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                Bitmap btp = BitmapFactory.decodeStream(inputStream);
                boolean isSuccess = btp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
                fos.flush();
                fos.close();
                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                if (isSuccess) {
                    showTip("保存成功!");
                } else {
                    showTip("保存失败!");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            showTip("保存失败!");
        }
       // return false;
       /* x.image().loadFile(imageUrlList.get(index), null, new Callback.CacheCallback<File>() {
            @Override
            public boolean onCache(File result) {

                Bitmap bitmap = BitmapFactory.decodeFile(result.getPath());

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
        });*/
    }
    private void showTip(String msg){
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 0101:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    saveImageToGallery();
                    //用户授权成功
                }else {
                    //用户没有授权
                    AlertDialog.Builder alert = new AlertDialog.Builder(MNImageBrowserActivity.this);
                    alert.setTitle("提示");
                    alert.setMessage("您需要设置权限才能使用该功能");
                    alert.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getAppDetailSettingIntent(MNImageBrowserActivity.this);
                        }
                    });
                    alert.show();

                }
                break;
            default:
                break;
        }
    }
    /**
     * 设置权限界面
     * @param context
     */
    public  void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        context.startActivity(localIntent);
    }

}
