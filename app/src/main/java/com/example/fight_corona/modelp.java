package com.example.fight_corona;

public class modelp
{
        public String State,Phone;

    public modelp(String state, String phone) {
        State = state;
        Phone = phone;
    }

    public String getState() {
        return State;
    }

    public String getPhone() {
        return Phone;
    }

    public void setState(String state) {
        State = state;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
