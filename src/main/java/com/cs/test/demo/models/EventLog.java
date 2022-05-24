package com.cs.test.demo.models;

import com.cs.test.demo.enums.LogType;
import com.cs.test.demo.enums.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventLog {

    @JsonProperty("id")
    private String id;

    @JsonProperty("timestamp")
    private long eventTime;

    @JsonIgnore
    @JsonProperty("host")
    private String host;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    @JsonProperty("state")
    private State state;

    @JsonIgnore
    @JsonProperty("type")
    private LogType type;


}
