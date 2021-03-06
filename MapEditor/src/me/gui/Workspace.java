/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.gui;

import audio_manager.AudioManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import saf.components.AppWorkspaceComponent;
import me.MapEditorApp;
import me.PropertyType;
import me.controller.Controller;
import me.data.DataManager;
import me.data.ImageData;
import me.data.Map;
import me.data.Subregion;
import me.file.FileManager;
import properties_manager.PropertiesManager;
import static saf.settings.AppPropertyType.NEW_ERROR_MESSAGE;
import static saf.settings.AppPropertyType.NEW_ERROR_TITLE;
import static saf.settings.AppPropertyType.SAVE_WORK_TITLE;
import static saf.settings.AppStartupConstants.PATH_WORK;
import saf.ui.AppMessageDialogSingleton;

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
    ColorPicker changeMapBorderColor;
    Slider changeMapBorderThickness;
    Button randomizeSubregionColorsButton;
    boolean isFocused;
    Slider zoomBar;
    double zoom = 0;
    double initialZoom = 0;
    Button addImageButton;
    ObservableList<Subregion> observable;
    ArrayList colList;
    private ImageView removeImageView;
    Button removeImageButton;
    static boolean loading = false;
    DropShadow ds = new DropShadow( 20, Color.AQUA );
    Button saveButton;
    int color;
    static int indexOfInitialRegion;
    Pane pane;
    TextField changeX;
    TextField changeY;
    Button change;
    ImageData removeImageData;

    public Workspace(MapEditorApp initApp) {
        app = initApp;
        workspace = new Pane();
        colList = new ArrayList<>();
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
        DataManager dataManager = (DataManager)app.getDataComponent();
        topToolbar = (FlowPane) app.getGUI().getAppPane().getTop();
        exportButton = new Button();
        saveButton = (Button) topToolbar.getChildren().get(2);
        exportButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.EXPORT_ICON))));
        topToolbar.getChildren().add(exportButton);
        HBox editToolbar = new HBox();
        editToolbar.setSpacing(5);
        Label mapColorLabel = new Label(props.getProperty(PropertyType.MAP_BACKGROUND_COLOR));
        Label borderColorLabel = new Label(props.getProperty(PropertyType.MAP_BORDER_COLOR));
        Label borderThicknessLabel = new Label(props.getProperty(PropertyType.MAP_THICKNESS));
        changeMapBackgroundColor = new ColorPicker();
        changeMapBorderColor = new ColorPicker();
        changeMapBorderThickness = new Slider();
        changeMapBorderThickness = new Slider(0, 20, 1);
        changeMapBorderThickness.setMajorTickUnit(1);
        changeMapBorderThickness.setMinorTickCount(0);
        changeMapBorderThickness.setSnapToTicks(true);
        changeMapBorderThickness.setFocusTraversable(false);
        VBox mapBackgroundHolder = new VBox();
        mapBackgroundHolder.getChildren().addAll(mapColorLabel, changeMapBackgroundColor);
        VBox borderColorHolder = new VBox();
        borderColorHolder.getChildren().addAll(borderColorLabel, changeMapBorderColor);
        VBox borderThicknessHolder = new VBox();
        borderThicknessHolder.getChildren().addAll(borderThicknessLabel, changeMapBorderThickness);
        randomizeSubregionColorsButton = new Button(props.getProperty(PropertyType.SUBREGION_COLORS));
        playSubregionAnthemButton = new Button();
        Image playImage = new Image(props.getProperty(PropertyType.PLAY_ICON));
        playSubregionAnthemButton.setGraphic(new ImageView(playImage));
        changeMapNameButton = new Button(props.getProperty(PropertyType.CHANGE_MAP_NAME));
        addImageButton = new Button();
        removeImageButton = new Button();
        Image addImage = new Image(props.getProperty(PropertyType.ADD_IMAGE));
        Image removeImage = new Image(props.getProperty(PropertyType.REMOVE_IMAGE));
        addImageButton.setGraphic(new ImageView(addImage));
        removeImageButton.setGraphic(new ImageView(removeImage));
        HBox zoomIcons = new HBox();
        VBox zoomHolder = new VBox();
        ImageView zoomOut = new ImageView(new Image(props.getProperty(PropertyType.ZOOM_OUT)));
        ImageView zoomIn = new ImageView(new Image(props.getProperty(PropertyType.ZOOM_IN)));
        zoomIcons.getChildren().addAll(zoomOut, zoomIn);
        zoomBar = new Slider(0, 5000, 1);
        zoomBar.setMajorTickUnit(1);
        zoomBar.setMinorTickCount(0);
        zoomBar.setSnapToTicks(true);
        zoomBar.setFocusTraversable(false);
        zoomIcons.setSpacing(80);
        zoomHolder.getChildren().addAll(zoomIcons, zoomBar);
        changeX = new TextField();
        changeX.setFocusTraversable(false);
        changeX.setMaxWidth(50);
        changeX.setText("Width");
        changeY = new TextField();
        changeY.setFocusTraversable(false);
        changeY.setText("Height");
        changeY.setMaxWidth(50);
        change = new Button("Change");
        HBox changeHolder = new HBox();
        Label changeLabel = new Label("Change Dimensions");
        VBox changeBox = new VBox();
        changeBox.getChildren().addAll(changeLabel, changeHolder);
        changeHolder.setSpacing(5);
        changeHolder.getChildren().addAll(changeX, changeY, change);
        editToolbar.getChildren().addAll(changeMapNameButton, addImageButton, removeImageButton, mapBackgroundHolder, borderColorHolder, borderThicknessHolder, randomizeSubregionColorsButton, playSubregionAnthemButton, zoomHolder, changeBox);
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
        pane = new Pane();
        mapHolder = new StackPane();
        mapHolder.setPrefSize(802, 536);
        mapHolder.setMinSize(802, 536);
        mapHolder.setMaxSize(802, 536);
        Rectangle clip = new Rectangle(802,536);

        mapHolder.setClip(clip);
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
        colList.add(regionCol);
        colList.add(capitalCol);
        colList.add(leaderCol);
        subregionTable.setFocusTraversable(false);

        return subregionTable;
    }

    public void initHandlers() {
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        DataManager dataManager = (DataManager) app.getDataComponent();
        FileManager fileManager = new FileManager();
        AudioManager audioManager = new AudioManager();
        Button newButton = (Button)topToolbar.getChildren().get(0);    
        
        Button loadButton = (Button)topToolbar.getChildren().get(1);
     
        loadButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            loading = true;
            saveButton.setDisable(false);
        });
        
        change.setOnAction(e->{
            mapHolder.setMinSize(Double.parseDouble(changeX.getText()), Double.parseDouble(changeY.getText()));
            mapHolder.setMaxSize(Double.parseDouble(changeX.getText()), Double.parseDouble(changeY.getText()));
            mapHolder.setPrefSize(Double.parseDouble(changeX.getText()), Double.parseDouble(changeY.getText()));
            dataManager.setMapWidth(Double.parseDouble(changeX.getText()));
            dataManager.setMapHeight(Double.parseDouble(changeY.getText()));
            Rectangle clip = new Rectangle(Double.parseDouble(changeX.getText()), Double.parseDouble(changeY.getText()));
            mapHolder.setClip(clip);
        });
        
        
        
        
        zoomBar.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.longValue() > oldValue.longValue()) {
                zoom = (dataManager.getMap().getZoomLevel() + newValue.doubleValue() - oldValue.doubleValue() );
            }else if(newValue.longValue() < oldValue.longValue()){
                zoom = (dataManager.getMap().getZoomLevel() - (oldValue.longValue()-newValue.longValue()));
            }
            polygonGroup.setScaleX(zoom);
            polygonGroup.setScaleY(zoom);
            dataManager.getMap().setZoomLevel(zoom);
            renderAfter();
        });

        changeMapBorderThickness.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            for (int i = 0; i < dataManager.getMap().getSubregionsList().size(); i++) {
                changeMapBorderThickness.setValue((double) newValue);
                dataManager.getMap().getSubregionsList().get(i).getPolygon().setStrokeWidth(changeMapBorderThickness.getValue()/dataManager.getMap().getZoomLevel());
                dataManager.getMap().setBorderThickness(((double) newValue/dataManager.getMap().getZoomLevel()));
            }
            renderAfter();
            setScale();
        });
        
        changeMapBorderColor.setOnAction(e ->{
            Map map = dataManager.getMap();
            for(int i = 0; i < map.getSubregionsList().size(); i++){
                map.getSubregionsList().get(i).getPolygon().setStroke(changeMapBorderColor.getValue());
                //map.getSubregionsList().get(i).getPolygon().setStrokeWidth(map.getBorderThickness());
                map.setBorderColor(toRGBCode(changeMapBorderColor.getValue()));
            }
        });
        
        changeMapBackgroundColor.setOnAction(e ->{
            mapHolder.setBackground(new Background(new BackgroundFill(changeMapBackgroundColor.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));
            pane.setBackground(new Background(new BackgroundFill(changeMapBackgroundColor.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));
            dataManager.getMap().setBackgroundColor(toRGBCode(changeMapBackgroundColor.getValue()));
        });
    
        newButton.addEventHandler(ActionEvent.ACTION, (e) -> {
            NewMapDialog newDialog = new NewMapDialog();
            newDialog.showDialog();
            dataManager.setRawMapData(newDialog.getRawData());
            dataManager.setMapParentDirectory(newDialog.getParentDir());
            dataManager.setMapName(newDialog.getName());
            new File("./work/" + dataManager.getMapName()).mkdirs();
            dataManager.setWorkDir("./work/" + dataManager.getMapName() + "\\");
            new File(dataManager.getMapParentDirectory() + "\\" + dataManager.getMapName()).mkdirs();
            dataManager.setExpDir(dataManager.getMapParentDirectory() + "\\" + dataManager.getMapName());
             File soundCheck = new File(dataManager.getWorkDir() + dataManager.getMapName() + " National Anthem.mid");
            if(!soundCheck.exists()){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show("Sound Check", "Put Nation Anthem file in the work directory then close this to be able to play music");
            }
            try {
                audioManager.loadAudio("anthem", dataManager.getWorkDir() + dataManager.getMapName() + " National Anthem.mid");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InvalidMidiDataException | MidiUnavailableException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
            reloadWorkspace();
            render();
            if (!loading) {
                setScaleInitial();
            }

        });
        
        mapHolder.setOnMouseClicked(e ->{
           mapHolder.requestFocus();
        });
        
        app.getGUI().getPrimaryScene().setOnKeyPressed(e -> {
            
           if(null != e.getCode())switch (e.getCode()) {
                case UP:
                    polygonGroup.setTranslateY(polygonGroup.getTranslateY() + 10);
                    break;
                case DOWN:
                    polygonGroup.setTranslateY(polygonGroup.getTranslateY() - 10);
                    break;
                case RIGHT:
                    polygonGroup.setTranslateX(polygonGroup.getTranslateX() - 10);
                    break;
                case LEFT:
                    polygonGroup.setTranslateX(polygonGroup.getTranslateX() + 10);
                    break;
                default:
                    break;
            }
        });
      
        
        randomizeSubregionColorsButton.setOnAction(e ->{
            controller.randomizeColors();
        });
        
        changeMapNameButton.setOnAction(e -> {
            controller.launchChangeNameWindow();
        });
        
        
        
        addImageButton.setOnAction((ActionEvent e) ->{
            Map map = dataManager.getMap();
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_WORK));
            fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
            File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
            ImageView image = null;
            map.getImagePaths().add(selectedFile.getPath());
            
            try {
                image = new ImageView(new Image(selectedFile.toURI().toURL().toString()));
                ImageData imageData = new ImageData(image, image.getLayoutX(), image.getLayoutY(), selectedFile.toURI().toString());
                map.getImageData().add(imageData);
                image.setLayoutX(imageData.getX());
                image.setLayoutY(imageData.getY());
                for (ImageData imageData1 : map.getImageData()) {
                    imageData1.getImage().setOnMouseDragged(k -> {
                        imageData1.getImage().setTranslateX(imageData1.getImage().getTranslateX() + k.getX());
                        imageData1.getImage().setTranslateY(imageData1.getImage().getTranslateY()+ k.getY());
                        imageData1.setX((int) imageData1.getImage().getTranslateX());
                        imageData1.setY((int) imageData1.getImage().getTranslateY());
                        
                    });
                    imageData1.getImage().setOnMouseClicked(l -> {
                        if (removeImageView != null) {
                            removeImageView.setEffect(null);
                        }
                        removeImageView = imageData1.getImage();
                        removeImageView.setEffect(ds);
                        removeImageData = imageData1;
                    });
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
            mapHolder.getChildren().add(image);
            
            
            
        });
        
        playSubregionAnthemButton.setOnAction(e -> {
            if (playing) {
                audioManager.stop("anthem");
                playSubregionAnthemButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.PLAY_ICON))));
                playing = false;

            } else {
                audioManager.play("anthem", true);
                playSubregionAnthemButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.PAUSE_ICON))));
                playing = true;
            }
        });
        
        removeImageButton.setOnAction(e->{
            dataManager.getImageList().remove(removeImageView);
            dataManager.getMap().getImageData().remove(removeImageData);
            mapHolder.getChildren().remove(removeImageView);

        });

        exportButton.setOnAction(e -> {
                WritableImage wi = new WritableImage((int)dataManager.getMapWidth(),(int)dataManager.getMapHeight());
                WritableImage snapshot = mapHolder.snapshot(new SnapshotParameters(), wi);
                File output = new File(dataManager.getExpDir()+"\\"+dataManager.getMapName()+".png");
            try {
                output.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
            } catch (IOException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                FileChooser fc = new FileChooser();
                fc.setInitialDirectory(new File(dataManager.getExpDir()));
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
        AudioManager audioManager = new AudioManager();
        
        try {
            fileManager.loadCoords(dataManager, dataManager.getRawMapData());
        } catch (IOException ex) {
            Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (loading) {
            render();
            loading = false;
            Map map = dataManager.getMap();
            System.out.println("zoom"+map.getZoomLevel());
            polygonGroup.setScaleX(map.getZoomLevel());
            polygonGroup.setScaleY(map.getZoomLevel());
            for (int i = 0; i < map.getSubregionsList().size(); i++) {
                map.getSubregionsList().get(i).getPolygon().setStroke(Color.valueOf("#" + map.getBorderColor()));
                map.getSubregionsList().get(i).getPolygon().setStrokeWidth(map.getBorderThickness());
            }
            //changeMapBorderColor.setValue(Color.valueOf("#"+map.getBackgroundColor()));
            mapHolder.setBackground(new Background(new BackgroundFill(Color.valueOf("#"+map.getBackgroundColor()), CornerRadii.EMPTY, Insets.EMPTY)));
            pane.setBackground(new Background(new BackgroundFill(Color.valueOf("#"+map.getBorderColor()), CornerRadii.EMPTY, Insets.EMPTY)));
            changeMapBorderColor.setValue(Color.valueOf("#"+map.getBorderColor()));
            zoomBar.setValue(map.getZoomLevel()-map.getInitialZoom());
            dataManager.setWorkDir("./work/"+dataManager.getMapName()+"\\");
            dataManager.setExpDir(dataManager.getMapParentDirectory()+"\\"+dataManager.getMapName());
            for(int i = 0; i < map.getSubregionsList().size(); i++) {
                map.getSubregionsList().get(i).setName(map.getLoadSub().get(i).getName());
                map.getSubregionsList().get(i).setLeader(map.getLoadSub().get(i).getLeader());
                map.getSubregionsList().get(i).setCapital(map.getLoadSub().get(i).getCapital());
            }
            refreshTableView(subregionTable, colList, dataManager.getMap().getSubregionsList());
            for (int i = 0; i < map.getImageData().size(); i++) {
                ImageView image = new ImageView(new Image(map.getImageData().get(i).getImagePath()));
                System.out.print(image.toString());
                dataManager.getImageList().add(image);
                for (ImageData imageData1 : map.getImageData()) {
                    imageData1.getImage().setOnMouseDragged(k -> {
                        imageData1.getImage().setTranslateX(imageData1.getImage().getTranslateX() + k.getX());
                        imageData1.getImage().setTranslateY(imageData1.getImage().getTranslateY()+ k.getY());
                        imageData1.setX((int) imageData1.getImage().getTranslateX());
                        imageData1.setY((int) imageData1.getImage().getTranslateY()); 
                    });
                    imageData1.getImage().setOnMouseClicked(l -> {
                        if (removeImageView != null) {
                            removeImageView.setEffect(null);
                        }
                        removeImageView = imageData1.getImage();
                        removeImageView.setEffect(ds);
                        removeImageData = imageData1;
                    });
                }
                mapHolder.getChildren().add(image);
            }
            
            File soundCheck = new File(dataManager.getWorkDir() + dataManager.getMapName() + " National Anthem.mid");
            if(!soundCheck.exists()){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show("Sound Check", "Put Nation Anthem file in the work directory then close this to be able to play music");
            }
            
            try {
                audioManager.loadAudio("anthem", dataManager.getWorkDir() + dataManager.getMapName() + " National Anthem.mid");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InvalidMidiDataException | MidiUnavailableException ex) {
                Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
            }
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            playSubregionAnthemButton.setOnAction(e -> {
                if (playing) {
                    audioManager.stop("anthem");
                    playSubregionAnthemButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.PLAY_ICON))));
                    playing = false;

            } else {
                audioManager.play("anthem", true);
                playSubregionAnthemButton.setGraphic(new ImageView(new Image(props.getProperty(PropertyType.PAUSE_ICON))));
                playing = true;
            }
        });
        }

    }

    @Override
    public void initStyle() {

    }

    public void renderAfter() {
        DataManager dataManager = (DataManager) app.getDataComponent();

        polygonGroup.getChildren().clear();
        ArrayList<Subregion> polygons = dataManager.getMap().getSubregionsList();
        for (int i = 0; i < polygons.size(); i++) {
            polygonGroup.getChildren().add(polygons.get(i).getPolygon());
        }
    }

    public void render() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        Controller controller = new Controller(app);
        ArrayList<Subregion> polygons = controller.getXAndY();
        dataManager.getMap().setSubregionsList(polygons);
        controller.setPolygonColors();
        if (!loading) {
            setScaleInitial();
        }
        for (int i = 0; i < polygons.size(); i++) {
            polygons.get(i).getPolygon().setStrokeWidth(1 / dataManager.getMap().getZoomLevel());
            polygonGroup.getChildren().add(polygons.get(i).getPolygon());
        }
        for (int i = 0; i < dataManager.getMap().getSubregionsList().size(); i++) {
            int subregionNumber = i + 1;
            dataManager.getMap().getSubregionsList().get(i).setName("Subregion " + subregionNumber);
        }
        observable = FXCollections.observableArrayList((dataManager.getMap().getSubregionsList()));
        
        subregionTable.setItems(observable);
        for (int i = 0; i < dataManager.getMap().getSubregionsList().size(); i++) {
            Subregion subregion = dataManager.getMap().getSubregionsList().get(i);
            Polygon polygon = dataManager.getMap().getSubregionsList().get(i).getPolygon();
            polygon.setOnMouseClicked(e -> {  
                if (isFocused) {
                    controller.setPolygonColors();
                    isFocused = false;
                }
                if (e.getButton() == MouseButton.SECONDARY) {
                    polygon.setFill(Color.rgb(subregion.getBlueColor(), subregion.getBlueColor(), subregion.getBlueColor()));
                    isFocused = false;
                }else{
                subregion.getPolygon().setFill(Color.valueOf("#FFFF00"));
                subregionTable.getSelectionModel().select(subregion);
                }
                isFocused = true;
                if (e.getClickCount() == 2) {
                    SubRegionDialog dialog = new SubRegionDialog();
                    //dialog.getPolyHolder().setScaleX(dataManager.getMap().getZoomLevel());
                    //dialog.getPolyHolder().setScaleY(dataManager.getMap().getZoomLevel());
                    dialog.setZoom(dataManager.getMap().getZoomLevel());
                    Polygon p2 = new Polygon();
                    p2.getPoints().addAll(polygon.getPoints());
                    p2.setStroke(polygon.getStroke());
                    p2.setFill(Color.rgb(subregion.getRedColor(), subregion.getGreenColor(), subregion.getBlueColor()));
                    p2.setStrokeWidth(polygon.getStrokeWidth());
                    dialog.setRegionName(subregion.getName());
                    dialog.setSubregionToEdit(subregion);
                    dialog.setRegionPolygon(p2);
                    if (!subregion.getName().contains("Subregion") && subregion.getName() != null) {
                        dialog.setFlagImage(dataManager.getWorkDir() + subregion.getName()+" Flag.png");
                    }
                    if (subregion.getLeader() != null && !"".equals(subregion.getLeader())) {
                        dialog.setLeaderImage(dataManager.getWorkDir() + subregion.getLeader()+".png");
                    }
                    dialog.showDialog();
                    refreshTableView(subregionTable, colList, dataManager.getMap().getSubregionsList());
                }

            });
        }

        subregionTable.setRowFactory(e->{
            TableRow<Subregion> row = new TableRow<>();
            Map map = dataManager.getMap();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    isFocused = false;
                    row.getItem().getPolygon().setFill(Color.rgb(row.getItem().getBlueColor(), row.getItem().getBlueColor(), row.getItem().getBlueColor()));
                }
                if (event.getClickCount() == 1 && !row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                     if (isFocused) {
                         controller.setPolygonColors();
                         isFocused = false;
                     }
                     row.getItem().getPolygon().setFill(Color.valueOf("#FFFF00"));
                     isFocused = true;
                 } else if (row.isEmpty()) {
                     for (int i = 0; i < map.getSubregionsList().size(); i++) {
                         color = map.getColorList().get(i);                      
                         map.getSubregionsList().get(i).getPolygon().setFill(Color.rgb(color, color, color));
                     }
                }
                Polygon polygon = row.getItem().getPolygon();
                Subregion subregion = row.getItem();
                indexOfInitialRegion = map.getSubregionsList().indexOf(subregion);

                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    SubRegionDialog dialog = new SubRegionDialog();
                    dialog.setZoom(dataManager.getMap().getZoomLevel());
                    Polygon p2 = new Polygon();
                    p2.getPoints().addAll(polygon.getPoints());
                    p2.setStroke(polygon.getStroke());
                    p2.setFill(Color.rgb(subregion.getRedColor(), subregion.getGreenColor(), subregion.getBlueColor()));
                    p2.setStrokeWidth(polygon.getStrokeWidth());
                    dialog.setRegionName(subregion.getName());
                    dialog.setSubregionToEdit(subregion);
                    dialog.setRegionPolygon(p2);
                    if (!subregion.getName().contains("Subregion") && subregion.getName() != null) {
                        dialog.setFlagImage(dataManager.getWorkDir() + subregion.getName() + " Flag.png");
                    }
                    if (subregion.getLeader() != null && !"".equals(subregion.getLeader())) {
                        dialog.setLeaderImage(dataManager.getWorkDir() + subregion.getLeader() + ".png");
                    }                
                    dialog.showDialog();
                    
                    
                    
                    refreshTableView(subregionTable, colList, dataManager.getMap().getSubregionsList());
                }
            
        });
            return row;
            
        });
    }
    
    public void setScaleInitial(){
        DataManager dataManager = (DataManager) app.getDataComponent();
        double xDiff = 0;
        double xScale = 0;
        double yDiff = 0;
        double yScale = 0;
        xDiff = dataManager.getMaxX() - dataManager.getSmallestX();
        xScale = dataManager.getMapWidth() / xDiff;
        yDiff = dataManager.getMaxY() - dataManager.getSmallestY();
        yScale = dataManager.getMapHeight() / yDiff;
        
        if(xScale < yScale){
            dataManager.getMap().setZoomLevel(xScale);
            polygonGroup.setScaleX(xScale);
            polygonGroup.setScaleY(xScale);
            initialZoom = xScale;
            dataManager.getMap().setInitialZoom(initialZoom);
        }else{
            dataManager.getMap().setZoomLevel(yScale);
            polygonGroup.setScaleX(yScale);
            polygonGroup.setScaleY(yScale);
            initialZoom = yScale;
            dataManager.getMap().setInitialZoom(initialZoom);
        }

    }

    private void setScale() {
        DataManager dataManager = (DataManager) app.getDataComponent();
        polygonGroup.setScaleX(dataManager.getMap().getZoomLevel());
        polygonGroup.setScaleY(dataManager.getMap().getZoomLevel());
    }
    
    public static String toRGBCode( Color color )
    {
        return String.format( "%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }
    
    public static <T,U> void refreshTableView(TableView<T> tableView, List<TableColumn<T,U>> columns, List<T> rows) {        
    tableView.getColumns().clear();
    tableView.getColumns().addAll(columns);

    ObservableList<T> list = FXCollections.observableArrayList(rows);
    tableView.setItems(list);
}


}
