package com.tdin360.zjw.marathon.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 用于监听网络状态的广播
 * Created by admin on 17/2/17.
 */

public class NetWorkStateBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG="Net---->>>>";
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d(TAG, "onReceive: "+isOpenNetwork(context));

    }

    /**
     * 对网络连接状态进行判断
     * @return  true, 可用； false， 不可用
     */
    private boolean isOpenNetwork(Context cxt) {
        ConnectivityManager connManager = (ConnectivityManager)cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if(networkInfo!= null) {
             //2.获取当前网络连接的类型信息
            int networkType = networkInfo.getType();
            if(ConnectivityManager.TYPE_WIFI == networkType){
                //当前为wifi网络
                 }else if(ConnectivityManager.TYPE_MOBILE == networkType){
                //当前为mobile网络
                }
            return connManager.getActiveNetworkInfo().isAvailable();


        }

        return false;
    }
}
