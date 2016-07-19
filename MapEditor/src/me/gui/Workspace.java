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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import saf.components.AppWorkspaceComponent;
import me.MapEditorApp;
import me.PropertyType;
import me.controller.Controller;
import me.data.DataManager;
import me.data.Map;
import me.data.Subregion;
import me.file.FileManager;
import properties_manager.PropertiesManager;
import static saf.settings.AppPropertyType.NEW_ERROR_MESSAGE;
import static saf.settings.AppPropertyType.NEW_ERROR_TITLE;
import static saf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static saf.settings.AppStartupConstants.PATH_WORK;

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
    StackPane mapHolder;
    Pane subregionHolder;
    TableView subregionTable;
    Button exportButton;
    Group polygonGroup;
    ProgressBar progressBar;
    SimpleDoubleProperty prop;
    private static final double EPSILON = 0.0000005;
    FlowPane topToolbar;
    ColorPicker changeMapBackgroundColor;

    public Workspace(MapEditorApp initApp) {
        app = initApp;
        workspace = new Pane();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager dataManager = (DataManager) app.getDataComponent();
        controller = new Controller(app);
        playing = false;
        polygonGroup = new Group();
        prop = new SimpleDoubleProperty(0);
        progressBar = new ProgressBar();
        progressBar.progressProperty().bind(prop);
        layoutGUI();
        initHandlers();

    }

    private void layoutGUI() {
        layoutEditToolbar();
        newMapDialog = new NewMapDialog();
        //newMapDialog.showDialog();
        subRegionDialog = new SubRegionDialog();
        //subRegionDialog.showDialog();
        layoutSplitPane();
    }

    private void layoutEditToolbar() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        topToolbar = (FlowPane) app.getGUI().getAppPane().getTop();
        exportButton = new Button();
        exportButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.EXPORT_ICON))));
        topToolbar.getChildren().add(exportButton);
        HBox editToolbar = new HBox();
        editToolbar.setSpacing(20);
        Label mapColorLabel = new Label(props.getProperty(PropertyType.MAP_BACKGROUND_COLOR));
        Label borderColorLabel = new Label(props.getProperty(PropertyType.MAP_BORDER_COLOR));
        Label borderThicknessLabel = new Label(props.getProperty(PropertyType.MAP_THICKNESS));
        changeMapBackgroundColor = new ColorPicker();
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
        editToolbar.setPadding(new Insets(0, 0, 0, 20));
        topToolbar.getChildren().add(editToolbar);
    }

    private void layoutSplitPane() {
        SplitPane splitPane = new SplitPane();
        splitPane.prefWidthProperty().bind(app.getGUI().getPrimaryScene().widthProperty());
        splitPane.prefHeightProperty().bind(app.getGUI().getPrimaryScene().heightProperty());
        splitPane.getItems().addAll(layoutMapHolder(), layoutTableView());
        splitPane.setDividerPosition(0, .58);
        workspace.getChildren().add(splitPane);
    }

    private Pane layoutMapHolder() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Pane pane = new Pane();
        mapHolder = new StackPane();
        mapHolder.setPrefSize(802, 536);
        mapHolder.setMinSize(802, 536);
        mapHolder.setMaxSize(800, 536);
        pane.getChildren().add(mapHolder);
        mapHolder.getChildren().add(polygonGroup);
        return pane;
    }

    private TableView layoutTableView() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        subregionTable = new TableView();
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
        subregionTable.getColumns().addAll(regionCol, capitalCol, leaderCol);

        return subregionTable;
    }

    public void initHandlers() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager dataManager = (DataManager) app.getDataComponent();
        FileManager fileManager = new FileManager();
        Button newButton = (Button)topToolbar.getChildren().get(0);
        
        changeMapBackgroundColor.setOnAction(e ->{
            mapHolder.setBackground(new Background(new BackgroundFill(changeMapBackgroundColor.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));
        });
        
        newButton.addEventHandler(ActionEvent.ACTION, (e)-> {
            NewMapDialog newDialog = new NewMapDialog();
            newDialog.showDialog();
            dataManager.setRawMapData(newDialog.getRawData());
            reloadWorkspace();
            render();
            //System.out.print(dataManager.getMap().getSubregionsList());

        });
        
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
        exportButton.setOnAction(e -> {
            try {
                //DataManager dataManager = (DataManager) app.getDataComponent();
                FileChooser fc = new FileChooser();
                fc.setInitialDirectory(new File(PATH_WORK));
                fc.setTitle(props.getProperty(SAVE_WORK_TITLE));

                File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
                fileManager.exportData(dataManager, selectedFile.getPath());
            } catch (IOException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public void reloadWorkspace() {
        FileManager fileManager = new FileManager();
        DataManager dataManager = (DataManager) app.getDataComponent();
        mapHolder.setStyle("-fx-background-color: #ffffff");
        try {
            fileManager.loadCoords(dataManager, dataManager.getRawMapData());
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //setHardCodedValues();
        //System.out.println("Size:" + dataManager.getSubregionList());*/
    }

    @Override
    public void initStyle() {

    }

    /*public void setHardCodedValues() {
        int t = 0;
        DataManager dataManager = (DataManager) app.getDataComponent();
        Map andorraMap = dataManager.getMap().get(t);
        System.out.print(dataManager.getMap().size());
        mapHolder.setStyle("-fx-background-color: #" + andorraMap.getBackgroundColor());
        for (int i = 0; i < andorraMap.getImagePaths().size(); i++) {
            Image image = new Image(andorraMap.getImagePaths().get(i));
            ImageView imageView = new ImageView(image);
            mapHolder.getChildren().add(imageView);
            imageView.setX(andorraMap.getImageLocationsX().get(i));
            imageView.setY(andorraMap.getImageLocationsY().get(i));
        }
        t++;
        render();
    }*/

    public void render() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        Controller controller = new Controller(app);
        ArrayList<Subregion> polygons = controller.getXAndY();
        dataManager.getMap().setSubregionsList(polygons);
        /*final Task<Void> task = new Task<Void>() {
            final int N_ITERATIONS = 30;

            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < N_ITERATIONS; i++) {
                    updateProgress(i + 1, N_ITERATIONS);
                    Thread.sleep(10);
                }

                return null;
            }
        };

        final ProgressBar progress = new ProgressBar();
        progress.progressProperty().bind(
                task.progressProperty()
        );
        progress.progressProperty().addListener(observable -> {
            if (progress.getProgress() >= 1 - EPSILON) {
                progress.setStyle("-fx-accent: forestgreen;");
            }
            if (progress.getProgress() == 1) {
                mapHolder.getChildren().remove(progress);
            }
        });

        mapHolder.getChildren().add(progress);
        final Thread thread = new Thread(task, "task-thread");
        thread.setDaemon(true);
        thread.start();*/
        
        controller.setPolygonColors();

        for (int i = 0; i < polygons.size(); i++) {
            polygonGroup.getChildren().add(polygons.get(i).getPolygon());
        }
        setScale();
        for(int i = 0; i< dataManager.getMap().getSubregionsList().size(); i++){
            int subregionNumber = i+1;
            dataManager.getMap().getSubregionsList().get(i).setName("Subregion " + subregionNumber);
        }
        ObservableList<Subregion> observable = FXCollections.observableArrayList((dataManager.getMap().getSubregionsList()));
        subregionTable.setItems(observable);
    }
    
    public void setScale(){
        DataManager dataManager = (DataManager) app.getDataComponent();
        double xDiff = dataManager.getMaxX() - dataManager.getSmallestX();
        double xScale = dataManager.getMapWidth() / xDiff;
        double yDiff = dataManager.getMaxY() - dataManager.getSmallestY();
        double yScale = dataManager.getMapHeight() / yDiff;
        
        if(xScale < yScale){
            polygonGroup.setScaleX(xScale);
            polygonGroup.setScaleY(xScale);
        }else{
            polygonGroup.setScaleX(yScale);
            polygonGroup.setScaleY(yScale);
        }

    }

}
