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
import com.madgear.ninjatrials.R;
import com.madgear.ninjatrials.ResultLoseScene;
import com.madgear.ninjatrials.ResultWinScene;
import com.madgear.ninjatrials.hud.GameHUD;
import com.madgear.ninjatrials.hud.ShurikenEnemyCounter;
import com.madgear.ninjatrials.managers.GameManager;
import com.madgear.ninjatrials.managers.ResourceManager;
import com.madgear.ninjatrials.managers.SFXManager;
import com.madgear.ninjatrials.managers.SceneManager;
import com.madgear.ninjatrials.managers.UserData;
import com.madgear.ninjatrials.test.MusicTest;
import com.madgear.ninjatrials.test.TestingScene;
import com.madgear.ninjatrials.trials.shuriken.ShurikenCoordinates;
import com.madgear.ninjatrials.trials.shuriken.ShurikenEnemy;
import com.madgear.ninjatrials.trials.shuriken.ShurikenHands;

/**
 * Trial Scene Shuriken for Ninja Trials
 * 
 * Task number: 44
 * Design document: section 3.12.4
 * @author Madgear Games
 *
 */
public class TrialSceneShuriken extends GameScene{
	
	private final float SCRNWIDTH = ResourceManager.getInstance().cameraWidth;
	private final float SCRNHEIGHT = ResourceManager.getInstance().cameraHeight;
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
	Timer generateEnemiesTimer;
	
	public TrialSceneShuriken(){		
		super(1f);
		generateEnemiesTimer = new Timer();
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
		shurikenEnemyCounterHUD = new ShurikenEnemyCounter(SCRNWIDTH*.84f, SCRNHEIGHT*.92f, enemiesLeft);
		hands = new ShurikenHands();
		hands.setZIndex(99);
	}
	
	/**
	 * Sets the gamehud.
	 * Attaches hands.
	 * calls start()
	 */
	@Override
	public void onShowScene() {		
	    
	    // Set trial in game manager:
	    GameManager.setCurrentTrial(GameManager.TRIAL_SHURIKEN);
	    
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
	
	/**
	 * Custom zIndex setting
	 */
	@Override
	public void attachChild(final IEntity pEntity) {
	    if (this.mChildren != null) {
	        pEntity.setZIndex(this.mChildren.size());
	    }
	    super.attachChild(pEntity);
	    sortEnemiesZIndex();
	    sortChildren();
	}
	
	/**
	 * Sorts ZIndexes of enemies.
	 */
	private void sortEnemiesZIndex() {
		if (enemies != null && enemies.size() > 1) {
			int temp = enemies.get(enemies.size() - 1).getZIndex();			
			for (int i = enemies.size() - 1; i > 0; i--) {
				enemies.get(i).setZIndex(enemies.get(i - 1).getZIndex());
			}
			enemies.get(0).setZIndex(temp);			
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
                		gameHUD.showMessage(ResourceManager.getInstance().loadAndroidRes().getString(R.string.trial_shuriken_ready), 0, 2);
                	}
                }
                if(ResourceManager.getInstance().engine.getSecondsElapsedTotal() >
                gameStartTime + waitPeriodForGoMsg) {
                    TrialSceneShuriken.this.unregisterUpdateHandler(trialUpdateHandler);
                    gameHUD.showMessage(ResourceManager.getInstance().loadAndroidRes().getString(R.string.trial_shuriken_go), 0, 1);
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
			ShurikenEnemy enemy = new ShurikenEnemy(1, enemySpeed);
			enemies.add(enemy);
			attachChild(enemy);
		}
		generateEnemiesTimer.schedule(new EnemyGenerator(), enemyInsertionInterval*1000);
	}
	
	/**
	 * Aux. class for generateEnemies()
	 */
	private class EnemyGenerator extends TimerTask {

		@Override
		public void run() {
			ShurikenEnemy enemy = new ShurikenEnemy(1, enemySpeed);
			enemies.add(enemy);
			attachChild(enemy);
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
		float checkInterval = .1f;
		Timer timer = new Timer();
		timer.schedule(new enemyStatusChecker(), (int)(checkInterval*1000));
	}
	
	/**
	 * Aux. class for checkEnemiesStatus()
	 */
	private class enemyStatusChecker extends TimerTask {

