package com.gui.carousel.carousel;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;

/**
 * Created by guizhongyun on 17/2/22.
 */

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> mViewlist;

    public MyPagerAdapter(ArrayList<View> viewlist) {
        mViewlist = viewlist;
    }

    @Override
    public int getCount() {
        return 200000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // instantiateItem()中已经调用过removeView()方法,这里不用操作
    }

    /**
     * 生成viewpager中子view的回调方法
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 设置循环生成子view时的view
        int listPosition = position % mViewlist.size();
        if (listPosition < 0) {
            listPosition = listPosition + mViewlist.size();
        }

        View itemView = mViewlist.get(listPosition);

        // 如果该子view上次生成时已经添加了父组件,就remove掉.防止多次添加父组件而抛出IllegalStateException
        ViewGroup parent = (ViewGroup) itemView.getParent();
        if (parent != null) {
            parent.removeView(itemView);
        }
        container.addView(itemView);

        return itemView;
    }
}
