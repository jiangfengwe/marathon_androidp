package com.tdin360.zjw.marathon.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;



/**
 * 自定义表格控件
 * @author zhangzhijun
 * Created by admin on 17/3/28.
 */

public class MyTableView extends TextView{


    Paint paint = new Paint();

    public MyTableView(Context context) {
        super(context);
        int color =  Color.parseColor("#CCCCCC");
           // 为边框设置颜色
         paint.setColor(color);
    }

    public MyTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int color = Color.parseColor("#CCCCCC");
        // 为边框设置颜色
         paint.setColor(color);
    }


    @Override
      protected void onDraw(Canvas canvas) {
              super.onDraw(canvas);
            // 画TextView的4个边
              canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
              canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
              canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);
              canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);
           }

}
