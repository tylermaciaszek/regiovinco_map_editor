package drivers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import me.gui.SubRegionDialog;

/**
 *
 * @author Tyler
 */
public class SubregionDialogDriver extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        SubRegionDialog testDialog = new SubRegionDialog();
        testDialog.showDialog();
    }
    
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }
}
