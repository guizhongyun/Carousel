package com.gui.carousel.carousel;

import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Context;
import java.util.ArrayList;
import android.os.Handler;
import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private ViewPager vp;
    private Handler mHandler;
    private final int WHAT_START_PLAY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initHandler();
        ArrayList<String> imgUrls = new ArrayList<String>();
        imgUrls.add("http://7xq533.com1.z0.glb.clouddn.com/world_is_big.png");
        imgUrls.add("http://7xq533.com1.z0.glb.clouddn.com/cable-tip.png");
        imgUrls.add("http://7xq533.com1.z0.glb.clouddn.com/cable-tip.png");
        initViewPager(imgUrls);
        Message msg = mHandler.obtainMessage(WHAT_START_PLAY, 10000 + 1, 0);
        mHandler.sendMessageDelayed(msg, 2000);
    }

    private void initHandler(){
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    // 开始轮播
                    case WHAT_START_PLAY: {
                        vp.setCurrentItem(message.arg1, true);
                        break;
                    }
                }
            }
        };
    }
    private void initView(){
        vp = (ViewPager) findViewById(R.id.vp);
    }

    /**
     * 初始化viewpager,并开启轮播
     *
     * @param imageUrls 轮播图的url
     */
    private void initViewPager(ArrayList imageUrls) {

        ArrayList<View> imageViews = new ArrayList<>();
        for (Object url : imageUrls) {
            String urlStr = (String) url;
            ImageView imageView = new ImageView(mContext);
//            Glide.with(this).load(urlStr).asBitmap().into(imageView);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageViews.add(imageView);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(imageViews);
        vp.setAdapter(adapter);
        vp.setCurrentItem(10000);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // 自动播放时的下一页
            private int nextPosition = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                nextPosition = position + 1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    // viewpager处于拖拽状态,表示用户正在用手滑动.此时禁用掉轮播状态
                    case ViewPager.SCROLL_STATE_DRAGGING: {
                        if (mHandler.hasMessages(WHAT_START_PLAY))
                            mHandler.removeMessages(WHAT_START_PLAY);
                        break;
                    }

                    // viewpager已经静止,开启轮播状态
                    case ViewPager.SCROLL_STATE_IDLE: {
                        Message message = mHandler.obtainMessage(WHAT_START_PLAY, nextPosition, 0);
                        mHandler.sendMessageDelayed(message, 2000);
                        break;
                    }

                    // viewpager由拖拽状态进入弹性滑动状态
                    case ViewPager.SCROLL_STATE_SETTLING: {
                        break;
                    }
                }
            }
        });


    }
}
