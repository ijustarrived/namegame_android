package com.willowtreeapps.namegame.core;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;

import com.willowtreeapps.namegame.network.NetworkModule;
import com.willowtreeapps.namegame.MainMenu.FragHelper.FragHelper;

public class NameGameApplication extends Application {

    private ApplicationComponent component;

    public static NameGameApplication get(@NonNull Context context) {
        return (NameGameApplication) context.getApplicationContext();
    }

    private FragHelper frag;

    public FragHelper GetFrag()
    {
        return frag;
    }

    public void SetFrag(FragHelper frag)
    {
        this.frag = frag;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    public ApplicationComponent component() {
        return component;
    }

    protected ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule("https://willowtreeapps.com/"))
                .build();
    }
}
