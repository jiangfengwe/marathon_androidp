package com.tdin360.zjw.marathon.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by admin on 17/1/2.
 */

public class FileManager {

    private static FileManager fileManager;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    private FileManager(){}

    public static FileManager getFileManager(){

        if(fileManager==null){

            fileManager=new FileManager();
        }

        return fileManager;
    }

    //判断sd卡是否可用
    public boolean isSDCanUse(){

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            return true;
        }
     // 当前不可用

        return false;
    }

    private File file;
    public File saveFilePath(String fileName)throws Exception {

        if(isSDCanUse()){

          file = new File(path,fileName);

         return file;
        }

        throw  null;
    }

    public File getFile()throws Exception{

        if(file!=null){

            return file;
        }

        throw null;
    }

    //删除文件
    public String deleteFile(File file){


        if(file.exists()){

            file.delete();

            return "删除成功";
        }

        return "删除失败";
    }
}
