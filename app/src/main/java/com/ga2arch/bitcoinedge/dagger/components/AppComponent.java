package com.ga2arch.bitcoinedge.dagger.components;

import com.ga2arch.bitcoinedge.MainActivity;
import com.ga2arch.bitcoinedge.actor.coinmarketcap.CoinMarketCapActor;
import com.ga2arch.bitcoinedge.actor.main.CoinActor;
import com.ga2arch.bitcoinedge.cocktail.CocktailListAdapterFactory;
import com.ga2arch.bitcoinedge.dagger.modules.ActorModule;
import com.ga2arch.bitcoinedge.dagger.modules.AppModule;
import com.gabriele.actor.internals.ActorRef;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, ActorModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(CocktailListAdapterFactory cocktailListAdapterFactory);
    void inject(CoinMarketCapActor coinMarketCapActor);
    void inject(CoinActor coinActor);

    @Named("CoinActor")
    ActorRef getCoinActor();
}