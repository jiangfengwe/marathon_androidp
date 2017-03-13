package com.tdin360.zjw.marathon.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.step.StepSettingModel;
import com.tdin360.zjw.marathon.ui.fragment.StepFragment;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;

/**
 * 计步设置
 * @author zhangzhijun
 */
public class StepSettingActivity extends BaseActivity {


    private TextView lmTv;
    private SeekBar lmSeekBar;
    private TextView stepTv;
    private SeekBar step_seekBar;
    private TextView weightTv;
    private SeekBar weightSeekBar;

    private int lm;//灵敏度
    private int step_length;//步长
    private int weight;//体重

   private StepSettingModel settingModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBarTitle("个性设置");
        showBackButton();

        initView();

    }


    private void initView() {


        this. settingModel = SharedPreferencesManager.getStepSettingModel(this);
        initParams();


//        灵敏度
        this.lmTv = (TextView) this.findViewById(R.id.lmTv);
        this.lmSeekBar  = (SeekBar) this.findViewById(R.id.lmSeekBar);
        this.lmSeekBar.setProgress(settingModel.getLm());
        this.lmTv.setText(settingModel.getLm()+"");
        this.lmSeekBar.setOnSeekBarChangeListener(new MySeekBarListener());
//        步长
        this.stepTv = (TextView) this.findViewById(R.id.stepTv);
        this.step_seekBar  = (SeekBar) this.findViewById(R.id.stepSeekBar);
        this.step_seekBar.setProgress(settingModel.getStep_length());
        this.stepTv.setText(settingModel.getStep_length()+"");
        this.step_seekBar.setOnSeekBarChangeListener(new MySeekBarListener());
        //体重
        this.weightTv = (TextView) this.findViewById(R.id.weightTv);
        this.weightSeekBar  = (SeekBar) this.findViewById(R.id.weightSeekBar);
        this.weightSeekBar.setProgress(settingModel.getWeight());
        this.weightTv.setText(settingModel.getWeight()+"");
        this.weightSeekBar.setOnSeekBarChangeListener(new MySeekBarListener());


    }

    /**
     * 初始化参数设置
     */
    private void initParams(){

        //初始化设置参数

        this.lm = this.settingModel.getLm();
        this.step_length  =this.settingModel.getStep_length();
        this.weight = this.settingModel.getWeight();

    }
    @Override
    public int getLayout() {
        return R.layout.activity_step_setting;
    }


    /**
     * 用于监听设置滑块的值
     */
    private class MySeekBarListener implements SeekBar.OnSeekBarChangeListener{


        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


             switch (seekBar.getId()){

                 case R.id.lmSeekBar:
                     lm = seekBar.getProgress();
                     lmTv.setText(seekBar.getProgress()+"");

                     break;

                 case R.id.stepSeekBar:
                     step_length = seekBar.getProgress();
                     stepTv.setText(seekBar.getProgress()+"");
                     break;

                 case R.id.weightSeekBar:

                     weight = seekBar.getProgress();
                     weightTv.setText(seekBar.getProgress()+"");

                     break;
             }


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    //保存设置参数
    public void save(View view) {


        switch (view.getId()){

            case R.id.save://保存设置
                SharedPreferencesManager.saveStepParams(getApplicationContext(),new StepSettingModel(lm,step_length,weight));
                finish();
                break;
            case R.id.rest://恢复默认设置
                SharedPreferencesManager.clearStepParams(getApplicationContext());
                initParams();

                break;
        }

        setResult(StepFragment.SETTING_CODE);
        Toast.makeText(this,"设置成功",Toast.LENGTH_SHORT).show();
        finish();
    }




}
