package mrmathami.thegame.entity.bullet;

import mrmathami.thegame.Config;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

public class MachineGunBullet extends AbstractBullet {
    public MachineGunBullet(long createdTick, double posX, double posY, AbstractEnemy target) {
        super(createdTick, posX, posY, target, Config.MACHINE_GUN_BULLET_SPEED, Config.MACHINE_GUN_BULLET_STRENGTH, Config.MACHINE_GUN_BULLET_TTL);
    }
}
