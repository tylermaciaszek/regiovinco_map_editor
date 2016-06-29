/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import saf.components.AppWorkspaceComponent;
import me.MapEditorApp;
import me.PropertyType;
import me.data.DataManager;
import properties_manager.PropertiesManager;

/**
 *
 * @author Tyler Maciaszek
 */
public class Workspace extends AppWorkspaceComponent {
    MapEditorApp app;

    public Workspace(MapEditorApp initApp) {
        app = initApp;
        workspace = new Pane();
        //PropertiesManager props = PropertiesManager.getPropertiesManager();
        //DataManager dataManager = (DataManager) app.getDataComponent();

        layoutGUI();
    }

    private void layoutGUI(){
       layoutEditToolbar();       
       layoutSplitPane();
    }
    
    private void layoutEditToolbar(){
       FlowPane topToolbar = (FlowPane)app.getGUI().getAppPane().getTop();
       FlowPane editToolbar = new FlowPane();
       editToolbar.setHgap(20);
       JFXButton changeMapBackgroundColor = new JFXButton("Change Map Color");
       JFXButton changeMapBorderColor = new JFXButton("Change Border Color");
       JFXSlider changeMapBorderThickness = new JFXSlider();
       editToolbar.getChildren().addAll(changeMapBackgroundColor, changeMapBorderColor, changeMapBorderThickness);
       topToolbar.getChildren().add(editToolbar);
    }
    
    private void layoutSplitPane(){
        SplitPane splitPane = new SplitPane();
        splitPane.prefWidthProperty().bind(app.getGUI().getPrimaryScene().widthProperty());
        splitPane.prefHeightProperty().bind(app.getGUI().getPrimaryScene().heightProperty());
        splitPane.getItems().addAll(layoutMapHolder(), layoutTableView());
        splitPane.setDividerPosition(0, .75);
        workspace.getChildren().add(splitPane);
    }
    
    private Pane layoutMapHolder(){
        Pane mapHolder = new Pane();
        return mapHolder;
    }
    
    private JFXTreeTableView layoutTableView(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        JFXTreeTableView subregionTable = new JFXTreeTableView();
        subregionTable.setEditable(false);
        
        JFXTreeTableColumn regionCol = new JFXTreeTableColumn<>(props.getProperty(PropertyType.SUBREGION_TABLE_HEADER));
        regionCol.prefWidthProperty().bind(subregionTable.widthProperty());
        subregionTable.getColumns().add(regionCol);
       
        
        return subregionTable;
    }

    @Override
    public void reloadWorkspace() {
        
    }

    @Override
    public void initStyle() {
        
    }
}
