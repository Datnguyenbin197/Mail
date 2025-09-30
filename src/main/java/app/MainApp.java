package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Secure Mail Client");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/ui/" + fxml));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        // không cần gọi lại primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
