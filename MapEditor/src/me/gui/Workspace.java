/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import saf.components.AppWorkspaceComponent;
import me.MapEditorApp;
import me.PropertyType;
import me.controller.Controller;
import me.data.DataManager;
import properties_manager.PropertiesManager;

/**
 *
 * @author Tyler Maciaszek
 */
public class Workspace extends AppWorkspaceComponent {
    MapEditorApp app;
    NewMapDialog newMapDialog;
    SubRegionDialog subRegionDialog;
    Button changeMapNameButton;
    Controller controller;
    

    public Workspace(MapEditorApp initApp) {
        app = initApp;
        workspace = new Pane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager dataManager = (DataManager) app.getDataComponent();
        controller = new Controller();

        layoutGUI();
        initHandlers();
    }

    private void layoutGUI(){
       layoutEditToolbar();   
       //newMapDialog = new NewMapDialog();
       //newMapDialog.showDialog();
       subRegionDialog = new SubRegionDialog();
       subRegionDialog.showDialog();
       layoutSplitPane();
    }
    
    private void layoutEditToolbar(){
       PropertiesManager props = PropertiesManager.getPropertiesManager();
       FlowPane topToolbar = (FlowPane)app.getGUI().getAppPane().getTop();
       HBox editToolbar = new HBox();
       editToolbar.setSpacing(20);
       Label mapColorLabel = new Label(props.getProperty(PropertyType.MAP_BACKGROUND_COLOR));
       Label borderColorLabel = new Label(props.getProperty(PropertyType.MAP_BORDER_COLOR));
       Label borderThicknessLabel = new Label(props.getProperty(PropertyType.MAP_THICKNESS));
       ColorPicker changeMapBackgroundColor = new ColorPicker();
       ColorPicker changeMapBorderColor = new ColorPicker();
       Slider changeMapBorderThickness = new Slider();
       VBox mapBackgroundHolder = new VBox();
       mapBackgroundHolder.getChildren().addAll(mapColorLabel, changeMapBackgroundColor);
       VBox borderColorHolder = new VBox();
       borderColorHolder.getChildren().addAll(borderColorLabel, changeMapBorderColor);
       VBox borderThicknessHolder = new VBox();
       borderThicknessHolder.getChildren().addAll(borderThicknessLabel, changeMapBorderThickness);
       Button randomizeSubregionColorsButton = new Button(props.getProperty(PropertyType.SUBREGION_COLORS));
       Button playSubregionAnthemButton = new Button();
       Image playImage = new Image(props.getProperty(PropertyType.PLAY_ICON));
       playSubregionAnthemButton.setGraphic(new ImageView(playImage));
       changeMapNameButton = new Button(props.getProperty(PropertyType.CHANGE_MAP_NAME));
       Button addImageButton = new Button(props.getProperty(PropertyType.ADD_IMAGE));
       //Image addImage = new Image(props.getProperty(PropertyType.ADD_IMAGE));
       //addImageButton.setGraphic(new ImageView(addImage));
       editToolbar.getChildren().addAll(changeMapNameButton, addImageButton, mapBackgroundHolder, borderColorHolder, borderThicknessHolder, randomizeSubregionColorsButton, playSubregionAnthemButton);
       editToolbar.setAlignment(Pos.BOTTOM_CENTER);
       editToolbar.setPadding(new Insets(0,0,0,200));
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
        JFXTreeTableColumn capitalCol = new JFXTreeTableColumn<>(props.getProperty(PropertyType.CAPITAL_TABLE_HEADER));
        JFXTreeTableColumn leaderCol = new JFXTreeTableColumn<>(props.getProperty(PropertyType.LEADER_TABLE_HEADER));
        regionCol.prefWidthProperty().bind(subregionTable.widthProperty().divide(3.0));
        capitalCol.prefWidthProperty().bind(subregionTable.widthProperty().divide(3.0));
        leaderCol.prefWidthProperty().bind(subregionTable.widthProperty().divide(3.0));

        subregionTable.getColumns().addAll(regionCol, capitalCol, leaderCol);
       
        
        return subregionTable;
    }
    
    public void initHandlers(){
        changeMapNameButton.setOnAction(e -> {
            controller.launchChangeNameWindow();
        });
    }

    @Override
    public void reloadWorkspace() {
        
    }

    @Override
    public void initStyle() {
        
        
    }
}
