package com.bwie.renjue.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwie.renjue.R;

import java.util.List;

/**
 * @author 任珏
 * @date 2017/3/1019:39
 */
public class GridAdapter extends BaseAdapter{
    private Context context;
    private List<String> titles;

    public GridAdapter(Context context, List<String> titles) {
        this.context = context;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context, R.layout.grid_item,null);
        TextView item_tv = (TextView) convertView.findViewById(R.id.item_tv);
        item_tv.setText(titles.get(position));
        return convertView;
    }
}
