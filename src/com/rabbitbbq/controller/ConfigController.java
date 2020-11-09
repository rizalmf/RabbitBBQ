/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.rabbitbbq.model.Factory;
import com.rabbitbbq.model.Queue;
import com.rabbitbbq.util.Session;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class ConfigController implements Initializable {

    @FXML
    private AnchorPane parent;
    @FXML
    private JFXTextField tfHost;
    @FXML
    private JFXTextField tfPort;
    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXTextField tfPassword;
    @FXML
    private JFXTextField tfVhost;
    @FXML
    private JFXButton bSave;
    @FXML
    private JFXTextField tfName;
    @FXML
    private JFXTextField tfExchange;
    @FXML
    private JFXTextField tfRoutingKey;
    @FXML
    private JFXCheckBox cbDurable;
    @FXML
    private JFXCheckBox cbAutoDelete;
    @FXML
    private JFXCheckBox cbExclusive;
    @FXML
    private JFXTextField tfArguments;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            bSaveProperties();
            loadProperties();
        });
    }    
    
    private void loadProperties(){
        Session session = new Session();
        Factory factory = session.getFactory();
        Queue queue = session.getQueue();
        if (factory != null) {
            tfHost.setText(factory.getHost());
            tfPort.setText(factory.getPort()+""); 
            tfUsername.setText(factory.getUsername()); 
            tfPassword.setText(factory.getPassword()); 
            tfVhost.setText(factory.getVirtualHost());
        }
        if (queue != null) {
            tfName.setText(queue.getName()); 
            tfExchange.setText(queue.getExchange()); 
            tfRoutingKey.setText(queue.getRoutingKey()); 
            cbDurable.setSelected(queue.isDurable());
            cbExclusive.setSelected(queue.isExclusive());
            cbAutoDelete.setSelected(queue.isAutoDelete()); 
        }
    }
    private void  bSaveProperties(){
        bSave.setOnAction((e) -> {
            Session session = new Session();
            session.saveFactory(new Factory(
                tfHost.getText(), 
                Integer.parseInt(tfPort.getText().replaceAll("[^0-9]","")), 
                tfUsername.getText(), 
                tfPassword.getText(), 
                tfVhost.getText()
            ));
            session.saveQueue(new Queue(
                tfName.getText(), 
                tfExchange.getText(), 
                tfRoutingKey.getText(), 
                cbDurable.isSelected(), 
                cbExclusive.isSelected(), 
                cbAutoDelete.isSelected(), 
                null // temporary disabled
            ));
            Stage stage = (Stage) parent.getScene().getWindow();
            stage.close();
        });
    }
}
