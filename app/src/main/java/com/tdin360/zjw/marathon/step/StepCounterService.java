package com.tdin360.zjw.marathon.step;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.tdin360.zjw.marathon.model.StepModel;
import com.tdin360.zjw.marathon.ui.fragment.StepFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

//service负责后台的需要长期运行的任务
// 计步器服务
// 运行在后台的服务程序，完成了界面部分的开发后
// 就可以开发后台的服务类StepService
// 注册或注销传感器监听器，在手机屏幕状态栏显示通知，与StepActivity进行通信，走过的步数记到哪里了？？？
public class StepCounterService extends Service {

	private final int WHAT=0x09;
	public static Boolean FLAG = false;// 服务运行标志
	public static final int START=0;//开始记步
	public static final int PAUSE=1;//暂停记步
	public static final String KEY="cmd";//传值标记


	private SensorManager mSensorManager;// 传感器服务
	private StepDetector detector;// 传感器监听对象

	private PowerManager mPowerManager;// 电源管理服务
	private WakeLock mWakeLock;// 屏幕灯

	private long timer = 0;// 运动时间
	private  long startTimer = 0;// 开始时间
	private  long tempTime = 0;


	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {

		super.onCreate();


	   // 创建监听器类，实例化监听对象
		detector = new StepDetector();


		//获取传感器的服务，初始化传感器
		mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

		//注册传感器，注册监听器
		mSensorManager.registerListener(detector,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		// 电源管理服务
//		mPowerManager = (PowerManager) this
//				.getSystemService(Context.POWER_SERVICE);
//		mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
//				| PowerManager.ACQUIRE_CAUSES_WAKEUP, "S");
//		mWakeLock.acquire();




	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if(intent!=null){

			int cmd = intent.getIntExtra(KEY, -1);

			switch (cmd) {//开始记步

				case START:
					startTimer = System.currentTimeMillis();
					tempTime = timer;
					handler.sendEmptyMessage(WHAT);
					FLAG = true;
					break;
				case PAUSE://暂停计步
					FLAG = false;
					handler.removeMessages(WHAT);
					break;
			}

		}
		return super.onStartCommand(intent, flags, startId);
	}



	private Handler handler = new Handler(){

		private int temp = 0;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);



				if (temp != StepDetector.CURRENT_STEP) {
					temp = StepDetector.CURRENT_STEP;
				}
				if (startTimer != System.currentTimeMillis()) {
					timer = tempTime + System.currentTimeMillis()
							- startTimer;
				}

				//发送广播更新ui
				Intent intent = new Intent(StepFragment.STEP_ACTION);
				intent.putExtra("step", StepDetector.CURRENT_STEP);
				intent.putExtra("time", timer);
				sendBroadcast(intent);
			handler.sendEmptyMessageDelayed(WHAT,300);



		}
	};


	@Override
	public void onDestroy() {

		super.onDestroy();
		FLAG = false;// 服务停止
		handler.removeMessages(WHAT);
		StepDetector.CURRENT_STEP=0;
		if (detector != null) {
			mSensorManager.unregisterListener(detector);
		}

//		if (mWakeLock != null) {
//			mWakeLock.release();
//		}
	}


}
