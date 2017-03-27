package com.bwie.renjue.application;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.x;

import channelmanager.bwie.com.draggridviewlibrary.db.SQLHelper;
import cn.jpush.android.api.JPushInterface;

/**
 * @author 任珏
 * @date 2017/3/169:56
 */
public class MyApplication extends Application{
    private static MyApplication myApplication;
    private SQLHelper sqlHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        //初始化极光
        JPushInterface.init(this);
        //设置debug模式
        JPushInterface.setDebugMode(true);
        //XUtils3.0初始化
        x.Ext.init(this);
        x.Ext.setDebug(false);
        //ImageLoader全局配置初始化
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new WeakMemoryCache())
                .build();
        ImageLoader.getInstance().init(configuration);
    }
    /** 获取Application */
    public static MyApplication getApp() {
        return myApplication;
    }

    /** 获取数据库Helper */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(myApplication);
        return sqlHelper;
    }

    /** 摧毁应用进程时候调用 */
    public void onTerminate() {
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
    }

    public void clearAppCache() {
    }
}
