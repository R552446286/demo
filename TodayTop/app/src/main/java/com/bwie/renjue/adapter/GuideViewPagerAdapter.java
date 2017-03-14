package com.bwie.renjue.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author 任珏
 * @date 2017/3/1414:00
 */
public class GuideViewPagerAdapter extends PagerAdapter{
    private Context context;
    private int[] guides;

    public GuideViewPagerAdapter(Context context, int[] guides) {
        this.context = context;
        this.guides = guides;
    }

    @Override
    public int getCount() {
        return guides.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView guideView=new ImageView(context);
        guideView.setImageResource(guides[position]);
        guideView.setScaleType(ImageView.ScaleType. CENTER_CROP);
        container.addView(guideView);
        return guideView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
