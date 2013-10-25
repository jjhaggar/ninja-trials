package com.madgear.ninjatrials.trials.shuriken;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.util.Log;

import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.trials.TrialSceneShuriken;

public class ShurikenEnemy extends Entity{
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRENHEIGHT = ResourceManager.getInstance().cameraHeight;
	private char direction; // r for right, l for left, n for none, i for invincible
	private int lifes;
	private float speed; // % of horizontal screen size per second
	private ShurikenCoordinates position;
	private boolean playerHit = false;
	private AnimatedSprite enemy;
	private float startTime;
	private IUpdateHandler enemyUpdateHandler;
	private int animationTimeCounter = 0;
	private ShurikenCoordinates [] routes = {
			new ShurikenCoordinates(SCRNWIDTH*9/10, SCRENHEIGHT*4/3), // [0] punto de entrada superior
			new ShurikenCoordinates(SCRNWIDTH*9/10, SCRENHEIGHT*4/5), // [1] punto de inicio superior derecho
			new ShurikenCoordinates(SCRNWIDTH/10, SCRENHEIGHT*4/5), // [2] punto final superior izquierdo
			new ShurikenCoordinates(SCRNWIDTH/10, SCRENHEIGHT*4/3),	// [3] punto de escape superior
			new ShurikenCoordinates(SCRNWIDTH/10, SCRENHEIGHT*5/3), // [4] punto de entrada medio 
			new ShurikenCoordinates(SCRNWIDTH/10, SCRENHEIGHT*2/3), // [5] punto de inicio medio derecho
			new ShurikenCoordinates(SCRNWIDTH*9/10, SCRENHEIGHT*2/3), // [6] punto final medio izquierdo
			new ShurikenCoordinates(SCRNWIDTH*9/10, SCRENHEIGHT*5/3),	// [7] punto de escape medio
			new ShurikenCoordinates(SCRNWIDTH/2, SCRENHEIGHT*3), // [8] punto de entrada inferior
			new ShurikenCoordinates(SCRNWIDTH/2, SCRENHEIGHT/2),	// [9] punto final inferior
	};
	
