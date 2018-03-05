package com.tdin360.zjw.marathon.ui.activity;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sqk.emojirelease.Emoji;
import com.sqk.emojirelease.FaceFragment;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.adapter.RecyclerViewBaseAdapter;
import com.tdin360.zjw.marathon.utils.MyEmojiUtil;
import com.tdin360.zjw.marathon.utils.NetWorkUtils;
import com.tdin360.zjw.marathon.weight.SpaceItemDecoration;

import org.xutils.view.annotation.ViewInject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * 发表圈友动态
 */
public class AddCircleActivity extends BaseActivity implements FaceFragment.OnEmojiClickListener,View.OnLayoutChangeListener{
    @ViewInject(R.id.edit)
    private EditText edit;
    @ViewInject(R.id.wordCount)
    private TextView wordText;
    @ViewInject(R.id.address)
    private TextView address;
    @ViewInject(R.id.mRecyclerView)
    private RecyclerView mRecyclerView;
    private LocationManager manager;
    private MyLocationListener listener;
    //private PhotosAdapter adapter;
    private FaceFragment faceFragment;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private boolean isFirst;
    @ViewInject(R.id.addEmoji)
    private CheckBox emojiBox;
    private InputMethodManager imm;
    @ViewInject(R.id.emoji)
    private FrameLayout layout;
    //private List<MediaBean>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBackButton();
        setToolBarTitle("发表动态");
        navRightItemTitle().setText("发表");
        int color = getResources().getColor(R.color.blue);
        navRightItemTitle().setTextColor(color);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        this.mRecyclerView.addItemDecoration(new SpaceItemDecoration(5,3));
        //添加动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // this.adapter =new PhotosAdapter(AddCircleActivity.this,list,R.layout.image_list_item);
        //this.mRecyclerView.setAdapter(adapter);
        this.imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);

        this.findViewById(R.id.mainLayout).addOnLayoutChangeListener(this);

        /**
         * 点击发表
         */
        navRightItemTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /**
         * 输入文字个数限制
         */
        this.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int length = 140 - s.length();

                if (length > 0) {

                    wordText.setText(length + "");

                } else {

                    wordText.setText("0");
                }


            }
        });

        /**
         * 添加表情
         *
         */

        this.emojiBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //显示表情键盘
                if(isChecked){

                   imm.hideSoftInputFromWindow(edit.getWindowToken(),0);

                    layout.setVisibility(View.VISIBLE);
                    if(faceFragment==null){
                        faceFragment=FaceFragment.Instance();
                        transaction.add(R.id.emoji,faceFragment);
                        isFirst=true;

                    }else {

                        isFirst=false;
                        transaction.show(faceFragment);
                    }




                }else {
                    layout.setVisibility(View.GONE);
                    imm.toggleSoftInputFromWindow(edit.getWindowToken(),0,InputMethodManager.SHOW_IMPLICIT);

                    if(faceFragment!=null&&faceFragment.isAdded()){

                        transaction.hide(faceFragment);
                    }


                }

                transaction.commit();
            }

        });




        /**
         * 获取定位信息
         */


        this.manager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        getLocationInfo();



    }

    @Override
    public int getLayout() {
        return R.layout.activity_add_circle;
    }


    /**
     * 获取定位信息
     */

    private void getLocationInfo(){


        /**
         * 定位权限
         */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermission(1000, Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }

        if(NetWorkUtils.isNetworkAvailable(getBaseContext())){




            if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                this.listener = new MyLocationListener();
                this.manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, listener);
            }
        }

    }


    /**
     * 添加图片
     * @param view
     */
    public void add(View view) {
                //自定义方法的多选
    /* RxGalleryFinal.with(AddCircleActivity.this)
                        .image()
                        .multiple()
                        .maxSize(8)
                        .imageLoader(ImageLoaderType.GLIDE)
                        .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                            @Override
                            protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                         list.addAll(imageMultipleResultEvent.getResult());
                         adapter.update(list);
                                //选择结果
                            }

                        }).openGallery();*/

    }

    @Override
    public void onEmojiDelete() {


        int idx = edit.getSelectionStart();

        if(idx<=0){

            return;
        }
        String text = edit.getText().toString();

        String s = text.substring(0,idx);

        if (text.isEmpty()) {
            return;
        }
        if ("]".equals(text.substring(s.length() - 1, s.length()))) {
            int index = s.lastIndexOf("[");

            if (index == -1) {
                int action = KeyEvent.ACTION_DOWN;
                int code = KeyEvent.KEYCODE_DEL;
                KeyEvent event = new KeyEvent(action, code);
                edit.onKeyDown(KeyEvent.KEYCODE_DEL, event);

                displayTextView();

                return;
            }

            Editable editable = edit.getText().delete(index, s.length());
            int l = text.length() - editable.length();

            displayTextView();

            if (idx > 0) {
                edit.setSelection(idx - l);


            }

            return;

        }

        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        edit.onKeyDown(KeyEvent.KEYCODE_DEL, event);
        displayTextView();
        if(idx>0) {
            edit.setSelection(idx - 1);
        }



        }


    private void displayTextView() {

            try {
                String content = edit.getText().toString();
                SpannableStringBuilder builder = MyEmojiUtil.handlerTextToEmojiSpannable(content,this);

                edit.setText(builder);

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    @Override
    public void onEmojiClick(Emoji emoji) {


        /**
         * 处理输入表情剩余个数是否还能输入
         */
        int  l = 140-edit.getText().length();

           if(emoji.getContent().length()<=l) {


               int index = edit.getSelectionStart();
               Editable editable = edit.getEditableText();
               if (index < 0) {
                   editable.append(emoji.getContent());
               } else {
                   editable.insert(index, emoji.getContent());
               }

               displayTextView();
               edit.setSelection(index + emoji.getContent().length());
           }
    }


    //监听键盘的弹出与隐藏
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {


        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if(oldBottom != 0 && bottom != 0 &&(oldBottom - bottom > keyHeight)){

             layout.setVisibility(View.GONE);

            if(!isFirst) {
//                Toast.makeText(EmojiActivity.this, "键盘弹出...", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                  emojiBox.setChecked(false);


                if (faceFragment != null && faceFragment.isAdded()) {
                    transaction.hide(faceFragment);

                }


                transaction.commit();
            }

        }else if(oldBottom != 0 && bottom != 0 &&(bottom - oldBottom > keyHeight)){

//            Toast.makeText(EmojiActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        }



    }

    /**
     * 定位回调
     */

    class MyLocationListener implements LocationListener{


     @Override
     public void onLocationChanged(Location location) {

         if(location!=null){


             /**
              * 反地理编码获取位置信息
              */
             Geocoder geo = new Geocoder(AddCircleActivity.this);
             try {
                 List<Address> fromLocation = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                 StringBuilder sb = new StringBuilder();

                 for (Address item :fromLocation){

                     sb.append(item.getLocality());
                     sb.append(item.getSubLocality());

                 }

                 address.setText(sb.toString() == "" ? "获取地位信息失败":sb.toString());

             } catch (IOException e) {
                 e.printStackTrace();
             }


         }


     }

     @Override
     public void onStatusChanged(String provider, int status, Bundle extras) {

     }

     @Override
     public void onProviderEnabled(String provider) {

     }

     @Override
     public void onProviderDisabled(String provider) {

     }
 }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          getLocationInfo();
    }


    /**
     * 图片展示
     */
    /*class PhotosAdapter extends RecyclerViewBaseAdapter<MediaBean> {


        public PhotosAdapter(Context context, List<MediaBean> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        protected void onBindNormalViewHolder(final NormalViewHolder holder, MediaBean model) {

            holder.setBitmap(R.id.imageView,BitmapFactory.decodeFile(model.getOriginalPath()));
            *//**
             * 删除选择的图片
             *//*
            holder.getViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     removeItem(holder.getAdapterPosition());
                }
            });

        }
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(listener!=null){
         manager.removeUpdates(listener);
        }
    }
}
