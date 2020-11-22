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
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author rizal
 */
public class MessageDetailController implements Initializable {

    private final Exceptions exceptions =  new Exceptions(this.getClass());
    private KeyCombination findKey;
    private int stateMatch;
    private String findTooltip, unfindTooltip;
    public List<Map<String, Integer>> matcherList; 
    private Message message;
    @FXML
    private AnchorPane parent;
    @FXML
    private JFXButton bBeautify;
    @FXML
    private JFXTextArea taBody;
    @FXML
    private TextField tfFind;
    @FXML
    private Button bFindUp;
    @FXML
    private Button bFindDown;
    @FXML
    private JFXButton bTogleFind;
    @FXML
    private FontAwesomeIconView viewTogleFind;
    @FXML
    private HBox hbFind;
    @FXML
    private HBox hbFindParent;
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
            findProperties();
        });
    }    
    
    private void taBodyProperties(){
        findKey = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
        taBody.setOnKeyPressed((e) -> {
            if (findKey.match(e)) {
                findState();
            }
            else if (e.getCode() == KeyCode.ESCAPE) {
                unfindState();
            }
        });
        taBody.setText(message.getPayload());
    }
        
    private void bBeautifyProperties(){
        bBeautify.setOnAction((e) -> {
            try {
                taBody.setText(new Beautify().format(message.getPayload()));
            } catch (Exception ex) {
                exceptions.log(ex);
            }
        });
    }
    
    private void findProperties(){
        matcherList = new ArrayList<>();
        findTooltip = "Find (Ctrl + F)";
        unfindTooltip = "Leave find state (Esc)";
        bTogleFind.setTooltip(new Tooltip(findTooltip));
        hbFindParent.getChildren().remove(hbFind);
        bTogleFind.setOnAction((e) -> {
            if (hbFindParent.getChildren().size() == 1) {
                findState();
            }else{ unfindState(); }
        });
        
        tfFind.setOnKeyPressed((e) -> {
            switch(e.getCode()){
                case ENTER : findAreaByKey(tfFind.getText());
                    break;
                case ESCAPE : unfindState();
                    break;
            }
        });
        bFindUp.setOnAction((e) -> {
            focusArea(true);
        });
        bFindDown.setOnAction((e) -> {
            focusArea(false);
        });
    }
    
    private void findState(){
        hbFindParent.getChildren().add(0, hbFind);
        viewTogleFind.setIcon(FontAwesomeIcon.CLOSE);
        tfFind.clear();
        tfFind.requestFocus();
        bTogleFind.setTooltip(new Tooltip(unfindTooltip));
    }
    
    private void unfindState(){
        hbFindParent.getChildren().remove(hbFind);
        viewTogleFind.setIcon(FontAwesomeIcon.SEARCH);
        bTogleFind.setTooltip(new Tooltip(findTooltip));
    }
    
    private void findAreaByKey(String key){
        matcherList = new ArrayList<>();
        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(taBody.getText());
        stateMatch = 0;
        while (matcher.find()) {
            Map<String, Integer> found = new HashMap<>();
            found.put("start", matcher.start());
            found.put("end", matcher.end());
            matcherList.add(found);
            taBody.selectRange(matcher.start(), matcher.end());
        }
        
        focusArea(false);
    }
    
    private void focusArea(boolean isUp){
        if (matcherList.size() > 0) {
            if (isUp) {
                stateMatch = ((stateMatch - 1) < 0 )? matcherList.size()-1 : --stateMatch;
            }else{
                stateMatch = (stateMatch >= (matcherList.size() - 1))? 0 : ++stateMatch;
            }
            Map<String, Integer> found = matcherList.get(stateMatch);
            taBody.selectRange(found.get("start"), found.get("end"));
        }
    }
}
