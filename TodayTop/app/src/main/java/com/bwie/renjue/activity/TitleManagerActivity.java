package com.bwie.renjue.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bwie.renjue.R;
import com.bwie.renjue.adapter.GridAdapter;
import com.bwie.renjue.sqlite.NewsSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TitleManagerActivity extends AppCompatActivity {
    private TextView tv_close;
    private GridView mineGridView;
    private GridView moreGridView;
    private List<String> titles=new ArrayList<>();
    private List<String> titlesMore=new ArrayList<>();
    private SQLiteDatabase sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去头
        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_title_manager);
        //初始化控件
        initView();
        //查询
        NewsSQLiteOpenHelper soh = new NewsSQLiteOpenHelper(this);
        sd = soh.getWritableDatabase();
        Cursor cursor = sd.rawQuery("select * from title", null);
        while(cursor.moveToNext()){
            titles.add(cursor.getString(1));
        }
        Cursor cursor1 = sd.rawQuery("select * from titlemore", null);
        while(cursor1.moveToNext()){
            titlesMore.add(cursor1.getString(1));
        }
        final GridAdapter adapter1 = new GridAdapter(this, titles);
        final GridAdapter adapter2 = new GridAdapter(this, titlesMore);
        mineGridView.setAdapter(adapter1);
        moreGridView.setAdapter(adapter2);
        mineGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    sd.execSQL("insert into titlemore(name) values('"+titles.get(position)+"')");
                    titlesMore.add(titles.get(position));
                    sd.execSQL("delete from title where name='"+titles.get(position)+"'");
                    titles.remove(position);
                    adapter1.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                }
            }
        });
        moreGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sd.execSQL("insert into title(name) values('"+titlesMore.get(position)+"')");
                titles.add(titlesMore.get(position));
                sd.execSQL("delete from titlemore where name='"+titlesMore.get(position)+"'");
                titlesMore.remove(position);
                adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        });
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TitleManagerActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        tv_close = (TextView) findViewById(R.id.tv_close);
        mineGridView = (GridView) findViewById(R.id.mineGridView);
        moreGridView = (GridView) findViewById(R.id.moreGridView);
    }
}
