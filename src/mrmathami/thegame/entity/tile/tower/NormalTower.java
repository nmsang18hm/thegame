package mrmathami.thegame.entity.tile.tower;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mrmathami.thegame.Config;
import mrmathami.thegame.entity.bullet.NormalBullet;
import mrmathami.thegame.entity.enemy.AbstractEnemy;

import javax.annotation.Nonnull;
import java.io.File;

public final class NormalTower extends AbstractTower<NormalBullet> {
	static  File sound = new File("./res/sound/normalTower.mp3");

	static void playSoundEffect()
	{
		AudioClip audioClip = new AudioClip(sound.toURI().toString());
		audioClip.setCycleCount(1);
		audioClip.play();

	}
	public NormalTower(long createdTick, long posX, long posY) {
		super(createdTick, posX, posY, Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_SPEED);
	}

	@Nonnull
	@Override
	protected final NormalBullet doSpawn(long createdTick, double posX, double posY, AbstractEnemy target) {
		playSoundEffect();
		return new NormalBullet(createdTick, posX, posY, target);
	}
}
