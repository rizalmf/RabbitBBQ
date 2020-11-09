/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq;

import com.rabbitbbq.model.Factory;
import static com.rabbitbbq.role.PathRole.APP_TITLE;
import static com.rabbitbbq.role.PathRole.ICON_PATH;
import static com.rabbitbbq.role.PathRole.MAIN_PATH;
import com.rabbitbbq.util.Session;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author rizal
 */
public class RabbitBBQ extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(
                MAIN_PATH));
        stage.setOnCloseRequest((WindowEvent ev) -> {
            System.exit(0);
        });
        Scene scene = new Scene(root);
        stage.setTitle(APP_TITLE);
        stage.getIcons().add(
                new Image(getClass().getClassLoader().getResourceAsStream(
                        ICON_PATH)));
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
