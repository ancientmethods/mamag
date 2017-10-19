package com.mamags.mamag.di.component;

import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.di.module.ApiModule;
import com.mamags.mamag.di.module.AppModule;
import com.mamags.mamag.di.module.NetworkModule;
import com.mamags.mamag.ui.MainActivity;
import com.mamags.mamag.ui.MenusListFragment;
import com.mamags.mamag.viewmodel.MenuViewModel;


import javax.inject.Singleton;

import dagger.Component;

/**
 *
 *
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, ApiModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
    void inject(MenusListFragment fragment);



    //void inject(MenuViewModel menuViewModel);

}
