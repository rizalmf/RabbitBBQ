/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.rabbitbbq.exception.Exceptions;
import com.rabbitbbq.model.Message;
import com.rabbitbbq.util.Beautify;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class MessageDetailController implements Initializable {

    private final Exceptions exceptions =  new Exceptions(this.getClass());
    private Message message;
    @FXML
    private AnchorPane parent;
    @FXML
    private JFXButton bBeautify;
    @FXML
    private JFXTextArea taBody;
    public void setMessage(Message message){
        this.message = message;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            taBodyProperties();
            bBeautifyProperties();
        });
    }    
    
    private void taBodyProperties(){
        taBody.setText(message.getBody());
    }
    
    private void bBeautifyProperties(){
        bBeautify.setOnAction((e) -> {
            try {
                taBody.setText(new Beautify().format(message.getBody()));
            } catch (Exception ex) {
                exceptions.log(ex);
            }
        });
    }
}
