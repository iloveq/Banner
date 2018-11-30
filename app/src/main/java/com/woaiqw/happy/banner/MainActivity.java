package com.woaiqw.happy.banner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.qwer.banner.Banner;
import com.qwer.banner.ImageEngine;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Banner banner = findViewById(R.id.auto_bid_detail_banner);
        banner.setIndicatorGravity(Gravity.CENTER);
        banner.dimissLoadingView();
        banner.setImageLoader(new ImageEngine() {
            @Override
            public void displayImage(Context context, Object o, View view) {
                ImageLoader.loadImage(context, (ImageView) view, (Integer) o);
            }

            @Override
            public View createImageView(Context context) {
                return new ImageView(context);
            }
        });
        Integer[] list = new Integer[]{R.mipmap.detail_banner_0, R.mipmap.detail_banner_1};
        banner.setImages(Arrays.asList(list));
        banner.start();
    }
}
