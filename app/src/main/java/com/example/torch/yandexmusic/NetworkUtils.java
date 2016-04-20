package com.example.torch.yandexmusic;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/*Утилитарный класс для работы с сетью*/
public class NetworkUtils {
    private static NetworkUtils sNetworkUtils;
    private RequestQueue mRequestQueue;
    private static Context sContext;
    private ImageLoader mImageLoader;

    private NetworkUtils(Context context){
        sContext = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });
    }

    public static synchronized NetworkUtils getInstance(Context context){
        if (sNetworkUtils == null){
            sNetworkUtils = new NetworkUtils(context);
        }
        return sNetworkUtils;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(sContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader(){
        return  mImageLoader;
    }


}