		@Override
		public void run() {	
			int tempEnemiesLeft = enemyCount;
			int tempEnemiesDefeated = 0;
			for (ShurikenEnemy enemy: enemies) {
				if (enemy.hasHitPlayer()) {
					enemy.hide();
					tempEnemiesLeft--;
					impactsOnPlayer++;
					if (impactsOnPlayer >= AllowedImpactsOnPlayer) {
						Log.d("Bruno", "The player has been impacted too many times.");
						gameOver();
					}
				}
				if (enemy.getLifes() <= 0) {					
				    tempEnemiesLeft--;
				    tempEnemiesDefeated++;
				}
			}
			enemiesDefeated = tempEnemiesDefeated;
			enemiesLeft = tempEnemiesLeft;
			shurikenEnemyCounterHUD.setEnemiesLeft(enemiesLeft);
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
        // Save Achievements:
        UserData.saveAchiev(ResourceManager.getInstance().context);
        
		Log.d("Bruno", "GameOver");
		gameFinished = true;
		generateEnemiesTimer.cancel();
		SFXManager.stopMusic(ResourceManager.getInstance().trialShurikens);
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
		Log.d("Bruno", "Checking for impact");
		boolean hit = false;
		float horizontalPreliminaryErrorMargin = .1f;
		float horizontalErrorMargin = .1f;
		for (ShurikenEnemy enemy: enemies) {
			if (enemy.getLifes() > 0) {
				if (Math.abs(hands.getPosition().x - enemy.getPosition().x) < SCRNWIDTH * horizontalPreliminaryErrorMargin) {
					Log.d("Bruno", "Hands are aligned horizontally with an enemy (H, E)=("+hands.getPosition().x+", "+enemy.getPosition().x+") and it is moving "+enemy.getDirection());
					/*
					float timeToImpact = (enemy.getPosition().y - hands.getPosition().y) / (shurikenSpeed * SCRNHEIGHT);
					Log.d("Bruno", "Predicted time to hypothetical impact is "+timeToImpact);
					float enemyPredictedHorizontal;
					if (enemy.getDirection() == 'r') {
						enemyPredictedHorizontal = enemy.getPosition().x + enemySpeed * SCRNWIDTH * timeToImpact;
						Log.d("Bruno", "(r)By then, the enemy will be at "+enemyPredictedHorizontal);
					}
					else if (enemy.getDirection() == 'l') {
						enemyPredictedHorizontal = enemy.getPosition().x - enemySpeed * SCRNWIDTH * timeToImpact;
						Log.d("Bruno", "(l)By then, the enemy will be at "+enemyPredictedHorizontal);
					}
					else {
						enemyPredictedHorizontal = enemy.getPosition().x;
						Log.d("Bruno", "(n)By then, the enemy will be at "+enemyPredictedHorizontal);
					}

					if (enemy.getDirection() != 'i' && Math.abs(hands.getPosition().x - enemyPredictedHorizontal) < SCRNWIDTH * horizontalErrorMargin) {
						hit = true;
						Log.d("Bruno", "Enemy hit!");
					}
					*/
					hit = true;
				}
				if (hit) {
				    
				    // TODO: controlar si se ha muerto o no.
                    // Achievement 2:
                    if(!GameManager.player1achiev.achievements[1].isCompleted()) {
                        GameManager.player1achiev.achievements[1].progressIncrement(1);
                        if(GameManager.player1achiev.achievements[1].isCompleted()) {
                            gameHUD.showAchievementCompleted(2);
                            GameManager.player1achiev.unlock(2);
                        }
                    }
				    
					enemy.hit();
					break;
				}
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
	
	/**
	 * The player launches a shuriken, but only in the case that
	 * the game already started it will be counted and checked for impact.
	 */
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
    	Log.d("Bruno", "DpadRight pressed.");
    }
    public void onReleaseDpadLeft() {
		hands.stop();
	}
    public void onReleaseDpadRight() {
    	hands.stop();
    	Log.d("Bruno", "DpadRight released.");
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
				SCRNHEIGHT * 0.5f,
                ResourceManager.getInstance().fontBig, s,
                new TextOptions(HorizontalAlign.CENTER),
                ResourceManager.getInstance().engine
                .getVertexBufferObjectManager());
	}
	
	public SpriteBackground getBG(){
		ITextureRegion backgroundTextureRegion = ResourceManager.getInstance().shurikenBackground;
        Sprite backgroundSprite = new Sprite(SCRNWIDTH / 2, SCRNHEIGHT / 2, 
        		backgroundTextureRegion, ResourceManager.getInstance().engine
        		.getVertexBufferObjectManager());
        return new SpriteBackground(backgroundSprite);     
	}
}
