package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

public class SniperBullet extends AbstractBullet {
    public SniperBullet(long createdTick, double posX, double posY, AbstractEnemy target) {
        super(createdTick, posX, posY, target, Config.SNIPER_BULLET_SPEED, Config.SNIPER_BULLET_STRENGTH, Config.SNIPER_BULLET_TTL);
    }
}
