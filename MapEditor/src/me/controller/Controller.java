/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.controller;

import java.util.ArrayList;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import me.MapEditorApp;
import me.PropertyType;
import me.data.DataManager;
import me.data.Map;
import me.data.Subregion;
import me.gui.Workspace;
import properties_manager.PropertiesManager;
import saf.AppTemplate;
import saf.components.AppComponentsBuilder;

/**
 *
 * @author Tyler
 */
public class Controller extends AppTemplate {

    MapEditorApp app;
    DataManager dataManager;
    Workspace work;
    int index;

    public Controller(MapEditorApp initApp) {
        app = initApp;
        dataManager = (DataManager) app.getDataComponent();
        work = (Workspace) app.getWorkspaceComponent();
    }

    public ArrayList<Subregion> getXAndY() {
        index = 0;
        ArrayList<Double> cords = new ArrayList<>();
        ArrayList<Subregion> polygons = new ArrayList<>();
        dataManager.convertToScreen();
        Map map = dataManager.getMap();
        for (int i = 0; i < dataManager.getSubregionCordsX().size(); i++) {
            for (int k = 0; k < dataManager.getSubregionCordsX().get(i).size(); k++) {
                cords.add(dataManager.getSubregionCordsX().get(i).get(k));
                cords.add(dataManager.getSubregionCordsY().get(i).get(k));
                if (k == dataManager.getSubregionCordsX().get(i).size() - 1) {
                    Polygon poly = new Polygon();
                    poly.getPoints().addAll(cords);
                    poly.setStrokeWidth(1);
                    Subregion subregion = new Subregion(poly);
                    polygons.add(subregion);
                    index++;
                }
            }
            cords.clear();
        }
        return polygons;
    }
    
    public void setPolygonColors(){
        Map map = dataManager.getMap();
        int colorIncr = 254 / dataManager.getMap().getSubregionsList().size();
        int red = 254;
        int green = 254;
        int blue = 254;
        for(int i = 0; i < map.getSubregionsList().size(); i++){
            red = red - colorIncr;
            green = green - colorIncr;
            blue = blue - colorIncr;
            map.getSubregionsList().get(i).getPolygon().setFill(Color.rgb(red, green, blue));
        }        
    }
    

    @Override
    public AppComponentsBuilder makeAppBuilderHook() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void launchChangeNameWindow() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TextInputDialog dialog = new TextInputDialog("Current Map Name");
        dialog.setTitle(props.getProperty(PropertyType.CHANGE_MAP_NAME));
        dialog.setHeaderText(props.getProperty(PropertyType.CHANGE_MAP_NAME_HEADER));
        dialog.setContentText(props.getProperty(PropertyType.CHANGE_MAP_NAME_CONTEXT));
        dialog.showAndWait();
    }
}

