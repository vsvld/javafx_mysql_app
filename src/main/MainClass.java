package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainWindowController;

import java.io.IOException;

public class MainClass extends Application {	
	private Stage primaryStage;    
  
	public MainClass() {              
    }    
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Students Marks");
        initMainWindow();        
    }

    /**
     * Initializes the root layout.
     */
    public void initMainWindow() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainClass.class.getResource("/view/MainWindow.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
         // Give the controller access to the main app.
            MainWindowController mainController = loader.getController();
//            mainController.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
   
    /**
     * Returns the main stage.
     * @return
     */
	public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}