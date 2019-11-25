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
import java.io.FileWriter;
import java.lang.InterruptedException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main class. Entry point of the game.
 */
public final class Main extends Application {
    public static void main(String[] args) {
		launch(args);

    }



    @Override
    public void start(Stage primaryStage) {
        final GameController gameController = new GameController(primaryStage);
    }

}