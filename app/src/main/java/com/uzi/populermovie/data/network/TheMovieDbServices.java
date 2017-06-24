package com.uzi.populermovie.data.network;


import com.uzi.populermovie.data.config.MovieConfig;
import com.uzi.populermovie.data.model.MovieResponse;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by uzi on 24/06/17.
 * Email : fauzisholichin@gmail.com
 */


public interface TheMovieDbServices {

    @GET("movie/popular")
    Observable<MovieResponse> getPopularMovie(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRatedMovie(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    class Creator {
        public static TheMovieDbServices instance() {
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            final TheMovieDbInterceptor theMovieDbInterceptor = new TheMovieDbInterceptor();

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(theMovieDbInterceptor)
                    .build();

            final Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(MovieConfig.THEMOVIEDB_API)
                    .build();
            return retrofit.create(TheMovieDbServices.class);
        }
    }
}
