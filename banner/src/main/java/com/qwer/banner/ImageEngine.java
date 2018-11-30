package com.qwer.banner;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

/**
 * Created by woaigmz on 2018/1/17.
 */

public interface ImageEngine<T extends View> extends Serializable {

    void displayImage(Context context, Object path, T imageView);

    T createImageView(Context context);
}
