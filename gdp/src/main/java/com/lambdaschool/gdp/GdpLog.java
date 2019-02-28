package com.lambdaschool.gdp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GdpLog implements Serializable {

    private final String text;
    private final String formattedDate;

    public GdpLog(String text) {
        this.text = text;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        formattedDate = dateFormat.format(date);
    }
}
