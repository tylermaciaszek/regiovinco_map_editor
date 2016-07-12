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
import static javafx.application.Application.launch;
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
public class TestSaveSlovakia extends Application {

    static AppTemplate app;
    static DataManager dataManager = new DataManager((MapEditorApp) app);

    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void generateSlovakiaMap() {
        try {
            FileManager fileManager = new FileManager();
            
            Map slovakiaMap = new Map();
            dataManager.setMapWidth(802);
            dataManager.setMapHeight(536);
            slovakiaMap.setBackgroundColor("DC6E00");
            slovakiaMap.setBorderColor("000000");
            slovakiaMap.setBorderThickness(1);
            slovakiaMap.getImageLocationsX().add(19);
            slovakiaMap.getImageLocationsY().add(26);
            slovakiaMap.getImageLocationsX().add(542);
            slovakiaMap.getImageLocationsY().add(371);
            slovakiaMap.setScrollLocationX(0.0);
            slovakiaMap.setScrollLocationY(0.0);
            slovakiaMap.setZoomLevel(1);
            slovakiaMap.getImagePaths().add("file:./work/World/Europe/Slovakia/slovakiacrest.png");
            slovakiaMap.getImagePaths().add("file:./work/World/Europe/Slovakia/slovakiaflag.png");
            dataManager.setMapName("Slovakia");
            dataManager.setRawMapData("./work/raw_map_data/Slovakia.json");
            dataManager.setMapParentDirectory("./work/World/Europe/");
            dataManager.addMap(slovakiaMap);
            fileManager.loadCoords(dataManager, "./work/raw_map_data/Slovakia.json");
            dataManager.setSubregionList(getXAndY());
            ArrayList<Subregion> subregions = dataManager.getSubregionList();
            subregions.get(0).setName("Bratislava");
            subregions.get(1).setName("Trnava");
            subregions.get(2).setName("Trencin");
            subregions.get(3).setName("Nitra");
            subregions.get(4).setName("Zilina");
            subregions.get(5).setName("Banska Bystric");
            subregions.get(6).setName("Presov");
            subregions.get(7).setName("Kosice");
            subregions.get(0).setRedColor(250);
            subregions.get(1).setRedColor(249);
            subregions.get(2).setRedColor(248);
            subregions.get(3).setRedColor(247);
            subregions.get(4).setRedColor(246);
            subregions.get(5).setRedColor(245);
            subregions.get(6).setRedColor(244);
            subregions.get(7).setRedColor(243);
            subregions.get(0).setGreenColor(250);
            subregions.get(1).setGreenColor(249);
            subregions.get(2).setGreenColor(248);
            subregions.get(3).setGreenColor(247);
            subregions.get(4).setGreenColor(246);
            subregions.get(5).setGreenColor(245);
            subregions.get(6).setGreenColor(244);
            subregions.get(7).setGreenColor(243);
            subregions.get(0).setBlueColor(250);
            subregions.get(1).setBlueColor(249);
            subregions.get(2).setBlueColor(248);
            subregions.get(3).setBlueColor(249);
            subregions.get(4).setBlueColor(246);
            subregions.get(5).setBlueColor(245);
            subregions.get(6).setBlueColor(244);
            subregions.get(7).setBlueColor(243);
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
        generateSlovakiaMap();
        Parent root = new Parent() {};
        primaryStage.setScene(new Scene(root, 0, 0));
        
    }
    

}