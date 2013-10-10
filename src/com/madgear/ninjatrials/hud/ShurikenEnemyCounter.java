//TODO Usar hud_head_shuriken.png

package com.madgear.ninjatrials.hud;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;

import com.madgear.ninjatrials.managers.ResourceManager;

public class ShurikenEnemyCounter extends Entity{
	private int enemyCount, enemiesLeft;
	private Sprite enemyLogo;
	private Text exSymbol;
	private Text[] numbers;
	private float enemyLogoPosX, enemyLogoPosY, exPosX, exPosY, longNumberPosX,
	longNumberPosY, shortNumberPosX, shortNumberPosY;
	public ShurikenEnemyCounter(float posX, float posY, int enemyCount){
		this.enemyCount = enemyCount;
		enemiesLeft = enemyCount;
		
		enemyLogoPosX = posX;
		enemyLogoPosY = posY;
		exPosX = posX + 90;
		exPosY = posY - 25;
		longNumberPosX = posX + 200;
		longNumberPosY = posY;
		shortNumberPosX = posX + 150;
		shortNumberPosY = posY;
		
		generateNumbers(enemyCount);
		for (Text number: numbers){
			attachChild(number);
		}
		generateEnemyLogo();
        attachChild(enemyLogo);
        generateExSymbol();
        attachChild(exSymbol);
	}
	
	public boolean setEnemiesLeft(int count){
		if(0 <= count && count <= enemyCount){
			numbers[enemiesLeft].setVisible(false);
			this.enemiesLeft = count;
			numbers[enemiesLeft].setVisible(true);			
			return true;
		}
		return false;	
	}
	
	private void generateExSymbol(){
		exSymbol = new Text(
                exPosX,
                exPosY,
                ResourceManager.getInstance().fontBig, "X",
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine
                .getVertexBufferObjectManager());
		exSymbol.setScale(.67f);
	}
	
	private void generateEnemyLogo(){
		BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath("gfx/trial_shuriken/");
		TextureManager textureManager = ResourceManager.getInstance()
				.activity.getTextureManager();
		BitmapTextureAtlas dianaBitmapTextureAtlas = 
				new BitmapTextureAtlas(textureManager, 480, 820, 
						TextureOptions.NEAREST);
		ITextureRegion dianaTextureRegion =	
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(
						dianaBitmapTextureAtlas, ResourceManager
						.getInstance().activity, 
						"shuriken_temp_strawman.png", 0, 0);
		dianaBitmapTextureAtlas.load();
		enemyLogo = new Sprite(enemyLogoPosX, enemyLogoPosY, dianaTextureRegion, 
				ResourceManager.getInstance().engine
				.getVertexBufferObjectManager());
		enemyLogo.setScale(.25f);
	}
	
	private void generateNumbers(int enemyCount){
		numbers = new Text[enemyCount + 1];
		for(int i = 0; i < enemyCount && i < 10; i++){
			numbers[i] = new Text(
					shortNumberPosX,
					shortNumberPosY,
	                ResourceManager.getInstance().fontBig,
	                Integer.toString(i),
	                new TextOptions(HorizontalAlign.CENTER),
	                ResourceManager.getInstance().engine
	                .getVertexBufferObjectManager());
			numbers[i].setScale(1.33f);
			numbers[i].setVisible(false);
		}
		for(int i = 10; i <= enemyCount; i++){
			numbers[i] = new Text(
					longNumberPosX,
					longNumberPosY,
	                ResourceManager.getInstance().fontBig,
	                Integer.toString(i),
	                new TextOptions(HorizontalAlign.CENTER),
	                ResourceManager.getInstance().engine
	                .getVertexBufferObjectManager());
			numbers[i].setScale(1.33f);
			numbers[i].setVisible(false);
		}
		numbers[enemyCount].setVisible(true);
	}
}
