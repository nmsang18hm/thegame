package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;

import javax.annotation.Nonnull;

public final class NormalEnemy extends AbstractEnemy {
	public NormalEnemy(long createdTick, double posX, double posY) {
		super(createdTick, posX, posY,  Config.NORMAL_ENEMY_SIZE, Config.NORMAL_ENEMY_HEALTH, Config.NORMAL_ENEMY_ARMOR, Config.NORMAL_ENEMY_SPEED, Config.NORMAL_ENEMY_REWARD);
	}

	@Override
	public void increaseCoin(@Nonnull GameField field) {
		field.setCoins(field.getCoins() + Config.NORMAL_ENEMY_REWARD);
	}
}
