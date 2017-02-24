package com.tdin360.zjw.marathon.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tdin360.zjw.marathon.R;

/**
 * Created by admin on 17/2/24.
 * 加载对话框工具类
 */

public class MyProgressDialogUtils {


    private static MyProgressDialogUtils utils;

    private Dialog dialog;
    private TextView tv;
    private MyProgressDialogUtils(Context context){

        dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading, null);
        this.tv = (TextView) view.findViewById(R.id.msg);
        dialog.setContentView(view);
        dialog.setCancelable(false);
    }


    public static MyProgressDialogUtils getUtils(Context context){

        if(utils==null){

            utils=new MyProgressDialogUtils(context);
        }

        return utils;
    }


    /**
     * 显示对话框
     */
    public void showDialog(String msg){
        this.tv.setText(msg);
       dialog.show();

    }

    /**
     * 关闭对话框
     */
    public void closeDialog(){

        if(dialog!=null){

            dialog.dismiss();
        }
    }
}
