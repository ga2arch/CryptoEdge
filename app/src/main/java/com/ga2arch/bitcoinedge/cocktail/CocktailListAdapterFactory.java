
package com.ga2arch.bitcoinedge.cocktail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.ga2arch.bitcoinedge.R;
import com.ga2arch.bitcoinedge.actor.main.bean.CoinBean;
import com.ga2arch.bitcoinedge.actor.main.request.GetCoinsRequest;
import com.ga2arch.bitcoinedge.application.BitcoinEdgeApp;
import com.ga2arch.bitcoinedge.persistence.DatabaseService;
import com.gabriele.actor.internals.ActorRef;

import javax.inject.Inject;
import javax.inject.Named;

public class CocktailListAdapterFactory extends BroadcastReceiver implements RemoteViewsFactory {
    static final String TAG = "CocktailListAdapter ";

    @Inject Context mContext;
    @Inject DatabaseService databaseService;
    @Inject @Named("CoinActor") ActorRef coinActor;

    public CocktailListAdapterFactory(Context context) {
        Log.d(TAG, "CocktailListAdapterFactory constructor ");
        ((BitcoinEdgeApp) context).getAppComponent().inject(this);
    }

    @Override
    public int getCount() {
        return databaseService.getSize();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        CoinBean data = (CoinBean) databaseService.getData(position);
        String text = String.format("%s\n%s€\n1h: %s%%\n24h: %s%%", data.getName(),
                data.getPriceEur(), data.getPercentChange1h(), data.getPercentChange24h());

        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, data.getName().length(), 0);

        int color1h = data.getPercentChange1h().startsWith("-") ? Color.RED : Color.GREEN;
        spanString.setSpan(new ForegroundColorSpan(color1h), data.getName().length()+2+data.getPriceEur().length()+5,
                data.getName().length()+2+data.getPriceEur().length()+5+data.getPercentChange1h().length(), 0);

        int color24h = data.getPercentChange24h().startsWith("-") ? Color.RED : Color.GREEN;
        spanString.setSpan(new ForegroundColorSpan(color24h),
                data.getName().length()+2+data.getPriceEur().length()+5+data.getPercentChange1h().length()+2+5,
                data.getName().length()+2+data.getPriceEur().length()+5
                        +data.getPercentChange1h().length()+2+5+data.getPercentChange24h().length(), 0);

        contentView.setTextViewText(R.id.currencyName, spanString);
        contentView.setTextViewText(R.id.amount, data.getPriceBtc());

        return contentView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void onCreate() {
        coinActor.tell(new GetCoinsRequest(), null);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("com.ga2arch.refresh");
        mContext.registerReceiver(this, filter);
        mContext.registerReceiver(this, filter1);
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
        mContext.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        coinActor.tell(new GetCoinsRequest(), null);
    }
}
