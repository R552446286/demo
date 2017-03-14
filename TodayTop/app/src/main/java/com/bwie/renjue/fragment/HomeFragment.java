package com.bwie.renjue.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.bwie.renjue.R;

/**
 * @author 任珏
 * @date 2017/3/1419:59
 */
public class HomeFragment extends Fragment{

    private RadioGroup type_rg;
    private FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.homefragment,null);
        initView(view);
        return view;
    }
    //初始化控件
    private void initView(View view) {
        type_rg = (RadioGroup) view.findViewById(R.id.type_rg);
        //设置默认为首页
        manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.framelayout,new FirstFragment());
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //点击切换fragment
        type_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.first_rb:
                        replaceFragment(new FirstFragment());
                        break;
                    case R.id.video_rb:
                        replaceFragment(new VideoFragment());
                        break;
                    case R.id.care_rb:
                        replaceFragment(new CareFragment());
                        break;
                }
            }
        });
    }
    //替换fragment
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }
}
