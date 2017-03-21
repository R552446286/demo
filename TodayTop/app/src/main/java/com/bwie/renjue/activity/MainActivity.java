package com.bwie.renjue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bwie.renjue.R;
import com.bwie.renjue.adapter.GuideViewPagerAdapter;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager guide_viewPager;
    private LinearLayout point_linearLayout;
    private Button startapp_bt;
    private int[] guides=new int[]{R.mipmap.guide_a,R.mipmap.guide_b,R.mipmap.guide_c,R.mipmap.guide_d};
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去头
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        //sp存取值
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        edit = config.edit();
        boolean flag=config.getBoolean("flag",false);
        if (flag){
            //跳转
            intentActivity();
        }
        //初始化控件
        initView();
        //配置ViewPager
        initViewPager();
        //按钮监听
        startapp_bt.setOnClickListener(this);
    }

    private void initView() {
        guide_viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        point_linearLayout = (LinearLayout) findViewById(R.id.point_linearLayout);
        startapp_bt = (Button) findViewById(R.id.startapp_bt);
    }

    private void initViewPager() {
        //设置适配器
        guide_viewPager.setAdapter(new GuideViewPagerAdapter(this,guides));
        //初始化小圆点
        initPoint();
        //ViewPager设置监听
        guide_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count=point_linearLayout.getChildCount();
                for (int i=0;i<count;i++){
                    //选中页对应小点变色
                    View view=point_linearLayout.getChildAt(i);
                    view.setEnabled(position==i?false:true);
                }
                //进入按钮的显示隐藏
                if (position==(guides.length-1)){
                    startapp_bt.setVisibility(View.VISIBLE);
                    point_linearLayout.setVisibility(View.GONE);
                }else{
                    startapp_bt.setVisibility(View.GONE);
                    point_linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPoint() {
        for (int i = 0; i < guides.length; i++) {
            View view=new View(this);
            view.setBackgroundResource(R.drawable.guide_point_bg);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(15,15);
            params.rightMargin=20;
            point_linearLayout.addView(view,params);
        }
        View view=point_linearLayout.getChildAt(0);
        view.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        //sp存值
        edit.putBoolean("flag",true);
        edit.commit();
        //跳转
        intentActivity();
    }

    private void intentActivity() {
        Intent intent=new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }
}
