package mrmathami.thegame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.WindowEvent;
import mrmathami.thegame.drawer.GameDrawer;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.SniperTower;
import mrmathami.utilities.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A game controller. Everything about the game should be managed in here.
 */
public final class GameController extends AnimationTimer {
	/**
	 * Advance stuff. Just don't touch me. Google me if you are curious.
	 */
	private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(
			new ThreadFactoryBuilder()
					.setNamePrefix("Tick")
					.setDaemon(true)
					.setPriority(Thread.NORM_PRIORITY)
					.build()
	);

	/**
	 * The screen to draw on. Just don't touch me. Google me if you are curious.
	 */
	private final GraphicsContext graphicsContext;

	/**
	 * Game field. Contain everything in the current game field.
	 * Responsible to update the field every tick.
	 * Kinda advance, modify if you are sure about your change.
	 */
	private GameField field;
	/**
	 * Game drawer. Responsible to draw the field every tick.
	 * Kinda advance, modify if you are sure about your change.
	 */
	private GameDrawer drawer;

	/**
	 * Beat-keeper Manager. Just don't touch me. Google me if you are curious.
	 */
	private ScheduledFuture<?> scheduledFuture;

	/**
	 * The tick value. Just don't touch me.
	 * Google me if you are curious, especially about volatile.
	 * WARNING: Wall of text.
	 */
	private volatile long tick;

	/**
	 * The constructor.
	 *
	 * @param graphicsContext the screen to draw on
	 */
	public GameController(GraphicsContext graphicsContext) {
		// The screen to draw on
		this.graphicsContext = graphicsContext;

		// Just a few acronyms.
		final long width = Config.TILE_HORIZONTAL;
		final long height = Config.TILE_VERTICAL;

		// The game field. Please consider create another way to load a game field.
		// TODO: I don't have much time, so, spawn some wall then :)
		this.field = new GameField(GameStage.load("/stage/Man1.txt"));

		// The drawer. Nothing fun here.
		this.drawer = new GameDrawer(graphicsContext, field);

		// Field view region is a rectangle region
		// [(posX, posY), (posX + SCREEN_WIDTH / zoom, posY + SCREEN_HEIGHT / zoom)]
		// that the drawer will select and draw everything in it in an self-defined order.
		// Can be modified to support zoom in / zoom out of the map.
		drawer.setFieldViewRegion(0.0, 0.0, Config.TILE_SIZE);
	}

	/**
	 * Beat-keeper. Just don't touch me.
	 */
	private void tick() {
		//noinspection NonAtomicOperationOnVolatileField
		this.tick += 1;
	}

	/**
	 * A JavaFX loop.
	 * Kinda advance, modify if you are sure about your change.
	 *
	 * @param now not used. It is a timestamp in nanosecond.
	 */
	@Override
	public void handle(long now) {
		// don't touch me.
		final long currentTick = tick;
		final long startNs = System.nanoTime();

		// do a tick, as fast as possible
		field.tick();

//		// if it's too late to draw a new frame, skip it.
//		// make the game feel really laggy, so...
//		if (currentTick != tick) return;

		// draw a new frame, as fast as possible.
		drawer.render();

		// MSPT display. MSPT stand for Milliseconds Per Tick.
		// It means how many ms your game spend to update and then draw the game once.
		// Draw it out mostly for debug
		final double mspt = (System.nanoTime() - startNs) / 1000000.0;
		graphicsContext.setFill(Color.BLACK);
		graphicsContext.fillText(String.format("MSPT: %3.2f", mspt), 0, 12);
		graphicsContext.fillText("Coins: " + field.getCoins(), 100, 12);

		// if we have time to spend, do a spin
		while (currentTick == tick) Thread.onSpinWait();
	}

	/**
	 * Start rendering and ticking. Just don't touch me.
	 * Anything that need to initialize should be in the constructor.
	 */
	@Override
	public void start() {
		// Start the beat-keeper. Start to call this::tick at a fixed rate.
		this.scheduledFuture = SCHEDULER.scheduleAtFixedRate(this::tick, 0, Config.GAME_NSPT, TimeUnit.NANOSECONDS);
		// start the JavaFX loop.
		super.start();
	}

	/**
	 * On window close request.
	 * Kinda advance, modify if you are sure about your change.
	 *
	 * @param windowEvent currently not used
	 */
	final void closeRequestHandler(WindowEvent windowEvent) {
		scheduledFuture.cancel(true);
		stop();
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Key down handler.
	 *
	 * @param keyEvent the key that you press down
	 */
	final void keyDownHandler(KeyEvent keyEvent) {
		final KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.W) {
		} else if (keyCode == KeyCode.S) {
		} else if (keyCode == KeyCode.A) {
		} else if (keyCode == KeyCode.D) {
		} else if (keyCode == KeyCode.I) {
		} else if (keyCode == KeyCode.J) {
		} else if (keyCode == KeyCode.K) {
		} else if (keyCode == KeyCode.L) {
		}
	}

