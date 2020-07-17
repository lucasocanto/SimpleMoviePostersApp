package com.example.moviesapp.http;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TopMoviesModule {

    public final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    public final String API_KEY = "1d2bb2c7fac80ecd75c4f2904c9a930e";

    @Provides
    public OkHttpClient provideClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("api_key", API_KEY)
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .build();
    }

    @Provides
    public Retrofit providesRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public TopMoviesService providesApiService(){
        return providesRetrofit(BASE_URL, provideClient()).create(TopMoviesService.class);
    }
}
