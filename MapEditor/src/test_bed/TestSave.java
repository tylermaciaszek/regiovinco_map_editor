/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.MapEditorApp;
import me.data.DataManager;
import me.data.Map;
import me.file.FileManager;
import me.gui.Workspace;
import saf.AppTemplate;



/**
 *
 * @author Tyler
 */
public class TestSave {
    static MapEditorApp app;

    
    public static void main(String[] args){
        generateAndorraMap();
    }
        
        
    public static void generateAndorraMap() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        FileManager fileManager = (FileManager) app.getFileComponent();
        Map andorraMap = new Map();
        dataManager.setMapWidth(802);
        dataManager.setMapWidth(536);
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
        dataManager.setMapParentDirectory("test dir");
        dataManager.setRawMapData("./work/World/Europe/Andorra/Andorra.json");
        dataManager.setMapParentDirectory("./work/World/Europe/");
        dataManager.addMap(andorraMap);
        try {
            fileManager.loadCoords(dataManager, dataManager.getRawMapData());
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    

}
