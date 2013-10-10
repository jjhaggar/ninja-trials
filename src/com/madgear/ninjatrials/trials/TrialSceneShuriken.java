package com.madgear.ninjatrials.trials;

import org.andengine.engine.handler.IUpdateHandler;
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
import org.andengine.opengl.font.FontFactory;
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
import com.madgear.ninjatrials.hud.GameHUD;
import com.madgear.ninjatrials.hud.ShurikenEnemyCounter;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.trials.shuriken.ShurikenDiana;
import com.madgear.ninjatrials.trials.shuriken.ShurikenRotatingShuriken;

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
	
    private GameHUD gameHUD;
    private float timerStartedIn;
	IUpdateHandler trialUpdateHandler;
	
	private ShurikenRotatingShuriken rotatingShuriken;
	private ShurikenDiana diana0, diana1, diana2, diana3;
	private ShurikenEnemyCounter enemyCounterHUD;
	private int currentDiana = -1;
	private int roundCounter = 0;
	private int maxRounds = 10;
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
        gameHUD = new GameHUD();
        diana0 = new ShurikenDiana(targetPositions[0][0], targetPositions[0][1], .75f);
        diana1 = new ShurikenDiana(targetPositions[1][0], targetPositions[1][1], .6f);
        diana2 = new ShurikenDiana(targetPositions[2][0], targetPositions[2][1], 1f);
        diana3 = new ShurikenDiana(targetPositions[3][0], targetPositions[3][1], .5f);
        rotatingShuriken = new ShurikenRotatingShuriken(launchPositions, targetPositions);
        enemyCounterHUD = new ShurikenEnemyCounter(width-300, height-100, maxRounds);
	}

	@Override
	public void onShowScene() {
		attachChild(diana0);
		attachChild(diana1);
		attachChild(diana2);
		attachChild(diana3);
		attachChild(rotatingShuriken);
		attachChild(enemyCounterHUD);
		ResourceManager.getInstance().engine.getCamera().setHUD(gameHUD);
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
				throwAndWaitAndRestart();
			}else{
				missAndWaitAndRestart();
			}	
		}else{
			Log.d("Bruno", "Button 0 ignored.");
		}			
	}
	
	public void throwAndWaitAndRestart(){
		Log.d("Bruno", "Throwing shuriken");
		rotatingShuriken.throwIt();
		score += 10;
		gameHUD.showMessage("Hit! +10pts", 1, .5f);
		timerStartedIn = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		trialUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
            	float waitPeriod = 2.1f;
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                timerStartedIn + waitPeriod) {
                    TrialSceneShuriken.this.unregisterUpdateHandler(trialUpdateHandler);
                    nextRound();
                  }
            }
            @Override public void reset() {}
        };
        registerUpdateHandler(trialUpdateHandler);
	}
	
	public void missAndWaitAndRestart(){
		Log.d("Bruno", "Not throwing shuriken. Missed!");
		gameHUD.showMessage("Miss!", .5f, .5f);
		timerStartedIn = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		trialUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
            	float waitPeriod = 1.1f;
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                timerStartedIn + waitPeriod) {
                    TrialSceneShuriken.this.unregisterUpdateHandler(trialUpdateHandler);
                    nextRound();
                  }
            }
            @Override public void reset() {}
        };
        registerUpdateHandler(trialUpdateHandler);
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
    	gameHUD.showMessage("U", 0, 1);
    }
    public void onPressButtonY() {
    	gameHUD.showMessage("Y", 0, 1);
    }
    public void onPressButtonA() {
    	gameHUD.showMessage("A", 0, 1);
    }
    public void onPressDpadUp() {
    	gameHUD.showMessage("DPAD UP", 0, 1);
    }
    public void onPressDpadDown() {
    	gameHUD.showMessage("DPAD DOWN", 0, 1);
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
		ShurikenDiana[] dianas = {diana0, diana1, diana2, diana3};
		if(currentDiana >= 0 && currentDiana < dianas.length){
			dianas[currentDiana].hide();
		}
		roundCounter++;
		enemyCounterHUD.setEnemiesLeft(maxRounds - roundCounter + 1);
		if(roundCounter == maxRounds + 1){
			String msg = "Your score: "+score+"/100\nGame over";
			gameHUD.showMessage(msg, 2.5f, 10);
		}else{
			gameHUD.showMessage("Next round!", 0, 1);
			int rndDiana = currentDiana;
			while (rndDiana == currentDiana)
				currentDiana = new Random().nextInt(4);
			dianas[currentDiana].show();
			button0Enabled = true;
			Log.d("Bruno", "Button0 enabled");
		}
	}
	
	public static int getScore() {
	    // TODO Auto-generated method stub
	    return 1;
	}

	public static int getStamp(int score2) {
	    // TODO Auto-generated method stub
	    return 0;
	}

	public static int getTimeScore() {
	    // TODO Auto-generated method stub
	    return 0;
	}

	public static int getPrecissionScore() {
	    // TODO Auto-generated method stub
	    return 0;
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