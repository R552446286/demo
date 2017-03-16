package com.bwie.renjue.bean;

import java.util.List;

/**
 * @author 任珏
 * @date 2017/3/1620:33
 */
public class NewsData {
    public Result result;

    @Override
    public String toString() {
        return "NewsData{" +
                "result=" + result +
                '}';
    }
    public class Result{
        public List<News> data;

        @Override
        public String toString() {
            return "Result{" +
                    "data=" + data +
                    '}';
        }
        public class News{
            public String author_name;
            public String date;
            public String thumbnail_pic_s;
            public String thumbnail_pic_s02;
            public String thumbnail_pic_s03;
            public String title;
            public String url;

            @Override
            public String toString() {
                return "News{" +
                        "author_name='" + author_name + '\'' +
                        ", date='" + date + '\'' +
                        ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                        ", thumbnail_pic_s02='" + thumbnail_pic_s02 + '\'' +
                        ", thumbnail_pic_s03='" + thumbnail_pic_s03 + '\'' +
                        ", title='" + title + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }
    }
}
