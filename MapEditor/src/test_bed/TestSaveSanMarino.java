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
import static javafx.application.Application.launch;

/**
 *
 * @author Tyler
 */
public class TestSaveSanMarino extends Application {

    static AppTemplate app;
    static DataManager dataManager = new DataManager((MapEditorApp) app);

    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void generateSanMarinoMap() {
        try {
            FileManager fileManager = new FileManager();
            
            Map sanMarinoMap = new Map();
            dataManager.setMapWidth(802);
            dataManager.setMapHeight(536);
            sanMarinoMap.setBackgroundColor("DC6E00");
            sanMarinoMap.setBorderColor("000000");
            sanMarinoMap.setBorderThickness(1.0);
            dataManager.setMapName("San Marino");
            dataManager.setRawMapData("./work/raw_map_data/San Marino.json");
            dataManager.setMapParentDirectory("./work/World/Europe/");
            dataManager.addMap(sanMarinoMap);
            fileManager.loadCoords(dataManager, "./work/raw_map_data/San Marino.json");
            dataManager.setSubregionList(getXAndY());
            ArrayList<Subregion> subregions = dataManager.getSubregionList();
            subregions.get(0).setName("Acquaviva");
            subregions.get(1).setName("Borgo Maggiore");
            subregions.get(2).setName("Chiesanuova");
            subregions.get(3).setName("Domagnano");
            subregions.get(4).setName("Faetano");
            subregions.get(5).setName("Fiorentino");
            subregions.get(6).setName("Montegiardino");
            subregions.get(7).setName("City of San Marino");
            subregions.get(8).setName("Serravalle");
            subregions.get(0).setLeader("Lucia Tamagnini");
            subregions.get(1).setLeader("Sergio Nanni");
            subregions.get(2).setLeader("Franco Santi");
            subregions.get(3).setLeader("Gabriel Guidi");
            subregions.get(4).setLeader("Pier Mario Bedetti");
            subregions.get(5).setLeader("Gerri Fabbri");
            subregions.get(6).setLeader("Marta Fabbri");
            subregions.get(6).setLeader("Maria Teresa Beccari");
            subregions.get(6).setLeader("Leandro Maiani");
            subregions.get(0).setRedColor(225);
            subregions.get(1).setRedColor(200);
            subregions.get(2).setRedColor(175);
            subregions.get(3).setRedColor(150);
            subregions.get(4).setRedColor(125);
            subregions.get(5).setRedColor(100);
            subregions.get(6).setRedColor(75);
            subregions.get(7).setRedColor(50);
            subregions.get(8).setRedColor(25);
            subregions.get(0).setBlueColor(225);
            subregions.get(1).setBlueColor(200);
            subregions.get(2).setBlueColor(175);
            subregions.get(3).setBlueColor(150);
            subregions.get(4).setBlueColor(125);
            subregions.get(5).setBlueColor(100);
            subregions.get(6).setBlueColor(75);
            subregions.get(7).setBlueColor(50);
            subregions.get(8).setBlueColor(25);
            subregions.get(0).setGreenColor(225);
            subregions.get(1).setGreenColor(200);
            subregions.get(2).setGreenColor(175);
            subregions.get(3).setGreenColor(150);
            subregions.get(4).setGreenColor(125);
            subregions.get(5).setGreenColor(100);
            subregions.get(6).setGreenColor(75);
            subregions.get(7).setGreenColor(50);
            subregions.get(8).setGreenColor(25);
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
        generateSanMarinoMap();
        Parent root = new Parent() {};
        primaryStage.setScene(new Scene(root, 0, 0));
        
    }
    

}