package com.madgear.ninjatrials.trials.run;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;

import com.madgear.ninjatrials.managers.ResourceManager;


/**
 * Class load Background to run trial.
 */
public class RunBg extends Entity {

	private ParallaxEntity parallaxEntityFloor;
	private ParallaxEntity parallaxEntityTreesFront;
	private ParallaxEntity parallaxEntityTreesBack;

	private AutoParallaxBackground autoParallaxBg;

    /**
     * Contructor. Create backgroung to run trial using parallax backgroung.
     *
     * @param speed general speed of parallax backgroung
     * @param speedFloor floor speed of parallax background
     * @param speedTreesFront trees front speed of parallax background
     * @param speedTreesBack trees back speed of parallax background
     *
     * @return RunBg object
     */
    public RunBg(float speed, float speedFloor, float speedTreesFront, float speedTreesBack) {
		final Scene scene = new Scene();
		autoParallaxBg = new AutoParallaxBackground(0, 0, 0, 5);
		scene.setBackground(autoParallaxBg);
		this.attachChild(scene);

		final Sprite parallaxBackSprite = new Sprite(0, 306,
                ResourceManager.getInstance().runBgTreesBack,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
        parallaxBackSprite.setOffsetCenter(0, 0);
        parallaxEntityTreesBack = new ParallaxEntity(speedTreesBack, parallaxBackSprite);
		autoParallaxBg.attachParallaxEntity(parallaxEntityTreesBack);

		final Sprite parallaxFloorSprite = new Sprite(0, 0,
                ResourceManager.getInstance().runBgFloor,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		parallaxFloorSprite.setOffsetCenter(0, 0);
        parallaxEntityFloor = new ParallaxEntity(speedFloor, parallaxFloorSprite);
		autoParallaxBg.attachParallaxEntity(parallaxEntityFloor);

		final Sprite parallaxFrontSprite = new Sprite(0,
                ResourceManager.getInstance().cameraHeight -
                    ResourceManager.getInstance().runBgTreesFront.getHeight(),
                ResourceManager.getInstance().runBgTreesFront,
                ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		parallaxFrontSprite.setOffsetCenter(0, 0);
        parallaxEntityTreesFront = new ParallaxEntity(speedTreesFront, parallaxFrontSprite);
		autoParallaxBg.attachParallaxEntity(parallaxEntityTreesFront);

		autoParallaxBg.setParallaxChangePerSecond(speed);
	}

    /**
     * Update speed.
     *
     * @param speed float with new speed.
     */
    public void updateSpeed(int speed) {
        autoParallaxBg.setParallaxChangePerSecond(speed);
	}
}
