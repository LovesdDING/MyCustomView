package com.cz10000.mycustomview;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cz10000_001 on 2018/3/7.
 * 继承intentservice
 * 必须重写  onHandlerIntent  方法   --进行异步任务的逻辑实现
 *    必须要有无参的构造函数  --super（"intentservice name"） ；
 *    intentService  的使用 必须在manifest清单文件注册
 */

public class MyIntentService extends IntentService{
    public static final String DOWNLOAD_URL="download_url";
    public static final String INDEX_FLAG="index_flag";

    private String TAG = "MyIntentService";
    public static  UpdateUI updateUI = null;


    public static void setUpdateUI(UpdateUI updateInterface){
        updateUI = updateInterface ;
    }



    public MyIntentService(){
        super("MyIntentService") ;
    }


    /**
     *  实现异步任务的方法
     *  intent Activity 传过来的intent 数据封装在intent中
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bitmap bitmap = downLoadUrlBitmap(intent.getStringExtra(DOWNLOAD_URL)) ;
        Message msg1 = new Message() ;
        msg1.obj = bitmap ;
        msg1.what = intent.getIntExtra(INDEX_FLAG,0) ;
        //通知主线程更新UI
        if(updateUI!=null){
            updateUI.updateMsg(msg1);
        }

        Log.i(TAG, "onHandleIntent: ");


    }


    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.i(TAG, "onStart: ");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return super.onBind(intent);
    }



    interface UpdateUI{
        void updateMsg(Message msg) ;
    }



    // 图片下载
    private Bitmap downLoadUrlBitmap(String urlStr) {
        HttpURLConnection connection = null ;
        BufferedInputStream in = null ;
        Bitmap bitmap = null ;
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(connection.getInputStream(),8*1024) ;
            bitmap = BitmapFactory.decodeStream(in) ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }

            try {
                if(in!=null ){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return bitmap ;
    }

}
