# Banner
1：简化了 young-banner
2：添加了通过 ViewFilpper 自定义banner
a simple banner for android , thanks young
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		 repositories {
			 ...
			 maven { url 'https://jitpack.io' }
		}
}
```
 Step 2. Add the dependency
```
dependencies {
	        compile 'com.github.woaigmz:Banner:v1.0.0'
	}
```
 
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
