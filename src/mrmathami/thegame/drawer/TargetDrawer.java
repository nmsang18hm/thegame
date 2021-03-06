package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Target;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class TargetDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		//graphicsContext.setStroke(Color.DARKRED);
		//graphicsContext.setLineWidth(4);
		//graphicsContext.strokeRect(screenPosX, screenPosY, screenWidth, screenHeight);
		//graphicsContext.setFill(Color.BLACK);
		//graphicsContext.fillText("Health: " + ((Target)entity).getHealth(), 200, 12);
		try {
			Image image = new Image(new FileInputStream(".\\res\\image\\Target.png"));
			Image image1 = DeleteWhiteImage.deleteWhiteImage(image);
			graphicsContext.drawImage(image1, screenPosX, screenPosY);
		}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
		graphicsContext.setFill(Color.RED);
		graphicsContext.setFont(new Font("Time New Roman", 60));
		graphicsContext.fillText(((Target)entity).getHealth() + "", 5* Config.TILE_SIZE + 5, 10*Config.TILE_SIZE - 12);
	}
}
