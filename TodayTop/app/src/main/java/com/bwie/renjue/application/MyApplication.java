package com.bwie.renjue.application;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 任珏
 * @date 2017/3/169:56
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
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
}
