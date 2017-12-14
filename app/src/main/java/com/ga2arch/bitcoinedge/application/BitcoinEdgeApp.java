package com.ga2arch.bitcoinedge.application;

import com.ga2arch.bitcoinedge.dagger.components.AppComponent;
import com.ga2arch.bitcoinedge.dagger.components.DaggerAppComponent;
import com.ga2arch.bitcoinedge.dagger.modules.ActorModule;
import com.ga2arch.bitcoinedge.dagger.modules.AppModule;
import com.gabriele.actor.android.ActorApplication;

public class BitcoinEdgeApp extends ActorApplication {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .actorModule(new ActorModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}