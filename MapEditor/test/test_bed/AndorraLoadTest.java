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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.MapEditorApp;
import me.data.DataManager;
import me.data.Map;
import me.data.Subregion;
import me.file.FileManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import saf.AppTemplate;
import static saf.settings.AppStartupConstants.PATH_WORK;
import static test_bed.TestLoad.dataManager;
import static test_bed.TestSaveAndorra.dataManager;

/**
 *
 * @author Tyler
 */
public class AndorraLoadTest {
    AppTemplate app;
    DataManager dataManager = new DataManager((MapEditorApp) app);
    
    public AndorraLoadTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of main method, of class TestLoad.
     */
    @Test
    public void testAndorra() {
                FileManager fileManager = new FileManager();
        try {
                fileManager.loadData(dataManager, "./work/testAnd.rvme");
                fileManager.loadCoords(dataManager, dataManager.getRawMapData());
            } catch (IOException ex) {
                Logger.getLogger(TestSaveAndorra.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        Assert.assertEquals("Map Name", "Andorra", dataManager.getMapName());
        Assert.assertEquals("Map Name", "Andorra", dataManager.getMapName());
        Assert.assertEquals("Map Name", "Andorra", dataManager.getMapName());
        Assert.assertEquals("Map Name", "Andorra", dataManager.getMapName());
        Assert.assertEquals("Map Name", "Andorra", dataManager.getMapName());
        
    }
    
    
    
}