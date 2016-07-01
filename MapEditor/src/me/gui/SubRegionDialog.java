/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import javafx.scene.Scene;
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
        //newSubStage.setScene(layoutGUI());
    }
    
    private void layoutGUI(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Scene newSubScene;
        
        
        
        
        //newMapScene = new Scene(gridPane, 300,300);
        //return newSubScene;
    }
    
    public void showDialog(){
        newSubStage.show();
        
    }
}
