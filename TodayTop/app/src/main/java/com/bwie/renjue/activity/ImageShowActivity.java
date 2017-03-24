package com.bwie.renjue.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.bwie.renjue.R;
import com.bwie.renjue.adapter.ShowViewPagerAdapter;

import java.util.ArrayList;

public class ImageShowActivity extends AppCompatActivity {

    private ViewPager imageShow_viewPager;
    private RelativeLayout relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去头
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_image_show);
        Intent intent = getIntent();
        ArrayList<String> imagesUrl = intent.getStringArrayListExtra("imagesUrl");
        int position = intent.getIntExtra("position", 0);
        relative = (RelativeLayout) findViewById(R.id.relative);
        imageShow_viewPager = (ViewPager) findViewById(R.id.imageShow_viewPager);
        imageShow_viewPager.setAdapter(new ShowViewPagerAdapter(this,imagesUrl));
        imageShow_viewPager.setCurrentItem(position);
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
