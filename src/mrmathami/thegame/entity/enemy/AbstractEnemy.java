package mrmathami.thegame.entity.enemy;

import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.tile.Road;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.Collection;

public abstract class AbstractEnemy extends AbstractEntity implements UpdatableEntity, EffectEntity, LivingEntity, DestroyListener {
	private static final double SQRT_2 = Math.sqrt(2.0) / 2.0;
	private static final double[][] DELTA_DIRECTION_ARRAY = {
			{0.0, -1.0}, {0.0, 1.0}, {-1.0, 0.0}, {1.0, 0.0},
			{-SQRT_2, -SQRT_2}, {SQRT_2, SQRT_2}, {SQRT_2, -SQRT_2}, {-SQRT_2, SQRT_2},
	};

	private long health;
	private long armor;
	private double speed;
	private long reward;
	private String EnemyName;
	private long MaxHealth;

	protected AbstractEnemy(long createdTick, double posX, double posY, double size, long health, long armor, double speed, long reward) {
		super(createdTick, posX, posY, size, size);
		this.MaxHealth = health;
		this.health = health;
		this.armor = armor;
		this.speed = speed;
		this.reward = reward;
		EnemyName = "";
	}

	public long getMaxHealth() {
		return MaxHealth;
	}


	private static double evaluateDistance(@Nonnull Collection<GameEntity> overlappableEntities,
										   @Nonnull GameEntity sourceEntity, double posX, double posY, double width, double height) {
		double distance = 0.0;
		double sumArea = 0.0;
		for (GameEntity entity : GameEntities.getOverlappedEntities(overlappableEntities, posX, posY, width, height)) {
			if (sourceEntity != entity && GameEntities.isCollidable(sourceEntity.getClass(), entity.getClass())) return Double.NaN;
			if (entity instanceof Road) {
				final double entityPosX = entity.getPosX();
				final double entityPosY = entity.getPosY();
				final double area = (Math.min(posX + width, entityPosX + entity.getWidth()) - Math.max(posX, entityPosX))
						* (Math.min(posY + height, entityPosY + entity.getHeight()) - Math.max(posY, entityPosY));
				sumArea += area;
				distance += area * ((Road) entity).getDistance();
			}
		}
		return distance / sumArea;
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		final double enemyPosX = getPosX();
		final double enemyPosY = getPosY();
		final double enemyWidth = getWidth();
		final double enemyHeight = getHeight();
		final Collection<GameEntity> overlappableEntities = GameEntities.getOverlappedEntities(field.getEntities(),
				getPosX() - speed, getPosY() - speed, speed + getWidth() + speed, speed + getHeight() + speed);
		double minimumDistance = Double.MAX_VALUE;
		double newPosX = enemyPosX;
		double newPosY = enemyPosY;
		for (double realSpeed = speed * 0.125; realSpeed <= speed; realSpeed += realSpeed) {
			for (double[] deltaDirection : DELTA_DIRECTION_ARRAY) {
				final double currentPosX = enemyPosX + deltaDirection[0] * realSpeed;
				final double currentPosY = enemyPosY + deltaDirection[1] * realSpeed;
				System.out.println("currentPosX: " + currentPosX +",currentPosY: "+currentPosY);
				final double currentDistance = evaluateDistance(overlappableEntities, this, currentPosX, currentPosY, enemyWidth, enemyHeight);
				if (currentDistance < minimumDistance) {
					minimumDistance = currentDistance;
					newPosX = currentPosX;
					newPosY = currentPosY;
				}
			}
		}
		setPosX(newPosX);
		setPosY(newPosY);
	}
	static File sound = new File("./res/sound/zombiedeath.wav");
	static void playZombieDeathSound()
	{
		AudioClip clip = new AudioClip(sound.toURI().toString());
		clip.setCycleCount(1);
		clip.play();
	}
	Rectangle getHealthBar(int posX,int posY)
	{
		Rectangle rectangle = new Rectangle(health*9,30);
		return rectangle;
	}
	@Override
	public final void onDestroy(@Nonnull GameField field) {

		// TODO: reward
		increaseCoin(field);
	}

	public abstract void increaseCoin(@Nonnull GameField field);

	@Override
	public final boolean onEffect(@Nonnull GameField field, @Nonnull LivingEntity livingEntity) {
		// TODO: harm the target
		if(!livingEntity.isDestroyed()) livingEntity.doEffect(-1);

		this.health = Long.MIN_VALUE;
		return false;
	}

	@Override
	public final long getHealth() {
		return health;
	}

	@Override
	public final void doEffect(long value) {
		if (health != Long.MIN_VALUE && (value < -armor || value > 0))
		{
			this.health += value;
			if(isDestroyed())
			{
				doDestroy();
			}
		}
	}

	@Override
	public final void doDestroy() {
		playZombieDeathSound();

		this.health = Long.MIN_VALUE;
	}

	@Override
	public final boolean isDestroyed() {
		return health <= 0L;
	}

	public String getEnemyName() {
		return EnemyName;
	}

	public void setEnemyName(String enemyName) {
		EnemyName = enemyName;
	}
}
