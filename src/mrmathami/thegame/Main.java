package mrmathami.thegame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Main class. Entry point of the game.
 */
public final class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Canvas canvasMainMenu = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		StackPane stackPaneMainMenu = new StackPane();
		stackPaneMainMenu.getChildren().add(canvasMainMenu);
		Scene sceneMainMenu = new Scene(stackPaneMainMenu);
		primaryStage.setScene(sceneMainMenu);
		GraphicsContext gcMainMenu = canvasMainMenu.getGraphicsContext2D();
		try {
			Image imageMainMenu = new Image(new FileInputStream("C:\\Users\\user\\Documents\\GitHub\\thegame\\res\\image\\MainMenu.png"));
			gcMainMenu.drawImage(imageMainMenu, 0, 0);
		}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
		primaryStage.show();
		Rectangle rectanglePlay = new Rectangle(404, 171, 316, 126);
		canvasMainMenu.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(rectanglePlay.contains(mouseEvent.getX(), mouseEvent.getY())) {
					final Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
					final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
					final GameController gameController = new GameController(graphicsContext);

					canvas.setFocusTraversable(true);
					graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);


					// keyboard and mouse events to catch. Add if you need more
					canvas.setOnKeyPressed(gameController::keyDownHandler);
					canvas.setOnKeyReleased(gameController::keyUpHandler);
					//		canvas.setOnKeyTyped(...);

					canvas.setOnMousePressed(gameController::mouseDownHandler);
					canvas.setOnMouseReleased(gameController::mouseUpHandler);
					//		canvas.setOnMouseClicked(...);
					//		canvas.setOnMouseMoved(...);


					primaryStage.setResizable(false);
					primaryStage.setTitle(Config.GAME_NAME);
					primaryStage.setOnCloseRequest(gameController::closeRequestHandler);
					primaryStage.setScene(new Scene(new StackPane(canvas)));
					primaryStage.show();

					gameController.start();
				}
			}
		});
	}
}