/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import static saf.settings.AppStartupConstants.PATH_WORK;
import static test_bed.TestSaveAndorra.app;

/**
 *
 * @author Tyler
 */
public class TestLoad extends Application {
    static DataManager dataManager = new DataManager((MapEditorApp) app);


    public static void main(String[] args) {
        launch(args);
       
    }
    
    public void printOut(){
        Map map = dataManager.getMap();
        System.out.println("Map Info :");
        System.out.println("Map Name: " + dataManager.getMapName());
        System.out.println("Map Parent Directory: " + dataManager.getMapParentDirectory());
        System.out.println("Map Data File: " + dataManager.getRawMapData());
        System.out.println("Map Height: " + dataManager.getMapHeight());
        System.out.println("Map Width: " + dataManager.getMapWidth());
        System.out.println("Edit Info: ");
        System.out.println("Map Background Color: " + map.getBackgroundColor());
        System.out.println("Map Border Color: " + map.getBorderColor());
        System.out.println("Map Border Thickness: " + map.getBorderThickness());
        System.out.println("Map Scroll X: " + map.getScrollLocationX());
        System.out.println("Map Scroll Y: " + map.getScrollLocationY());
        System.out.println("Map Zoom: " + map.getZoomLevel());
        System.out.println("Map Image Paths: " + map.getImagePaths());
        System.out.println("Map Image X Locations: " + map.getImageLocationsX());
        System.out.println("Map Image Y Locations: " + map.getImageLocationsY());
        System.out.println("Regions: ");
        System.out.print(printArrayList(dataManager.getSubregionList()));
    }
    
    public String printArrayList(ArrayList list){
        String print = list.get(0).toString();
        for(int i = 1; i < list.size(); i++){
            print += "\n" + list.get(i).toString();
        }
        
        return print;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FileManager fileManager = new FileManager();
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
        File selectedFile = fc.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                fileManager.loadData(dataManager, selectedFile.getPath());
                fileManager.loadCoords(dataManager, dataManager.getRawMapData());
            } catch (IOException ex) {
                Logger.getLogger(TestSaveAndorra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        printOut();
        Parent root = new Parent() {};
        primaryStage.setScene(new Scene(root, 0, 0));
    }

}
