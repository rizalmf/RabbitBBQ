/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.service;

import com.rabbitbbq.model.Factory;
import com.rabbitbbq.model.Queue;
import com.rabbitbbq.util.Session;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rizal
 */
public class BBQ {

    private static Connection connection;
    public BBQ() {
    }
    
    public void listen(DeliverCallback deliverCallback) throws Exception{
        if (connection != null) {
            connection.close();
            connection = null;
        }
        
        connection = getFactory().newConnection();
        Channel channel = connection.createChannel();
        
        Queue queueData = new Session().getQueue();
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
    
    public void publish(Queue queueData, String message) throws Exception{        
        try (Connection connectionTemp = getFactory().newConnection()) {
            Channel channel = connectionTemp.createChannel();
            String queue = queueData.getName();
            String exchange = queueData.getExchange();
            String routingKey = queueData.getRoutingKey();
            boolean durable = queueData.isDurable();
            boolean exclusive = queueData.isExclusive();
            boolean autoDelete = queueData.isAutoDelete();
            channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
            
//            temp disable headers & properties
//            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
//            Map<String,Object> headerMap = new HashMap<>();
//            headerMap.put(key,value)
//            headerMap.put(key1,value1)
//            headerMap.put(key2,value2)
//            builder.headers(headerMap);

            channel.basicPublish(exchange, routingKey, null, message.getBytes());
        }
    }
    
    private ConnectionFactory getFactory(){
        Session session = new Session();
        Factory factoryData = session.getFactory();
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(factoryData.getHost());
        factory.setPort(factoryData.getPort());
        factory.setUsername(factoryData.getUsername());
        factory.setPassword(factoryData.getPassword());
        factory.setVirtualHost(factoryData.getVirtualHost());
        
        return factory;
    }

}
