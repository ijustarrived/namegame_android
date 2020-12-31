package com.willowtreeapps.namegame.core;

import com.willowtreeapps.namegame.network.NetworkModule;
import com.willowtreeapps.namegame.MainActivity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {
    void inject(MainActivity activity);
}