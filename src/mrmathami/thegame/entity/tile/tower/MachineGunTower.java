package mrmathami.thegame.entity.tile.tower;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.MachineGunBullet;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;
import java.io.File;

public class MachineGunTower extends AbstractTower<MachineGunBullet> {
    static File sound = new File("./res/sound/machineGun.mp3");

    static void playSoundEffect()
    {
        AudioClip audioClip = new AudioClip(sound.toURI().toString());
        audioClip.setCycleCount(1);
        audioClip.play();
    }

    public MachineGunTower(long createdTick, long posX, long posY) {
        super(createdTick, posX, posY, Config.MACHINE_GUN_TOWER_RANGE, Config.MACHINE_GUN_TOWER_SPEED);
    }

    @Nonnull
    @Override
    protected final MachineGunBullet doSpawn(long createdTick, double posX, double posY, AbstractEnemy target) {
        playSoundEffect();
        return new MachineGunBullet(createdTick, posX, posY, target);
    }
}
