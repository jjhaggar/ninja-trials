package com.madgear.ninjatrials.trials.shuriken;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.trials.TrialSceneShuriken;

public class ShurikenEnemy extends Entity{
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRENHEIGHT = ResourceManager.getInstance().cameraHeight;
	private char direction = 'r';
	private int lifes;
	private float speed;
	private ShurikenCoordinates position;
	private boolean playerHit = false;
	private AnimatedSprite enemy;
	
	public ShurikenEnemy(int lifes, float speed) {
		this.lifes = lifes;
		this.speed = speed;
		this.position = new ShurikenCoordinates(SCRNWIDTH*9/10, SCRENHEIGHT*4/5);
		ITiledTextureRegion enemyITTR = ResourceManager.getInstance().shurikenStrawman1;
		enemy = new AnimatedSprite(position.x, position.y, enemyITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		enemy.setCurrentTileIndex(1);
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
		float fromX = position.x;
		float toX = SCRNWIDTH/10;
		float fromY = position.y;
		float toY = position.y;
		float time = (fromX - toX) / (speed * SCRNWIDTH);
		TrialSceneShuriken.moveSprite(enemy, fromX, fromY, toX, toY, time);
	}
	
	public ShurikenCoordinates getPosition() {
		if (playerHit) {
			return new ShurikenCoordinates(-1, -1);
		}
		return position;
	}
	
	public void hide() {
		this.setAlpha(0f);
	}
	
	public char getDirection(){
		return this.direction;
	}
	
	public int getLifes(){
		return this.lifes;
	}
	
	public void setLifes(int lifes){
		this.lifes = lifes;
	}
}
