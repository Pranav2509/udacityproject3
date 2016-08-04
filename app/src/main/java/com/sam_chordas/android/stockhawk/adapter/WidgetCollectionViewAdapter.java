package com.sam_chordas.android.stockhawk.adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.domains.StockDomain;
import com.sam_chordas.android.stockhawk.ui.ChartActivity;

import java.util.ArrayList;

/**
 * Created by Pranav on 27/06/16.
 */
    public class WidgetCollectionViewAdapter implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetCollection";
    Context mContext;
    ArrayList<StockDomain> stockList = new ArrayList<StockDomain>();

    public WidgetCollectionViewAdapter(Context context){

        this.mContext = context;

    }

    @Override
    public void onCreate() {
        //getDataToBeDisplayed
        Cursor cursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);

        stockList.clear();
        while (cursor.moveToNext()){
            Log.i("LV","in movetonext item" + cursor.getPosition());
            StockDomain stockDomain = new StockDomain();
            stockDomain.setStock_name(cursor.getString(cursor.getColumnIndex("symbol")));
            stockDomain.setRate(cursor.getString(cursor.getColumnIndex("bid_price")));
            stockDomain.setStock_percent(cursor.getString(cursor.getColumnIndex("percent_change")));
            stockList.add(stockDomain);
        }
    }

    @Override
    public void onDataSetChanged() {
        //getDataToBeDisplayed

        Cursor cursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, null, null, null);

        stockList.clear();
        while (cursor.moveToNext()){

            Log.i("LV","in movetonext item in ondataset changed" + cursor.getPosition());

            StockDomain stockDomain = new StockDomain();
            stockDomain.setStock_name(cursor.getString(cursor.getColumnIndex("symbol")));
            stockDomain.setRate(cursor.getString(cursor.getColumnIndex("bid_price")));
            stockDomain.setStock_percent(cursor.getString(cursor.getColumnIndex("percent_change")));
            stockList.add(stockDomain);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.i("LV","in getCount");
        return stockList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Log.i("LV","in getViewAt");

        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.list_item_quote);
        view.setTextViewText(R.id.stock_symbol, stockList.get(position).getStock_name());
        view.setTextViewText(R.id.bid_price, stockList.get(position).getRate());
        view.setTextViewText(R.id.change, stockList.get(position).getStock_percent());

        Intent in = new Intent(mContext, ChartActivity.class);
        in.putExtra("stock_symbol", stockList.get(position).getStock_name());
        Log.i(TAG, stockList.get(position).getStock_name());
        //PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, in, 0);
        view.setOnClickFillInIntent(R.id.base_ll, in);

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
