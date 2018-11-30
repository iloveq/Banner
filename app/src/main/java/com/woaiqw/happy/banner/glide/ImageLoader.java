package com.woaiqw.happy.banner.glide;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Created by haoran on 2017/4/20.
 * 图片加载
 * Glide的二次封装
 */

public class ImageLoader {

    private static float defaultScale = 0.75f;

    private ImageLoader() {
        throw new IllegalStateException(" cannot to new the Object ");
    }


    /**
     * 设置gif图片
     * 不设置占位可降低内存占用
     * 修改缓存策略
     */
    public static void setGif(Context context, ImageView imageView, Object picResource) {
        GlideApp.with(context)
                .asGif()
                .load(picResource)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView);
    }


    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        loadImage(context, imageView, imageUrl);

    }


    public static void loadImageWithPlaceHolder(ImageView imageView, String imageUrl, @DrawableRes int placeholderRes, @DrawableRes int errorRes) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        loadImageWithPlaceHolder(context, imageView, imageUrl, placeholderRes, errorRes);

    }


    public static void loadCircleImage(ImageView imageView, String imageUrl) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        loadCircleImage(context, imageView, imageUrl);

    }


    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        try {
            GlideApp.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImage(Context context, ImageView imageView, Integer imageUrl) {
        try {
            GlideApp.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadImageWithPlaceHolder(Context context, ImageView imageView, String imageUrl, @DrawableRes int placeholderRes, @DrawableRes int errorRes) {
        try {
            GlideApp.with(context)
                    .load(imageUrl)
                    .placeholder(placeholderRes)
                    .error(errorRes == 0 ? placeholderRes : errorRes)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadCircleImage(Context context, ImageView imageView, String imageUrl) {
        try {
            GlideApp.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .dontAnimate()
                    .transform(new CircleImageTransform())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadImageWithSize(Context context, ImageView imageView, String imageUrl, int width, int height, float scale) {
        try {
            GlideApp.with(context)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .thumbnail(scale == 0 ? defaultScale : scale)
                    .override(width, height)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageWithSize(Context context, ImageView imageView, File file, int width, int height, float scale) {
        try {
            GlideApp.with(context)
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .thumbnail(scale == 0 ? defaultScale : scale)
                    .override(width, height)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageWithSize(Context context, ImageView imageView, Uri uri, int width, int height, float scale) {
        try {
            GlideApp.with(context)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .thumbnail(scale == 0 ? defaultScale : scale)
                    .override(width, height)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadImageWithUri(Context context, ImageView imageView, Uri uri) {
        try {
            GlideApp.with(context)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageWithPath(Context context, ImageView imageView, String path) {
        try {
            GlideApp.with(context)
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageWithFile(Context context, ImageView imageView, File file) {
        try {
            GlideApp.with(context)
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
