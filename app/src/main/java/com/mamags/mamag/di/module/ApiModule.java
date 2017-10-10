package com.mamags.mamag.di.module;

import com.mamags.mamag.api.RestAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 *
 *
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    RestAPI provideRetrofit(Retrofit retrofit) {
        return retrofit.create(RestAPI.class);
    }

}
