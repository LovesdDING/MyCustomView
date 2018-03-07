package com.cz10000.mycustomview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *  @author cz10000_001
 *  handlerTHread  测试
 *
 *
 * HandlerThread本质上是一个线程类，它继承了Thread；
HandlerThread有自己的内部Looper对象，可以进行looper循环；
通过获取HandlerThread的looper对象传递给Handler对象，可以在handleMessage方法中执行异步任务。
创建HandlerThread后必须先调用HandlerThread.start()方法，Thread会先调用run方法，创建Looper对象。

 */

public class TestHandlerThreadActivity extends AppCompatActivity {


    /**
     * 图片地址集合
     */
    private String url[]={
            "http://img.blog.csdn.net/20160903083245762",
            "http://img.blog.csdn.net/20160903083252184",
            "http://img.blog.csdn.net/20160903083257871",
            "http://img.blog.csdn.net/20160903083257871",
            "http://img.blog.csdn.net/20160903083311972",
            "http://img.blog.csdn.net/20160903083319668",
            "http://img.blog.csdn.net/20160903083326871"
    };
    private ImageView imageView;


    Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ImageModel imageModel = (ImageModel) msg.obj;
            imageView.setImageBitmap(imageModel.bitmap);

        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_handler_thread);
        imageView = findViewById(R.id.iv1) ;

        //创建异步handlerThread
        HandlerThread handlerThread = new HandlerThread("downloadImg" ) ;
        handlerThread.start(); // 必须先开启线程

        //子线程handler
        Handler childHandler = new Handler(handlerThread.getLooper(),new ChildCallBack()) ;
        for (int i = 0; i < 7; i++) {
            // 每隔1秒更新图片
            childHandler.sendEmptyMessageDelayed(i,1000*i);
            
        }

    }


    class ChildCallBack implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            Bitmap bitmap = downLoadUrlBitmap(url[msg.what]) ;
            ImageModel imageModel = new ImageModel() ;
            imageModel.bitmap = bitmap ;
            imageModel.url = url[msg.what] ;
            Message msg1 = new Message() ;
            msg1.what = msg.what ;
            msg1.obj  = imageModel ;
            //通知 主线程更新UI
            mUIHandler.sendMessage(msg1) ;
            return false;
        }
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
