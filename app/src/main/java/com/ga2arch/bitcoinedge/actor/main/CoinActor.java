package com.ga2arch.bitcoinedge.actor.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.SystemClock;

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

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class CoinActor extends AbstractActor {
    private static final String TAG = "CoinActor";
    public static final String INTENT_REFRESH_COINS = "com.ga2arch.refresh.coins";

    @Inject DatabaseService databaseService;
    @Inject AlarmManager alarmManager;

    private ActorRef coinMarketCapActorRef;

    @Override
    public void preStart() {
        super.preStart();
        ((BitcoinEdgeApp) getContext()).getAppComponent().inject(this);
        coinMarketCapActorRef = getActorContext().actorOf(Props.create(CoinMarketCapActor.class));

        startPolling();
    }

    private void startPolling() {
        getSelf().tell(new GetCoinsRequest(), getSelf());
        getEventBus().subscribe(INTENT_REFRESH_COINS, getSelf());

        Intent intent = new Intent(INTENT_REFRESH_COINS);
        alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                TimeUnit.MINUTES.toMillis(10),
                PendingIntent.getBroadcast(getContext(), 0, intent, 0));
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof GetCoinsRequest) {
            coinMarketCapActorRef.tell(o, getSelf());

        } else if (o instanceof CoinsBean) {
            onCoinsBean((CoinsBean) o);

        } else if (o instanceof Intent) {
            onIntent((Intent) o);
        }
    }

    private void onIntent(Intent intent) {
        switch (intent.getAction()) {
            case INTENT_REFRESH_COINS:
                getSelf().tell(new GetCoinsRequest(), getSelf());
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
