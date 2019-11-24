package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class TankerEnemyDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		//graphicsContext.setFill(Color.VIOLET);
		//graphicsContext.fillRoundRect(screenPosX, screenPosY, screenWidth, screenHeight, 4, 4);
		try {
			AbstractEnemy enemy = (AbstractEnemy) entity;
			graphicsContext.setFill(Color.GREEN);
			graphicsContext.fillRect(screenPosX, screenPosY -20, Config.TANKER_ENEMY_SIZE*Config.TILE_SIZE,14);

			Image image = new Image(new FileInputStream(".\\res\\image\\TankerEnemy.png"));
			Image image1 = DeleteWhiteImage.deleteWhiteImage(image);
			graphicsContext.drawImage(image1, screenPosX, screenPosY);
		}
		catch (FileNotFoundException e) {
			System.out.println("Does not found TankerEnemy image");
		}
	}
}
