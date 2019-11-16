package mrmathami.thegame.entity.enemy;

import mrmathami.thegame.Config;
import mrmathami.thegame.GameField;

import javax.annotation.Nonnull;

public class TankerEnemy extends AbstractEnemy{
    public TankerEnemy(long createdTick, double posX, double posY)
    {
        super(createdTick,posX,posY, Config.TANKER_ENEMY_SIZE,Config.TANKER_ENEMY_HEALTH,Config.TANKER_ENEMY_ARMOR,Config.TANKER_ENEMY_SPEED,Config.TANKER_ENEMY_REWARD);
    }

    @Override
    public void increaseCoin(@Nonnull GameField field) {
        field.setCoins(field.getCoins() + Config.TANKER_ENEMY_REWARD);
    }
}
