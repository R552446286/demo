package com.bwie.renjue.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.renjue.R;

public class StartActivity extends AppCompatActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0){
                Intent intent=new Intent(StartActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去头
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_start);
        //子线程实现3秒跳转
        new Thread(){
            @Override
            public void run() {
                //发送3秒延时消息
                handler.sendEmptyMessageDelayed(0,3000);
            }
        }.start();
    }
}
