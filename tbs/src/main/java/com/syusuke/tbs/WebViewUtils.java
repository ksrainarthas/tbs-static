package com.syusuke.tbs;

import android.annotation.SuppressLint;
import android.content.Context;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by  on 2021/5/31.
 */
public class WebViewUtils {
    private static WebViewUtils mWebViewUtils;
    private Context mContext;

    public WebViewUtils(Context context) {
        mContext = context;
    }

    /**
     * 全局单例,只创建一次,全局复用
     *
     * @param context
     * @return
     */
    public static WebViewUtils getInstance(Context context) {
        if (mWebViewUtils == null) {
            synchronized (WebViewUtils.class) {
                if (mWebViewUtils == null) {
                    mWebViewUtils = new WebViewUtils(context);
                }
            }
        }
        return mWebViewUtils;
    }

    /**
     * 初始化WebSettings
     *
     * @param webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void initWebSettings(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //存储路径
        String cacheDirPath = mContext.getFilesDir().getAbsolutePath();

        //UA标识
//        webSettings.setUserAgentString("app/idealClass");

        //提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        //支持插件,播放flash
        webSettings.setPluginsEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        //存储(storage)
        //启用HTML5 DOM storage API,默认值 false
        webSettings.setDomStorageEnabled(true);
        //启用Web SQL Database API,这个设置会影响同一进程内的所有WebView,默认值 false
        //此API已不推荐使用,参考:https://www.w3.org/TR/webdatabase/
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(cacheDirPath);

        //启用Application Caches API,必需设置有效的缓存路径才能生效,默认值 false
        //此API已废弃,参考:https://developer.mozilla.org/zh-CN/docs/Web/HTML/Using_the_application_cache
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(cacheDirPath);
        //定位(location)
        webSettings.setGeolocationEnabled(true);

        //是否保存表单数据
        webSettings.setSaveFormData(true);
        //是否当WebView调用requestFocus时为页面的某个元素设置焦点,默认值 true
        webSettings.setNeedInitialFocus(true);

        //是否支持viewport属性,默认值 false
        //页面通过`<meta name="viewport" ... />`自适应手机屏幕
        webSettings.setUseWideViewPort(true);
        //是否使用overview mode加载页面,默认值 false
        //当页面宽度大于WebView宽度时,缩小使页面宽度等于WebView宽度
        webSettings.setLoadWithOverviewMode(true);
        //自适应屏幕,布局算法
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //是否支持Javascript,默认值false,必须设置,最重要
        webSettings.setJavaScriptEnabled(true);
        //是否支持多窗口,默认值false
        webSettings.setSupportMultipleWindows(false);
        //是否可用Javascript(window.open)打开窗口,默认值 false
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //资源访问
        webSettings.setAllowContentAccess(true); //是否可访问Content Provider的资源,默认值 true
        webSettings.setAllowFileAccess(true);    //是否可访问本地文件,默认值 true
        //是否允许通过file url加载的Javascript读取本地文件,默认值 false
        webSettings.setAllowFileAccessFromFileURLs(false);
        //是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https),默认值 false
        webSettings.setAllowUniversalAccessFromFileURLs(false);

        //WebView是否下载图片资源,默认为true。注意,该方法控制所有图片的下载,包括使用URI嵌入的图片
        //(使用setBlockNetworkImage(boolean) 只控制使用网络URI的图片的下载)。
        //如果该设置项的值由false变为true,WebView展示的内容所引用的所有的图片资源将自动下载
        webSettings.setLoadsImagesAutomatically(false); //是否自动加载图片

        //缩放(zoom)
        webSettings.setSupportZoom(true);          //是否支持缩放
        webSettings.setBuiltInZoomControls(true); //是否使用内置缩放机制
        webSettings.setDisplayZoomControls(false);  //是否显示内置缩放控件

        //5.0以上允许加载http和https混合的页面(5.0以下默认允许,5.0+默认禁止)
        //解决5.0以后的WebView加载的链接为Https开头,但是链接里面的内容,比如图片为Http链接加载不出来问题
        webSettings.setMixedContentMode(webSettings.getMixedContentMode());

        if (NetworkUtils.isNetworkConnected(mContext)) {
            //根据cache-control(网页决定)决定是否从网络上取数据
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //没网,离线加载,优先加载缓存(即使已经过期)
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }
}