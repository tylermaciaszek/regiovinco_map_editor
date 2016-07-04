/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drivers;

import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import me.gui.NewMapDialog;

/**
 *
 * @author Tyler
 */
public class NewMapDialogDriver extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NewMapDialog testDialog = new NewMapDialog();
        testDialog.showDialog();
    }
    
    public static void main(String[] args) {
	Locale.setDefault(Locale.US);
	launch(args);
    }
}
