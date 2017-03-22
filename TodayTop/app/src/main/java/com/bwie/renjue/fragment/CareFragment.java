package com.bwie.renjue.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.renjue.R;
import com.bwie.renjue.activity.AddCareActivity;
import com.bwie.renjue.activity.TypeActivity;
import com.bwie.renjue.adapter.CareAdapter;
import com.bwie.renjue.sqlite.NewsSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 任珏
 * @date 2017/3/14 20:45
 */
public class CareFragment extends Fragment {

    private ListView care_listView;
    private TextView nothing_tv;
    private TextView addcare_tv;
    private SQLiteDatabase sd;
    private List<String> list=new ArrayList<>();
    private CareAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.care_fragment,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        care_listView = (ListView) view.findViewById(R.id.care_listView);
        addcare_tv = (TextView) view.findViewById(R.id.addcare_tv);
        nothing_tv = (TextView) view.findViewById(R.id.nothing_tv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsSQLiteOpenHelper soh = new NewsSQLiteOpenHelper(getActivity());
        sd = soh.getWritableDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        Cursor cursor = sd.rawQuery("select * from care", null);
        while(cursor.moveToNext()){
            if (cursor.getString(1)!=null){
                list.add(cursor.getString(1));
            }
        }
        if (list.size()==0){
            nothing_tv.setVisibility(View.VISIBLE);
            care_listView.setVisibility(View.GONE);
        }else{
            nothing_tv.setVisibility(View.GONE);
            care_listView.setVisibility(View.VISIBLE);
            adapter = new CareAdapter(getActivity(),list);
            care_listView.setAdapter(adapter);
        }
        addcare_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddCareActivity.class);
                getActivity().startActivity(intent);
            }
        });
        care_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), TypeActivity.class);
                intent.putExtra("name",list.get(position));
                startActivity(intent);
            }
        });
        care_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("是否确认删除？");
                builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sd.execSQL("delete from care where name='"+list.get(position)+"'");
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return false;
            }
        });
    }
}
