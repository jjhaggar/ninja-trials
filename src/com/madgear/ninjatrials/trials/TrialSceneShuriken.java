package com.madgear.ninjatrials.trials;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.align.HorizontalAlign;

import android.util.Log;

import com.madgear.ninjatrials.GameScene;
import com.madgear.ninjatrials.ResultLoseScene;
import com.madgear.ninjatrials.ResultWinScene;
import com.madgear.ninjatrials.hud.GameHUD;
import com.madgear.ninjatrials.hud.ShurikenEnemyCounter;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.test.MusicTest;
import com.madgear.ninjatrials.test.TestingScene;
import com.madgear.ninjatrials.trials.shuriken.ShurikenCoordinates;
import com.madgear.ninjatrials.trials.shuriken.ShurikenEnemy;
import com.madgear.ninjatrials.trials.shuriken.ShurikenHands;

/**
 * Trial Scene Shuriken V2 for Ninja Trials
 * 
 * @author Madgear Games
 * @version 0.1 16-Oct-2013
 */
public class TrialSceneShuriken extends GameScene{
	
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRENHEIGHT = ResourceManager.getInstance().cameraHeight;
	private int AllowedImpactsOnPlayer = 1;
	private int impactsOnPlayer = 0;
	private ShurikenEnemyCounter shurikenEnemyCounterHUD;
	private int enemyCount = 10;
	private int enemiesLeft = enemyCount;
	private int enemyInsertionInterval = 5; // seconds
	private float enemySpeed = 0.25f; // % of horizontal screen size per second
	private int enemyLifes = 1;
	private float shurikenSpeed = 0.5f; // % of vertical screen size per second
	private int currentImpactsOnPlayer = 0;
	private int enemiesDefeated = 0;
	private int shurikensLaunched = 0;
	private float gameStartTime;
	private float gameEndTime;
	IUpdateHandler trialUpdateHandler;
	private GameHUD gameHUD;
	private boolean readyShow = false;
	private boolean gameStarted = false;
	private boolean gameFinished = false;
	private float timeScore;
	private float precissionScore;
	private float score;
	private float maxTime = 100; // seconds to get a 0 time score
	private ShurikenHands hands;
	private ArrayList<ShurikenEnemy> enemies;
	private int shurikenAnimationCounter;
	
	public TrialSceneShuriken(){
		super(1f);
	}
	
	/**
	 * Loading scene
	 */
	@Override
	public Scene onLoadingScreenLoadAndShown() {
		Scene loadingScene = new Scene();		
		loadingScene.getBackground().setColor(.15f, .15f, .15f);		
        loadingScene.attachChild(getLoadingText("SHURIKEN TRIAL V2"));        
		return loadingScene;
	}

	@Override
	public void onLoadingScreenUnloadAndHidden() {}

	/**
	 * Shows background.
	 * Creates gamehud.
	 * Creates hands.
	 */
	@Override
	public void onLoadScene() {
		// TODO inicializar y mostrar shurikenEnemyCounter
		ResourceManager.getInstance().loadShurikenSceneResources();
		setBackground(getBG());
		gameHUD = new GameHUD();
		shurikenEnemyCounterHUD = new ShurikenEnemyCounter(SCRNWIDTH*.84f, SCRENHEIGHT*.92f, enemiesLeft);
		hands = new ShurikenHands();
	}
	
	/**
	 * Sets the gamehud.
	 * Attaches hands.
	 * calls start()
	 */
	@Override
	public void onShowScene() {		
		ResourceManager.getInstance().engine.getCamera().setHUD(gameHUD);
		attachChild(hands);
		attachChild(shurikenEnemyCounterHUD);
		start();
	}

	@Override
	public void onHideScene() {}

	@Override
	public void onUnloadScene() {
		ResourceManager.getInstance().unloadShurikenSceneResources();
	}
	
	@Override
	public void attachChild(final IEntity pEntity) {
	    // manually set index
	    if (this.mChildren != null) {
	        pEntity.setZIndex(this.mChildren.size());
	        Log.d("Bruno", "Attaching child with ZIndex "+this.mChildren.size());
	    }
	    super.attachChild(pEntity);
	    sortEnemiesZIndex();
	}
	
