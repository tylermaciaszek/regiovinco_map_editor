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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    Button okButton;
    Label dataLabel;
    Label parentLabel;

    public NewMapDialog() {
        newMapStage = new Stage();
        newMapStage.setScene(layoutGUI());
        initHandlers();
    }
    
    private Scene layoutGUI(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Scene newMapScene;
        GridPane gridPane = new GridPane();
        Label mapNameLabel = new Label(props.getProperty(PropertyType.MAP_NAME));
        TextField mapNameField = new TextField();
        mapNameField.setPrefWidth(10);
        Label chooseParentLabel = new Label("Choose Parent Directory: ");
        Button chooseParentDirectory = new Button();
        chooseParentDirectory.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.FOLDER_ICON))));
        parentLabel = new Label();
        Label chooseDataLabel = new Label("Choose Data Directory: ");
        Button chooseDataDirectory = new Button();
        chooseDataDirectory.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.FOLDER_ICON))));
        dataLabel = new Label();
        okButton = new Button("OK");
        gridPane.add(mapNameLabel, 0, 1);
        gridPane.add(mapNameField, 1, 1);
        gridPane.add(chooseParentLabel, 0, 2);
        gridPane.add(chooseParentDirectory, 2, 2);
        gridPane.add(parentLabel, 1, 2);
        gridPane.add(chooseDataLabel, 0, 3);
        gridPane.add(chooseDataDirectory, 2, 3);
        gridPane.add(dataLabel, 1, 3);
        gridPane.add(okButton, 1, 4);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(20);
        newMapScene = new Scene(gridPane, 300,200);
        return newMapScene;
    }
    
    public void initHandlers(){
        
        
        okButton.setOnAction(e ->{
            //createNewMap();
            newMapStage.close();
        });
    }
    
    public void createNewMap(){
        
    }
    
    public void showDialog(){
        newMapStage.setTitle("Create A New Map");
        newMapStage.show();
        
    }
}
