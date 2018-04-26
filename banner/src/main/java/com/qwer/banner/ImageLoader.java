package com.qwer.banner;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by woaigmz on 2018/1/17.
 */

public abstract class ImageLoader implements ImageLoaderInterface<ImageView> {

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }

}