	/**
	 * Sorts ZIndexes of enemies.
	 */
	private void sortEnemiesZIndex() {
		if (enemies != null && enemies.size() > 1) {
			if (enemies.size() == 5) {
				Log.d("Bruno", "Before: "+enemies.get(0).getZIndex()+", "+enemies.get(1).getZIndex()+", "+enemies.get(2).getZIndex()+", "+enemies.get(3).getZIndex()+", "+enemies.get(4).getZIndex()+".");
			}
			int temp = enemies.get(enemies.size() - 1).getZIndex();			
			for (int i = enemies.size() - 1; i > 0; i--) {
				// machacar el i con el i-1
				enemies.get(i).setZIndex(enemies.get(i - 1).getZIndex());
				// http://hawaiiantime.jp/blog/58/
			}
			enemies.get(0).setZIndex(temp);
			sortChildren();
			if (enemies.size() == 5) {
				Log.d("Bruno", "After: "+enemies.get(0).getZIndex()+", "+enemies.get(1).getZIndex()+", "+enemies.get(2).getZIndex()+", "+enemies.get(3).getZIndex()+", "+enemies.get(4).getZIndex()+".");
			}
		}		
	}
	
	/**
	 * Shows Ready and Go messages and then starts the game.
	 */
	private void start() {
		gameStartTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		trialUpdateHandler = new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
            	float waitPeriodForReadyMsg = 2f;
            	float waitPeriodForGoMsg = 5f;
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                gameStartTime + waitPeriodForReadyMsg) {
                	if (!readyShow){
                		readyShow = true;
                		gameHUD.showMessage("Ready?", 0, 2);
                    	Log.d("Bruno", "Ready?");
                	}
                }
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                gameStartTime + waitPeriodForGoMsg) {
                    TrialSceneShuriken.this.unregisterUpdateHandler(trialUpdateHandler);
                    gameHUD.showMessage("Go!", 0, 1);
                    Log.d("Bruno", "Go!");
                    gameStarted = true;
                    SFXManager.playMusic(ResourceManager.getInstance().trialShurikens);
                    generateEnemies();
                    checkEnemiesStatus();
                    gameStartTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
                }
            }
            @Override public void reset() {}
        };
        registerUpdateHandler(trialUpdateHandler);
	}
	
	/**
	 * Adds an enemy every enemyInsertionInterval seconds.
	 */
	private void generateEnemies() {
		if (enemies == null){
			enemies = new ArrayList<ShurikenEnemy>(enemyCount);
			ShurikenEnemy enemy = new ShurikenEnemy(1, enemySpeed, 'l');
			enemies.add(enemy);
			attachChild(enemy);
			Log.d("Bruno", "New enemy generated with zIndex"+enemy.getZIndex()+", there are "+enemies.size()+" enemies ("+enemyCount+" max).");
		}
		Timer timer = new Timer();
		timer.schedule(new EnemyGenerator(), enemyInsertionInterval*1000);
	}
	
	/**
	 * Aux. class for generateEnemies()
	 */
	private class EnemyGenerator extends TimerTask {

		@Override
		public void run() {
			ShurikenEnemy enemy = new ShurikenEnemy(1, enemySpeed, 'l');
			enemies.add(enemy);
			attachChild(enemy);
			Log.d("Bruno", "New enemy generated with Z-Index "+enemy.getZIndex()+", there are "+enemies.size()+" enemies ("+enemyCount+" max).");
			if (!gameFinished && enemies.size() < enemyCount){
				generateEnemies();
			}			
		}
		
	}
	
	/**
	 * Checks enemies current status every checkInterval seconds.
	 * The following cases are checked:
	 * - The enemy has impacted on player.
	 * - The enemy has been defeated.
	 * - There are no enemies left.
	 */
	private void checkEnemiesStatus() {
		float checkInterval = .5f;
		Timer timer = new Timer();
		timer.schedule(new enemyStatusChecker(), (int)(checkInterval*1000));
	}
	
	/**
	 * Aux. class for checkEnemiesStatus()
	 */
	private class enemyStatusChecker extends TimerTask {

		@Override
		public void run() {
			for (ShurikenEnemy enemy: enemies) {
				if (enemy.hasHitPlayer()) {
					enemy.hide();
					enemiesLeft--;
					impactsOnPlayer++;
					if (impactsOnPlayer >= AllowedImpactsOnPlayer) {
						Log.d("Bruno", "The player has been impacted too many times.");
						gameOver();
					}
				}
				if (enemy.getLifes() <= 0) {
					enemy.hide();
					enemiesDefeated++;
					enemiesLeft--;
				}
			}
			if (enemiesLeft <= 0) {
				Log.d("Bruno", "There are no enemies left.");
				gameOver();
			}
			if (!gameFinished){
				checkEnemiesStatus();
			}			
		}		
	}
	
	/*
	 * Stops enemy generation and enemy status checking.
	 * Compute scores.
	 * Hide hands.
	 * Shows ResultWinScene or ResultsLoseScene
	 */
	private void gameOver() {
		Log.d("Bruno", "GameOver");
		gameFinished = true;
		SFXManager.pauseMusic(ResourceManager.getInstance().trialShurikens);
		gameEndTime = ResourceManager.getInstance().engine.getSecondsElapsedTotal();
		if (shurikensLaunched == 0) {
			precissionScore = 0;
		}
		else {
			precissionScore = enemiesDefeated / shurikensLaunched;
		}		
		timeScore = (maxTime - (gameEndTime - gameStartTime)) / maxTime;
		hands.hide();
		if (impactsOnPlayer >= AllowedImpactsOnPlayer) {
			SceneManager.getInstance().showScene(new ResultLoseScene());
		}
		else {
			SceneManager.getInstance().showScene(new ResultWinScene());
		}
	}
	
	private void checkForImpact() {
		boolean hit = false;
		float horizontalErrorMargin = .1f;
		float verticalErrorMargin = .2f;
		for (ShurikenEnemy enemy: enemies) {
			if (Math.abs(hands.getPosition().x - enemy.getPosition().x) < SCRNWIDTH * horizontalErrorMargin) {
				float timeToImpact = (enemy.getPosition().y - hands.getPosition().y) / (shurikenSpeed * SCRENHEIGHT);
				float enemyPredictedHorizontal;
				if (enemy.getDirection() == 'r'){
					enemyPredictedHorizontal = enemy.getPosition().y - enemySpeed * SCRNWIDTH * timeToImpact;
				}
				else {
					enemyPredictedHorizontal = enemy.getPosition().y + enemySpeed * SCRNWIDTH * timeToImpact;
				}
				if (Math.abs(hands.getPosition().y - enemyPredictedHorizontal) < SCRENHEIGHT * verticalErrorMargin) {
					hit = true;
				}
			}
			if (hit) {
				enemy.setLifes(enemy.getLifes() + 1);
				break;
			}
		}
		
	}
	
	/*
	 * Stops enemy generation and checking.
	 * Returns to TestingScene.
	 */
	@Override
    public void onPressButtonMenu() {
		gameFinished = true;
		SFXManager.pauseMusic(ResourceManager.getInstance().trialShurikens);
		SceneManager.getInstance().showScene(new TestingScene());
    }
	
	public void onPressButtonO() {
		hands.launch();
		if (gameStarted) {
			shurikensLaunched++;
			checkForImpact();
		}
	}
	public void onPressDpadLeft() {
		hands.moveLeft();
	}
    public void onPressDpadRight() {
    	hands.moveRight();
    }
    public void onReleaseDpadLeft() {
		hands.stop();
	}
    public void onReleaseDpadRight() {
    	hands.stop();
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
	
	/**
	 * Moves an Sprite through the screen using PathModifier
	 */
	public static void moveSprite(Sprite sprite, float fromX, float fromY, float toX, float toY, float seconds){
		Path p = new Path(2).to(fromX, fromY).to(toX, toY);
		PathModifier pathModifier = new PathModifier(seconds, p);
		sprite.registerEntityModifier(pathModifier);
	}
	
	public Text getLoadingText(String s){
		return new Text(
				SCRNWIDTH * 0.5f,
				SCRENHEIGHT * 0.5f,
                ResourceManager.getInstance().fontBig, s,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine
                .getVertexBufferObjectManager());
	}
	
	public SpriteBackground getBG(){
		ITextureRegion backgroundTextureRegion = ResourceManager.getInstance().shurikenBackground;
        Sprite backgroundSprite = new Sprite(SCRNWIDTH / 2, SCRENHEIGHT / 2, 
        		backgroundTextureRegion, ResourceManager.getInstance().engine
        		.getVertexBufferObjectManager());
        return new SpriteBackground(backgroundSprite);        
	}
}
