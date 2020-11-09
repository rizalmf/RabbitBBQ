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
public class Queue {
    private String name;
    private String exchange;
    private String routingKey;
    private boolean durable;
    private boolean exclusive;
    private boolean autoDelete;
    private Map<String, Object> arguments;

    public Queue(String name, String exchange, String routingKey, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
        this.name = name;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
        this.arguments = arguments;
    }

    public Queue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
        
    @Override
    public String toString() {
        return "name:"+this.name
                +" exchange:"+this.exchange
                +" routingKey:"+this.routingKey
                +" durable:"+this.durable
                +" exclusive:"+this.exclusive
                +" autoDelete:"+this.autoDelete;
    }
}
