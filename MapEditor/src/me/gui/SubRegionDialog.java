/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Tyler
 */
public class SubRegionDialog {
     Stage newSubStage;

    public SubRegionDialog() {
        newSubStage = new Stage();
        newSubStage.setScene(layoutGUI());
    }
    
    private Scene layoutGUI(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Scene newSubScene;
        VBox subRegionHolder = new VBox();
        HBox subRegionHolderTop = new HBox();
        Button prevButton = new Button();
        Button nextButton = new Button();
        Label subRegionName = new Label();
        subRegionHolderTop.getChildren().addAll(prevButton, subRegionName, nextButton);
        subRegionHolder.getChildren().add(subRegionHolderTop);
        
        
        
        
        newSubScene = new Scene(subRegionHolder, 300,300);
        return newSubScene;
    }
    
    public void showDialog(){
        newSubStage.show();
        
    }
}
