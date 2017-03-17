package com.bwie.renjue.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.renjue.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 任珏
 * @date 2017/3/1420:45
 */
public class VideoFragment extends Fragment{
    private TabLayout video_tablayout;
    private ViewPager video_viewpager;
    private List<String> titles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.video_fragment,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        video_tablayout = (TabLayout) view.findViewById(R.id.video_tablayout);
        video_viewpager = (ViewPager) view.findViewById(R.id.video_viewpager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        for (int i = 0; i < titles.size(); i++) {
            video_tablayout.addTab(video_tablayout.newTab().setText(titles.get(i)));
        }
        video_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
        video_viewpager.setAdapter(adapter);
        video_tablayout.setupWithViewPager(video_viewpager);
    }

    private void initData() {
        titles.add("推荐");
        titles.add("音乐");
        titles.add("搞笑");
        titles.add("社会");
        titles.add("小品");
        titles.add("生活");
        titles.add("影视");
        titles.add("娱乐");
    }
    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            VideoTypeFragment fragment= new VideoTypeFragment();
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
