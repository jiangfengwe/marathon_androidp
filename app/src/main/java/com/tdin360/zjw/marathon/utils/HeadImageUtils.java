package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tdin360.zjw.marathon.ui.fragment.MyFragment;

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
    public HeadImageUtils(Context context){

        this.context=context;

        this.root=  context.getExternalFilesDir("images").toString();
    }

    /**
     * 获取头像保存目录
     * @return
     */
    public synchronized String getFilePath(String fileName){

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
    public Bitmap getImage(String fileName){
            Bitmap bitmap = BitmapFactory.decodeFile(getFilePath(fileName));

            return bitmap;

    }

    /**
     * 下载头像图片
     */
    public void download(final String url, final String fileName)  {



        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    URL u = new URL(url);


                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();

                    connection.setConnectTimeout(5 * 1000);
                    connection.setRequestMethod("GET");
                    connection.connect();

                    FileOutputStream os = new FileOutputStream(getFilePath(fileName));
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
        Intent intent = new Intent(MyFragment.ACTION);
        context.sendBroadcast(intent);
    }
}
