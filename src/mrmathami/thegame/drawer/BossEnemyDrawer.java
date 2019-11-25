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

public final class BossEnemyDrawer implements EntityDrawer {
	@Override
	public void draw(long tickCount, @Nonnull GraphicsContext graphicsContext, @Nonnull GameEntity entity, double screenPosX, double screenPosY, double screenWidth, double screenHeight, double zoom) {
		//graphicsContext.setFill(Color.DARKVIOLET);
		//graphicsContext.fillRoundRect(screenPosX, screenPosY, screenWidth, screenHeight, 4, 4);
		try {
			AbstractEnemy enemy = (AbstractEnemy) entity;
			graphicsContext.setFill(Color.GREEN);
			graphicsContext.fillRect(screenPosX, screenPosY -20, ((double)enemy.getHealth()/(double)Config.BOSS_ENEMY_HEALTH)*Config.TILE_SIZE*Config.BOSS_ENEMY_SIZE,14);

			Image image = new Image(new FileInputStream(".\\res\\image\\BossEnemy.png"));
			Image image1 = DeleteWhiteImage.deleteWhiteImage(image);
			graphicsContext.drawImage(image1, screenPosX, screenPosY);
		}
		catch (FileNotFoundException e) {
			System.out.println("Does not found BossEnemy image ");
		}
	}
}
