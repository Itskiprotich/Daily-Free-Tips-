package com.keeprawteach.free.Model;

public class Tips {
    String date;
    String game;
    String league;
    String odd;
    String results;
    String score;
    String time;
    String tip;

    public Tips(String date, String game, String league, String odd, String results, String score, String time, String tip) {
        this.date = date;
        this.game = game;
        this.league = league;
        this.odd = odd;
        this.results = results;
        this.score = score;
        this.time = time;
        this.tip = tip;
    }

    public Tips() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
