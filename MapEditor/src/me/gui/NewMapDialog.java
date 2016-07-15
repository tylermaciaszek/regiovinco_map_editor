/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.MapEditorApp;
import me.PropertyType;
import me.data.DataManager;
import me.file.FileManager;
import properties_manager.PropertiesManager;
import saf.AppTemplate;
import static saf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static saf.settings.AppStartupConstants.PATH_WORK;

/**
 *
 * @author Tyler
 */
public class NewMapDialog {
    Stage newMapStage;
    Button okButton;
    Label dataLabel;
    Label parentLabel;
    Button chooseParentDirectory;
    Button chooseDataDirectory;
    AppTemplate app;
    TextField mapNameField;
    String parentDir;
    String dataFile;

    public NewMapDialog() {
        newMapStage = new Stage();
        newMapStage.setScene(layoutGUI());
        initHandlers();
    }
    
    private Scene layoutGUI(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        parentDir = "";
        dataFile = "";
        Scene newMapScene;
        GridPane gridPane = new GridPane();
        Label mapNameLabel = new Label(props.getProperty(PropertyType.MAP_NAME));
        mapNameField = new TextField();
        mapNameField.setPrefWidth(10);
        Label chooseParentLabel = new Label("Choose Parent Directory: ");
        chooseParentDirectory = new Button();
        chooseParentDirectory.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.FOLDER_ICON))));
        parentLabel = new Label();
        Label chooseDataLabel = new Label("Choose Data File: ");
        chooseDataDirectory = new Button();
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
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        FileManager fileManager = new FileManager();
        chooseParentDirectory.setOnAction(e -> {
            DataManager dataManager = new DataManager((MapEditorApp) app);
            DirectoryChooser fc = new DirectoryChooser();
            fc.setInitialDirectory(new File("./work/World/"));
            fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
            File selectedFile = fc.showDialog(newMapStage);
            parentLabel.setText(selectedFile.getPath());
            parentDir = selectedFile.getPath();
        });
        
        chooseDataDirectory.setOnAction(e -> {
            DataManager dataManager = new DataManager((MapEditorApp) app);
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("./work/World/"));
            fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
            File selectedFile = fc.showOpenDialog(newMapStage);
            dataLabel.setText(selectedFile.getPath());
            dataFile = selectedFile.getPath();
        });
        
        okButton.setOnAction(e ->{
            DataManager dataManager = new DataManager((MapEditorApp) app);
            dataManager.setMapName(mapNameField.getText());
            dataManager.setMapParentDirectory(parentDir);
            dataManager.setRawMapData(dataFile);
            System.out.print(dataManager.getMapName() + " " + dataManager.getMapParentDirectory() + " " + dataManager.getRawMapData());
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
