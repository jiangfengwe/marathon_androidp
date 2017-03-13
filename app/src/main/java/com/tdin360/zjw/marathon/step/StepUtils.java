package com.tdin360.zjw.marathon.step;

import java.text.DecimalFormat;

/**
 * 记步工具类
 * Created by admin on 17/3/9.
 * @author zhangzhijun
 */

public class StepUtils {


    /**
     *  计算实际的步数
     */
    public static int countStep() {
        int step=0;
        if (StepDetector.CURRENT_STEP % 2 == 0) {
            step = StepDetector.CURRENT_STEP;
        } else {
            step = StepDetector.CURRENT_STEP +1;
        }
        step = StepDetector.CURRENT_STEP;
        return step;
    }

    /**
     * 计算并格式化doubles数值，保留两位有效数字
     *
     * @param doubles
     *
     */
    public static String formatDouble(Double doubles) {
        DecimalFormat format = new DecimalFormat("####.##");
        String distanceStr = format.format(doubles);
        return distanceStr.equals("0") ? "0.0"
                : distanceStr;
    }

    /**
     * 计算行程
     * @param step_length 步长
     * @return
     */
    public static double countDistance(int step_length) {
        double distance=0;
        if (StepDetector.CURRENT_STEP % 2 == 0) {
            distance = (StepDetector.CURRENT_STEP / 2) * 3 * step_length * 0.01;
        } else {
            distance = ((StepDetector.CURRENT_STEP / 2) * 3 + 1) * step_length * 0.01;
        }
        return  distance;
    }

    /**
     * 得到一个格式化的时间
     *
     * @param time
     *            时间 毫秒
     * @return 时：分：秒：毫秒
     */
    public static String getFormatTime(long time) {
        time = time / 1000;
        long second = time % 60;
        long minute = (time % 3600) / 60;
        long hour = time / 3600;

        // 毫秒秒显示两位
        // String strMillisecond = "" + (millisecond / 10);
        // 秒显示两位
        String strSecond = ("00" + second)
                .substring(("00" + second).length() - 2);
        // 分显示两位
        String strMinute = ("00" + minute)
                .substring(("00" + minute).length() - 2);
        // 时显示两位
        String strHour = ("00" + hour).substring(("00" + hour).length() - 2);

        return strHour + ":" + strMinute + ":" + strSecond;
        // + strMillisecond;
    }


}
