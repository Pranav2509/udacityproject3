package com.sam_chordas.android.stockhawk.rest;

import com.sam_chordas.android.stockhawk.domains.ChartDomain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pranav on 04/08/16.
 */
public class ResponseParser {

    public static ArrayList<ChartDomain> parseChartData(String response) throws JSONException {

        JSONObject responsejson = new JSONObject(response);
        ArrayList<ChartDomain> closingList = new ArrayList<ChartDomain>();
        ChartDomain chartDomain;

        JSONObject query = responsejson.has("query")  ? responsejson.getJSONObject("query") : null;

        if(query!=null){
            JSONObject results = query.has("results") ? query.getJSONObject("results") : null;
            if(results!=null){
                JSONArray quotes = results.has("quote") ? results.getJSONArray("quote") : null;

                if(quotes!=null){

                    for(int i=0; i<quotes.length(); i++){

                        chartDomain = new ChartDomain();

                        JSONObject singleQuote = quotes.getJSONObject(i);
                        chartDomain.setClosing(singleQuote.getString("Close"));
                        chartDomain.setDate(singleQuote.getString("Date"));

                        closingList.add(chartDomain);
                    }

                }
            }
        }
        return closingList;
    }

}
