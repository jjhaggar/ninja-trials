package com.madgear.ninjatrials.trials;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap
.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;

import java.util.Random;

import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.MainMenuScene;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SceneManager;

public class TrialSceneShuriken extends GameScene {
	
	private final float width = ResourceManager.getInstance().cameraWidth;
    private final float height = ResourceManager.getInstance().cameraHeight;
    
    private final float[][] launchPositions = 
    	{{width / 5 * 1, 32f},
    	{width / 5 * 2, 32f},
    	{width / 5 * 3, 32f},
    	{width / 5 * 4, 32f}};
    
    private final float[][] targetPositions = {
    	{width / 6 * 1, height * .7f},
    	{width / 5 * 2, height * .8f},
    	{width / 5 * 3, height * .5f},
    	{width / 6 * 5, height * .9f}};
	
	private RotatingShuriken rotatingShuriken;
	private Diana diana0, diana1, diana2, diana3;
	private int currentDiana = -1;
	private int roundCounter = 0;
	private int score = 0;
	private boolean button0Enabled = false;
	
	/** Superclass constructor call **/
	public TrialSceneShuriken(){
		super(1f);
	}

	@Override
	public Scene onLoadingScreenLoadAndShown() {
		Scene loadingScene = new Scene();		
		loadingScene.getBackground().setColor(.15f, .15f, .15f);		
        loadingScene.attachChild(getLoadingText("SHURIKEN TRIAL\n(alpha)"));        
		return loadingScene;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() {}

	@Override
	public void onLoadScene() {
        setBackground(getBG());
        diana0 = new Diana(targetPositions[0][0], targetPositions[0][1], .75f);
        diana1 = new Diana(targetPositions[1][0], targetPositions[1][1], .6f);
        diana2 = new Diana(targetPositions[2][0], targetPositions[2][1], 1f);
        diana3 = new Diana(targetPositions[3][0], targetPositions[3][1], .5f);
        rotatingShuriken = new RotatingShuriken();
                
	}

	@Override
	public void onShowScene() {
		attachChild(diana0);
		attachChild(diana1);
		attachChild(diana2);
		attachChild(diana3);
		attachChild(rotatingShuriken);
		nextRound();
	}

	@Override
	public void onHideScene() {}

	@Override
	public void onUnloadScene() {}
	
	@Override
	public void onPressButtonO(){
		if(button0Enabled){
			Log.d("Bruno", "Button0 pressed (d "+currentDiana+", s "+rotatingShuriken.getRoute()+")");
			button0Enabled = false;
			Log.d("Bruno", "Button0 disabled");
			if(currentDiana == rotatingShuriken.getRoute()){
				Log.d("Bruno", "Throwing shuriken");
				rotatingShuriken.throwIt();
				score += 10;
			}else{
				Log.d("Bruno", "Not throwing shuriken. Missed!");
				nextRound();
			}	
		}else{
			Log.d("Bruno", "Button 0 ignored.");
		}			
	}
	
	@Override
    public void onPressButtonMenu() {
		System.exit(0);
    }
	
	@Override
    public void onPressDpadLeft() {
        rotatingShuriken.moveLeft();
        Log.d("Bruno", "DPad Left pressed.");
    }

    @Override
    public void onPressDpadRight() {
        rotatingShuriken.moveRight();
        Log.d("Bruno", "DPad Right pressed.");
    }
    
    public void onReleaseButtonO() {
    	Log.d("Bruno", "ButtonO released.");
    }
    public void onPressButtonU() {
    	Log.d("Bruno", "ButtonU pressed.");
    }
    public void onPressButtonY() {
    	Log.d("Bruno", "ButtonY pressed.");
    }
    public void onPressButtonA() {
    	Log.d("Bruno", "ButtonA pressed.");
    }
    public void onPressDpadUp() {
    	Log.d("Bruno", "DPad Up pressed.");
    }
    public void onPressDpadDown() {
    	Log.d("Bruno", "DPad Down pressed.");
    }
	
	public SpriteBackground getBG(){
		BitmapTextureAtlasTextureRegionFactory
		.setAssetBasePath("gfx/trial_shuriken/");
		TextureManager textureManager = ResourceManager.getInstance()
				.activity.getTextureManager();
        BitmapTextureAtlas backgroundBitmapTextureAtlas = 
        		new BitmapTextureAtlas(textureManager, 1920, 1080, 
        				BitmapTextureFormat.RGB_565, TextureOptions.NEAREST);
        ITextureRegion backgroundTextureRegion = 
        		BitmapTextureAtlasTextureRegionFactory
        		.createFromAsset(backgroundBitmapTextureAtlas, 
        				ResourceManager.getInstance().activity, 
        				"shuriken_background.png", 0, 0);
        backgroundBitmapTextureAtlas.load();
        Sprite backgroundSprite = new Sprite(width / 2, height / 2, 
        		backgroundTextureRegion, ResourceManager.getInstance().engine
        		.getVertexBufferObjectManager());
        return new SpriteBackground(backgroundSprite);        
	}
	
	public Text getLoadingText(String s){
		return new Text(
                ResourceManager.getInstance().cameraWidth * 0.5f,
                ResourceManager.getInstance().cameraHeight * 0.5f,
                ResourceManager.getInstance().fontBig, s,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine
                .getVertexBufferObjectManager());
	}
	
	public void nextRound(){
		Diana[] dianas = {diana0, diana1, diana2, diana3};
		if(currentDiana >= 0 && currentDiana < dianas.length){
			dianas[currentDiana].hide();
		}
		roundCounter++;
		if(roundCounter == 11){
			Log.d("Bruno", "Round "+roundCounter+", total score "+score);
		}else{
			currentDiana = new Random().nextInt(4);		
			dianas[currentDiana].show(); // no va si en la ronda anterior fue la misma diana
			Log.d("Bruno", "Round "+roundCounter);
			button0Enabled = true;
			Log.d("Bruno", "Button0 enabled");
		}		
	}
	
	
	
	
	// PRIVATE CLASSES
	
	
	
	
	private class RotatingShuriken extends Entity{
				
		private Sprite shurikenSprite;
		private int currentRoute;
		public boolean isMoving;
		
		public RotatingShuriken(){
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
						                nextRound();
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
	
	
	
	
	private class Diana extends Entity{
		
		private Sprite dianaSprite;
		
		public Diana(float posX, float posY, float scale){
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
	        dianaSprite.setAlpha(.2f);
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
	
	
	

}

/*
CAJON DE SASTRE

public void rotate(){
			
	shurikenSprite.registerEntityModifier(new LoopEntityModifier(
			new RotationModifier(2, 0, 360)));

	shurikenSprite.registerEntityModifier(new SequenceEntityModifier(
            new FadeInModifier(1f),
            new DelayModifier(0.5f),                    
            new RotationModifier(10, 0, 360),
            new DelayModifier(0.5f),
            new FadeOutModifier(.5f)));            
}

*/