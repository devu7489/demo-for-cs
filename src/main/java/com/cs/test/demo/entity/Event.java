package com.cs.test.demo.entity;

import com.cs.test.demo.enums.State;

import javax.persistence.*;

@Entity
@Table(name = "FINAL_EVENTS")
public class Event {

    public Event() {
    }
    public Event(String id, long duration, String host) {
        this.id = id;
        this.duration = duration;
        this.host = host;
        this.alert = duration > 4 ? true:false;
    }

    @Id
    private String id;

    private long duration;

    private String host;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
        if(duration > 4){
            this.alert = true;
        }else{
            this.alert = false;
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    private boolean alert;


}
