package com.bwie.renjue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.renjue.R;
import com.bwie.renjue.activity.ChannelActivity;
import com.bwie.renjue.application.MyApplication;

import java.util.ArrayList;
import java.util.List;

import channelmanager.bwie.com.draggridviewlibrary.bean.ChannelItem;
import channelmanager.bwie.com.draggridviewlibrary.bean.ChannelManage;

/**
 * @author 任珏
 * @date 2017/3/1420:45
 */
public class FirstFragment extends Fragment{
    private TabLayout first_tablayout;
    private ViewPager first_viewpager;
    private TextView first_addpro;
    private List<String> titles;
    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.first_fragment,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        first_tablayout = (TabLayout) view.findViewById(R.id.first_tablayout);
        first_viewpager = (ViewPager) view.findViewById(R.id.first_viewpager);
        first_addpro = (TextView) view.findViewById(R.id.first_addpro);
    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }*/

    @Override
    public void onResume() {
        super.onResume();
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(MyApplication.getApp().getSQLHelper()).getUserChannel());
        titles=new ArrayList<>();
        for (int i = 0; i < userChannelList.size(); i++) {
            titles.add(userChannelList.get(i).getName());
            Log.e("ssssssssssssssssssss",userChannelList.get(i).getName());
        }
        for (int i = 0; i < titles.size(); i++) {
            first_tablayout.addTab(first_tablayout.newTab().setText(titles.get(i)));
        }
        first_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
        first_viewpager.setAdapter(adapter);
        first_tablayout.setupWithViewPager(first_viewpager);
        first_addpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ChannelActivity.class);
                startActivity(intent);
            }
        });
    }

    public class MyViewPagerAdapter extends FragmentPagerAdapter{

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FirstTypeFragment fragment= (FirstTypeFragment) FirstTypeFragment.getInstance(titles.get(position));
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
