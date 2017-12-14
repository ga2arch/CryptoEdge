
package com.ga2arch.cryptoedge.cocktail;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.ga2arch.cryptoedge.R;
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailManager;
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailProvider;

import static com.ga2arch.cryptoedge.actor.main.CoinActor.INTENT_REFRESH_COINS;

public class CocktailListAdapterProvider extends SlookCocktailProvider {
    private static final String TAG = "CocktailListAdapterProvider";

    @Override
    public void onUpdate(Context context, SlookCocktailManager cocktailManager, int[] cocktailIds) {
        Intent intent = new Intent(context, CocktailListAdapterService.class);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        views.setRemoteAdapter(R.id.widgetlist, intent);
        views.setEmptyView(R.id.widgetlist, R.id.emptylist);

        Intent refreshintent = new Intent(INTENT_REFRESH_COINS);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0xff, refreshintent, PendingIntent.FLAG_UPDATE_CURRENT);
        SlookCocktailManager.getInstance(context).setOnPullPendingIntent(cocktailIds[0], R.id.widgetlist, pendingIntent);

        for (int cocktailId : cocktailIds) {
            cocktailManager.updateCocktail(cocktailId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled");
    }
}
