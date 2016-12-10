package com.trebuh.clarity.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Singleton for performance reasons
public class RetrofitService  {
    private Retrofit retrofit;
    private FiveHundredPxService service;

    private RetrofitService() {
        // Add logging
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // Add api key to every request
        Interceptor apiKeyInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                // Add consumer key to every request
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(wtf.PARAM_CONSUMER_KEY, wtf.CONSUMER_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor).addInterceptor(apiKeyInterceptor).build();

        retrofit = new Retrofit.Builder().
                baseUrl(wtf.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(FiveHundredPxService.class);
    }

    public FiveHundredPxService getService() {
        return service;
    }

    // Initialization-on-demand holder idiom
    // https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class LazyHolder {
        private static final RetrofitService INSTANCE = new RetrofitService();
    }

    public static RetrofitService getInstance() {
        return LazyHolder.INSTANCE;
    }
}
