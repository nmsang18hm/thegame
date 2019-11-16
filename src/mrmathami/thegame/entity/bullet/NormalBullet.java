package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

public final class NormalBullet extends AbstractBullet {
	public NormalBullet(long createdTick, double posX, double posY, AbstractEnemy target) {
		super(createdTick, posX, posY, target, Config.NORMAL_BULLET_SPEED, Config.NORMAL_BULLET_STRENGTH, Config.NORMAL_BULLET_TTL);
	}
}
