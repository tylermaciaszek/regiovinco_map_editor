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
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import saf.components.AppWorkspaceComponent;
import me.MapEditorApp;
import me.PropertyType;
import me.controller.Controller;
import me.data.DataManager;
import me.data.Map;
import me.data.Subregion;
import me.file.FileManager;
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
    Button playSubregionAnthemButton;
    Boolean playing;
    Pane mapHolder;
    Pane subregionHolder;
    

    public Workspace(MapEditorApp initApp) {
        app = initApp;
        workspace = new Pane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager dataManager = (DataManager) app.getDataComponent();
        controller = new Controller(app);
        playing = false;

        layoutGUI();
        initHandlers();
        
    }

    private void layoutGUI(){
       layoutEditToolbar();   
       newMapDialog = new NewMapDialog();
       //newMapDialog.showDialog();
       subRegionDialog = new SubRegionDialog();
       //subRegionDialog.showDialog();
       layoutSplitPane();
    }
    
    private void layoutEditToolbar(){
       PropertiesManager props = PropertiesManager.getPropertiesManager();
       FlowPane topToolbar = (FlowPane)app.getGUI().getAppPane().getTop();
       Button exportButton = new Button();
       exportButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.EXPORT_ICON))));
       topToolbar.getChildren().add(exportButton);
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
       playSubregionAnthemButton = new Button();
       Image playImage = new Image(props.getProperty(PropertyType.PLAY_ICON));
       playSubregionAnthemButton.setGraphic(new ImageView(playImage));
       changeMapNameButton = new Button(props.getProperty(PropertyType.CHANGE_MAP_NAME));
       Button addImageButton = new Button();
       Button removeImageButton = new Button();
       Image addImage = new Image(props.getProperty(PropertyType.ADD_IMAGE));
       Image removeImage = new Image(props.getProperty(PropertyType.REMOVE_IMAGE));
       addImageButton.setGraphic(new ImageView(addImage));
       removeImageButton.setGraphic(new ImageView(removeImage));
       HBox zoomIcons = new HBox();
       VBox zoomHolder = new VBox();
       ImageView zoomOut = new ImageView(new Image(props.getProperty(PropertyType.ZOOM_OUT)));
       ImageView zoomIn = new ImageView(new Image(props.getProperty(PropertyType.ZOOM_IN)));
       zoomIcons.getChildren().addAll(zoomOut, zoomIn);
       Slider zoomBar = new Slider();
       zoomIcons.setSpacing(80);
       zoomHolder.getChildren().addAll(zoomIcons, zoomBar);      
       editToolbar.getChildren().addAll(changeMapNameButton, addImageButton, removeImageButton, mapBackgroundHolder, borderColorHolder, borderThicknessHolder, randomizeSubregionColorsButton, playSubregionAnthemButton, zoomHolder);
       editToolbar.setAlignment(Pos.BOTTOM_CENTER);
       editToolbar.setPadding(new Insets(0,0,0,20));
       topToolbar.getChildren().add(editToolbar);
    }
    
    private void layoutSplitPane(){
        SplitPane splitPane = new SplitPane();
        splitPane.prefWidthProperty().bind(app.getGUI().getPrimaryScene().widthProperty());
        splitPane.prefHeightProperty().bind(app.getGUI().getPrimaryScene().heightProperty());
        splitPane.getItems().addAll(layoutMapHolder(), layoutTableView());
        splitPane.setDividerPosition(0, .58);
        workspace.getChildren().add(splitPane);
    }
    
    private Pane layoutMapHolder(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();    
        Pane pane = new Pane();
        mapHolder = new Pane();
        subregionHolder = new Pane();
        subregionHolder.setPrefSize(802,536);
        subregionHolder.setMinSize(802, 536);
        subregionHolder.setMaxSize(800, 536);
        mapHolder.setPrefSize(802,536);
        mapHolder.setMinSize(802, 536);
        mapHolder.setMaxSize(800, 536);
        pane.getChildren().add(mapHolder);
        mapHolder.getChildren().add(subregionHolder);
        return pane;
    }
    
    private TableView layoutTableView(){
        DataManager dataManager = (DataManager) app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TableView subregionTable = new TableView();
        subregionTable.setEditable(false);
        TableColumn regionCol = new TableColumn<>(props.getProperty(PropertyType.SUBREGION_TABLE_HEADER));
        TableColumn capitalCol = new TableColumn<>(props.getProperty(PropertyType.CAPITAL_TABLE_HEADER));
        TableColumn leaderCol = new TableColumn<>(props.getProperty(PropertyType.LEADER_TABLE_HEADER));
        regionCol.setCellValueFactory(new PropertyValueFactory<Subregion, String>("name"));
        capitalCol.setCellValueFactory(new PropertyValueFactory<String, String>("capital"));
        leaderCol.setCellValueFactory(new PropertyValueFactory<String, String>("leader"));
        regionCol.prefWidthProperty().bind(subregionTable.widthProperty().divide(3.0));
        capitalCol.prefWidthProperty().bind(subregionTable.widthProperty().divide(3.0));
        leaderCol.prefWidthProperty().bind(subregionTable.widthProperty().divide(3.0));
        Subregion testRegion = new Subregion("United States", "Washington D.C.", "Obama");
        Subregion testRegion1 = new Subregion("TEST", "TEST", "TEST");
        Subregion testRegion2 = new Subregion("Another Test", "Another Test", "Another Test");

        
        dataManager.addItem(testRegion);
        dataManager.addItem(testRegion1);
        dataManager.addItem(testRegion2);

        subregionTable.getColumns().addAll(regionCol, capitalCol, leaderCol);
        subregionTable.setItems(dataManager.getData());
       
        
        return subregionTable;
    }
    
    public void initHandlers(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        changeMapNameButton.setOnAction(e -> {
            controller.launchChangeNameWindow();
        });
        playSubregionAnthemButton.setOnAction(e -> {
            if (playing) {
                playSubregionAnthemButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.PAUSE_ICON))));
                playing = false;

            } else {
                playSubregionAnthemButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.PLAY_ICON))));
                playing = true;
            }
        });
    }

    @Override
    public void reloadWorkspace() {
        setHardCodedValues();
    }

    @Override
    public void initStyle() {
        
    }
    


      public void setHardCodedValues() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        Map andorraMap = dataManager.getMap().get(0);
        mapHolder.setStyle("-fx-background-color: #" + andorraMap.getBackgroundColor());
        for (int i = 0; i < andorraMap.getImagePaths().size(); i++) {
            Image image = new Image(andorraMap.getImagePaths().get(i));
            ImageView imageView = new ImageView(image);
            mapHolder.getChildren().add(imageView);
            imageView.setX(andorraMap.getImageLocationsX().get(i));
            imageView.setY(andorraMap.getImageLocationsY().get(i));           
        }
        render();
    }
    
          public void render(){
        ArrayList<Subregion> polygons = controller.getXAndY();
        for(int i = 0; i < polygons.size(); i++){
            subregionHolder.getChildren().add(polygons.get(i).getPolygon());
            
        }   
    }  
          
        
}
