import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

       FXMLLoader loader = new FXMLLoader(getClass().getResource("StartWindow.fxml"));
        Scene scene = new Scene(loader.load());
        StartWindowController startWindowController = (StartWindowController) loader.getController();
        stage.setScene(scene);
        startWindowController .setStage(stage);
        stage.show();
        startWindowController .getPremiers();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
