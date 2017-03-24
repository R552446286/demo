package com.bwie.renjue.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.bwie.renjue.R;
import com.bwie.renjue.adapter.ApkAdapter;
import com.bwie.renjue.bean.ApkData;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class ApkActivity extends AppCompatActivity {

    private ListView apk_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去头
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_apk);
        initView();
        getServerData();
    }

    private void getServerData() {
        String url = "http://mapp.qzone.qq.com/cgi-bin/mapp/mapp_subcatelist_qq?yyb_cateid=-10&categoryName=%E8%85%BE%E8%AE%AF%E8%BD%AF%E4%BB%B6&pageNo=1&pageSize=20&type=app&platform=touch&network_type=unknown&resolution=412x732";
        RequestParams params = new RequestParams(url);
        params.setCacheMaxAge(1000 * 60 * 10);
        x.http().get(params, new Callback.CacheCallback<String>() {
            private String result = null;

            @Override
            public boolean onCache(String result) {
                this.result = result;
                getGson(result);
                return true;
            }


            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    this.result = result;
                    getGson(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getGson(String result) {
        String json=result.substring(0,result.length()-1);
        Gson gson=new Gson();
        ApkData apkData = gson.fromJson(json, ApkData.class);
        List<ApkData.App> app = apkData.app;
        apk_lv.setAdapter(new ApkAdapter(this,app));
    }


    private void initView() {
        apk_lv = (ListView) findViewById(R.id.apk_lv);
    }
}
