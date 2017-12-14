package com.ga2arch.bitcoinedge.dagger.modules;

import android.content.Context;

import com.ga2arch.bitcoinedge.actor.main.CoinActor;
import com.gabriele.actor.android.ActorApplication;
import com.gabriele.actor.internals.ActorRef;
import com.gabriele.actor.internals.ActorSystem;
import com.gabriele.actor.internals.Props;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ActorModule {

    @Provides
    @Singleton
    ActorSystem providesActorSystem(Context context) {
        return ((ActorApplication) context).getSystem();
    }

    @Provides
    @Singleton
    @Named("CoinActor")
    ActorRef providesCoinActor(ActorSystem system) {
        return system.actorOf(Props.create(CoinActor.class));
    }

}