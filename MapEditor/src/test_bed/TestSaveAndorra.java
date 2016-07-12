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
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.MapEditorApp;
import me.controller.Controller;
import me.data.DataManager;
import me.data.Map;
import me.data.Subregion;
import me.file.FileManager;
import me.gui.Workspace;
import saf.AppTemplate;
import static saf.settings.AppStartupConstants.PATH_WORK;

/**
 *
 * @author Tyler
 */
public class TestSaveAndorra extends Application {

    static AppTemplate app;
    static DataManager dataManager = new DataManager((MapEditorApp) app);

    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void generateAndorraMap() {
        try {
            FileManager fileManager = new FileManager();
            
            Map andorraMap = new Map();
            dataManager.setMapWidth(802);
            dataManager.setMapHeight(536);
            andorraMap.setBackgroundColor("DC6E00");
            andorraMap.setBorderColor("000000");
            andorraMap.setBorderThickness(1);
            andorraMap.getImageLocationsX().add(8);
            andorraMap.getImageLocationsY().add(9);
            andorraMap.getImageLocationsX().add(581);
            andorraMap.getImageLocationsY().add(390);
            andorraMap.getImagePaths().add("file:./work/World/Europe/Andorra/andorracrest.png");
            andorraMap.getImagePaths().add("file:./work/World/Europe/Andorra/andorraflag.png");
            andorraMap.setScrollLocationX(0.0);
            andorraMap.setScrollLocationY(0.0);
            andorraMap.setZoomLevel(1);
            dataManager.setMapName("Andorra");
            dataManager.setRawMapData("./work/raw_map_data/Andorra.json");
            dataManager.setMapParentDirectory("./work/World/Europe/");
            dataManager.addMap(andorraMap);
            fileManager.loadCoords(dataManager, "./work/raw_map_data/Andorra.json");
            dataManager.setSubregionList(getXAndY());
            ArrayList<Subregion> subregions = dataManager.getSubregionList();
            subregions.get(0).setName("Ordino");
            subregions.get(1).setName("Carillo");
            subregions.get(2).setName("Encamp");
            subregions.get(3).setName("Escaldes-Engordany");
            subregions.get(4).setName("La Massanna");
            subregions.get(5).setName("Andorra la Vella");
            subregions.get(6).setName("Sant Julia de Loria");
            subregions.get(0).setCapital("Ordino");
            subregions.get(1).setCapital("Canillo");
            subregions.get(2).setCapital("Encamp");
            subregions.get(3).setCapital("Escaldes-Engordany");
            subregions.get(4).setCapital("La Massanna");
            subregions.get(5).setCapital("Andorra la Vella");
            subregions.get(6).setCapital("Julia de Loria");
            subregions.get(0).setLeader("Ventura Espot");
            subregions.get(1).setLeader("Enric Casadevall Medrano");
            subregions.get(2).setLeader("Miquel Alís Font");
            subregions.get(3).setLeader("Montserrat Capdevila Pallarés");
            subregions.get(4).setLeader("Josep Areny");
            subregions.get(5).setLeader("Maria Rosa Ferrer Obiols");
            subregions.get(6).setLeader("Josep Pintat Forné");
            subregions.get(0).setRedColor(200);
            subregions.get(1).setRedColor(198);
            subregions.get(2).setRedColor(196);
            subregions.get(3).setRedColor(194);
            subregions.get(4).setRedColor(192);
            subregions.get(5).setRedColor(190);
            subregions.get(6).setRedColor(188);
            subregions.get(0).setGreenColor(200);
            subregions.get(1).setGreenColor(198);
            subregions.get(2).setGreenColor(196);
            subregions.get(3).setGreenColor(194);
            subregions.get(4).setGreenColor(192);
            subregions.get(5).setGreenColor(190);
            subregions.get(6).setGreenColor(188);
            subregions.get(0).setBlueColor(200);
            subregions.get(1).setBlueColor(198);
            subregions.get(2).setBlueColor(196);
            subregions.get(3).setBlueColor(194);
            subregions.get(4).setBlueColor(192);
            subregions.get(5).setBlueColor(190);
            subregions.get(6).setBlueColor(188);
            System.out.println(dataManager.getSubregionList());
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_WORK));
            File selectedFile = fc.showSaveDialog(new Stage());
            if (selectedFile != null) {
                try {
                    fileManager.saveData(dataManager, selectedFile.getPath());
                } catch (IOException ex) {
                    Logger.getLogger(TestSaveAndorra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TestSaveAndorra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
        
    public static ArrayList<Subregion> getXAndY() {
        int index = 0;
        ArrayList<Double> cords = new ArrayList<>();
        ArrayList<Subregion> polygons = new ArrayList<>();
        dataManager.convertToScreen();
        Map map = dataManager.getMap().get(0);
        for (int i = 0; i < dataManager.getSubregionCordsX().size(); i++) {
            for (int k = 0; k < dataManager.getSubregionCordsX().get(i).size(); k++) {
                cords.add(dataManager.getSubregionCordsX().get(i).get(k));
                cords.add(dataManager.getSubregionCordsY().get(i).get(k));
                if (k == dataManager.getSubregionCordsX().get(i).size() - 1) {
                    Polygon poly = new Polygon();
                    poly.getPoints().addAll(cords);
                    poly.setFill(Color.valueOf("#4cff4c"));
                    poly.setStroke(Color.valueOf("#"+map.getBorderColor()));
                    Subregion subregion = new Subregion(poly);
                    polygons.add(subregion);
                    index++;
                }
            }
            cords.clear();
        }
        dataManager.setSubregionList(polygons);
        return polygons;
    }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        generateAndorraMap();
        Parent root = new Parent() {};
        primaryStage.setScene(new Scene(root, 0, 0));
        
    }
    

}