	public ShurikenEnemy(int lifes, float speed) {
		this.lifes = lifes;
		this.speed = speed;
		this.direction = 'i';
		this.position = new ShurikenCoordinates(routes[0].x, routes[0].y);
		ITiledTextureRegion enemyITTR = ResourceManager.getInstance().shurikenStrawman1;
		enemy = new AnimatedSprite(routes[0].x, routes[0].y, enemyITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		enemy.setCurrentTileIndex(2);
		attachChild(enemy);
		start();
	}
	
	public void start() {
		/*
		 * TODO
		 * Aparece al fondo
		 * Va recorriendo horizontalmente la pantalla
		 * Se acerca de forma alternada
		 * Hay dos “carriles” horizontales, uno lejos y otro a media distancia.
		 * Si no son abatidos por los shurikens los enemigos
		 * bajarán primero por el que está lejos y recorrerán
		 * una parte del “carril”, tras eso subirán de nuevo
		 * a los árboles y bajarán en el “carril”
		 * que está a media distancia, recorrerán una parte de ese carril
		 * y volverán a ascender a los árboles, tras
		 * eso caerá junto al personaje y mostrará el strawman con cartel     
		 */
		startTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		enemyUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
            	float period = 1f;
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                startTime + period*animationTimeCounter) {
                	if (animationTimeCounter == 0) {
                		// caer 1
                		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanDescend);
                		TrialSceneShuriken.moveSprite(enemy, routes[0].x, routes[0].y, routes[1].x, routes[1].y, .9f);
                		direction = 'i';
                	}
                	else if (animationTimeCounter == 1) {
                		// expandirse 1
                		enemy.setCurrentTileIndex(1);
                	}
                	else if (animationTimeCounter == 2 && lifes > 0) {
                		// moverse <= 1
                		enemy.setScaleX(-1f);
                		enemy.setCurrentTileIndex(0);
                		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanMove);
                		TrialSceneShuriken.moveSprite(enemy, routes[1].x, routes[1].y, routes[2].x, routes[2].y, 3.9f);
                		direction = 'l';
                	}
                	else if (animationTimeCounter == 6 && lifes > 0) {
                		// expandirse 1
                		enemy.setCurrentTileIndex(1);
                		direction = 'n';
                	}
                	else if (animationTimeCounter == 7 && lifes > 0) {
                		// subir 1
                		enemy.setCurrentTileIndex(2);
                		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanAscend);
                		TrialSceneShuriken.moveSprite(enemy, routes[2].x, routes[2].y, routes[3].x, routes[3].y, .9f);
                		direction = 'i';
                	}
                	else if (animationTimeCounter == 8 && lifes > 0) {
                		// caer 2
                		detachChild(enemy);
                		ITiledTextureRegion enemyITTR = ResourceManager.getInstance().shurikenStrawman2;
                		ShurikenEnemy.this.enemy = new AnimatedSprite(routes[4].x, routes[4].y, enemyITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
                		enemy.setCurrentTileIndex(2);
                		attachChild(enemy);
                		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanDescend);
                		TrialSceneShuriken.moveSprite(enemy, routes[4].x, routes[4].y, routes[5].x, routes[5].y, .9f);
                	}
                	else if (animationTimeCounter == 9 && lifes > 0) {
                		// expandirse 2
                		enemy.setCurrentTileIndex(1);
                		direction = 'n';
                	}
                	else if (animationTimeCounter == 10 && lifes > 0) {
                		// moverse => 2
                		enemy.setCurrentTileIndex(0);
                		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanMove);
                		TrialSceneShuriken.moveSprite(enemy, routes[5].x, routes[5].y, routes[6].x, routes[6].y, 3.9f);
                		direction = 'r';
                	}
                	else if (animationTimeCounter == 14 && lifes > 0) {
                		// expandirse 2
                		enemy.setCurrentTileIndex(1);
                		direction = 's';
                	}
                	else if (animationTimeCounter == 15 && lifes > 0) {
                		// subir 2
                		enemy.setCurrentTileIndex(2);
                		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanAscend);
                		TrialSceneShuriken.moveSprite(enemy, routes[6].x, routes[6].y, routes[7].x, routes[7].y, .9f);
                		direction = 'i';
                	}
                	else if (animationTimeCounter == 16 && lifes > 0){
                		// caer 3
                		detachChild(enemy);
                		ITextureRegion enemyITR = ResourceManager.getInstance().shurikenStrawman3;
                		Sprite finalEnemy = new Sprite(routes[8].x, routes[8].y, enemyITR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
                		attachChild(finalEnemy);
                		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanDescend);
                		TrialSceneShuriken.moveSprite(finalEnemy, routes[8].x, routes[8].y, routes[9].x, routes[9].y, .9f);
                	}
                	else if (animationTimeCounter == 19 && lifes > 0) {
                		playerHit = true;
                		unregisterUpdateHandler(enemyUpdateHandler);
                	}
                	animationTimeCounter++;
                }	                
            }
            @Override public void reset() {}
        };
        registerUpdateHandler(enemyUpdateHandler);
	}
	
	public ShurikenCoordinates getPosition() {
		this.position = new ShurikenCoordinates(enemy.getX(), enemy.getY());
		return position;
	}
	
	public void hide() {
		enemy.setAlpha(0f);
	}
	
	public char getDirection() {
		return this.direction;
	}
	
	public int getLifes() {
		return this.lifes;
	}
	
	public boolean hasHitPlayer() {
		return this.playerHit;
	}
	
	public void hit() {
		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanHit);
		this.lifes--;
		if (lifes == 0) {
			this.destroy();
		}
	}
	
	private void destroy() {
		Log.d("Bruno", "Enemy destroyed");
		SFXManager.playSound(ResourceManager.getInstance().trialShurikenStrawmanDestroyed);
		this.hide();
	}
}
