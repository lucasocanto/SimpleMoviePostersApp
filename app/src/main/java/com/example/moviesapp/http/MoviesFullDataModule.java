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
public class MoviesFullDataModule {

    public final String BASE_URL = "http://www.omdbapi.com/";
    public final String API_KEY = "66915ea1";

    @Provides
    public OkHttpClient provideClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("apikey", API_KEY)
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .build();
    }

    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public MoviesFullDataService provideApiService(){
        return provideRetrofit(BASE_URL, provideClient()).create(MoviesFullDataService.class);
    }
}
