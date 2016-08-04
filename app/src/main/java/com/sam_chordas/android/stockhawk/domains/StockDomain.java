package com.sam_chordas.android.stockhawk.domains;

/**
 * Created by Pranav on 03/07/16.
 */
public class StockDomain {

    String stock_name;
    String rate;
    String stock_percent;

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStock_percent() {
        return stock_percent;
    }

    public void setStock_percent(String stock_percent) {
        this.stock_percent = stock_percent;
    }
}
