package com.madgear.ninjatrials.trials.shuriken;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.madgear.ninjatrials.managers.ResourceManager;

public class ShurikenRotatingShuriken extends Entity{
	
	
	private final float width = ResourceManager.getInstance().cameraWidth;
    private final float height = ResourceManager.getInstance().cameraHeight;
	private Sprite shurikenSprite;
	private int currentRoute;
	public boolean isMoving;
	private final float[][] launchPositions;
	private final float[][] targetPositions;
	
	public ShurikenRotatingShuriken(float[][] launchPositions, 
			float[][] targetPositions){
		
		this.launchPositions = launchPositions;
		this.targetPositions = targetPositions;
		
		BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath("gfx/trial_shuriken/");
		TextureManager textureManager = ResourceManager.getInstance()
				.activity.getTextureManager();
		BitmapTextureAtlas shurikenBitmapTextureAtlas = 
				new BitmapTextureAtlas(textureManager, 128, 128, 
						TextureOptions.NEAREST);
		ITextureRegion shurikenTextureRegion = 
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(
						shurikenBitmapTextureAtlas, ResourceManager
						.getInstance().activity, 
						"shuriken_temp_shuriken.png", 0, 0);
		shurikenBitmapTextureAtlas.load();
		shurikenSprite = new Sprite(width / 2, height / 2, 
				shurikenTextureRegion, ResourceManager.getInstance().engine
				.getVertexBufferObjectManager());
        shurikenSprite.setAlpha(1f);
        attachChild(shurikenSprite);
        
        isMoving = false;
        currentRoute = 2;
        setRoute(currentRoute);
	}		
	
	public void throwIt(){
		if(!isMoving){
			isMoving = true;
			float distance = targetPositions[currentRoute][1] - 
					launchPositions[currentRoute][1];
			float timeFlying = distance * 2 / height * .4f;
			float finalScale = 1 - (distance / height);
			SequenceEntityModifier shurikenLaunchPathSequenceEntityModifier
					= getShurikenLaunchPathSequenceEntityModifier(
							launchPositions[currentRoute][0], 
							launchPositions[currentRoute][1], 
							targetPositions[currentRoute][0], 
							targetPositions[currentRoute][1],
							timeFlying);
			shurikenSprite.registerEntityModifier(
					shurikenLaunchPathSequenceEntityModifier);
			shurikenSprite.registerEntityModifier(
					new RotationModifier(timeFlying, 0, 1800));
			shurikenSprite.registerEntityModifier(
					new SequenceEntityModifier(
						new ScaleModifier(timeFlying, 2f, finalScale),
						new DelayModifier(1f),
						new FadeOutModifier(.5f){
							@Override
					        protected void onModifierFinished(IEntity pItem)
					        {
					                super.onModifierFinished(pItem);
					                isMoving = false;
					                setRoute(currentRoute);						                
					                //nextRound();
					        }
						}));
		}		
	}		
	
	public void setRoute(int routeN){
		if(!isMoving){
			isMoving = true;
			shurikenSprite.setScale(2f);
			shurikenSprite.registerEntityModifier(new FadeInModifier(.2f));
			Path path = new Path(2)
			.to(launchPositions[currentRoute][0]-1f, 
					launchPositions[currentRoute][1]-1f)
			.to(launchPositions[routeN][0], launchPositions[routeN][1]);
			shurikenSprite.registerEntityModifier(
					new PathModifier(.2f, path){
						@Override
				        protected void onModifierFinished(IEntity pItem)
				        {
				                super.onModifierFinished(pItem);
				                isMoving = false;
				        }
					});				
			currentRoute = routeN;
		}			
	}
	
	public int getRoute(){
		return currentRoute;
	}
	
	public void moveRight(){
		if (currentRoute < 3){
			setRoute(currentRoute + 1);
		}
	}		
	
	public void moveLeft(){
		if (currentRoute > 0){
			setRoute(currentRoute - 1);
		}
	}		
	
	private SequenceEntityModifier 
	getShurikenLaunchPathSequenceEntityModifier(float originX, 
			float originY, float targetX, float targetY, float timeFlying){
		Path p1, p2, p3, p4;
		p1 = new Path(2).to(originX, originY)
				.to(originX+(targetX-originX)*.5f, 
						originY+(targetY-originY)*.5f);
		p2 = new Path(2).to(originX+(targetX-originX)*.5f,
				originY+(targetY-originY)*.5f)
				.to(originX+(targetX-originX)*.8f,
						originY+(targetY-originY)*.8f);
		p3 = new Path(2).to(originX+(targetX-originX)*.8f,
				originY+(targetY-originY)*.8f)
				.to(originX+(targetX-originX)*.95f,
						originY+(targetY-originY)*.95f);
		p4 = new Path(2).to(originX+(targetX-originX)*.95f,
				originY+(targetY-originY)*.95f)
				.to(targetX, targetY);
		return new SequenceEntityModifier(
				new PathModifier(timeFlying/8, p1),
				new PathModifier(timeFlying/8*3, p2),
				new PathModifier(timeFlying/4, p3),
				new PathModifier(timeFlying/4, p4));
	}

}
