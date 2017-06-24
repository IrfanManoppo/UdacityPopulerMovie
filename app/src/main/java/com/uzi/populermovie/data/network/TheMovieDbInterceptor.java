package com.uzi.populermovie.data.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */


public class TheMovieDbInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        final HttpUrl url = chain.request()
                .url()
                .newBuilder()
                .build();
        final Request request = chain.request().newBuilder().url(url).build();
        return chain.proceed(request);
    }

}
