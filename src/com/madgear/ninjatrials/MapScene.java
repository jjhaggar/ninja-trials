package com.madgear.ninjatrials;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import android.util.Log;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * Map Scene for Ninja Trials
 * 
 * Task number: 78
 * Design document: section 3.10 (pages 11-12)
 * @author Madgear Games
 *
 */

public class MapScene extends GameScene {
	
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRNHEIGHT = ResourceManager.getInstance().cameraHeight;
	private List<Place> places;
	private Parchment parchment;
	private Player player;
	
	private int currentPosition = 1; // Where to get this parameter value?

	@Override
	public Scene onLoadingScreenLoadAndShown() {
		return null;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() { }

	@Override
	public void onLoadScene() {
		ResourceManager.getInstance().loadMenuMapResources();
		setBackground(getBG());
	}

	@Override
	public void onShowScene() {
		places = new ArrayList<Place>();
		places.add(new Place(184, 368, 0, "Run"));
		places.add(new Place(344, 370, 1, "Jump"));
		places.add(new Place(920, 220, 2, "Cut"));
		places.add(new Place(1076, 210, 3, "Shuriken"));
		places.add(new Place(1322, 350, 4, "Other 1"));
		places.add(new Place(1040, 572, 5, "Other 2"));
		places.add(new Place(966, 978, 6, "Other 3"));
		for(Place p : places) {
			attachChild(p);
		}
		parchment = new Parchment();
		attachChild(parchment);
		parchment.animate();
		places.get(currentPosition).setStatus("selected");
		player = new Player();
		attachChild(player);
	}

	@Override
	public void onHideScene() {	}

	@Override
	public void onUnloadScene() {
		ResourceManager.getInstance().unloadMenuMapResources();		
	}
	
	/*
	 * Returns to TestingScene.
	 */
	@Override
    public void onPressButtonMenu() {
		SceneManager.getInstance().showScene(new TestingScene());
    }
	
	public SpriteBackground getBG() {
		ITextureRegion backgroundTextureRegion = ResourceManager.getInstance().menuMapBackground;
        Sprite backgroundSprite = new Sprite(SCRNWIDTH / 2, SCRNHEIGHT / 2, 
        		backgroundTextureRegion, ResourceManager.getInstance().engine
        		.getVertexBufferObjectManager());
        return new SpriteBackground(backgroundSprite);     
	}
	
	private class Place extends Entity{
		private float posX, posY;
		private int orderingPosition;
		private String name;
		private int status;
		private AnimatedSprite marker;
		private final String[] STATUSES = {"unavailable", "selected", "activated", "finished"};
		
		public Place (float posX, float posY, int orderingPosition, String name) {
			this.posX = posX;
			this.posY = posY;
			this.orderingPosition = orderingPosition;
			this.name = name;
			this.status = 0;
			ITiledTextureRegion markerITTR = ResourceManager.getInstance().menuMapBackgroundMarks;
			marker = new AnimatedSprite(posX, posY, markerITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			marker.setCurrentTileIndex(status);	
			attachChild(marker);
		}
		
		public boolean setStatus(String status) {
			boolean success = false;
			for (int i = 0; i < STATUSES.length; i++) {
				if ( STATUSES[i] == status) {
					this.status = i;
					success = true;
					break;
				}
			}
			marker.setCurrentTileIndex(this.status);
			return success;
		}
	}
	
	private class Parchment extends Entity {
		
		private AnimatedSprite parchment;
		
		public Parchment() {
			ITiledTextureRegion parchmentITTR = ResourceManager.getInstance().menuMapScroll;
			parchment = new AnimatedSprite(SCRNWIDTH/4, SCRNHEIGHT*3/4, parchmentITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			attachChild(parchment);
		}
		
		public void setPosition(float x, float y) {
			parchment.setPosition(x, y);
		}
		
		public void animate() {
			parchment.animate(500);
		}
	}
	
	private class Player extends Entity {
		
		private AnimatedSprite player;
		
		public Player() {
			ITiledTextureRegion playerITTR;
			if (GameManager.getSelectedCharacter() == GameManager.CHAR_SHO) {
				playerITTR = ResourceManager.getInstance().menuMapChSho;
	        }
	        else if (GameManager.getSelectedCharacter() == GameManager.CHAR_RYOKO) {
	        	playerITTR = ResourceManager.getInstance().menuMapChRyoko;
	        }
	        else {
	        	playerITTR = ResourceManager.getInstance().menuMapChSho;
	        	Log.d("MapScene", "Warning: selected character is unknown, using Sho as default.");
	        }
			player = new AnimatedSprite(places.get(currentPosition).posX + 10, places.get(currentPosition).posY + 75, playerITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			player.setCurrentTileIndex(1);				
			attachChild(player);
		}
		
		public void moveTo(float x, float y) {
			// TODO
		}
	}

}
