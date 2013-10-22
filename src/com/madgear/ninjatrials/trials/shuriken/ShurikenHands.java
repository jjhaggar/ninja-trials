package com.madgear.ninjatrials.trials.shuriken;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.util.Log;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.trials.TrialSceneShuriken;
import com.madgear.ninjatrials.trials.run.RunCharacter;

public class ShurikenHands extends Entity{
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRENHEIGHT = ResourceManager.getInstance().cameraHeight;
	private ShurikenCoordinates coordinates;
	private Sprite[] handsSprites = new Sprite[3];
	private boolean ignoreInputBecauseMoving = false;
	private float movementDistanceDelta = 50; // pixels
	private float movementTimeDelta = .025f; // seconds
	private int movementAnimationExtraTimeMargin = 13; // miliseconds
	private IUpdateHandler shurikenUpdateHandler;
	private float shurikenLaunchTime;
	private AnimatedSprite hands;
	private int shurikenAnimationCounter;
	
	public ShurikenHands() {
		float posX = SCRNWIDTH/2;
		float posY = 160f;
		this.coordinates = new ShurikenCoordinates(posX, posY);
		ITiledTextureRegion handsITTR;
		if (GameManager.getSelectedCharacter() == GameManager.CHAR_SHO) {
			handsITTR = ResourceManager.getInstance().shurikenShoHands;
        }
        else if (GameManager.getSelectedCharacter() == GameManager.CHAR_RYOKO) {
        	handsITTR = ResourceManager.getInstance().shurikenRyokoHands;
        }
        else {
        	handsITTR = ResourceManager.getInstance().shurikenShoHands;
        	Log.d("Bruno", "Warning: selected character unknown, using Sho as default.");
        }
		hands = new AnimatedSprite(posX, posY, handsITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		hands.setCurrentTileIndex(2);				
		attachChild(hands);
	}
	public void moveLeft() {
		if (coordinates.x - movementDistanceDelta >= 0 && !ignoreInputBecauseMoving){
			ignoreInputBecauseMoving = true;
			TrialSceneShuriken.moveSprite(hands, coordinates.x, coordinates.y, coordinates.x - movementDistanceDelta, coordinates.y, movementTimeDelta);
			coordinates.x = coordinates.x - movementDistanceDelta;
			Timer timer = new Timer();
			timer.schedule(new HandsMovementWaiter(this), (int)(movementTimeDelta * 1000) + movementAnimationExtraTimeMargin);
		}			
	}
	public void moveRight() {
		Log.d("Bruno", "Hands positions are ("+hands.getX()+", "+hands.getY()+")");
		if (coordinates.x + movementDistanceDelta <= SCRNWIDTH && !ignoreInputBecauseMoving){
			ignoreInputBecauseMoving = true;
			TrialSceneShuriken.moveSprite(hands, coordinates.x, coordinates.y, coordinates.x + movementDistanceDelta, coordinates.y, movementTimeDelta);
			coordinates.x = coordinates.x + movementDistanceDelta;
			Timer timer = new Timer();
			timer.schedule(new HandsMovementWaiter(this), (int)(movementTimeDelta * 1000) + movementAnimationExtraTimeMargin);
		}
	}
	public void stop() {
		
	}
	public void hide() {
		// TODO Las oculta lentamente por la parte inferior de la pantalla.
	}
	public ShurikenCoordinates getPosition() {
		return this.coordinates;
	}
	public void launch() {
		/*
		 * el lanzamiento es puramente vertical
		 * los shurikens se destruyen tras impactar/perderse
		 * pueden simultanearse varios lanzamientos (no hay bloqueo)
		 * la velocidad vertical es constante (shurikenSpeed)
		 */		
		hands.animate(200, 0);
		ShurikenShuriken shuriken = new ShurikenShuriken();
		attachChild(shuriken);
		shuriken.launch(this.coordinates.x);
		SFXManager.playSound(ResourceManager.getInstance().trialShurikenThrowing);
		if (generateCustomRandom(.25f)) {
			if (GameManager.getSelectedCharacter() == GameManager.CHAR_SHO) {
				SFXManager.playSound(ResourceManager.getInstance().shoShurikenThrow);
	        }
	        else if (GameManager.getSelectedCharacter() == GameManager.CHAR_RYOKO) {
	        	SFXManager.playSound(ResourceManager.getInstance().ryokoShurikenThrow);
	        }
	        else {
	        	SFXManager.playSound(ResourceManager.getInstance().shoShurikenThrow);
	        	Log.d("Bruno", "Warning: selected character unknown, using Sho as default.");
	        }
		}
	}
	private boolean generateCustomRandom(float chanceOfTrue) {
		Random randomGenerator = new Random();
		float n = randomGenerator.nextFloat();
		return (n > 1 - chanceOfTrue);
	}
	
	
	/**
	 * Aux. class for class Hands.
	 * TimerTask that enables input
	 */
	private class HandsMovementWaiter extends TimerTask {		
		ShurikenHands hands;
		
		public HandsMovementWaiter(ShurikenHands hands){
			super();
			this.hands = hands;
		}
		
		@Override
		public void run() {
			hands.ignoreInputBecauseMoving = false;			
		}		
	}
}
