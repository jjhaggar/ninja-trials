package com.madgear.ninjatrials.trials.shuriken;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.madgear.ninjatrials.managers.ResourceManager;

public class ShurikenDiana extends Entity{
	
	private Sprite dianaSprite;
	
	public ShurikenDiana(float posX, float posY, float scale){
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
		dianaSprite = new Sprite(posX, posY, dianaTextureRegion, 
				ResourceManager.getInstance().engine
				.getVertexBufferObjectManager());
        dianaSprite.setAlpha(.1f);
        dianaSprite.setScale(scale);
        attachChild(dianaSprite);
	}
	
	public void show(){
		//dianaSprite.registerEntityModifier(new FadeInModifier(.5f));
		dianaSprite.setAlpha(1f);
	}
	
	public void hide(){
		//dianaSprite.registerEntityModifier(new FadeOutModifier(.5f));
		dianaSprite.setAlpha(0f);
	}

}
