package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.GameField;
import mrmathami.thegame.entity.*;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;

public abstract class AbstractBullet extends AbstractEntity implements UpdatableEntity, EffectEntity, DestroyableEntity {
	private AbstractEnemy target;
	private double speedBullet;
	private final long strength;
	private long tickDown;

	protected AbstractBullet(long createdTick, double posX, double posY, AbstractEnemy target, double speed, long strength, long timeToLive) {
		super(createdTick, posX, posY, 0.2, 0.2);
		this.target = target;
		this.speedBullet = speed;
		this.strength = strength;
		this.tickDown = timeToLive;
	}

	@Override
	public final void onUpdate(@Nonnull GameField field) {
		this.tickDown -= 1;
		if(target.isDestroyed())
		{
			doDestroy();
			//
			setPosX(1000);
			setPosY(1000);
		}
		else
		{
			double kcX = target.getPosX() - getPosX();
			double kcY = target.getPosY() - getPosY();
			double normalize = speedBullet / Math.sqrt(kcX * kcX + kcY * kcY);
			double deltaX = kcX * normalize;
			double deltaY = kcY * normalize;
			setPosX(getPosX() + deltaX);
			setPosY(getPosY() + deltaY);
		}

	}

	@Override
	public final boolean onEffect(@Nonnull GameField field, @Nonnull LivingEntity livingEntity) {
		livingEntity.doEffect(-strength);
		this.tickDown = 0;
		return false;
	}

	@Override
	public final void doDestroy() {
		this.tickDown = 0;
	}

	@Override
	public final boolean isDestroyed() {
		return tickDown <= -0;
	}
}
