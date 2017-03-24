package com.bwie.renjue.bean;

import java.util.List;

/**
 * @author 任珏
 * @date 2017/3/249:38
 */
public class ApkData {
    public List<App> app;

    @Override
    public String toString() {
        return "ApkData{" +
                "app=" + app +
                '}';
    }
    public class App{
        public String name;
        public String shortDes;
        public String size;
        public String url;

        @Override
        public String toString() {
            return "App{" +
                    "name='" + name + '\'' +
                    ", shortDes='" + shortDes + '\'' +
                    ", size='" + size + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }
}
