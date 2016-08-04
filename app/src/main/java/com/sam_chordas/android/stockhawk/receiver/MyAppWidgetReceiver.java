package com.sam_chordas.android.stockhawk.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.RemoteWidgetViewService;
import com.sam_chordas.android.stockhawk.ui.ChartActivity;

/**
 * Created by Pranav on 27/06/16.
 */
public class MyAppWidgetReceiver extends AppWidgetProvider{

    private int randomNumber = 1;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_stocks_layout);

        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, RemoteWidgetViewService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_stocks_layout);
            rv.setRemoteAdapter(appWidgetId, R.id.widget_lv, intent);
            rv.setEmptyView(R.id.widget_lv, R.id.empty_tv);
            Intent in = new Intent(context, ChartActivity.class);

            Intent startActivityIntent = new Intent(context, ChartActivity.class);
            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context,
                    0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widget_lv, startActivityPendingIntent);


            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, in, 0);
            rv.setOnClickPendingIntent(R.id.stock_symbol, pendingIntent);


            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }

    }
}
