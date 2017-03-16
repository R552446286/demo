package com.bwie.renjue.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 任珏
 * @date 2017/3/1620:28
 */
public class StreamUtils {
    public static String streamToString(InputStream stream){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        byte[] b=new byte[1024];
        int len=0;
        try {
            while((len=stream.read(b))!=-1){
                outputStream.write(b,0,len);
            }
            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
