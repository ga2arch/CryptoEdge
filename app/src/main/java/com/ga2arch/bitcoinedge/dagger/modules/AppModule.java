package com.ga2arch.bitcoinedge.dagger.modules;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

import com.ga2arch.bitcoinedge.persistence.DatabaseService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    static OkHttpClient providesOkHttp() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    static DatabaseService providesDatabaseService() {
        return new DatabaseService();
    }

    @Provides
    @Singleton
    static Gson providesGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    static AlarmManager providesAlarmManager(Context context) {
        return (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }


}