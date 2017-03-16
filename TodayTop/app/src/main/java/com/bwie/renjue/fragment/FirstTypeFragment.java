package com.bwie.renjue.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.renjue.R;
import com.bwie.renjue.adapter.NewsAdapter;
import com.bwie.renjue.bean.NewsData;
import com.bwie.renjue.utils.StreamUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import xlistview.bawei.com.xlistviewlibrary.XListView;

/**
 * @author 任珏
 * @date 2017/3/1618:25
 */
public class FirstTypeFragment extends Fragment{

    private XListView xListView;
    private HashMap<String,String> typeMap=new HashMap<>();
    private String jsonUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.typefragment,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        xListView = (XListView) view.findViewById(R.id.xListView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initMap();
        Bundle bundle=this.getArguments();
        String type=bundle.getString("type");
        String typePin="";
        Set<String> set = typeMap.keySet();
        for (String s:set) {
            if (s.equals(type)){
                typePin=typeMap.get(s);
            }
        }
        jsonUrl = "http://v.juhe.cn/toutiao/index?type="+typePin+
                "&key=32b9973df2e6ee0c2bf094b61c7d7844";
        getServerData();
    }

    public static Fragment getInstance(String type){
        FirstTypeFragment fragment=new FirstTypeFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initMap() {
        typeMap.put("推荐","tuijian");
        typeMap.put("热点","redian");
        typeMap.put("本地","bendi");
        typeMap.put("社会","shehui");
        typeMap.put("娱乐","yule");
        typeMap.put("科技","keji");
        typeMap.put("汽车","qiche");
        typeMap.put("美女","meinu");
        typeMap.put("财经","caijing");
        typeMap.put("军事","junshi");
        typeMap.put("国际","guoji");
        typeMap.put("段子","duanzi");
        typeMap.put("趣图","qutu");
        typeMap.put("健康","jiankang");
        typeMap.put("体育","tiyu");
    }

    private void getServerData() {
        MyAsyncTask task=new MyAsyncTask();
        task.execute(jsonUrl);
    }

    private class MyAsyncTask extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            HttpClient client=new DefaultHttpClient();
            HttpGet request=new HttpGet(url);
            try {
                HttpResponse response = client.execute(request);
                if (response.getStatusLine().getStatusCode()==200){
                    InputStream inputStream = response.getEntity().getContent();
                    String result = StreamUtils.streamToString(inputStream);
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String json=s.toString();
            Gson gson=new Gson();
            NewsData newsData = gson.fromJson(json, NewsData.class);
            List<NewsData.Result.News> data = newsData.result.data;
            xListView.setAdapter(new NewsAdapter(getActivity(),data));
        }
    }
}
