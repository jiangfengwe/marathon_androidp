package com.tdin360.zjw.marathon.ui.activity;

import android.hardware.Camera;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tdin360.zjw.marathon.R;
import com.tdin360.zjw.marathon.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
/**
 * 二维码
 */

public class ZxingActivity extends BaseActivity implements QRCodeView.Delegate {
    @ViewInject(R.id.zxingview)
    private ZXingView zXingView;
    @ViewInject(R.id.zxing_back)
    private ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initZxing();
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initZxing() {
        zXingView.setDelegate(this);
        zXingView.startSpot();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_zxing;
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        zXingView.startSpot();
        ToastUtils.show(getApplicationContext(),result);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.show(getApplicationContext(),"打开相机出错");
    }
    @Override
    protected void onStart() {
        super.onStart();
        zXingView.startCamera();
        //mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        zXingView.showScanRect();
    }

    @Override
    protected void onStop() {
        zXingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zXingView.onDestroy();
        super.onDestroy();
    }
}
