/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.rabbitbbq.exception.Exceptions;
import com.rabbitbbq.model.Queue;
import static com.rabbitbbq.role.PathRole.PUBLISH_TITLE;
import com.rabbitbbq.service.BBQ;
import com.rabbitbbq.util.Session;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class SubscribeController implements Initializable {

    private final Exceptions exceptions =  new Exceptions(this.getClass());
    private Queue queue;
    
    @FXML
    private JFXTextField tfExchange;
    @FXML
    private JFXTextField tfRoutingKey;
    @FXML
    private JFXTextField tfHeaders;
    @FXML
    private JFXTextField tfProperties;
    @FXML
    private JFXTextArea taPayload;
    @FXML
    private JFXButton bPublish;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            bPublishProperties();
            loadProperties();
        });
    }    
    
    private void  bPublishProperties(){
        bPublish.setOnAction((e) -> {
            new Thread(() -> {
                try {
                    queue.setExchange(tfExchange.getText());
                    queue.setRoutingKey(tfRoutingKey.getText());
                    String message = taPayload.getText();
                    new BBQ().publish(queue, message);
                } catch (Exception ex) {
                    exceptions.log(ex);
                    setInfo(ex.getMessage());
                }
            }).start();
        });
    }
    private void loadProperties(){
        queue = new Session().getQueue();
        if (queue != null) {
            tfExchange.setText(queue.getExchange()); 
            tfRoutingKey.setText(queue.getRoutingKey()); 
        }
    }
    private void setInfo(String msg){
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(5), (e) -> {
            Stage stage = (Stage) bPublish.getScene().getWindow();
            stage.setTitle(msg);
        }));
        tl.setCycleCount(0);
        tl.setOnFinished((e) -> {
            Stage stage = (Stage) bPublish.getScene().getWindow();
            stage.setTitle(PUBLISH_TITLE);
        });
        tl.play();
    }
}
