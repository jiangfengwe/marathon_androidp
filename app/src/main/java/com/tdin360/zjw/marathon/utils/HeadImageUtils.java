package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;

import com.tdin360.zjw.marathon.ui.fragment.Personal_CenterFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * 下载头像图片工具类
 * Created by admin on 17/3/5.
 */

public class HeadImageUtils {

    private String root;
    private Context context;
    private String fileName;
    public HeadImageUtils(Context context,String fileName){

        this.context=context;
        this.fileName=fileName;
        this.root=  context.getExternalFilesDir("images").toString();
    }

    /**
     * 获取头像保存目录
     * @return
     */
    public synchronized String getFilePath(){

        File file = new File(root,fileName+".jpg");

        if(!file.exists()){

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file.getPath();
    }
    /**
     * 获取头像
     */
    public Bitmap getImage(){
            Bitmap bitmap = BitmapFactory.decodeFile(getFilePath());

            return bitmap;

    }

    /**
     * 下载头像图片
     */
    public void download(final String url)  {



        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    URL u = new URL(url);


                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();

                    connection.setConnectTimeout(5 * 1000);
                    connection.setRequestMethod("GET");
                    connection.connect();

                    FileOutputStream os = new FileOutputStream(getFilePath());
                    InputStream stream = null;
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        stream = connection.getInputStream();

                        int len = 0;

                        byte[] data = new byte[1024];

                        while ((len = stream.read(data)) != -1) {

                            os.write(data, 0, len);
                        }
                        os.flush();
                        os.close();
                        if (stream != null) {
                            stream.close();
                        }
                        connection.disconnect();

                         //通知更新头像
                         sendNotify();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //更新个人中心头像
    private void sendNotify(){
        Intent intent = new Intent(Personal_CenterFragment.ACTION);
        context.sendBroadcast(intent);
    }
}
