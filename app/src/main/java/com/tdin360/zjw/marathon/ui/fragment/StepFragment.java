package com.tdin360.zjw.marathon.ui.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.model.StepModel;
import com.tdin360.zjw.marathon.step.StepCounterService;
import com.tdin360.zjw.marathon.step.StepSettingModel;
import com.tdin360.zjw.marathon.step.StepUtils;
import com.tdin360.zjw.marathon.ui.activity.StepHistoryActivity;
import com.tdin360.zjw.marathon.ui.activity.StepSettingActivity;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.db.impl.StepServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 运动计步器界面
 * @author zhangzhijun
 * Created by admin on 17/3/9.
 */

public class StepFragment extends BaseFragment{

    public static final String STEP_ACTION="step";
    public static final int SETTING_CODE=0x11;//用于标示设置返回
    public static int LM;//灵敏度
    private int step=0;
    private Double distance = 0.0;// 路程：米
    private Double calories = 0.0;// 热量：卡路里
    private Double velocity = 0.0;// 速度：米每秒
    private int step_length;  //步长(厘米)
    private int weight;       //体重

    private TextView timeTv;
    private TextView tv;
    private TextView dis;
    private TextView kcalTv;
    private TextView speedTv;
    private CheckBox btn;

    private Animation animation;
    private ImageView stepAnim;
    private ObjectAnimator animator;
    public static StepFragment newInstance(){

        return new StepFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.frament_step,container,false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerReceiver();
        this.tv = (TextView) view.findViewById(R.id.tv);
        this.btn = (CheckBox) view.findViewById(R.id.btn);
        this.timeTv = (TextView) view.findViewById(R.id.time);
        this.dis = (TextView) view.findViewById(R.id.dis);
        this.kcalTv = (TextView) view.findViewById(R.id.kcal);
        this.speedTv = (TextView) view.findViewById(R.id.speed);
        this.stepAnim = (ImageView) view.findViewById(R.id.stepAnim);
        //获取并设置计步参数
        setStepParams();

        if(Build.VERSION.SDK_INT>=19) {
            animator = ObjectAnimator.ofFloat(stepAnim, "rotation", 0f, 360.0f);
            animator.setDuration(5000);
            animator.setInterpolator(new LinearInterpolator());//不停顿
            animator.setRepeatCount(-1);//设置动画重复次数
            animator.setRepeatMode(ValueAnimator.RESTART);//动画重复模式
        }else {
            this.animation = AnimationUtils.loadAnimation(getActivity(),R.anim.step_rotate);
//        设置图片匀速旋转
            LinearInterpolator lin = new LinearInterpolator();
            animation.setInterpolator(lin);
        }


        this.btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            Intent intent = new Intent(getActivity(), StepCounterService.class);
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    intent.putExtra(StepCounterService.KEY,StepCounterService.START);
                    getActivity().startService(intent);

                    if(Build.VERSION.SDK_INT>=19){

                        if(animator.isStarted()){//开始计步

                            animator.resume();//恢复动画
                        }else {

                            animator.start();//开始动画

                        }
                    }else {

                        stepAnim.startAnimation(animation);
                    }


                }else {//暂停计步

                    intent.putExtra(StepCounterService.KEY,StepCounterService.PAUSE);
                    getActivity().startService(intent);

                    if(Build.VERSION.SDK_INT>=19){

                        animator.pause();//暂停动画
                    }else {

                        stepAnim.clearAnimation();
                    }

                }

            }
        });


        /**
         * 设置计步参数
         *
         */

         view.findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent = new Intent(getActivity(), StepSettingActivity.class);
                 startActivityForResult(intent,SETTING_CODE);

             }
         });


        /**
         *  查看历史计步
         */
        view.findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StepHistoryActivity.class);

                startActivity(intent);
            }
        });

    }


    /**
     * 接收到设置返回后立即更新设置参数
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SETTING_CODE){

            //更新计步设置参数值
            setStepParams();
        }
    }

    private StepBroadcastReceiver receiver;

    //    注册广播
    private void registerReceiver(){

        IntentFilter filter = new IntentFilter(STEP_ACTION);

        this.receiver =  new StepBroadcastReceiver();

         getActivity().registerReceiver(receiver,filter);


    }

    /**
     * 注销广播
     */
    private void unRegisterReceiver(){
        if(receiver!=null){

            getActivity().unregisterReceiver(receiver);
        }


    }

    /**
     * 用于接收计步器计步的广播
     */

    private class StepBroadcastReceiver extends BroadcastReceiver {



        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent!=null) {

                step = intent.getIntExtra("step", 0);

                long time = intent.getLongExtra("time", 0);

                distance = StepUtils.countDistance(step_length);

                //计算速度和卡路里
                if (time != 0 && distance != 0.0) {

                    // 跑步热量（kcal）＝体重（kg）×距离（公里）×1.036
                    calories = weight * distance * 0.001 * 1.036;
                    //速度velocity
                    velocity = distance * 1000 / time;
                } else {
                    calories = 0.0;
                    velocity = 0.0;
                }
                tv.setText(step + "");
                timeTv.setText(StepUtils.getFormatTime(time));
                dis.setText(StepUtils.formatDouble(distance));
                speedTv.setText(StepUtils.formatDouble(velocity));
                kcalTv.setText(StepUtils.formatDouble(calories));
            }


        }
    }

    /**
     * 设置计步参数
     */
    private void setStepParams(){

        StepSettingModel model = SharedPreferencesManager.getStepSettingModel(getActivity());
        LM = model.getLm();
        step_length  =model.getStep_length();
        weight=model.getWeight();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();

        //停止计步
        Intent intent = new Intent(getActivity(), StepCounterService.class);
        getActivity().stopService(intent);
        //保存计步数据
        StepServiceImpl stepService = new StepServiceImpl(getContext());
        stepService.addStep(new StepModel(0,step,distance,calories,getTime()));

        Log.d("-----------", "onDestroy: ");
    }

    private String getTime(){

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        return f.format(new Date(System.currentTimeMillis()));

    }
}
