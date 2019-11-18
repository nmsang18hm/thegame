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
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Main class. Entry point of the game.
 */
public final class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	void playBackgroundMusic()
	{

		File soun = new File("./res/sound/nhacNenChinh.mp3");
		AudioClip clip = new AudioClip(soun.toURI().toString());
		clip.setCycleCount(10000);
		clip.play();
//		MediaPlayer player = new MediaPlayer(new Media(soun.toURI().toString()));
//		player.setStartTime(Duration.seconds(0));
//		player.setStopTime(Duration.hours(1000));
//		player.play();
	}

	@Override
	public void start(Stage primaryStage) {


		playBackgroundMusic();

		primaryStage.setTitle(Config.GAME_NAME);
		Canvas canvasMainMenu = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		StackPane stackPaneMainMenu = new StackPane();
		stackPaneMainMenu.getChildren().add(canvasMainMenu);
		Scene sceneMainMenu = new Scene(stackPaneMainMenu);
		primaryStage.setScene(sceneMainMenu);
		GraphicsContext gcMainMenu = canvasMainMenu.getGraphicsContext2D();
		try {
			Image imageMainMenu = new Image(new FileInputStream(".\\res\\image\\MainMenu.png"));
			gcMainMenu.drawImage(imageMainMenu, 0, 0);
		}
		catch (FileNotFoundException e) {
			System.out.println("Main Menu image not found");
		}
		primaryStage.show();
		Rectangle rectanglePlay = new Rectangle(404, 171, 316, 126);
		canvasMainMenu.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(rectanglePlay.contains(mouseEvent.getX(), mouseEvent.getY())) {
					final Canvas canvas = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
					final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
					final GameController gameController = new GameController(graphicsContext, canvas, primaryStage);

					canvas.setFocusTraversable(true);
					graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);


					// keyboard and mouse events to catch. Add if you need more
					canvas.setOnKeyPressed(gameController::keyDownHandler);
					canvas.setOnKeyReleased(gameController::keyUpHandler);
					//		canvas.setOnKeyTyped(...);

					canvas.setOnMousePressed(gameController::mouseDownHandler);
					//canvas.setOnMouseDragged(gameController::mouseMovedHandler);
					canvas.setOnMouseReleased(gameController::mouseUpHandler);
					//		canvas.setOnMouseClicked(...);


					primaryStage.setResizable(false);
					primaryStage.setOnCloseRequest(gameController::closeRequestHandler);
					primaryStage.setScene(new Scene(new StackPane(canvas)));
					primaryStage.show();

//					Thread t = new Thread(Main.this::playBackgroundMusic);
//					t.start();


					gameController.start();
				}
			}
		});

	}
}