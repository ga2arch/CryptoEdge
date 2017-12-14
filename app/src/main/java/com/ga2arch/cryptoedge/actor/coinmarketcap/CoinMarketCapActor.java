package com.ga2arch.cryptoedge.actor.coinmarketcap;

import android.util.Log;

import com.ga2arch.cryptoedge.actor.main.bean.CoinBean;
import com.ga2arch.cryptoedge.actor.main.bean.CoinsBean;
import com.ga2arch.cryptoedge.actor.main.bean.CoinsErrorBean;
import com.ga2arch.cryptoedge.actor.main.request.GetCoinsRequest;
import com.ga2arch.cryptoedge.application.BitcoinEdgeApp;
import com.gabriele.actor.internals.AbstractActor;
import com.gabriele.actor.internals.ActorRef;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CoinMarketCapActor extends AbstractActor {
    private static final String TAG = "MarketCapActor";
    private static final String ENDPOINT = "https://api.coinmarketcap.com/v1/ticker/?convert=EUR";

    @Inject OkHttpClient client;
    @Inject Gson gson;

    @Override
    public void preStart() {
        super.preStart();
        ((BitcoinEdgeApp) getContext()).getAppComponent().inject(this);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof GetCoinsRequest) {
            onGetCoinsRequest((GetCoinsRequest) o);
        }
    }

    private void onGetCoinsRequest(GetCoinsRequest getCoinsRequest) {
        Log.i(TAG, "getting home: " + getCoinsRequest);
        final ActorRef sender = getSender();
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onGetCoinsFailure(sender, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        onGetCoinsSuccess(sender, response);
                    }
                });
    }

    private void onGetCoinsSuccess(ActorRef sender, Response response) {
        Log.i(TAG, "got coins, processing");

        try (ResponseBody responseBody = response.body()) {
            if (!response.isSuccessful())
                onGetCoinsFailure(sender, new IOException("Unexpected code " + response));

            try {
                String body = responseBody.string();
                List<CoinBean> coins = gson.fromJson(body, new TypeToken<List<CoinBean>>(){}.getType());
                Log.d(TAG, "coins: " + coins);
                sender.tell(new CoinsBean(coins), getSelf());

            } catch (IOException e) {
                onGetCoinsFailure(sender, e);
            }
        }
    }

    private void onGetCoinsFailure(ActorRef sender, IOException e) {
        Log.e(TAG, "failure getting coins: " + e.getMessage());
        sender.tell(new CoinsErrorBean(e.getMessage()), getSelf());
    }
}
