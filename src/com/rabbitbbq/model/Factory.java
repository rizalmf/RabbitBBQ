/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.model;

/**
 *
 * @author rizal
 */
public class Factory {
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;

    public Factory(String host, int port, String username, String password, String virtualHost) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.virtualHost = virtualHost;
    }

    public Factory() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }
        
}
