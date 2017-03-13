package com.tdin360.zjw.marathon.step;

/**
 *
 * 计步设置参数
 * Created by admin on 17/3/10.
 */

public class StepSettingModel {

    private int lm;
    private int step_length;
    private int weight;


    public StepSettingModel(int lm, int step_length, int weight) {
        this.lm = lm;
        this.step_length = step_length;
        this.weight = weight;
    }

    public int getLm() {
        return lm;
    }

    public int getStep_length() {
        return step_length;
    }

    public int getWeight() {
        return weight;
    }
}