	/**
	 * Key up handler.
	 *
	 * @param keyEvent the key that you release up.
	 */
	final void keyUpHandler(KeyEvent keyEvent) {
		final KeyCode keyCode = keyEvent.getCode();
		if (keyCode == KeyCode.W) {
		} else if (keyCode == KeyCode.S) {
		} else if (keyCode == KeyCode.A) {
		} else if (keyCode == KeyCode.D) {
		} else if (keyCode == KeyCode.I) {
		} else if (keyCode == KeyCode.J) {
		} else if (keyCode == KeyCode.K) {
		} else if (keyCode == KeyCode.L) {
		}
	}

	/**
	 * Mouse down handler.
	 *
	 * @param mouseEvent the mouse button you press down.
	 */

	private boolean isChooseTower = false;
	private String nameTower = "";
	private  boolean isSellingTower = false;
	final void mouseDownHandler(MouseEvent mouseEvent) {
		Rectangle rectangleNormal = new Rectangle(0*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		Rectangle rectangleMachine = new Rectangle(1*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		Rectangle rectangleSniper = new Rectangle(2*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		Rectangle rectangleSellTower = new Rectangle(3 * Config.TILE_SIZE,9*Config.TILE_SIZE,Config.TILE_SIZE,Config.TILE_SIZE);

		if(rectangleNormal.contains(mouseEvent.getX(),mouseEvent.getY())) {
			nameTower = "NormalTower";
			isChooseTower = true;
		}
		else if(rectangleMachine.contains(mouseEvent.getX(), mouseEvent.getY())) {
			nameTower = "MachineTower";
			isChooseTower = true;
		}
		else if(rectangleSniper.contains(mouseEvent.getX(), mouseEvent.getY())) {
			nameTower = "SniperTower";
			isChooseTower = true;
		}
		else if(rectangleSellTower.contains(mouseEvent.getX(),mouseEvent.getY()))
		{
			isSellingTower = true;
			System.out.println("click to selling tower");

		}

		//		mouseEvent.getButton(); // which mouse button?
//		// Screen coordinate. Remember to convert to field coordinate
//		drawer.screenToFieldPosX(mouseEvent.getX());
//		drawer.screenToFieldPosY(mouseEvent.getY());
	}

	/**
	 * Mouse up handler.
	 *
	 * @param mouseEvent the mouse button you release up.
	 */
	final void mouseUpHandler(MouseEvent mouseEvent) {
		boolean isHaveTower = false;
		boolean isHaveMountain = false;
		List<GameEntity> containingEntities = (List<GameEntity>) GameEntities.getContainingEntities(field.getEntities(), mouseEvent.getX()/Config.TILE_SIZE, mouseEvent.getY()/Config.TILE_SIZE, 0.1/Config.TILE_SIZE, 0.1/Config.TILE_SIZE);
		for (GameEntity entity : containingEntities) {
			if(entity instanceof AbstractTower) isHaveTower = true;
			else if(entity instanceof Mountain) isHaveMountain = true;
		}
		if(isSellingTower)
		{
			if(containingEntities.size() > 0 && isHaveMountain == true && isHaveTower == true && isSellingTower == true)
			{
				System.out.println("check to remove tower");
				for (GameEntity entity : containingEntities)
				{
					if(entity instanceof AbstractTower && entity.isContaining((long)containingEntities.get(0).getPosX(), (long)containingEntities.get(0).getPosY(),Config.TILE_SIZE,Config.TILE_SIZE))
					{
						System.out.println("removing tower");
						AbstractTower tower = (AbstractTower)entity;

						field.addCoins(1000);

						containingEntities.remove(entity);

						System.out.println("remove Tower");
					}
				}
			}
		}
		if(isChooseTower) {


			 if(containingEntities.size() > 0 && isHaveMountain == true && isHaveTower == false) {
				if(nameTower == "NormalTower") {
					field.doSpawn(new NormalTower(field.getTickCount(), (long)containingEntities.get(0).getPosX(), (long)containingEntities.get(0).getPosY()));
				}
				else if(nameTower == "MachineTower") {
					field.doSpawn(new MachineGunTower(field.getTickCount(), (long)containingEntities.get(0).getPosX(), (long)containingEntities.get(0).getPosY()));
				}
				else if(nameTower == "SniperTower") {
					field.doSpawn(new SniperTower(field.getTickCount(), (long)containingEntities.get(0).getPosX(), (long)containingEntities.get(0).getPosY()));
				}
			}
		}
		isChooseTower = false;
		nameTower = "";
//		mouseEvent.getButton(); // which mouse button?
//		// Screen coordinate. Remember to convert to field coordinate
//		drawer.screenToFieldPosX(mouseEvent.getX());
//		drawer.screenToFieldPosY(mouseEvent.getY());
	}
}
