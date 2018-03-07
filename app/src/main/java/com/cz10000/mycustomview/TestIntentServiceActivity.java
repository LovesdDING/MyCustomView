package com.cz10000.mycustomview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 *  @author cz10000_001
 *  intentservice  使用示例
 *
 */
public class TestIntentServiceActivity extends AppCompatActivity implements MyIntentService.UpdateUI {

    /**
     * 图片地址集合
     */
    private String url[] = {
            "http://img.blog.csdn.net/20160903083245762",
            "http://img.blog.csdn.net/20160903083252184",
            "http://img.blog.csdn.net/20160903083257871",
            "http://img.blog.csdn.net/20160903083257871",
            "http://img.blog.csdn.net/20160903083311972",
            "http://img.blog.csdn.net/20160903083319668",
            "http://img.blog.csdn.net/20160903083326871"
    };




    private ImageView imageView;

    private Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intent_service);
        imageView =findViewById(R.id.iv2);

        Intent intent = new Intent(this,MyIntentService.class) ;
        for (int i = 0; i < 7; i++) {  //循环启动任务
                intent.putExtra(MyIntentService.DOWNLOAD_URL,url[i]) ;
                intent.putExtra(MyIntentService.INDEX_FLAG,i) ;
                startService(intent) ;
            
        }

        MyIntentService.setUpdateUI(this);


    }


    @Override
    public void updateMsg(Message msg) {
        mUIHandler.sendMessageDelayed(msg,msg.what*1000) ;
    }
}
