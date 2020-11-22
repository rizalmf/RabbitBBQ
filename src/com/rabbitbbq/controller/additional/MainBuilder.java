/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.controller.additional;

import com.rabbitbbq.controller.MessageDetailController;
import com.rabbitbbq.exception.Exceptions;
import com.rabbitbbq.model.Message;
import static com.rabbitbbq.role.PathRole.CONFIG_PATH;
import static com.rabbitbbq.role.PathRole.CONFIG_TITLE;
import static com.rabbitbbq.role.PathRole.DETAIL_TITLE;
import static com.rabbitbbq.role.PathRole.ICON_PATH;
import static com.rabbitbbq.role.PathRole.MESSAGE_DETAIL_PATH;
import static com.rabbitbbq.role.PathRole.PROJECT_URL;
import static com.rabbitbbq.role.PathRole.PUBLISH_PATH;
import static com.rabbitbbq.role.PathRole.PUBLISH_TITLE;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author rizal
 */
public class MainBuilder {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    private static Stage configStage;
    private static Stage messageDetailStage;
    private static Stage publishStage;
    
    public VBox buildMessage(Message message){
        Label lTime = new Label(message.getDate());
        lTime.setId("lTime");
        HBox timeBox = new HBox(lTime);
        timeBox.setId("hbMessageTime");
        
        Label lData = new Label(message.getPayload());
        lData.setId("lData");
        HBox dataBox = new HBox(lData);
        dataBox.setId("hbMessageData");
        
        VBox child = new VBox(timeBox, dataBox);
        child.setId("vbMessageChild");
        child.setOnMouseClicked((e) -> {
            openMessageDetail(message);
        });
        return child;
    }
    
    public void openMessageDetail(Message message){
        if (messageDetailStage == null) {
            messageDetailStage = new Stage();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(
        getClass().getClassLoader().getResource(MESSAGE_DETAIL_PATH));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            exceptions.log(ex);
        }
        MessageDetailController controller = fxmlLoader.getController();
        controller.setMessage(message);
        messageDetailStage.setOnCloseRequest((e) -> {
            messageDetailStage.close();
        });
        messageDetailStage.toFront();
        messageDetailStage.setTitle(message.getDate()+DETAIL_TITLE);
        messageDetailStage.getIcons().add(new Image(
                getClass().getClassLoader().getResourceAsStream(ICON_PATH)));
        Scene scene = new Scene(root);
        messageDetailStage.setScene(scene);  
        messageDetailStage.show();
    }
    
    public void openConfig(){
        if (configStage == null) {
            configStage = new Stage();
            configStage.initModality(Modality.APPLICATION_MODAL);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getClassLoader().getResource(CONFIG_PATH));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            exceptions.log(ex);
        }
        configStage.setOnCloseRequest((e) -> {
            configStage.close();
        });
        configStage.setTitle(CONFIG_TITLE);
        configStage.getIcons().add(new Image(
                getClass().getClassLoader().getResourceAsStream(ICON_PATH)));
        Scene scene = new Scene(root);
        configStage.setScene(scene);  
        configStage.showAndWait();
    }
    
    public void openPublish(Button floatButton){
        if (publishStage == null) {
            publishStage = new Stage();
            publishStage.showingProperty().addListener((obs, oldVal, newVal) -> {
                floatButton.setVisible(!newVal);
            });
        }
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getClassLoader().getResource(PUBLISH_PATH));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ex) {
            exceptions.log(ex);
        }
        publishStage.setOnCloseRequest((e) -> {
            publishStage.close();
        });
        publishStage.setTitle(PUBLISH_TITLE);
        publishStage.getIcons().add(new Image(
                getClass().getClassLoader().getResourceAsStream(ICON_PATH)));
        Scene scene = new Scene(root);
        publishStage.setScene(scene);  
        publishStage.show();
        
    }
    
    public void github(){
        new Thread(() -> {
            try {
                Desktop.getDesktop().browse(URI.create(PROJECT_URL));
            } catch (IOException e) {
                exceptions.log(e);
            }
        }).start();
    }
}
