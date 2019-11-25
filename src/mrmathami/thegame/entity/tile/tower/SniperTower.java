package mrmathami.thegame.entity.tile.tower;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.SniperBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;
import java.io.File;

public class SniperTower extends AbstractTower<SniperBullet> {
    private static File sound = new File("./res/sound/sniperTower.mp3");


    public SniperTower(long createdTick, long posX, long posY) {
        super(createdTick, posX, posY, Config.SNIPER_TOWER_RANGE, Config.SNIPER_TOWER_SPEED);
        super.setTowerName("SniperTower");


    }

    @Nonnull
    @Override
    protected final SniperBullet doSpawn(long createdTick, double posX, double posY, AbstractEnemy target) {
        playSoundEffect(sound);
        return new SniperBullet(createdTick, posX, posY, target);
    }
}
