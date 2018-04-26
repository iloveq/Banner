# Banner
a simple banner for android , thanks all githubor
使用：
a banner for android
```
 Banner banner = getView(R.id.cycleBanner);
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(urls);
            banner.start();
```
解析图片：
```
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context.getApplicationContext())
                .load(path)
                .into(imageView);

    }

}
```
