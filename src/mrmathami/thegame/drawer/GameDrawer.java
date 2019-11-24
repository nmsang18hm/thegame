package mrmathami.thegame.drawer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mrmathami.thegame.Config;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.bullet.*;
import mrmathami.thegame.entity.enemy.*;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Road;
import mrmathami.thegame.entity.tile.Target;
import mrmathami.thegame.entity.tile.spawner.*;
import mrmathami.thegame.entity.tile.tower.*;



import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileNotFoundException;

public final class GameDrawer {
	/**
	 * TODO: This is a list contains Entity type that can be drawn on screen.
	 * Remember to add your own entity class here if it can be drawn.
	 */
	@Nonnull private static final List<Class<?>> ENTITY_DRAWING_ORDER = List.of(
			Road.class,
			Mountain.class,
			NormalTower.class,
			SniperTower.class,
			MachineGunTower.class,
			NormalBullet.class,
			MachineGunBullet.class,
			SniperBullet.class,
			NormalEnemy.class,
			SmallerEnemy.class,
			TankerEnemy.class,
			BossEnemy.class,
			NormalSpawner.class,
			SmallerSpawner.class,
			TankerSpawner.class,
			BossSpawner.class,
			Target.class
	);
	/**
	 * TODO:
	 * This is a map between Entity type and its drawer.
	 * Remember to add your entity drawer here.
	 */
	@Nonnull private static final Map<Class<? extends GameEntity>, EntityDrawer> ENTITY_DRAWER_MAP = new HashMap<>(Map.ofEntries(
			Map.entry(Road.class, new RoadDrawer()),
			Map.entry(Mountain.class, new MountainDrawer()),
			Map.entry(NormalTower.class, new NormalTowerDrawer()),
			Map.entry(SniperTower.class, new SniperTowerDrawer()),
			Map.entry(MachineGunTower.class, new MachineGunTowerDrawer()),
			Map.entry(NormalBullet.class, new NormalBulletDrawer()),
			Map.entry(MachineGunBullet.class, new MachineGunBulletDrawer()),
			Map.entry(SniperBullet.class, new SniperBulletDrawer()),
			Map.entry(NormalEnemy.class, new NormalEnemyDrawer()),
			Map.entry(SmallerEnemy.class, new SmallerEnemyDrawer()),
			Map.entry(TankerEnemy.class, new TankerEnemyDrawer()),
			Map.entry(BossEnemy.class, new BossEnemyDrawer()),
			Map.entry(NormalSpawner.class, new SpawnerDrawer()),
			Map.entry(SmallerSpawner.class, new SpawnerDrawer()),
			Map.entry(TankerSpawner.class, new SpawnerDrawer()),
			Map.entry(BossSpawner.class, new SpawnerDrawer()),
			Map.entry(Target.class, new TargetDrawer())
	));

	@Nonnull private final GraphicsContext graphicsContext;
	@Nonnull private GameField gameField;
	private transient double fieldStartPosX = Float.NaN;
	private transient double fieldStartPosY = Float.NaN;
	private transient double fieldZoom = Float.NaN;

	public GameDrawer(@Nonnull GraphicsContext graphicsContext, @Nonnull GameField gameField) {
		this.graphicsContext = graphicsContext;
		this.gameField = gameField;
	}

	/**
	 * Do not touch me.
	 * This is a drawing order comparator, use to sort the entity list.
	 *
	 * @param entityA entity A
	 * @param entityB entity B
	 * @return order
	 */
	private static int entityDrawingOrderComparator(@Nonnull GameEntity entityA, @Nonnull GameEntity entityB) {
		final int compareOrder = Integer.compare(
				ENTITY_DRAWING_ORDER.indexOf(entityA.getClass()),
				ENTITY_DRAWING_ORDER.indexOf(entityB.getClass())
		);
		if (compareOrder != 0) return compareOrder;
		final int comparePosX = Double.compare(entityA.getPosX(), entityB.getPosX());
		if (comparePosX != 0) return comparePosX;
		final int comparePosY = Double.compare(entityA.getPosY(), entityB.getPosY());
		if (comparePosY != 0) return comparePosY;
		final int compareWidth = Double.compare(entityA.getWidth(), entityB.getWidth());
		if (compareWidth != 0) return compareWidth;
		return Double.compare(entityA.getHeight(), entityB.getHeight());
	}

	/**
	 * @param entity entity
	 * @return the drawer fot that entity, or null if that entity is not drawable.
	 */
	@Nullable
	private static EntityDrawer getEntityDrawer(@Nonnull GameEntity entity) {
		return ENTITY_DRAWER_MAP.get(entity.getClass());
	}

