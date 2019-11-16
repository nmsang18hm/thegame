package mrmathami.thegame.entity.tile.tower;

import mrmathami.thegame.GameEntities;
import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.UpdatableEntity;
import mrmathami.thegame.entity.bullet.AbstractBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;
import mrmathami.thegame.entity.tile.AbstractTile;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;

public abstract class AbstractTower<E extends AbstractBullet> extends AbstractTile implements UpdatableEntity {
	private final double range;
	private final long speed;

	private long tickDown;

	protected AbstractTower(long createdTick, long posX, long posY, double range, long speed) {
		super(createdTick, posX, posY, 1L, 1L);
		this.range = range;
		this.speed = speed;
		this.tickDown = 0;
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		if (tickDown <= 0) {
			// TODO: Find a target and spawn a bullet to that direction.
			// Use GameEntities.getFilteredOverlappedEntities to find target in range
			double centerX = ( 2*getPosX() + getWidth() )/2.0;
			double centerY = ( 2*getPosY() + getHeight() )/2.0;
			List<AbstractEnemy> enemies = (List<AbstractEnemy>) GameEntities.getFilteredOverlappedEntities(field.getEntities(), AbstractEnemy.class, centerX - range, centerY - range, 2*range, 2*range);
			if (enemies.size() > 0) {
				field.doSpawn(doSpawn(field.getTickCount(), centerX, centerY, enemies.get(0)));
				this.tickDown = speed;
			}
			// Remember to set this.tickDown back to this.speed after shooting something.
		}
	}

	/**
	 * Create a new bullet. Each tower spawn different type of bullet.
	 * Override this method and return the type of bullet that your tower shot out.
	 * See NormalTower for example.
	 *
	 * @param createdTick createdTick
	 * @param posX posX
	 * @param posY posY
	 * @param //deltaX deltaX
	 * @param //deltaY deltaY
	 * @return the bullet entity
	 */
	@Nonnull
	protected abstract E doSpawn(long createdTick, double posX, double posY, AbstractEnemy target);
}
