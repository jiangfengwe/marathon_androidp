package com.tdin360.zjw.marathon.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import com.tdin360.zjw.marathon.R;

/**
 * 自定义日期选择框
 * Created by admin on 17/1/3.
 */

public class MyDatePickerDialog  extends AlertDialog {

    private DatePicker datePicker;
    public MyDatePickerDialog(Context context) {
        super(context);
        init(context);
    }

    private  void init(Context context){

        View view = View.inflate(context, R.layout.datepicker_dialog, null);
        this.datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(listener!=null&&datePicker!=null){

                    listener.onChange(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
                }

            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setView(view);

    }

    public void setDate(int year,int month,int day){

        this.datePicker.updateDate(year,month,day);
    }

    public interface OnMyDatePickerChangeListener{

        void onChange(int year,int month,int day);
    }
    private OnMyDatePickerChangeListener listener;

    public void setOnMyDatePickerChangeListener(OnMyDatePickerChangeListener listener){

        this.listener=listener;
    }
}
