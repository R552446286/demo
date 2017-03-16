package com.bwie.renjue.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.renjue.R;
import com.bwie.renjue.fragment.CareFragment;
import com.bwie.renjue.fragment.FirstFragment;
import com.bwie.renjue.fragment.VideoFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class HomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private RadioGroup type_rg;
    private FragmentManager manager;
    private ImageView login_iv;
    private ImageView call_login;
    private ImageView qq_login;
    private ImageView weixin_login;
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private LinearLayout dayNight_linear;
    //默认的日间模式
    private int theme = R.style.AppTheme;
    private RelativeLayout not_login;
    private RelativeLayout already_login;
    private ImageView head_iv;
    private TextView nickname_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //恢复数据 做判空
        if (savedInstanceState != null) {
            theme = savedInstanceState.getInt("theme");
            //设置主题 此方法必须在setContentView()之前调用
            setTheme(theme);
        }
        //去头
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.homefragment);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,this.getApplicationContext());
        //初始化SMSSDK
        SMSSDK.initSDK(this, "1c0e2609bb4aa", "a941cdb1b2e606adc23902d0f08b60cf");
        //配置侧滑
        final SlidingMenu menu=new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffset(100);
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu);
        //初始化控件
        initView();
        //点击关闭打开
        login_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });
        //点击切换fragment
        type_rg.setOnCheckedChangeListener(this);
        call_login.setOnClickListener(this);
        qq_login.setOnClickListener(this);
        weixin_login.setOnClickListener(this);
        dayNight_linear.setOnClickListener(this);
    }
    //初始化控件
    private void initView() {
        login_iv = (ImageView) findViewById(R.id.login_iv);
        type_rg = (RadioGroup) findViewById(R.id.type_rg);
        call_login = (ImageView) findViewById(R.id.call_login);
        qq_login = (ImageView) findViewById(R.id.qq_login);
        weixin_login = (ImageView) findViewById(R.id.weixin_login);
        dayNight_linear = (LinearLayout) findViewById(R.id.dayNight_linear);
        not_login = (RelativeLayout) findViewById(R.id.not_login);
        already_login = (RelativeLayout) findViewById(R.id.already_login);
        head_iv = (ImageView) findViewById(R.id.head_iv);
        nickname_tv = (TextView) findViewById(R.id.nickname_tv);
        //设置默认为首页
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.framelayout,new FirstFragment());
        transaction.commit();
    }
    //替换fragment
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.first_rb:
                replaceFragment(new FirstFragment());
                break;
            case R.id.video_rb:
                replaceFragment(new VideoFragment());
                break;
            case R.id.care_rb:
                replaceFragment(new CareFragment());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.call_login:
                //打开注册页面
                final RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    @Override
                    public void afterEvent(int event, int result, Object data) {
                        super.afterEvent(event, result, data);
                        //解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            HashMap<String, Object> map = (HashMap<String, Object>) data;
                            String country = (String) map.get("country");
                            String phone = (String) map.get("phone");
                        }
                    }
                });
                registerPage.show(HomeActivity.this);
                break;
            case R.id.qq_login:
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(this,"all", mIUiListener);
                break;
            case R.id.weixin_login:

                break;
            case R.id.dayNight_linear:
                //切换日夜间模式
                theme = (theme == R.style.AppTheme) ? R.style.NightAppTheme : R.style.AppTheme;
                //重新创建
                recreate();
                break;
        }
    }
    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(HomeActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                        JSONObject res= (JSONObject) response;
                        String nickName=res.optString("nickname");
                        String figureurl_qq_1=res.optString("figureurl_qq_1");
                        Log.e(TAG,nickName+",,"+figureurl_qq_1);
                        not_login.setVisibility(View.GONE);
                        already_login.setVisibility(View.VISIBLE);
                        //通过ImageOptions.Builder().set方法设置图片的属性
                        ImageOptions options = new ImageOptions.Builder().setCircular(true).setCrop(true).setSize(100, 100).setLoadingDrawableId(R.mipmap.ic_launcher).build();
                        x.image().bind(head_iv,figureurl_qq_1,options);
                        x.image().bind(login_iv,figureurl_qq_1,options);
                        nickname_tv.setText(nickName);
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(HomeActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(HomeActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }
    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //保存数据
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
        outState.putInt("theme", theme);
    }

    //恢复数据
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        theme = savedInstanceState.getInt("theme");
    }
}
