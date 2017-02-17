package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import java.util.HashMap;
import java.util.Random;


/**
 * Created by admin on 16/11/15.
 * 获取融联云短信验证码
 */
public class SendSMSUtils {

    private HashMap<String, Object>  result =null;
    private CCPRestSmsSDK ccpRestSmsSDK;
    private static SendSMSUtils sendSMSUtils;
    private Random random;
    private String smsCode;

    private final int SUCCESS=200;
    private Context context;

     private SendSMSUtils(Context context){
         this.context=context;
         this.random = new Random();
         this.ccpRestSmsSDK = new CCPRestSmsSDK();
         // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
         ccpRestSmsSDK.init("app.cloopen.com", "8883");
         // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在"控制台-应用"中看到开发者主账号ACCOUNT SID和 主账号令牌AUTH TOKEN。
         ccpRestSmsSDK.setAccount("aaf98f895427cf500154315bcc5e0dd9",
                 "56a44e733ccc4abe95abce315117a959");
         ccpRestSmsSDK.setAppId("8a216da8567745c00156789c2b5b02ca");

     }
    //获取本工具类的实例
    public static SendSMSUtils instance(Context cxt){

        if(sendSMSUtils==null){

           sendSMSUtils=new SendSMSUtils(cxt);
        }

        return sendSMSUtils;
    }


    /***
     *
     * @param tel 接收验证码的手机号
     * @param format 短信模版
     * @return
     */
    public void sendSMSCode(final String tel, final String format){

          //获取验证码
          smsCode=initSMSCode(6);
        new Thread(new Runnable() {

            @Override
            public void run() {
                //如切换到生产环境，请使用自己创建应用的APPID
                 result  = ccpRestSmsSDK.sendTemplateSMS(tel,format ,new String[]{smsCode,"5"});
                System.out.println("SDKTestGetSubAccounts result=" + result );
//               发送验证码成功
                if("000000".equals(result.get("statusCode"))){
                    //正常返回输出data包体信息（map）
                   handler.sendEmptyMessage(SUCCESS);

                }else{
                    //异常返回输出错误码和错误信息
                   handler.sendEmptyMessage(0);

                }
            }
        }).start();

    }

    private Handler handler = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case SUCCESS:
                    Toast.makeText(context, "验证码已成功发送!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context," 获取验证码失败!",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    //获取当前验证码
    public String  getCurrentCode(){

        return this.smsCode;
    }

    /**
     *  随机生成验证码
     * @param length 验证码的长度
     * @return 验证码
     */
    private String initSMSCode(int length){

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<length;i++){

         int code = this.random.nextInt(9);

            sb.append(code);

        }

        return sb.toString();
    }
}


