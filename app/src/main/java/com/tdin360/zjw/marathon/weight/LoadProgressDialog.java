package com.tdin360.zjw.marathon.weight;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tdin360.zjw.marathon.R;


/**
 * Created by admin on 17/2/19.
 */

public class LoadProgressDialog {


    private  AlertDialog dialog ;
   public LoadProgressDialog(Context context){


       View view = LayoutInflater.from(context).inflate(R.layout.load_progress_dialog, null);
        this.dialog = new AlertDialog.Builder(context).setView(view).create();
       setDialogWindowAttr();
        dialog.show();



   }

    public  void setDialogWindowAttr(){
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = 200;//宽高可设置具体大小
        lp.height = 200;
         dialog.getWindow().setAttributes(lp);
    }

}
