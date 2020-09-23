package com.example.fight_corona;

public class modelstate
{
    public String active,confirmed,deaths,state;

    public modelstate(String active, String confirmed, String deaths,String state)
    {
        this.active = active;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.state=state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }
}
