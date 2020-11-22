/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.model;

import java.util.Map;

/**
 *
 * @author rizal
 */
public class Message {
    private String date;
    private String payload;
    private Map<String,Object> headers;
    private Map<String,Object> properties;

    public Message() {
    }

    public Message(String date, String payload) {
        this.date = date;
        this.payload = payload;
    }

    public Message(String payload, Map<String, Object> headers, Map<String, Object> properties) {
        this.payload = payload;
        this.headers = headers;
        this.properties = properties;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
    
}
