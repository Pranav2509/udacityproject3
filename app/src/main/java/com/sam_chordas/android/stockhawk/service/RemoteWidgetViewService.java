package com.sam_chordas.android.stockhawk.service;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.adapter.WidgetCollectionViewAdapter;

/**
 * Created by Pranav on 27/06/16.
 */
public class RemoteWidgetViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("LV", "onGetViewFactory");
        return new WidgetCollectionViewAdapter(this);
    }
}
