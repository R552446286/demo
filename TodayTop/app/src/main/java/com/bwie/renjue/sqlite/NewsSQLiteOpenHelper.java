package com.bwie.renjue.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author 任珏
 * @date 2017/3/1614:01
 */
public class NewsSQLiteOpenHelper extends SQLiteOpenHelper{
    public NewsSQLiteOpenHelper(Context context) {
        super(context, "news.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table title (_id Integer primary key autoincrement,name char)");
        db.execSQL("create table titlemore (_id Integer primary key autoincrement,name char)");
        db.execSQL("insert into title(name) values('推荐')");
        db.execSQL("insert into title(name) values('热点')");
        db.execSQL("insert into title(name) values('本地')");
        db.execSQL("insert into title(name) values('社会')");
        db.execSQL("insert into title(name) values('娱乐')");
        db.execSQL("insert into title(name) values('科技')");
        db.execSQL("insert into title(name) values('汽车')");
        db.execSQL("insert into titlemore(name) values('体育')");
        db.execSQL("insert into titlemore(name) values('财经')");
        db.execSQL("insert into titlemore(name) values('军事')");
        db.execSQL("insert into titlemore(name) values('国际')");
        db.execSQL("insert into titlemore(name) values('段子')");
        db.execSQL("insert into titlemore(name) values('趣图')");
        db.execSQL("insert into titlemore(name) values('健康')");
        db.execSQL("insert into titlemore(name) values('美女')");
        db.execSQL("create table care (_id Integer primary key autoincrement,name char)");
        db.execSQL("create table collection (_id Integer primary key autoincrement,author_name char,date char,thumbnail_pic_s char,thumbnail_pic_s02 char,thumbnail_pic_s03 char,title char,url char)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
