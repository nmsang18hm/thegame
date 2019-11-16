package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;

import javax.annotation.Nonnull;

public class SmallerEnemy extends AbstractEnemy {
    public SmallerEnemy(long createdTick, double posX, double posY)
    {
        super(createdTick,posX,posY, Config.SMALLER_ENEMY_SIZE,Config.SMALLER_ENEMY_HEALTH,Config.SMALLER_ENEMY_ARMOR,Config.SMALLER_ENEMY_SPEED,Config.SMALLER_ENEMY_REWARD);
    }

    @Override
    public void increaseCoin(@Nonnull GameField field) {
        field.setCoins(field.getCoins() + Config.SMALLER_ENEMY_REWARD);
    }
}
