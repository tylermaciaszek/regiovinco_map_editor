/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.MapEditorApp;
import me.data.DataManager;
import me.data.Map;
import me.file.FileManager;
import me.gui.Workspace;
import saf.AppTemplate;
import static saf.settings.AppStartupConstants.PATH_WORK;

/**
 *
 * @author Tyler
 */
public class TestSave extends Application {

    static AppTemplate app;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void generateAndorraMap() {
        DataManager dataManager = new DataManager((MapEditorApp) app);
        FileManager fileManager = new FileManager();
        Map andorraMap = new Map();
        dataManager.setMapWidth(802);
        dataManager.setMapHeight(536);
        andorraMap.setBackgroundColor("DC6E00");
        andorraMap.setBorderColor("ff69b4");
        andorraMap.setBorderThickness(1.0);
        andorraMap.getImageLocationsX().add(8);
        andorraMap.getImageLocationsY().add(9);
        andorraMap.getImageLocationsX().add(581);
        andorraMap.getImageLocationsY().add(390);
        andorraMap.getImagePaths().add("file:./work/World/Europe/Andorra/andorracrest.png");
        andorraMap.getImagePaths().add("file:./work/World/Europe/Andorra/andorraflag.png");
        dataManager.setMapName("Andorra");
        dataManager.setRawMapData("./work/World/Europe/Andorra/Andorra.json");
        dataManager.setMapParentDirectory("./work/World/Europe/");
        try {
            fileManager.loadCoords(dataManager, dataManager.getMapParentDirectory());
        } catch (IOException ex) {
            Logger.getLogger(TestSave.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(andorraMap.getSubregionsList());
        dataManager.addMap(andorraMap);
        try {
            fileManager.loadCoords(dataManager, dataManager.getRawMapData());
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
        File selectedFile = fc.showSaveDialog(new Stage());
        if (selectedFile != null) {
            try {                
                fileManager.saveData(dataManager, selectedFile.getPath());
            } catch (IOException ex) {
                Logger.getLogger(TestSave.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
        
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        generateAndorraMap();
        Parent root = new Parent() {};
        primaryStage.setScene(new Scene(root, 0, 0));
        
    }
    

}
