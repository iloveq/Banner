package com.woaiqw.happy.banner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;

import com.qwer.banner.Banner;
import com.woaiqw.happy.banner.glide.ImageLoader;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Banner banner = findViewById(R.id.banner);
        banner.setIndicatorGravity(Gravity.CENTER);
        Integer[] list = new Integer[]{R.mipmap.detail_banner_0, R.mipmap.detail_banner_1};
        //String[] list = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543559421206&di=850cfc255fdd0615cecf67831fb3f416&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F69%2F99%2F66%2F9fce5755f081660431464492a9aeb003.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543559479847&di=2ffebc403e887694a70ab862a2a32f90&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F17%2F01%2F15%2F51V58PICNre_1024.jpg"};
        banner.setImages(Arrays.asList(list));

        banner.setImageLoader(new com.qwer.banner.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoader.loadImage(context, imageView, (Integer) path);
            }
        });

        banner.start();
    }
}
