/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.controller;

import com.jfoenix.controls.JFXButton;
import com.rabbitbbq.controller.additional.MainBuilder;
import com.rabbitbbq.exception.Exceptions;
import com.rabbitbbq.model.Message;
import com.rabbitbbq.model.Queue;
import com.rabbitbbq.service.BBQ;
import com.rabbitbbq.util.Session;
import com.rabbitmq.client.DeliverCallback;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author rizal
 */
public class MainController implements Initializable {
    
    private final Exceptions exceptions =  new Exceptions(this.getClass());
    private SimpleDateFormat sdf;
    private String githubTooltip;
    private String clearTooltip;
    private String configTooltip;
    private MainBuilder builder;
    
    @FXML
    private AnchorPane parent;
    @FXML
    private JFXButton bGithub;
    @FXML
    private Label lInfo;
    @FXML
    private JFXButton bClear;
    @FXML
    private JFXButton bConfig;
    @FXML
    private ScrollPane spMessage;
    @FXML
    private VBox vbMessage;
    @FXML
    private HBox headerBox;
    @FXML
    private HBox bodyBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        builder = new MainBuilder();
        sdf = new SimpleDateFormat("HH:mm:ss dd MMM YY");
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            nodeInitiation();
        });
    } 
        
    private void nodeInitiation() {
        //tooltip
        githubTooltip = "Fork me on github";
        clearTooltip = "Clear history";
        configTooltip = "Set configuration";
        
        headerProperties();
        bodyProperties();
    }
    private void headerProperties(){        
        //set tooltip nodes
        bGithub.setTooltip(new Tooltip(githubTooltip));
        bGithub.setOnAction((e) -> { builder.github();});
    }
    private void bodyProperties(){
        bClear.setTooltip(new Tooltip(clearTooltip));
        bClear.setOnAction((e) -> {
            vbMessage.getChildren().clear();
        });
        
        bConfig.setTooltip(new Tooltip(configTooltip));
        bConfig.setOnAction((e) -> {
            builder.openConfig();
            grillBBQ();
        });
        vbMessage.getChildren().clear();
    }
    
    private void grillBBQ(){
        Session session = new Session();
        Queue queue = session.getQueue();
        if (queue != null) {
            try {
                setInfo(queue.toString());
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    try {
                        processMessage(message);
                    } catch (Exception e) {
                        exceptions.log(e);
                        setInfo(e.getMessage());
                    }
                };
                new BBQ().listen(deliverCallback);
            } catch (Exception e) {
                exceptions.log(e);
                setInfo(e.getMessage());
            }
        }else setInfo("null");
        
    }
    private void setInfo(String msg){
        Platform.runLater(() -> {
            lInfo.setText(msg);
            lInfo.setTooltip(new Tooltip(msg));
        });
    }
    private void processMessage(String msg){
        Platform.runLater(() -> {
            vbMessage.getChildren().add(0, builder.buildMessage(
                new Message(sdf.format(new Date()), msg)));
        });
    }
}
