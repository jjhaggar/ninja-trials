package com.madgear.ninjatrials;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
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
import com.madgear.ninjatrials.trials.TrialSceneShuriken;

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
	private float startTime;
	IUpdateHandler updateHandler;
	private int animationState = 0;
	private List<Place> places;
	private Parchment parchment;
	private Player player;
	private float playerToPlacePositionXAdjustment = 10;
	private float playerToPlacePositionYAdjustment = 75;
	
	private int currentPosition = 0; // Where is this parameter value?
	private int nextPosition = 1; // Where is this parameter value?

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
		parchment = new Parchment(SCRNWIDTH/4, SCRNHEIGHT*3/4);
		attachChild(parchment);
		places.get(currentPosition).setStatus("selected");
		player = new Player(places.get(currentPosition).posX + playerToPlacePositionXAdjustment, places.get(currentPosition).posY + playerToPlacePositionYAdjustment);
		attachChild(player);
		SFXManager.playMusic(ResourceManager.getInstance().map);
		startTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		updateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
            	float delayToMovePlayer = 3f;
            	float delayToUnfoldParchment = 4f;
            	float delayToShowParchmentPrint = 4.8f;
            	switch(animationState) {
            		case 0:
            			if (ResourceManager.getInstance().engine.getSecondsElapsedTotal() > startTime + delayToMovePlayer) {
            				player.moveTo(places.get(nextPosition).posX + playerToPlacePositionXAdjustment, places.get(nextPosition).posY + playerToPlacePositionYAdjustment);
            				places.get(currentPosition).setStatus("finished");
            				animationState = 1;
            			}
            			break;
            		case 1:
            			if (ResourceManager.getInstance().engine.getSecondsElapsedTotal() > startTime + delayToUnfoldParchment) {
            				places.get(nextPosition).setStatus("selected");
            				parchment.unfold();
            				animationState = 2;
            			}
            			break;
            		case 2:
            			if (ResourceManager.getInstance().engine.getSecondsElapsedTotal() > startTime + delayToShowParchmentPrint) {
            				parchment.showPrint();
            				unregisterUpdateHandler(updateHandler);
            			}
            			break;
            	}
            }
            @Override public void reset() {}            
        };
        registerUpdateHandler(updateHandler);
	}

	@Override
	public void onHideScene() {	}

	@Override
	public void onUnloadScene() {
		SFXManager.pauseMusic(ResourceManager.getInstance().map);
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
		private AnimatedSprite print;
		
		private float printToParchmentPositionXAdjustment = -50;
		private float printToParchmentPositionYAdjustment = 0;
		private float printToParchmentScaleAdjustment = .8f;
		private float printToParchmentRotationAdjustment = -20f;
		
		public Parchment(float posX, float posY) {
			ITiledTextureRegion parchmentITTR = ResourceManager.getInstance().menuMapScroll;
			parchment = new AnimatedSprite(posX, posY, parchmentITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			parchment.setAlpha(0f);
			attachChild(parchment);
			ITiledTextureRegion printITTR = ResourceManager.getInstance().menuMapDrawings;
			print = new AnimatedSprite(posX + printToParchmentPositionXAdjustment, posY + printToParchmentPositionYAdjustment, printITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			print.setRotation(printToParchmentRotationAdjustment);
			print.setScale(printToParchmentScaleAdjustment);
			print.setAlpha(0f);
			attachChild(print);
		}
		
		public void setPosition(float x, float y) {
			parchment.setPosition(x, y);
			print.setPosition(x, y);
		}
		
		public void unfold() {
			parchment.setAlpha(1f);
			parchment.animate(200, 0);			
		}
		
		public void showPrint() {
			print.setAlpha(1f);
		}
	}
	
	private class Player extends Entity {
		
		private AnimatedSprite player;
		
		public Player(float posX, float posY) {
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
			player = new AnimatedSprite(posX, posY, playerITTR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
			player.setCurrentTileIndex(1);			
			attachChild(player);
		}
		
		public void moveTo(float x, float y) {
			Path p = new Path(2).to(player.getX(), player.getY()).to(x, y);
			PathModifier pathModifier = new PathModifier(1f, p);
			player.registerEntityModifier(pathModifier);
		}
	}

}
