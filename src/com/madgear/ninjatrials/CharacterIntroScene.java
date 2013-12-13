/*
 * Ninja Trials is an old school style Android Game developed for OUYA & using
 * AndEngine. It features several minigames with simple gameplay.
 * Copyright 2013 Mad Gear Games <madgeargames@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.madgear.ninjatrials;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;

import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.TestingScene;

/**
 * Character Intro Scene for Ninja Trials
 * 
 * Task number: 52
 * Design document: section 3.4 (page 7)
 * @author Madgear Games
 *
 */

public class CharacterIntroScene extends GameScene{
	
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRNHEIGHT = ResourceManager.getInstance().cameraHeight;
	private final float exitDelay = 8f;
	private float startTime;
	IUpdateHandler updateHandler;
	private int animationState = 0;
	private final String CHARACTER_NAME_RYOKO = ResourceManager.getInstance().
			loadAndroidRes().getString(R.string.app_ryoko);
	private final String CHARACTER_NAME_SHO = ResourceManager.getInstance().
			loadAndroidRes().getString(R.string.app_sho);
	private static String lastCharacterShown;
	private String character;

	@Override
	public Scene onLoadingScreenLoadAndShown() {
		return null;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() { }

	@Override
	public void onLoadScene() {
		ResourceManager.getInstance().loadCharacterProfileResources();
		setBackground(getBG());
	}

	@Override
	public void onShowScene() {
		if (lastCharacterShown == "Ryoko") {
			character = "Sho";
		}
		else {
			character = "Ryoko";
		}
		Log.d("CharacterInfoScene", "Showing "+character+". Last character shown was "+lastCharacterShown+".");
		attachChild(new Character());
		startTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		updateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
            	if (ResourceManager.getInstance().engine.getSecondsElapsedTotal() > startTime + exitDelay) {
            		unregisterUpdateHandler(updateHandler);
            		skip();
            	}            	
            }
            @Override public void reset() {}            
        };
        registerUpdateHandler(updateHandler);
	}

	@Override
	public void onHideScene() { }

	@Override
	public void onUnloadScene() {
		lastCharacterShown = character;
		ResourceManager.getInstance().unloadCharacterProfileResources();	
	}
	
	private void skip() {
        if(GameManager.DEBUG_MODE)
            SceneManager.getInstance().showScene(new TestingScene());
        else
            SceneManager.getInstance().showScene(new RecordsScene());
	}
	
	@Override
    public void onPressButtonMenu() {
		skip();
    }
	
	@Override
	public void onPressButtonO() {
		skip();
	}
	
	@Override
	public void onPressButtonU() {
		skip();
	}
	
	@Override
	public void onPressButtonY() {
		skip();
	}
	
	@Override
	public void onPressButtonA() {
		skip();
	}
	
	public SpriteBackground getBG() {
		ITextureRegion backgroundTextureRegion;
		if (character == CHARACTER_NAME_RYOKO) {
			backgroundTextureRegion = ResourceManager.getInstance().characterProfileBackground1;
		}
		else{
			backgroundTextureRegion = ResourceManager.getInstance().characterProfileBackground2;
		}		
        Sprite backgroundSprite = new Sprite(SCRNWIDTH / 2, SCRNHEIGHT / 2, 
        		backgroundTextureRegion, ResourceManager.getInstance().engine
        		.getVertexBufferObjectManager());
        return new SpriteBackground(backgroundSprite);     
	}
	
	private class Character extends Entity {
			
			private Sprite sprite;
			private String name, name_jp, info;
			private float spritePosX = SCRNWIDTH*80/100;
			private float spritePosY = SCRNHEIGHT*35/100;
			private float spriteScaleAdjustment = 1.0f;
			private float namePosX = SCRNWIDTH * 15/100;
			private float namePosY = SCRNHEIGHT * 90/100;
			private float name_jpPosX = SCRNWIDTH * 25/100;
			private float name_jpPosY = SCRNHEIGHT * 75/100;
			private float infoPosX = SCRNWIDTH * 25/100;
			private float infoPosY = SCRNHEIGHT * 30/100;
			private Text nameT, name_jpT, infoT;
			
			public Character() {
				
				ITextureRegion characterITR;
				if (character == "Ryoko") {
					characterITR = ResourceManager.getInstance().characterProfileRyoko;
					name = ResourceManager.getInstance().loadAndroidRes().getString(R.string.profile_ryoko_name);
					name_jp = ResourceManager.getInstance().loadAndroidRes().getString(R.string.profile_ryoko_name_jap);
					info = ResourceManager.getInstance().loadAndroidRes().getString(R.string.profile_ryoko_info);
		        }
		        else {
		        	characterITR = ResourceManager.getInstance().characterProfileSho;
                    name = ResourceManager.getInstance().loadAndroidRes().getString(R.string.profile_sho_name);
					name_jp = ResourceManager.getInstance().loadAndroidRes().getString(R.string.profile_sho_name_jap);
					info = ResourceManager.getInstance().loadAndroidRes().getString(R.string.profile_sho_info);
		        }
				
				sprite = new Sprite(spritePosX, spritePosY, characterITR, ResourceManager.getInstance().engine.getVertexBufferObjectManager());
				sprite.setScale(spriteScaleAdjustment);
				attachChild(sprite);
				
				nameT = new Text(
						SCRNWIDTH * 0.5f,
						SCRNHEIGHT * 0.5f,
		                ResourceManager.getInstance().fontLatinChrName, name,
		                new TextOptions(HorizontalAlign.CENTER),
		                ResourceManager.getInstance().engine
		                .getVertexBufferObjectManager());
				nameT.setPosition(namePosX, namePosY);
				attachChild(nameT);
				
				name_jpT = new Text(
						SCRNWIDTH * 0.5f,
						SCRNHEIGHT * 0.5f,
		                ResourceManager.getInstance().fontJPChrName, name_jp,
		                new TextOptions(HorizontalAlign.CENTER),
		                ResourceManager.getInstance().engine
		                .getVertexBufferObjectManager());
				name_jpT.setPosition(name_jpPosX, name_jpPosY);
				attachChild(name_jpT);
				
				infoT = new Text(
						SCRNWIDTH * 0.5f,
						SCRNHEIGHT * 0.5f,
		                ResourceManager.getInstance().fontLatinChrInfo, info,
		                new TextOptions(HorizontalAlign.LEFT),
		                ResourceManager.getInstance().engine
		                .getVertexBufferObjectManager());
				infoT.setPosition(infoPosX, infoPosY);
				attachChild(infoT);
				
			}
	}
}
