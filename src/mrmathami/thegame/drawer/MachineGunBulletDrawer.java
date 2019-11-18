package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import mrmathami.thegame.entity.GameEntity;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class MachineGunBulletDrawer implements EntityDrawer {
	private final RadialGradient gradient = new RadialGradient(
			0.0,
			0.0,
			0.5,
			0.5,
			1.0,
			true,
			CycleMethod.NO_CYCLE,
			new Stop(0.0, Color.WHITE),
			new Stop(0.5, Color.YELLOW),
			new Stop(1.0, Color.RED)
	);

	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		//graphicsContext.setFill(gradient);
		//graphicsContext.fillOval(screenPosX, screenPosY, screenWidth, screenHeight);
		try {
			Image image = new Image(new FileInputStream(".\\res\\image\\MachineBullet.png"));
			Image image1 = DeleteWhiteImage.deleteWhiteImage(image);
			graphicsContext.drawImage(image1, screenPosX, screenPosY);
		}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}
	}
}
