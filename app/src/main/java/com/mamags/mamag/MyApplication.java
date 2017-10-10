package com.mamags.mamag;

/**
 * Created by samer on 10/10/2017.
 */


import android.app.Application;

import com.mamags.mamag.di.component.AppComponent;
import com.mamags.mamag.di.component.DaggerAppComponent;
import com.mamags.mamag.di.module.ApiModule;
import com.mamags.mamag.di.module.AppModule;
import com.mamags.mamag.di.module.NetworkModule;

/**
 * Main application class. Initializes dagger.
 */
public class MyApplication extends Application {

    private static AppComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
