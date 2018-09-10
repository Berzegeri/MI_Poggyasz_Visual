package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	Parent root;

	@Override
	public void start(Stage primaryStage) throws IOException {

			root = FXMLLoader.load(getClass().getResource("Poggyasz.fxml"));
			primaryStage.setTitle("Poggyasz");
			Scene scene = new Scene(root, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
