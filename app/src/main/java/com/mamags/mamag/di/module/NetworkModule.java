package com.mamags.mamag.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mamags.mamag.api.AutoValueGsonFactory;
import com.mamags.mamag.constants.MamaConstants;


import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 *
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                // All timestamps are returned in ISO 8601 format:
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                // Blank fields are included as null instead of being omitted.
                .serializeNulls()
                .create();
        //return new GsonBuilder().serializeNulls().registerTypeAdapterFactory(AutoValueGsonFactory.create()).create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().
                readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15,TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory factory) {
        return new Retrofit.Builder()
                .baseUrl(MamaConstants.API_URL)
                .client(okHttpClient)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
