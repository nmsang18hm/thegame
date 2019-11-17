package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.entity.GameEntity;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class SmallerEnemyDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		//graphicsContext.setFill(Color.MAGENTA);
		//graphicsContext.fillRoundRect(screenPosX, screenPosY, screenWidth, screenHeight, 4, 4);
		try {
			Image image = new Image(new FileInputStream(".\\res\\image\\SmallerEnemy.png"));
			Image image1 = DeleteWhiteImage.deleteWhiteImage(image);
			graphicsContext.drawImage(image1, screenPosX, screenPosY);
		}
		catch (FileNotFoundException e) {
			System.out.println("SmallerEnemy Image Not found");
		}
	}
}
