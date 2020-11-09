/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.service;

import com.rabbitbbq.model.Factory;
import com.rabbitbbq.model.Queue;
import com.rabbitbbq.util.Session;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 *
 * @author rizal
 */
public class BBQ {

    private static Connection connection;
    public BBQ() {
    }
    
    public void listen(DeliverCallback deliverCallback) throws Exception{
        Session session = new Session();
        Factory factoryData = session.getFactory();
        Queue queueData = session.getQueue();
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(factoryData.getHost());
        factory.setPort(factoryData.getPort());
        factory.setUsername(factoryData.getUsername());
        factory.setPassword(factoryData.getPassword());
        factory.setVirtualHost(factoryData.getVirtualHost());
        if (connection != null) {
            connection.close();
            connection = null;
        }
        connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String queue = queueData.getName();
        String exchange = queueData.getExchange();
        String routingKey = queueData.getRoutingKey();
        boolean durable = queueData.isDurable();
        boolean exclusive = queueData.isExclusive();
        boolean autoDelete = queueData.isAutoDelete();
        try {
            channel.queueBind(queue, exchange, routingKey);
        } catch (Exception e) {
            System.out.println("cannot bind. try declare");
            channel = connection.createChannel();
            channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
            channel.queueBind(queue, exchange, routingKey);
        }
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> { });
    }
}
