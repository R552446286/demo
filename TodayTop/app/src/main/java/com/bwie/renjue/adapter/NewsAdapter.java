package com.bwie.renjue.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.renjue.R;
import com.bwie.renjue.activity.ImageShowActivity;
import com.bwie.renjue.activity.StartActivity;
import com.bwie.renjue.bean.NewsData;
import com.bwie.renjue.utils.ImageLoaderOptionsUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 任珏
 * @date 2017/3/1620:53
 */
public class NewsAdapter extends BaseAdapter{
    private Context context;
    private List<NewsData.Result.News> data;
    private ArrayList<String> imagesUrl=new ArrayList<>();
    private List<ImageView> images=new ArrayList<>();

    public NewsAdapter(Context context, List<NewsData.Result.News> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).thumbnail_pic_s02==null||data.get(position).thumbnail_pic_s03==null){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 v1 = null;
        ViewHolder2 v2 = null;
        int type = getItemViewType(position);
        if (convertView==null){
            switch (type){
                case 0:
                    convertView=View.inflate(context, R.layout.xlv_item1,null);
                    v1=new ViewHolder1();
                    v1.type1_title= (TextView) convertView.findViewById(R.id.type1_title);
                    v1.type1_author= (TextView) convertView.findViewById(R.id.type1_author);
                    v1.type1_date= (TextView) convertView.findViewById(R.id.type1_date);
                    v1.type1_iv1= (ImageView) convertView.findViewById(R.id.type1_iv1);
                    v1.type1_iv2= (ImageView) convertView.findViewById(R.id.type1_iv2);
                    v1.type1_iv3= (ImageView) convertView.findViewById(R.id.type1_iv3);
                    convertView.setTag(v1);
                    break;
                case 1:
                    convertView=View.inflate(context,R.layout.xlv_item2,null);
                    v2=new ViewHolder2();
                    v2.type2_title= (TextView) convertView.findViewById(R.id.type2_title);
                    v2.type2_author= (TextView) convertView.findViewById(R.id.type2_author);
                    v2.type2_date= (TextView) convertView.findViewById(R.id.type2_date);
                    v2.type2_iv= (ImageView) convertView.findViewById(R.id.type2_iv);
                    convertView.setTag(v2);
                    break;
            }
        }else{
            switch (type){
                case 0:
                    v1= (ViewHolder1) convertView.getTag();
                    break;
                case 1:
                    v2= (ViewHolder2) convertView.getTag();
                    break;
            }
        }
        switch (type){
            case 0:
                v1.type1_title.setText(data.get(position).title);
                v1.type1_author.setText(data.get(position).author_name);
                v1.type1_date.setText(data.get(position).date);
                ImageLoader.getInstance().displayImage(data.get(position).thumbnail_pic_s,v1.type1_iv1, ImageLoaderOptionsUtils.imageLoaderOptions(R.mipmap.ic_launcher));
                ImageLoader.getInstance().displayImage(data.get(position).thumbnail_pic_s02,v1.type1_iv2, ImageLoaderOptionsUtils.imageLoaderOptions(R.mipmap.ic_launcher));
                ImageLoader.getInstance().displayImage(data.get(position).thumbnail_pic_s03,v1.type1_iv3, ImageLoaderOptionsUtils.imageLoaderOptions(R.mipmap.ic_launcher));
                final ArrayList<String> imagesUrl=new ArrayList<>();
                imagesUrl.add(data.get(position).thumbnail_pic_s);
                imagesUrl.add(data.get(position).thumbnail_pic_s02);
                imagesUrl.add(data.get(position).thumbnail_pic_s03);
                List<ImageView> images=new ArrayList<>();
                images.add(v1.type1_iv1);
                images.add(v1.type1_iv2);
                images.add(v1.type1_iv3);
                for (int i = 0; i < images.size(); i++) {
                    final int j=i;
                    images.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, ImageShowActivity.class);
                            intent.putStringArrayListExtra("imagesUrl", imagesUrl);
                            intent.putExtra("position",j);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case 1:
                v2.type2_title.setText(data.get(position).title);
                v2.type2_author.setText(data.get(position).author_name);
                v2.type2_date.setText(data.get(position).date);
                ImageLoader.getInstance().displayImage(data.get(position).thumbnail_pic_s,v2.type2_iv, ImageLoaderOptionsUtils.imageLoaderOptions(R.mipmap.ic_launcher));
                final ArrayList<String> imagesUrl1=new ArrayList<>();
                imagesUrl1.add(data.get(position).thumbnail_pic_s);
                List<ImageView> images1=new ArrayList<>();
                images1.add(v2.type2_iv);
                for (int i = 0; i < images1.size(); i++) {
                    final int j=i;
                    images1.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, ImageShowActivity.class);
                            intent.putStringArrayListExtra("imagesUrl", imagesUrl1);
                            intent.putExtra("position",j);
                            context.startActivity(intent);
                        }
                    });
                }
                break;
        }
        return convertView;
    }
    class ViewHolder1{
        TextView type1_title,type1_author,type1_date;
        ImageView type1_iv1,type1_iv2,type1_iv3;
    }
    class ViewHolder2{
        TextView type2_title,type2_author,type2_date;
        ImageView type2_iv;
    }
}
