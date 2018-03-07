package com.cz10000.mycustomview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Logger;

/**
 *   自定义View  实例集合
 *   Test2 APP
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void btClick(View view){
        switch (view.getId()){
            case R.id.btn1:
                startActivity(new Intent(this,TestIntentServiceActivity.class));

                break;
            case R.id.btn2:
                startActivity(new Intent(this,TestHandlerThreadActivity.class));
                break;
        }
    }

}
