
package com.ga2arch.bitcoinedge.cocktail;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.ga2arch.bitcoinedge.R;
import com.ga2arch.bitcoinedge.actor.main.bean.CoinBean;
import com.ga2arch.bitcoinedge.application.BitcoinEdgeApp;
import com.ga2arch.bitcoinedge.persistence.DatabaseService;
import com.gabriele.actor.internals.ActorRef;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.inject.Named;

public class CocktailListAdapterFactory implements RemoteViewsFactory {
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

        CoinBean coinBean = (CoinBean) databaseService.getData(position);

        contentView.setTextViewText(R.id.currencyName, coinBean.getName());
        contentView.setTextViewText(R.id.amount,
                String.format("%s€", coinBean.getPriceEur().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        contentView.setTextViewText(R.id.diff1h,
                String.format("%s%%", coinBean.getPercentChange1h().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        contentView.setTextViewText(R.id.diff24h,
                String.format("%s%%", coinBean.getPercentChange24h().setScale(2, BigDecimal.ROUND_HALF_EVEN)));

        int green = Color.parseColor("#C8E6C9");
        int red = Color.parseColor("#FFCDD2");

        if (coinBean.getPercentChange1h().compareTo(BigDecimal.ZERO) > 0) {
            contentView.setInt(R.id.diff1h, "setBackgroundColor", green);

        } else {
            contentView.setInt(R.id.diff1h, "setBackgroundColor", red);
        }

        if (coinBean.getPercentChange24h().compareTo(BigDecimal.ZERO) > 0) {
            contentView.setInt(R.id.diff24h, "setBackgroundColor", green);

        } else {
            contentView.setInt(R.id.diff24h, "setBackgroundColor", red);
        }

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

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

}
