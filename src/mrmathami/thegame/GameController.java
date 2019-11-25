
package mrmathami.thegame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mrmathami.thegame.drawer.*;
import mrmathami.thegame.entity.GameEntity;
import mrmathami.thegame.entity.tile.Mountain;
import mrmathami.thegame.entity.tile.Target;
import mrmathami.thegame.entity.tile.spawner.AbstractSpawner;
import mrmathami.thegame.entity.tile.tower.AbstractTower;
import mrmathami.thegame.entity.tile.tower.MachineGunTower;
import mrmathami.thegame.entity.tile.tower.NormalTower;
import mrmathami.thegame.entity.tile.tower.SniperTower;
import mrmathami.utilities.ThreadFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A game controller. Everything about the game should be managed in here.
 */
public final class GameController extends AnimationTimer {
	public double xMouse;
	public double yMouse;
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
	public Stage stageCurrent;
	public GraphicsContext graphicsContextCurrent;
	public Canvas canvasCurrent;
	public Canvas canvasMainMenu;
	public Scene sceneMainMenu;
	public GraphicsContext gcMainMenu;
	public Canvas canvasChooseStage;
	public Scene sceneChooseStage;
	public GraphicsContext gcChooseStage;
	public GameController gameController = this;

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
	 * @param //graphicsContext the screen to draw on
	 */
	public GameController(Stage primaryStage) {
		stageCurrent = primaryStage;

		playBackgroundMusic();

		stageCurrent.setTitle(Config.GAME_NAME);
		canvasMainMenu = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		StackPane stackPaneMainMenu = new StackPane();
		stackPaneMainMenu.getChildren().add(canvasMainMenu);
		sceneMainMenu = new Scene(stackPaneMainMenu);
		canvasCurrent = canvasMainMenu;
		stageCurrent.setScene(sceneMainMenu);
		gcMainMenu = canvasMainMenu.getGraphicsContext2D();
		graphicsContextCurrent = gcMainMenu;
		try {
			Image imageMainMenu = new Image(new FileInputStream(".\\res\\image\\MainMenu.png"));
			gcMainMenu.drawImage(imageMainMenu, 0, 0);
		}
		catch (FileNotFoundException e) {
			System.out.println("Main Menu image not found");
		}
		stageCurrent.show();

		canvasChooseStage = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		StackPane stackPaneChooseStage = new StackPane();
		stackPaneChooseStage.getChildren().add(canvasChooseStage);
		sceneChooseStage = new Scene(stackPaneChooseStage);
		gcChooseStage = canvasChooseStage.getGraphicsContext2D();

		Rectangle rectanglePlay = new Rectangle(404, 171, 316, 126);
		Rectangle rectangleQuit = new Rectangle(404, 483, 316, 126);
		canvasMainMenu.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(rectanglePlay.contains(mouseEvent.getX(), mouseEvent.getY())) {
					stageCurrent.setScene(sceneChooseStage);
					canvasCurrent = canvasChooseStage;
					graphicsContextCurrent = gcChooseStage;
					try {
						Image imageMainMenu = new Image(new FileInputStream(".\\res\\image\\chonman.png"));
						gcChooseStage.drawImage(imageMainMenu, 0, 0);
					}
					catch (FileNotFoundException e) {
						System.out.println("Main Menu image not found");
					}
					gcChooseStage.setFont(new Font("Time New Roma", 15));
					gcChooseStage.fillText("Màn 1", 230, 135);
					gcChooseStage.setFont(new Font("Time New Roma", 50));
					gcChooseStage.fillText("Stage Select", 425, 63);
					stageCurrent.show();
				}
				else if(rectangleQuit.contains(mouseEvent.getX(), mouseEvent.getY())) {
					//scheduledFuture.cancel(true);
					stop();
					Platform.exit();
					System.exit(0);
				}
			}
		});
		Rectangle rectangleMan1 = new Rectangle(148, 141, 205, 90);
		canvasChooseStage.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(rectangleMan1.contains(mouseEvent.getX(), mouseEvent.getY())) {
					canvasCurrent = new Canvas(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
					graphicsContextCurrent = canvasCurrent.getGraphicsContext2D();

					canvasCurrent.setFocusTraversable(true);
					graphicsContextCurrent.setFontSmoothingType(FontSmoothingType.LCD);


					// keyboard and mouse events to catch. Add if you need more
					canvasCurrent.setOnKeyPressed(gameController::keyDownHandler);
					canvasCurrent.setOnKeyReleased(gameController::keyUpHandler);
					//		canvas.setOnKeyTyped(...);

					canvasCurrent.setOnMousePressed(gameController::mouseDownHandler);
					//canvas.setOnMouseDragged(gameController::mouseMovedHandler);
					canvasCurrent.setOnMouseReleased(gameController::mouseUpHandler);
					//		canvas.setOnMouseClicked(...);


					stageCurrent.setResizable(false);
					stageCurrent.setOnCloseRequest(gameController::closeRequestHandler);
					stageCurrent.setScene(new Scene(new StackPane(canvasCurrent)));
					stageCurrent.show();

					// Just a few acronyms.
					final long width = Config.TILE_HORIZONTAL;
					final long height = Config.TILE_VERTICAL;

					// The game field. Please consider create another way to load a game field.
					// TODO: I don't have much time, so, spawn some wall then :)
					gameController.field = new GameField(GameStage.load("/stage/Man1.txt"));

					// The drawer. Nothing fun here.
					gameController.drawer = new GameDrawer(graphicsContextCurrent, field);

					// Field view region is a rectangle region
					// [(posX, posY), (posX + SCREEN_WIDTH / zoom, posY + SCREEN_HEIGHT / zoom)]
					// that the drawer will select and draw everything in it in an self-defined order.
					// Can be modified to support zoom in / zoom out of the map.
					drawer.setFieldViewRegion(0.0, 0.0, Config.TILE_SIZE);
					start();
				}
			}
		});
	}


	void playBackgroundMusic()
	{

		File sound = new File("./res/sound/nhacNenChinh.mp3");
		AudioClip clip = new AudioClip(sound.toURI().toString());
		clip.setCycleCount(10000);
		clip.play();
//		MediaPlayer player = new MediaPlayer(new Media(soun.toURI().toString()));
//		player.setStartTime(Duration.seconds(0));
//		player.setStopTime(Duration.hours(1000));
//		player.play();
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
		if(graphicsContextCurrent != gcMainMenu) {
			// do a tick, as fast as possible
			field.tick();

			// if it's too late to draw a new frame, skip it.
			// make the game feel really laggy, so...
			if (currentTick != tick) return;

			// draw a new frame, as fast as possible.
			drawer.render();

			// MSPT display. MSPT stand for Milliseconds Per Tick.
			// It means how many ms your game spend to update and then draw the game once.
			// Draw it out mostly for debug
			final double mspt = (System.nanoTime() - startNs) / 1000000.0;
			graphicsContextCurrent.setFill(Color.GOLD);
			//graphicsContextCurrent.fillText(String.format("MSPT: %3.2f", mspt), 0, 12);
            graphicsContextCurrent.setFont(new Font("Time New Roman", 60));
			graphicsContextCurrent.fillText((int)(field.getCoins()) + "", 7*Config.TILE_SIZE + 5, 10*Config.TILE_SIZE - 12);

			if(isChooseTower) {
				double Xgoc = xMouse - Config.TILE_SIZE/2.0;
				double Ygoc = yMouse - Config.TILE_SIZE/2.0;
				if(nameTower == "NormalTower") {
					NormalTowerDrawer normalTowerDrawer = new NormalTowerDrawer();
					normalTowerDrawer.draw(field.getTickCount(), graphicsContextCurrent, new NormalTower(field.getTickCount(), 0, 0), Xgoc, Ygoc, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
				}
				else if(nameTower == "MachineTower") {
					MachineGunTowerDrawer machineGunTowerDrawer = new MachineGunTowerDrawer();
					machineGunTowerDrawer.draw(field.getTickCount(), graphicsContextCurrent, new MachineGunTower(field.getTickCount(), 0, 0), Xgoc, Ygoc, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
				}
				else if (nameTower == "SniperTower") {
					SniperTowerDrawer sniperTowerDrawer = new SniperTowerDrawer();
					sniperTowerDrawer.draw(field.getTickCount(), graphicsContextCurrent, new SniperTower(field.getTickCount(), 0, 0), Xgoc, Ygoc, Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
				}
			}
			else if(isChooseSell) {
				double Xgoc1 = xMouse - Config.TILE_SIZE/2.0;
				double Ygoc1 = yMouse - Config.TILE_SIZE/2.0;
				try {

					Image image = new Image(new FileInputStream(".\\res\\image\\yellowdollarsign.png"));
					image = DeleteWhiteImage.deleteWhiteImage(image);
					graphicsContextCurrent.drawImage(image, Xgoc1, Ygoc1);
				}
				catch (FileNotFoundException e) {

				}
			}
			else {
				xMouse = 9999;
				xMouse = 9999;
			}

			canvasCurrent.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					xMouse = mouseEvent.getX();
					yMouse = mouseEvent.getY();
				}
			});
			boolean isHaveTarget = false;
			boolean isHaveSpawner = false;
			for(GameEntity entity : field.getEntities()) {
				if(entity instanceof Target) isHaveTarget = true;
				else if(entity instanceof AbstractSpawner) isHaveSpawner = true;
			}

			if(isHaveSpawner == false)
			{
				isPause = false;
				//stageCurrent.setScene(sceneMainMenu);
				//canvasCurrent = canvasMainMenu;
				//graphicsContextCurrent = gcMainMenu;
                try {
                    Image khung = new Image(new FileInputStream(".\\res\\image\\Khung.png"));
                    khung = DeleteWhiteImage.deleteWhiteImage(khung);
                    graphicsContextCurrent.drawImage(khung, 400, 200);
					Image home = new Image(new FileInputStream(".\\res\\image\\home.png"));
					home = DeleteWhiteImage.deleteWhiteImage2(home);
					graphicsContextCurrent.drawImage(home, 517, 307);
                    graphicsContextCurrent.setFont(new Font("Time New Roman", 30));
                    graphicsContextCurrent.setFill(Color.BLACK);
                    graphicsContextCurrent.fillText("Bạn đã thắng", 475, 270);
                }
                catch (FileNotFoundException e) {}
				stop();
			}
			else if(isHaveTarget == false) {
                isPause = false;
                try {
                    Image khung = new Image(new FileInputStream(".\\res\\image\\Khung.png"));
                    khung = DeleteWhiteImage.deleteWhiteImage2(khung);
                    graphicsContextCurrent.drawImage(khung, 400, 200);
					Image home = new Image(new FileInputStream(".\\res\\image\\home.png"));
					home = DeleteWhiteImage.deleteWhiteImage2(home);
					graphicsContextCurrent.drawImage(home, 517, 307);
                    graphicsContextCurrent.setFont(new Font("Time New Roman", 30));
                    graphicsContextCurrent.setFill(Color.BLACK);
                    graphicsContextCurrent.fillText("Bạn đã thua", 475, 270);
                }
                catch (FileNotFoundException e) {}
                stop();
            }
			if(isPause) {
				try {
					Image khungpause = new Image(new FileInputStream(".\\res\\image\\khungpause.png"));
					khungpause = DeleteWhiteImage.deleteWhiteImage(khungpause);
					graphicsContextCurrent.drawImage(khungpause, 375, 100);
				}
				catch (FileNotFoundException e) {}
				stop();
			}
		}
		else tick();

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
	public final void keyDownHandler(KeyEvent keyEvent) {
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
	private boolean isChooseSell = false;
	private boolean isPause = false;
	final void mouseDownHandler(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
		Rectangle rectangleNormal = new Rectangle(0*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		Rectangle rectangleMachine = new Rectangle(1*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		Rectangle rectangleSniper = new Rectangle(2*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		Rectangle rectangleSell = new Rectangle(3*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		Rectangle rectanglePause = new Rectangle(15*Config.TILE_SIZE, 9*Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
		Rectangle rectangleResume = new Rectangle(472, 177, 172, 80);
		Rectangle rectangleExit = new Rectangle(472, 456, 172, 80);

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
		else if(rectangleSell.contains(mouseEvent.getX(), mouseEvent.getY())) {
			isChooseSell = true;
		} else if(rectanglePause.contains(mouseEvent.getX(), mouseEvent.getY())) {
		    isPause = true;
        }else if(rectangleResume.contains(mouseEvent.getX(), mouseEvent.getY())) {
		    if(isPause) {
		    	isPause = false;
		    	super.start();
			}
        } else if(rectangleExit.contains(mouseEvent.getX(), mouseEvent.getY())) {
			if(isPause) {
				isPause = false;
				stageCurrent.setScene(sceneMainMenu);
				stop();
			}
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
		if(isChooseTower) {
			boolean isHaveTower = false;
			boolean isHaveMountain = false;
			List<GameEntity> containingEntities = (List<GameEntity>) GameEntities.getContainingEntities(field.getEntities(), mouseEvent.getX()/Config.TILE_SIZE, mouseEvent.getY()/Config.TILE_SIZE, 0.1/Config.TILE_SIZE, 0.1/Config.TILE_SIZE);
			for (GameEntity entity : containingEntities) {
				if(entity instanceof AbstractTower) isHaveTower = true;
				else if(entity instanceof Mountain) isHaveMountain = true;
			}
			if(containingEntities.size() > 0 && isHaveMountain == true && isHaveTower == false) {
				if(nameTower == "NormalTower" && field.getCoins() >= Config.NORMAL_TOWER_COST) {
					field.doSpawn(new NormalTower(field.getTickCount(), (long)containingEntities.get(0).getPosX(), (long)containingEntities.get(0).getPosY()));
					field.setCoins(field.getCoins() - Config.NORMAL_TOWER_COST);
				}
				else if(nameTower == "MachineTower" && field.getCoins() >= Config.MACHINE_GUN_TOWER_COST) {
					field.doSpawn(new MachineGunTower(field.getTickCount(), (long)containingEntities.get(0).getPosX(), (long)containingEntities.get(0).getPosY()));
					field.setCoins(field.getCoins() - Config.MACHINE_GUN_TOWER_COST);
				}
				else if(nameTower == "SniperTower" && field.getCoins() >= Config.SNIPER_TOWER_COST) {
					field.doSpawn(new SniperTower(field.getTickCount(), (long)containingEntities.get(0).getPosX(), (long)containingEntities.get(0).getPosY()));
					field.setCoins(field.getCoins() - Config.SNIPER_TOWER_COST);
				}
			}
		}
		else if(isChooseSell) {

			List<GameEntity> containingEntities = (List<GameEntity>) GameEntities.getContainingEntities(field.getEntities(), mouseEvent.getX()/Config.TILE_SIZE, mouseEvent.getY()/Config.TILE_SIZE, 0.1/Config.TILE_SIZE, 0.1/Config.TILE_SIZE);
			for (GameEntity entity : containingEntities) {
				if(entity instanceof NormalTower) {
					field.removeEntity(entity);
					field.setCoins(field.getCoins() + Config.NORMAL_TOWER_COST/2.0);
				}
				else if(entity instanceof  MachineGunTower) {
					field.removeEntity(entity);
					field.setCoins(field.getCoins() + Config.MACHINE_GUN_TOWER_COST/2.0);
				}
				else if(entity instanceof SniperTower) {
					field.removeEntity(entity);
					field.setCoins(field.getCoins() + Config.SNIPER_TOWER_COST);
				}
			}
		}
		isChooseTower = false;
		nameTower = "";
		isChooseSell = false;
//		mouseEvent.getButton(); // which mouse button?
//		// Screen coordinate. Remember to convert to field coordinate
//		drawer.screenToFieldPosX(mouseEvent.getX());
//		drawer.screenToFieldPosY(mouseEvent.getY());
	}


}