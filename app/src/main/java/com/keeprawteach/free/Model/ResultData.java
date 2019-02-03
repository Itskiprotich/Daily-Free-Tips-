package com.keeprawteach.free.Model;

public class ResultData {
    String dates;
    String day;
    String url;

    public ResultData(String dates, String day, String url) {
        this.dates = dates;
        this.day = day;
        this.url = url;
    }

    public ResultData() {
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
