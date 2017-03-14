package com.bwie.renjue.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bwie.renjue.R;
import com.bwie.renjue.fragment.HomeFragment;
import com.bwie.renjue.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //配置侧滑
        final SlidingMenu menu=new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffset(100);
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidingmenu);
        replaceFragment();
        //点击关闭打开
        ImageView login_iv = (ImageView) findViewById(R.id.login_iv);
        login_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
    }

    private void replaceFragment() {
        //填充fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.slidingmenu,new MenuFragment());
        transaction.commit();
        //主页fragment
        getSupportFragmentManager().beginTransaction().add(R.id.home_framelayout,new HomeFragment()).commit();
    }
}
