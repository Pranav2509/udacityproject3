package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.domains.ChartDomain;
import com.sam_chordas.android.stockhawk.rest.RequestQueueSingleton;
import com.sam_chordas.android.stockhawk.rest.ResponseParser;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pranav on 03/07/16.
 */
public class ChartActivity extends Activity {

    private static final String TAG = "ChartActivity";
    ImageView graph_iv;
    String mStockSymbol;
    LineChart line_chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chart_activity);

        Intent in = getIntent();
        mStockSymbol = in.hasExtra("stock_symbol") ? in.getStringExtra("stock_symbol") : null;

        line_chart = (LineChart) findViewById(R.id.linechart);



        if(mStockSymbol!=null)
            loadGraphFromApi(mStockSymbol);


    }

    private void loadGraphFromApi(final String stockSymbol) {
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata" +
                "%20where%20symbol%20%3D%20%22"+stockSymbol+"%22%20and%20startDate%20%3D%20%222009-09-11%22%20" +
                "and%20endDate%20%3D%20%222010-03-10%22&format=json&diagnostics=true&env=store%3A%2F%2F" +
                "datatables.org%2Falltableswithkeys&callback=";

        Log.i(TAG, url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.i(TAG, response);

                    ArrayList<ChartDomain> chartPointsList = ResponseParser.parseChartData(response);

                    ArrayList<Entry> values = new ArrayList<Entry>();


                    for(int i=0; i< chartPointsList.size(); i++){
                        Log.i(TAG,"value" + Float.parseFloat(chartPointsList.get(i).getClosing()) + " : " + i);
                        values.add(new Entry(Float.parseFloat(chartPointsList.get(i).getClosing()), (float)i));
                    }


                    LineDataSet dataset = new LineDataSet(values, "Stock Values over time");
                    dataset.setDrawCircles(true);
                    dataset.setDrawValues(true);
                    LineData data = new LineData(dataset);
                    line_chart.setDescription("Stock Values of " + stockSymbol);
                    line_chart.setData(data);
                    line_chart.animateY(1000);
                    line_chart.invalidate();


                    Log.i(TAG,"Setting line chart");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "on error" + error);
            }
        });

        RequestQueueSingleton.getInstance(getBaseContext()).addToRequestQueue(request);



    }
}
