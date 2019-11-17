package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.drawer.EntityDrawer;
import mrmathami.thegame.entity.GameEntity;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class MountainDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		//graphicsContext.setFill(Color.DARKGREEN);
		//graphicsContext.fillRect(screenPosX, screenPosY, screenWidth, screenHeight);
		try {
			Image image = new Image(new FileInputStream("C:\\Users\\user\\Documents\\GitHub\\thegame\\res\\image\\Mountain.png"));
			graphicsContext.drawImage(image, screenPosX, screenPosY);
		}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
	}
}
