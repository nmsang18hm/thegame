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

    static void playSoundEffect()
    {
        AudioClip audioClip = new AudioClip(sound.toURI().toString());
        audioClip.setCycleCount(1);
        audioClip.play();
    }

    public SniperTower(long createdTick, long posX, long posY) {
        super(createdTick, posX, posY, Config.SNIPER_TOWER_RANGE, Config.SNIPER_TOWER_SPEED);
    }

    @Nonnull
    @Override
    protected final SniperBullet doSpawn(long createdTick, double posX, double posY, AbstractEnemy target) {
        playSoundEffect();
        return new SniperBullet(createdTick, posX, posY, target);
    }
}
