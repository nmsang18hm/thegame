package mrmathami.thegame;

public final class Config {
	/**
	 * Game name. Change it if you want.
	 */
	public static final String GAME_NAME = "Tower Defense";
	/**
	 * Ticks per second
	 */
	public static final long GAME_TPS = 20;
	/**
	 * Nanoseconds per tick
	 */
	public static final long GAME_NSPT = Math.round(1000000000.0 / GAME_TPS);

	/**
	 * Size of the tile, in pixel.
	 * 1.0 field unit == TILE_SIZE pixel on the screen.
	 * Change it base on your texture size.
	 */
	public static final long TILE_SIZE = 70;
	/**
	 * Num of tiles the screen can display if fieldZoom is TILE_SIZE,
	 * in other words, the texture will be display as it without scaling.
	 */
	public static final long TILE_HORIZONTAL = 16;
	/**
	 * Num of tiles the screen can display if fieldZoom is TILE_SIZE,
	 * in other words, the texture will be display as it without scaling.
	 */
	public static final long TILE_VERTICAL = 10;
	/**
	 * An arbitrary number just to make some code run a little faster.
	 * Do not touch.
	 */
	public static final int _TILE_MAP_COUNT = (int) (TILE_HORIZONTAL * TILE_VERTICAL);


	/**
	 * Size of the screen.
	 */
	public static final long SCREEN_WIDTH = TILE_SIZE * TILE_HORIZONTAL;
	/**
	 * Size of the screen.
	 */
	public static final long SCREEN_HEIGHT = TILE_SIZE * TILE_VERTICAL;


	//Other config related to other entities in the game.

	//region Bullet
	public static final long NORMAL_BULLET_TTL = 30;
	public static final long NORMAL_BULLET_STRENGTH = 30;
	public static final double NORMAL_BULLET_SPEED = 0.3;

	public static final long MACHINE_GUN_BULLET_TTL = 15;
	public static final long MACHINE_GUN_BULLET_STRENGTH = 30;
	public static final double MACHINE_GUN_BULLET_SPEED = 0.4;

	public static final long SNIPER_BULLET_TTL = 60;
	public static final long SNIPER_BULLET_STRENGTH = 120;
	public static final double SNIPER_BULLET_SPEED = 0.5;
	//endregion

	//region Tower
	public static final long NORMAL_TOWER_SPEED = 30;
	public static final double NORMAL_TOWER_RANGE = 2.0;
	public static final double NORMAL_TOWER_COST = 4.0;

	public static final long MACHINE_GUN_TOWER_SPEED = 5;
	public static final double MACHINE_GUN_TOWER_RANGE = 1.0;
	public static final double MACHINE_GUN_TOWER_COST = 4.0;

	public static final long SNIPER_TOWER_SPEED = 60;
	public static final double SNIPER_TOWER_RANGE = 4.0;
	public static final double SNIPER_TOWER_COST = 4.0;
	//endregion

	//region Enemy
	public static final double NORMAL_ENEMY_SIZE = 0.9;
	public static final long NORMAL_ENEMY_HEALTH = 400;
	public static final long NORMAL_ENEMY_ARMOR = 3;
	public static final double NORMAL_ENEMY_SPEED = 0.12;
	public static final long NORMAL_ENEMY_REWARD = 1;

	public static final double SMALLER_ENEMY_SIZE = 0.7;
	public static final long SMALLER_ENEMY_HEALTH = 200;
	public static final long SMALLER_ENEMY_ARMOR = 0;
	public static final double SMALLER_ENEMY_SPEED = 0.32;
	public static final long SMALLER_ENEMY_REWARD = 2;

	public static final double TANKER_ENEMY_SIZE = 1.0;
	public static final long TANKER_ENEMY_HEALTH = 1200;
	public static final long TANKER_ENEMY_ARMOR = 5;
	public static final double TANKER_ENEMY_SPEED = 0.2/3.0;
	public static final long TANKER_ENEMY_REWARD = 3;

	public static final double BOSS_ENEMY_SIZE = 1.2;
	public static final long BOSS_ENEMY_HEALTH = 2000;
	public static final long BOSS_ENEMY_ARMOR = 8;
	public static final double BOSS_ENEMY_SPEED = 0.1;
	public static final long BOSS_ENEMY_REWARD = 10;
	//endregion

	private Config() {
	}


}
