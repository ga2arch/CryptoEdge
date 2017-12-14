package com.ga2arch.bitcoinedge.actor.main;

import android.content.ComponentName;

import com.ga2arch.bitcoinedge.R;
import com.ga2arch.bitcoinedge.actor.coinmarketcap.CoinMarketCapActor;
import com.ga2arch.bitcoinedge.actor.main.bean.CoinBean;
import com.ga2arch.bitcoinedge.actor.main.bean.CoinsBean;
import com.ga2arch.bitcoinedge.actor.main.request.GetCoinsRequest;
import com.ga2arch.bitcoinedge.application.BitcoinEdgeApp;
import com.ga2arch.bitcoinedge.cocktail.CocktailListAdapterProvider;
import com.ga2arch.bitcoinedge.persistence.DatabaseService;
import com.gabriele.actor.internals.AbstractActor;
import com.gabriele.actor.internals.ActorRef;
import com.gabriele.actor.internals.Props;
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailManager;

import javax.inject.Inject;

public class CoinActor extends AbstractActor {
    private static final String TAG = "MarketCapActor";
    private static final String ENDPOINT = "https://api.coinmarketcap.com/v1/ticker/?convert=EUR";

    @Inject DatabaseService databaseService;
    private ActorRef coinMarketCapActorRef;

    @Override
    public void preStart() {
        super.preStart();
        ((BitcoinEdgeApp) getContext()).getAppComponent().inject(this);
        coinMarketCapActorRef = getActorContext().actorOf(Props.create(CoinMarketCapActor.class));
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof GetCoinsRequest) {
            coinMarketCapActorRef.tell(o, getSelf());

        } else if (o instanceof CoinsBean) {
            onCoinsBean((CoinsBean) o);
        }
    }

    private void onCoinsBean(CoinsBean coinsBean) {
        databaseService.clearData();
        for (CoinBean coinBean: coinsBean.getCoins()) {
            databaseService.addData(coinBean);
        }

        SlookCocktailManager mgr = SlookCocktailManager.getInstance(getContext());
        int[] cocktailIds = mgr.getCocktailIds(new ComponentName(getContext(), CocktailListAdapterProvider.class));
        for (int cocktailId : cocktailIds) {
            mgr.notifyCocktailViewDataChanged(cocktailId, R.id.widgetlist);
        }
    }

}
