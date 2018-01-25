package com.tdin360.zjw.marathon.jiguan;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.tdin360.zjw.marathon.EnumEventBus;
import com.tdin360.zjw.marathon.EventBusClass;
import com.tdin360.zjw.marathon.model.CirclePriseTableModel;
import com.tdin360.zjw.marathon.model.NoticeMessageModel;
import com.tdin360.zjw.marathon.ui.activity.CircleMessageActivity;
import com.tdin360.zjw.marathon.ui.activity.MainActivity;
import com.tdin360.zjw.marathon.ui.activity.MyNoticeMessageActivity;
import com.tdin360.zjw.marathon.utils.SharedPreferencesManager;
import com.tdin360.zjw.marathon.utils.SystemUtils;
import com.tdin360.zjw.marathon.utils.db.CirclePraiseDatabaseImpl;
import com.tdin360.zjw.marathon.utils.db.impl.CircleNoticeDetailsServiceImpl;
import com.tdin360.zjw.marathon.utils.db.impl.NoticeMessageServiceImpl;
import com.tdin360.zjw.marathon.utils.db.impl.SystemNoticeDetailsServiceImpl;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import uk.co.senab.photoview.log.LoggerDefault;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
    public static  final int SystemMessage=10;
	public static  final int CustomMessage=20;
	private NoticeMessageServiceImpl service;
	private static CirclePraiseDatabaseImpl circlePraiseDatabase;
	private CircleNoticeDetailsServiceImpl circleNoticeDetailsService;
	private SystemNoticeDetailsServiceImpl systemNoticeDetailsService;

    private boolean flag;

	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
          //  Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	//Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	//processCustomMessage(context, bundle);
			String content =  bundle.getString(JPushInterface.EXTRA_MESSAGE);
			Log.d("notice", "onReceive: "+content);
			saveMessage(context,content);


        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
          // Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
           // Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			String content = bundle.getString("cn.jpush.android.ALERT");
        	 saveMessage(context,content);
			 initData(context, bundle);
			//获取数据并保存到sharepreference

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
           // Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

			//打开自定义的Activity
			if(SystemUtils.isAppAlive(context,context.getPackageName())){
				circleNoticeDetailsService=new CircleNoticeDetailsServiceImpl(context);
				systemNoticeDetailsService=new SystemNoticeDetailsServiceImpl(context);
				String string = bundle.getString(JPushInterface.EXTRA_EXTRA);
				Log.d("circlestring", "printBundle: "+string);
				Gson gson=new Gson();
				CirclePriseTableModel circlePriseTableModel = gson.fromJson(string, CirclePriseTableModel.class);
				String nickName = circlePriseTableModel.getNickName();
				String messageType = circlePriseTableModel.getMessageType();
				Log.d("circlenickName", "onReceive: "+nickName);
				if(messageType.equals("messagenotification")){
					Intent notice = new Intent(context,CircleMessageActivity.class);
					Intent main = new Intent(context,MainActivity.class);
					main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// notice.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivities(new Intent[]{main,notice});
					//context.startActivity(notice);
				}else{
					Intent notice = new Intent(context,MyNoticeMessageActivity.class);
					Intent main = new Intent(context,MainActivity.class);
					main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// notice.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivities(new Intent[]{main,notice});
					//context.startActivity(notice);
				}


			}else {
				Intent mLaunchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
				mLaunchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(mLaunchIntent);
			}

        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
           // Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        //	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        //	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
        //initData(context,bundle);

	}

	private void initData(Context context, Bundle bundle) {
		circleNoticeDetailsService=new CircleNoticeDetailsServiceImpl(context);
		systemNoticeDetailsService=new SystemNoticeDetailsServiceImpl(context);
		Log.d("mynotice", "onReceive: "+bundle);
		StringBuilder sb = new StringBuilder();
		/*for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));*/
					//Log.d("circlejson", "printBundle: "+json);
					String string = bundle.getString(JPushInterface.EXTRA_EXTRA);
					Log.d("circlestring", "printBundle: "+string);
					Gson gson=new Gson();
					CirclePriseTableModel circlePriseTableModel = gson.fromJson(string, CirclePriseTableModel.class);
					String nickName = circlePriseTableModel.getNickName();
					String messageType = circlePriseTableModel.getMessageType();
					Log.d("circlenickName", "onReceive: "+nickName);
					if(messageType.equals("messagenotification")){
						SharedPreferencesManager.isNotice(context,true);
						EnumEventBus circle = EnumEventBus.CIRCLENOTICE;
						EventBus.getDefault().post(new EventBusClass(circle));
						circleNoticeDetailsService.addCircleNotice(circlePriseTableModel);
					}else{
						SharedPreferencesManager.isNotice(context,true);
						EnumEventBus circle = EnumEventBus.SYSTEM;
						EventBus.getDefault().post(new EventBusClass(circle));
						systemNoticeDetailsService.addSystemNotice(circlePriseTableModel);
					}




					//Log.d("circlestring", "printBundle: "+circleNoticeDetailsService);
					//circleNoticeDetailsService.add(circlePriseTableModel);
					/*Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}*/
		/*		} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}*/
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					//Log.d("circlejson", "printBundle: "+json);
					String string = bundle.getString(JPushInterface.EXTRA_EXTRA);
					//Log.d("circlestring", "printBundle: "+string);
					Gson gson=new Gson();
					CirclePriseTableModel circlePriseTableModel = gson.fromJson(string, CirclePriseTableModel.class);
					//circlePraiseDat
					//
					//circleNoticeDetailsService.add(circlePriseTableModel);
					/*Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}*/
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		//Log.d("mynoticesb", "printBundle: "+sb.toString());

		return sb.toString();
	}
	
	//send msg to MainActivity
//	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra("type",CustomMessage);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//
//					Log.d(TAG, "processCustomMessage: ---->"+extraJson);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}
	//}

	//普通通知消息发送到MainActivity
	private void saveMessage(Context context, String msg){
		this.service = new NoticeMessageServiceImpl(context);

		service.addNotice(new NoticeMessageModel("", getTime(), msg.trim()));

	}

	//获取指定格式的当前系统时间
	private  String getTime(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(System.currentTimeMillis()));
	}
}