	public final double getFieldStartPosX() {
		return fieldStartPosX;
	}

	public final double getFieldStartPosY() {
		return fieldStartPosY;
	}

	public final double getFieldZoom() {
		return fieldZoom;
	}

	public final void setGameField(@Nonnull GameField gameField) {
		this.gameField = gameField;
	}

	/**
	 * Set the field view region, in other words, set the region of the field that will be drawn on the screen.
	 *
	 * @param fieldStartPosX pos x
	 * @param fieldStartPosY pos y
	 * @param fieldZoom      zoom
	 */
	public final void setFieldViewRegion(double fieldStartPosX, double fieldStartPosY, double fieldZoom) {
		this.fieldStartPosX = fieldStartPosX;
		this.fieldStartPosY = fieldStartPosY;
		this.fieldZoom = fieldZoom;
	}

	/**
	 * Do render. Should not touch.
	 */
	public final void render() {
		final GameField gameField = this.gameField;
		final double fieldStartPosX = this.fieldStartPosX;
		final double fieldStartPosY = this.fieldStartPosY;
		final double fieldZoom = this.fieldZoom;

		final List<GameEntity> entities = new ArrayList<>(GameEntities.getOverlappedEntities(gameField.getEntities(),
				fieldStartPosX, fieldStartPosY, Config.SCREEN_WIDTH / fieldZoom, Config.SCREEN_HEIGHT / fieldZoom));
		entities.sort(GameDrawer::entityDrawingOrderComparator);

		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0.0, 0.0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

		GameEntity lastEntity = null;
		for (final GameEntity entity : entities) {
			if (lastEntity != null && entityDrawingOrderComparator(entity, lastEntity) == 0) continue;
			lastEntity = entity;
			final EntityDrawer drawer = getEntityDrawer(entity);
			if (drawer != null) {
				drawer.draw(gameField.getTickCount(), graphicsContext, entity,
						(entity.getPosX() - fieldStartPosX) * fieldZoom,
						(entity.getPosY() - fieldStartPosY) * fieldZoom,
						entity.getWidth() * fieldZoom,
						entity.getHeight() * fieldZoom,
						fieldZoom
				);
			}
		}
		// Ve thap de mua tam thoi
		try
		{
			Image dollarSignImage  = new Image(new FileInputStream(".\\res\\image\\yellowdollarsign.png"));
			Image dollarSignImageRemovedWhite = DeleteWhiteImage.deleteWhiteImage(dollarSignImage);
			graphicsContext.drawImage(dollarSignImageRemovedWhite,3 * Config.TILE_SIZE,9*Config.TILE_SIZE,Config.TILE_SIZE,Config.TILE_SIZE);
		}
		catch (Exception e){}
		NormalTowerDrawer normalTowerDrawer = new NormalTowerDrawer();
		normalTowerDrawer.draw(gameField.getTickCount(), graphicsContext, new NormalTower(gameField.getTickCount(), 0, 0), 0, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		MachineGunTowerDrawer machineGunTowerDrawer = new MachineGunTowerDrawer();
		machineGunTowerDrawer.draw(gameField.getTickCount(), graphicsContext, new MachineGunTower(gameField.getTickCount(), 0, 0), Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		SniperTowerDrawer sniperTowerDrawer = new SniperTowerDrawer();
		sniperTowerDrawer.draw(gameField.getTickCount(), graphicsContext, new SniperTower(gameField.getTickCount(), 0, 0), 2*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		try {
			Image sell = new Image(new FileInputStream(".\\res\\image\\yellowdollarsign.png"));
			graphicsContext.drawImage(sell, 3*Config.TILE_SIZE, 9*Config.TILE_SIZE);
			Image pause = new Image(new FileInputStream(".\\res\\image\\pause.png"));
			graphicsContext.drawImage(pause, 15*Config.TILE_SIZE, 9*Config.TILE_SIZE);
			Image play = new Image(new FileInputStream(".\\res\\image\\play.png"));
			graphicsContext.drawImage(play, 14*Config.TILE_SIZE, 9*Config.TILE_SIZE);
		}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		}

	}

	public final double screenToFieldPosX(double screenPosX) {
		return screenPosX * fieldZoom + fieldStartPosX;
	}

	public final double screenToFieldPosY(double screenPosY) {
		return screenPosY * fieldZoom + fieldStartPosY;
	}

	public final double fieldToScreenPosX(double fieldPosX) {
		return (fieldPosX - fieldStartPosX) / fieldZoom;
	}

	public final double fieldToScreenPosY(double fieldPosY) {
		return (fieldPosY - fieldStartPosY) / fieldZoom;
	}
}
