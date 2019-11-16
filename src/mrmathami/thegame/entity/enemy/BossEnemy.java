package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;

import javax.annotation.Nonnull;

public class BossEnemy extends AbstractEnemy {
    public BossEnemy(long createdTick, double posX, double posY) {
        super(createdTick, posX, posY,  Config.BOSS_ENEMY_SIZE, Config.BOSS_ENEMY_HEALTH, Config.BOSS_ENEMY_ARMOR, Config.BOSS_ENEMY_SPEED, Config.BOSS_ENEMY_REWARD);
    }

    @Override
    public void increaseCoin(@Nonnull GameField field) {
        field.setCoins(field.getCoins() + Config.BOSS_ENEMY_REWARD);
    }
}
