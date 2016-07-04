/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import me.PropertyType;
import properties_manager.PropertiesManager;

/**
 *
 * @author Tyler
 */
public class NewMapDialog {
    Stage newMapStage;

    public NewMapDialog() {
        newMapStage = new Stage();
        newMapStage.setScene(layoutGUI());
    }
    
    private Scene layoutGUI(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Scene newMapScene;
        GridPane gridPane = new GridPane();
        Label heading = new Label(props.getProperty(PropertyType.NEW_MAP_HEADING));
        Label mapNameLabel = new Label(props.getProperty(PropertyType.MAP_NAME));
        TextField mapNameField = new TextField();
        Button chooseParentDirectory = new Button(props.getProperty(PropertyType.CHOOSE_PARENT));
        Label parentLabel = new Label(".test/dir/folder/file");
        gridPane.add(heading, 0, 0, 2, 1);
        gridPane.add(mapNameLabel, 0, 1);
        gridPane.add(mapNameField, 1, 1);
        gridPane.add(chooseParentDirectory, 0, 2);
        gridPane.add(parentLabel, 1, 2);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        newMapScene = new Scene(gridPane, 300,300);
        return newMapScene;
    }
    
    public void showDialog(){
        newMapStage.show();
        
    }
}
