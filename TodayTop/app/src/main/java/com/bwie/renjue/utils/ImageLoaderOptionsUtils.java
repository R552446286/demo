package com.bwie.renjue.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * @author 任珏
 * @date 2017/3/1621:23
 */
public class ImageLoaderOptionsUtils {
    public static DisplayImageOptions imageLoaderOptions(int imageid){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(imageid)
                .showImageForEmptyUri(imageid)
                .showImageOnLoading(imageid)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(android.graphics.Bitmap.Config.RGB_565)
                .build();
        return options;
    }
}
